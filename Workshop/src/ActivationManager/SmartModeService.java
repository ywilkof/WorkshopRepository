package ActivationManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SmartModeService {
	private static ScheduledExecutorService scheduler = null;
	private static ActivationManager manager = ActivationManager.getInstance();  
	private static final int INTERVAL_IN_SECONDS = 30;

	private SmartModeService() {
	}

	public static void startService() {
		if (scheduler == null) {
			scheduler =  Executors.newScheduledThreadPool(1);
			// waits INTERVAL_IN_SECONDS seconds after end of last execution
			scheduler.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					manager.processPhotoBuffer();
				}
			},
			20,
			INTERVAL_IN_SECONDS,
			TimeUnit.SECONDS);	
		}
	}

	public static void stopService() {
		scheduler.shutdown();
	}
}