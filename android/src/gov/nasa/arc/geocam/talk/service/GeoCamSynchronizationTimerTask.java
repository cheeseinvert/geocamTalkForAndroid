package gov.nasa.arc.geocam.talk.service;

import android.os.Handler;

import com.google.inject.Inject;

public class GeoCamSynchronizationTimerTask implements IGeoCamSynchronizationTimerTask {
	
	private IIntentHelper intentHelper;
	private Handler handler = new Handler();
	private long period = 60 * 10 * 1000; // 10 minutes TODO make shared preference 

	@Inject
	public GeoCamSynchronizationTimerTask(IIntentHelper intentHelper) {
		this.intentHelper = intentHelper;
		this.handler.postDelayed(this, period);
		this.handler.post(this);
	}

	@Override
	public void run() {
		this.intentHelper.Synchronize();
		this.handler.postDelayed(this, period);
	}

	@Override
	public void resetTimer() {
		this.handler.removeCallbacks(this);
		this.handler.postDelayed(this, period);
	}

}
