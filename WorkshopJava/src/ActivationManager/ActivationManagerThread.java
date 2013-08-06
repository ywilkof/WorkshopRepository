package ActivationManager;

import java.util.Queue;

import org.apache.http.client.fluent.Request;

import Common.Photo;

public class ActivationManagerThread {

	private static final ActivationManagerThread instance = new ActivationManagerThread();

	//states
	private static final int REGULAR_MODE = 0;
	private static final int DEDICATED_MODE = 1;

	private static final int BUFFER_SIZE = 100;

	// TODO: maybe do this by number of photos for collage
	private static final int CANDIDATE_EVENTS_FOR_COLLAGE = 5;
	private static final int NEW_CANDIDATE_THRESHOLD_DELTA = 30;

	//instance fields
	private Queue<Photo> buffer = new LimitedLinkedList<Photo>(BUFFER_SIZE);
	private int currentState = 0; // start in REGULAR_MODE;
	private int remainingEvents = CANDIDATE_EVENTS_FOR_COLLAGE;
	private int remainingHorizontal = 0;
	private int remainingVertical = 0;


	private ActivationManagerThread() {
	}

	public static ActivationManagerThread getInstance() {
		return instance;
	}

	private boolean isNewEventCandidate(Photo newPhoto) {
		Photo lastPhoto = EventCandidateContainer.getInstance().getLastAddedEvent().getLastAddedPhoto();
		int delta = lastPhoto.timeDeltaInSecondsFrom(newPhoto);
		return (delta > NEW_CANDIDATE_THRESHOLD_DELTA) ? true : false;
	}

	private boolean isCollageNeeded() {
		return (((currentState == DEDICATED_MODE || currentState == REGULAR_MODE)) && (remainingEvents == 0) || // currentState == DEDICATED_MODE || currentState == REGULAR_MODE
				(currentState == DEDICATED_MODE && 
				((remainingHorizontal == 0) ||
						(remainingVertical == 0)))); 
	}
	/**
	 * 
	 * @param photo
	 * @return TRUE if next module should be awakened
	 */
	private boolean processPhoto(Photo photo) {


		EventCandidate event = null;
		if (EventCandidateContainer.getInstance().isEmpty() || isNewEventCandidate(photo)) {  // new event
			event = new EventCandidate(photo);
			EventCandidateContainer.getInstance().addEvent(event);

			if (remainingEvents > 0) { 
				remainingEvents--;
			}
		}
		else  { // add photo to last added event in container
			event = EventCandidateContainer.getInstance().getLastAddedEvent();
			if (!event.isPhotoInEvent(photo)) {
				event.addPhoto(photo);
			}
			else {
				// TODO: handle this situation that should not happen
			}
		}

		if (currentState == DEDICATED_MODE && photo.isHorizontal()) {
			remainingHorizontal--;
		}
		if ((currentState == DEDICATED_MODE && !photo.isHorizontal())) {
			remainingVertical--;
		}

		if (isCollageNeeded()) {
			setToRegularMode(); // upon decision to create collage, resume REGULAR_MODE
			return true;
		}
		else 
			return false;
	}

	public synchronized boolean processPhotoBuffer() {
		boolean ret = false;
		while (!buffer.isEmpty()) { 
			if (buffer.peek() != null) 
				processPhoto(buffer.poll());
		}
		return ret;

	}

	public synchronized void addToBuffer(Photo p) {
		buffer.add(p);
	}

	public boolean consumeDedictedRequest(DedicatedRequest request) {
		// make sure dedicated request has information
		if ((request.getEventsNeeded() != 0 || request.getVerticalNeeded() !=0 || request.getHorizontalNeeded() != 0)) {
			setToDedicatedMode(request);
		}
		return true;
	}

	private void setMode(int newState, DedicatedRequest request) {
		switch (newState) {
		case DEDICATED_MODE: {
			if (request != null) {
				this.remainingEvents = request.getEventsNeeded();
				this.remainingHorizontal = request.getHorizontalNeeded();
				this.remainingVertical = request.getVerticalNeeded();
				currentState = DEDICATED_MODE;
			}
			break;
		}
		case REGULAR_MODE: {
			remainingEvents = CANDIDATE_EVENTS_FOR_COLLAGE;
			remainingHorizontal = 0;
			remainingVertical = 0;
			currentState = REGULAR_MODE;
		}
		}
	}
	
	private void setToRegularMode() {
		setMode(REGULAR_MODE, null);
	}
	
	private void setToDedicatedMode(DedicatedRequest request) {
		setMode(DEDICATED_MODE, request);
	}
}
