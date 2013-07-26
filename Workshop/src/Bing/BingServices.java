package Bing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import Common.BoundingBox;
import Common.Point;

public class BingServices {

	public static void getStaticMap(Point[] points) {

		StaticMap map = new StaticMap();

		//get jpeg
		map.setJpgPath(getStaticMapOrMetadataFile(map, false, points));
		// get metadata
		map.setMetadataPath(getStaticMapOrMetadataFile(map, true, points));

		File xmlFile = new File(map.getMetadataPath());
		fillStaticMapWithData(xmlFile, map);
		System.out.println("�ds");
	}


	private static String getStaticMapOrMetadataFile(StaticMap map, boolean metadata, Point[] points) {
		File file = null;
		try {

			URL                 url;
			URLConnection   urlConn;
			DataOutputStream    printout;
			DataInputStream     input;

			//Make the actual connection
			if (metadata) {
				url = new URL("http://dev.virtualearth.net/REST/v1/Imagery/Map/AerialWithLabels?mmd=1&o=xml&mapSize=700,600&dcl=1&key=AjuPzlE1V8n1TJJK7T7elqCZlfi6wdLGvjyYUn2aUsNJ5ORSwnc-ygOwBvTa9Czt");
			}
			else {
				url = new URL("http://dev.virtualearth.net/REST/v1/Imagery/Map/AerialWithLabels?mmd=0&mapSize=700,600&dcl=1&key=AjuPzlE1V8n1TJJK7T7elqCZlfi6wdLGvjyYUn2aUsNJ5ORSwnc-ygOwBvTa9Czt");
			}
			urlConn = url.openConnection();
			urlConn.setDoInput (true);
			urlConn.setDoOutput (true);
			urlConn.setUseCaches (false);
			urlConn.setRequestProperty("Content-Type", "text/plain");
			urlConn.setRequestProperty("charset",  "charset=utf-8");

			StringBuilder builder = new StringBuilder();

			for (Point point : points)  {
				builder.append("pp=");
				builder.append(point.toString());
				builder.append(";14;\r\n");
			}

			String strContent = builder.toString();

			urlConn.setRequestProperty("Content-Length", new Integer(strContent.getBytes().length).toString()); 
			printout = new DataOutputStream (urlConn.getOutputStream ());
			printout.writeBytes (strContent);
			printout.flush ();

			// Get response
			input = new DataInputStream (urlConn.getInputStream());
			int bt = -1;
			if (!metadata) {
				// TODO: make jpg data work with imageIO and not with file
				file = new File("./moshe.jpg");
			}
			else {
				file = new File("./moshe.xml");
			}

			FileOutputStream fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			while ((bt = input.read()) > -1) {
				fop.write(bt);	
			}

			if (!metadata) {
				map.setJpgPath(file.getPath());
			}
			else {

			}

			fop.flush();
			fop.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

		return file.getPath();
	}

	private static void fillStaticMapWithData(File xmlFile, StaticMap map) {

		SAXBuilder builder = new SAXBuilder();



		Document document = null;
		try {
			document = (Document) builder.build(xmlFile);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootNode = document.getRootElement();
		Namespace namespace = rootNode.getNamespace();
		
		try {
		
		Element metaNode = rootNode.getChild("ResourceSets",namespace);
		metaNode = metaNode.getChild("ResourceSet",namespace).getChild("Resources", namespace).getChild("StaticMapMetadata", namespace);

		Element node = metaNode.getChild("BoundingBox",namespace);

		double SouthLatitude = new Double(node. getChildText("SouthLatitude",namespace)); // "SouthLatitude"
		double WestLongitude = new Double(node. getChildText("WestLongitude",namespace)); // "WestLongitude"
		double NorthLatitude = new Double(node. getChildText("NorthLatitude",namespace)); // "NorthLatitude"
		double EastLongitude = new Double(node. getChildText("EastLongitude",namespace)); // "EastLongitude"


		BoundingBox box = new BoundingBox(new Point(NorthLatitude, WestLongitude), 
				new Point(EastLongitude, SouthLatitude));
		map.setBox(box);

		node = metaNode.getChild("MapCenter", namespace);
		double latitude = new Double(node.getChildText("Latitude", namespace));
		double longitude = new Double(node.getChildText("Longitude", namespace));

		Point center = new Point(latitude, longitude);
		map.setCenterPoint(center);

		Element pushpinsNode =  metaNode.getChild("Pushpins", namespace);

		for (Element pushpinNode : pushpinsNode.getChildren()) {

			node = pushpinNode.getChild("Point", namespace);
			latitude = new Double(node.getChildText("Latitude", namespace));	
			longitude = new Double(node.getChildText("Longitude", namespace)); 

			node = pushpinNode.getChild("Anchor", namespace);
			int ax =  new Integer(node.getChildText("X", namespace));	
			int ay =  new Integer(node.getChildText("Y", namespace));	

			node = pushpinNode.getChild("TopLeftOffset", namespace);
			int tx =  new Integer(node.getChildText("X", namespace));	
			int ty =  new Integer(node.getChildText("Y", namespace));	

			node = pushpinNode.getChild("BottomRightOffset", namespace);
			int bx =  new Integer(node.getChildText("X", namespace));	
			int by =  new Integer(node.getChildText("Y", namespace));	

			map.addPushpin(new Pushpin(new Point(latitude,longitude),
					new int[] {ax, ay},
					new int[] {tx, ty},
					new int[] {bx, by}));
		}
		}
		catch (NullPointerException exception) {
			// TODO: handle null (nodes that are not found)
		}
	}
}

