package ActivationManager;

public class DedicatedRequest {

	private int horizontalNeeded = 0;
	private int verticalNeeded = 0;
	private int eventsNeeded = 0;
	
	public int getHorizontalNeeded() {
		return horizontalNeeded;
	}

	public void setHorizontalNeeded(int horizontalNeeded) {
		this.horizontalNeeded = horizontalNeeded;
	}

	public int getVerticalNeeded() {
		return verticalNeeded;
	}

	public void setVerticalNeeded(int verticalNeeded) {
		this.verticalNeeded = verticalNeeded;
	}

	public int getEventsNeeded() {
		return eventsNeeded;
	}

	public void setEventsNeeded(int eventsNeeded) {
		this.eventsNeeded = eventsNeeded;
	}

	public DedicatedRequest() {
	}
}