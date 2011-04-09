package gov.nasa.arc.geocam.talk.service;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;

public class GeoCamSynchronizationTimerTask extends TimerTask
            implements IGeoCamSynchronizationTimerTask {
	
	private IIntentHelper intentHelper;
	private Timer timer = new Timer();

	@Inject
	public GeoCamSynchronizationTimerTask(IIntentHelper intentHelper) {
		this.intentHelper = intentHelper;
		
		this.timer.schedule(this, 0, 60 * 10 * 1000); // delay 0 period 10 min.
	}

	@Override
	public void run() {
		this.intentHelper.Synchronize();
	}

}
