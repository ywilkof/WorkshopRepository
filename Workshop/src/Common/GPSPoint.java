package Common;

import java.text.DecimalFormat;

import android.location.Location;

public class GPSPoint  {

	private double longitude;
	private double latitude;

	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	
	public GPSPoint(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/**
	 * method to return real distance between two GPS Points
	 * @param p
	 * @return
	 */
	public float distanceFrom(GPSPoint p) {
		float[] results = new float[3]; 
		// Using Android's Location.distanceBetween to ensure that calculation of distance is more accurate 
		Location.distanceBetween(this.latitude, this.longitude, p.getLatitude(), p.getLongitude(), results);
		return results[0];
	}
	
	@Override
	public String toString() {
		return (latitude + "," + longitude);
	}
	/**
	@Override
	public boolean equals(Object other)
	{ 
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof GPSPoint))
			return false;
		GPSPoint otherGPSPoint = (GPSPoint) other;
		
		double roundedThisLong = roundDouble(longitude) ;
		double roundedThisLat =  roundDouble(latitude);
		double roundedOtherLong = roundDouble(otherGPSPoint.getLongitude());
		double roundedOtherLat = roundDouble(otherGPSPoint.getLatitude());
		return (otherGPSPoint.distanceFrom(this) < 2);
		
		/**
		// because of accuracy bugs of double number, we will consider only 7 digits after dot
		return ((roundedOtherLat == roundedThisLat) && (roundedOtherLong == roundedThisLong));
		
	}
	 **/
	
	private double roundDouble (double num)
	{
		return (Math.floor(num * 10000000) / 10000000);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GPSPoint))
			return false;
		GPSPoint other = (GPSPoint) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
	
}
