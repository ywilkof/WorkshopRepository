package Common;

import java.util.Date;

public class Photo implements Comparable<Photo> {

	private Date takenDate;
	private Point location;


	private ActualEvent parentActualEvent;
	private boolean isHorizontal;
	private int height;
	private int width;
	
	//TODO private Image photo;

	public Photo(Date date, int width, int height, Point location, boolean horizontal) {
		this.takenDate = date;
		this.location = location;
		this.height = height;
		this.width = width;
		// TODO: get isHorizontal argument when using the EXIFInterface from android, and not calculate it
		this.isHorizontal = (width > height);
		
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
	
	public Date getTakenDate() {
		return this.takenDate;
	}
	
	public void attachToEvent(ActualEvent event) {
		if (event == null)
			parentActualEvent = event;
	}
	
	public double distanceFrom(Photo otherPhoto) {
		return this.getLocation().distanceFrom(otherPhoto.getLocation());
	}
	
	public int timeDeltaInSecondsFrom(Photo otherPhoto) {
		return 0;

	}

	@Override
	public int compareTo(Photo o) {
		return this.takenDate.compareTo(o.takenDate);
	}
	
	
}