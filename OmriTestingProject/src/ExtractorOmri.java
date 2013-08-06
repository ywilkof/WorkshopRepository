import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;




public class ExtractorOmri {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<Photo> photosForClusteringList = new LinkedList<Photo>();
		try {
			// file to write to
			
			File directory = new File("C:/Users/omri/Documents/Omri/Workshop/WorkshopRepository/photos");
			if (!directory.exists())
			{
				System.out.println("No such directort");
				return;
			}
			for (File file : directory.listFiles()) {


				// extract photo metadata
				Metadata metadata = null;
				try {
					metadata = ImageMetadataReader.readMetadata(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ImageProcessingException e) {
					continue;
				}

				//get location
				GpsDirectory directory1 = metadata.getDirectory(GpsDirectory.class);

				GeoLocation location = directory1.getGeoLocation();
				if (location == null) { // photo has not location
					continue;
				}
				ExifSubIFDDirectory directory2 = metadata.getDirectory(ExifSubIFDDirectory.class);

				Date date = directory2.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

				Photo photo = null;
				JpegDirectory jpgDirectory = metadata.getDirectory(JpegDirectory.class);
				try {
					int width = jpgDirectory.getImageWidth();
					int	height = jpgDirectory.getImageHeight();

					 photo = new Photo(
							date,
							width,
							height,
							new Point(location.getLatitude(),location.getLongitude()),
							null);
					 photosForClusteringList.add(photo);
				}
				catch (Exception e)
				{
					System.out.println("Error while parsing metaData");
				}
			}
			System.out.println("Done");

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		}
		int size = photosForClusteringList.size();
		if ( size > 10)
		{
			DBScan dbScan = new DBScan(photosForClusteringList);
			List<Cluster> returnList = dbScan.runAlgorithmClusters();
			System.out.println("Stop");
		}
	}
}