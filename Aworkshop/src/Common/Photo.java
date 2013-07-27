package Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class Photo implements Comparable<Photo> {

	private DateTime takenDate;
	private Point location;
	private boolean isHorizontal;
	private int height;
	private int width;
	private String path; // 
	private ActualEvent parentActualEvent;


	//TODO private Image photo;

	public Photo(Date date, int width, int height, Point location, boolean horizontal, String path) {
		this.takenDate = new DateTime(date);
		this.location = location;
		this.height = height;
		this.width = width;
		// TODO: get isHorizontal argument when using the EXIFInterface from android, and not calculate it
		this.isHorizontal = (width > height);
		this.path = path;

	}
	
	public File getPhotoFile() throws FileNotFoundException {
		File file = null;
		file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		return file;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public Point getLocation() {
		return location;
	}

	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	public DateTime getTakenDate() {
		return this.takenDate;
	}

	public void attachToEvent(ActualEvent event) {
		if (event == null)
			parentActualEvent = event;
	}

	// TODO: Use google Location DistanceTO
	public double distanceFrom(Photo otherPhoto) {
		return this.getLocation().distanceFrom(otherPhoto.getLocation());
	}

	public int timeDeltaInSecondsFrom(Photo otherPhoto) {
		DateTime thisTime = new DateTime(takenDate);
		DateTime otherTime = new DateTime(otherPhoto.getTakenDate());

		return Math.abs(Seconds.secondsBetween(otherTime,thisTime).getSeconds());
	}

	@Override
	public int compareTo(Photo o) {
		return this.takenDate.compareTo(o.takenDate);
	}


}
