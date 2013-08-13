package com.example.aworkshop;

import java.io.File;

import org.joda.time.DateTime;

import ActivationManager.SmartModeService;
import PhotoListener.PhotoListenerThread;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;

public class SettingsActivity extends Activity {
	
	// static final fields
	public static final File ROOT = new File(Environment.getExternalStorageDirectory(), "DCIM");
	//		File dataDirectory = new File(root + "/DCIM/Camera/");
	private static final String  PHOTO_DIR = ROOT + File.separator + "Camera" + File.separator;

	// global fields
	PhotoListenerThread observer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	
		observer = new PhotoListenerThread(PHOTO_DIR); // observer over the gallery directory
		
		observer.startWatching();
		SmartModeService.startService();
//		File directory = new File("E:/Pictures");
//		if (!directory.exists())
//			return;
//		File[] arrayOfPic =  directory.listFiles();
//		Photo tempPhoho = null;
//		List<Photo> photosToCluster = new LinkedList<Photo>(); 
//		for (File file : arrayOfPic)
//		{
//			try
//			{
//				tempPhoho = PhotoListenerThread.createPhotoFromFile(file.getAbsolutePath());
//			}
//			catch (Exception ex)
//			{
//			}
//			if (tempPhoho != null)
//				photosToCluster.add(tempPhoho);
//		}
//
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public DateTime getScheduledTime() {
		return null;
	}

}