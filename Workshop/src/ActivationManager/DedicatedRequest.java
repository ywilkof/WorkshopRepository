package ActivationManager;

/**
 * Represents a Request to change Mode of Activation Manager when in need of more horizontal/vertical/events
 * @author yonatan
 *
 */
public class DedicatedRequest {

	private int horizontalNeeded = 0;
	private int verticalNeeded = 0;
	private int eventsNeeded = 0;
	
	public int getHorizontalNeeded() {
		return horizontalNeeded;
	}

	/**
	 * set number of needed horizontal photos
	 * @param horizontalNeeded
	 */
	public void setHorizontalNeeded(int horizontalNeeded) {
		this.horizontalNeeded = horizontalNeeded;
	}

	public int getVerticalNeeded() {
		return verticalNeeded;
	}

	/**
	 * set number of needed horizontal photos
	 * @param verticalNeeded
	 */
	public void setVerticalNeeded(int verticalNeeded) {
		this.verticalNeeded = verticalNeeded;
	}

	public int getEventsNeeded() {
		return eventsNeeded;
	}

	/**
	 * set number of needed events
	 * @param eventsNeeded
	 */
	public void setEventsNeeded(int eventsNeeded) {
		this.eventsNeeded = eventsNeeded;
	}

	public DedicatedRequest() {
	}
}
