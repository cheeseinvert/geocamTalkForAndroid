package gov.nasa.arc.geocam.talk.service;

import java.util.TimerTask;

import com.google.inject.Inject;

public class GeoCamSynchronizationTimerTask extends TimerTask {
	
	private IIntentHelper intentHelper;

	@Inject
	public GeoCamSynchronizationTimerTask(IIntentHelper intentHelper) {
		this.intentHelper = intentHelper;
	}

	@Override
	public void run() {
		this.intentHelper.Synchronize();
	}

}
