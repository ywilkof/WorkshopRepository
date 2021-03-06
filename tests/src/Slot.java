


/**
 * Represents a fixed empty space for a photo in a template
 * @author yonatan
 *
 */
public class Slot{

	private PixelPoint topLeft;
	private PixelPoint bottomRight;
	private PixelPoint topRight;
	private PixelPoint bottomLeft;

	private boolean horizontal;	
	private Photo photo = null; // the photo that fills the slot

	public Slot(PixelPoint topLeft, PixelPoint bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.topRight = new PixelPoint(bottomRight.getX(), topLeft.getY());
		this.bottomLeft = new PixelPoint(topLeft.getX(), bottomRight.getY());
		this.horizontal = (getWidth() > getHeight()); 
	}

	public void assignToPhoto(Photo photo) {
		if (photo != null) {
			this.photo = photo;
		}
	}

	public boolean isAssignedToPhoto() {
		return (photo != null);
	}

	public PixelPoint getTopLeft() {
		return this.topLeft;
	}

	public PixelPoint getTopRight() {
		return this.topRight;
	}

	public PixelPoint getBottomLeft() {
		return this.bottomLeft;
	}

	public PixelPoint getBottomRight() {
		return this.bottomRight;
	}

	public boolean isHorizontal() {
		return this.horizontal;
	}

	public double getWidth() {
		return Math.abs(bottomRight.distanceFrom(new PixelPoint(topLeft.getX(), bottomRight.getY())));
	}

	public double getHeight() {
		return Math.abs(bottomRight.distanceFrom(new PixelPoint(bottomRight.getX(), topLeft.getY())));
	}

}
