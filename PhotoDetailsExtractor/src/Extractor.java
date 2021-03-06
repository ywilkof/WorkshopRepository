import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;


public class Extractor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			// file to write to
			File outFile = new File("/Users/yonatan/Documents/workspace/WorkshopRepository/Photos/locationList.txt");

			// if file doesnt exists, then create it
			if (!outFile.exists()) {
				outFile.createNewFile();
			}

			FileWriter	fw = new FileWriter(outFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			File directory = new File("/Users/yonatan/Documents/workspace/WorkshopRepository/photos/");
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


				bw.write(location.toString());
				bw.newLine();
			}


			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		}
	}
}
