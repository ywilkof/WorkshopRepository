package PhotoListener;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ActivationManager.ActivationManagerThread;
import Bing.BingServices;
import Common.Photo;
import Common.Point;

public class ListenerTestCases {

	@Test
	public void extractionTest() {

		PhotoListenerThread t = new PhotoListenerThread();

		File directory = new File("/Users/yonatan/Documents/workspace/WorkshopRepository/photos/");
		for (File file : directory.listFiles()) {
			Photo p = null;

			p = t.createPhotoFromFile(file);

			if (p != null) {
				ActivationManagerThread.getInstance().addToBuffer(p);
			}
		}

		ActivationManagerThread.getInstance().processPhotoBuffer();
	}

	@Test
	public void bingTest() {
		FileReader reader = null;
		try {
			reader = new FileReader(new File("/Users/yonatan/Documents/workspace/WorkshopRepository/photos/locationList.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bw = new BufferedReader(reader);
		String line;
		Point[] points = new Point[47];
		int i =0;
		try {
			while ((line = bw.readLine()) != null) {
				String[] partsString = line.split(",");
				points[i] = (new Point(new Double(partsString[0]), new Double(partsString[1])));
				i++;
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BingServices.getStaticMap(points);
	}
}
