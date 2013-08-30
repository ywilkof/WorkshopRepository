package PhotoListener;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

import Common.GPSPoint;
import Common.Photo;
import Common.PhotoContainer;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

/**
 * The thread that observes the camera's folder
 * @author yonatan
 *
 */
public class PhotoListenerThread extends FileObserver {

	private final String TAG = PhotoListenerThread.class.getName();
	String absolutePath;

	public PhotoListenerThread(String path) {
		super(path, FileObserver.CLOSE_WRITE | FileObserver.DELETE);
		this.absolutePath = path;
	}

	@Override
	public void onEvent(int event, String path) {

		if (isExternalStorageReadable()) {
			if (path.endsWith(".jpg")) {

				Photo photo = null;
				String file = absolutePath + path;

				if ((event & FileObserver.CLOSE_WRITE) > 0) {
					try {
						photo = createPhotoFromFile(file);
						Log.d(TAG, "Photo taken: " + file + " was read from file");

					} catch (ImageProcessingException e) {
						Log.e(TAG, "Photo taken: " + "was not yet fully saved properly");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try { // try reading again
							photo = createPhotoFromFile(file);
						} catch (ImageProcessingException e1) {
							Log.e(TAG, "Photo taken: " + "was NOT read from file properly");
						}
					}
					
					if (photo != null)
						PhotoContainer.getInstance().addToBuffer(photo);
				}
				
				if ((event & FileObserver.DELETE) > 0) {
					PhotoContainer.getInstance().onDelete(file);
				}
			}
		}
		else {
			Log.wtf(TAG,"External storage is not readable!");
		}
	}

	public static Photo createPhotoFromFile(String file) throws ImageProcessingException {

		Photo photo = null;

		File path = new File(file);

		// extract photo metadata
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(path);
		} catch (IOException e) {
			throw new ImageProcessingException(e);
		}

		//get location
		GpsDirectory directory1 = metadata.getDirectory(GpsDirectory.class);

		GeoLocation location = directory1.getGeoLocation();
		if (location == null) { // photo has no location
			return null;
		}

		//get time
		ExifSubIFDDirectory directory2 = metadata.getDirectory(ExifSubIFDDirectory.class);

		Date date = directory2.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);


		//get dimensions
		JpegDirectory jpgDirectory = metadata.getDirectory(JpegDirectory.class);
		try {
			int width = jpgDirectory.getImageWidth();
			int	height = jpgDirectory.getImageHeight();

			photo = new Photo(
					date,
					width,
					height,
					new GPSPoint(location.getLatitude(),location.getLongitude()),
					path.getPath());
		} catch (MetadataException e) {
			// TODO ERROR reading EXIF details of photo
			e.printStackTrace();
		}

		return photo;
	}

	/* Checks if external storage is available to at least read */
	private static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
