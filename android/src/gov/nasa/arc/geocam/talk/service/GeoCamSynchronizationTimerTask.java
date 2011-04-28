package gov.nasa.arc.geocam.talk.service;

import android.os.Handler;

import com.google.inject.Inject;

/**
 * The Class GeoCamSynchronizationTimerTask.
 */
public class GeoCamSynchronizationTimerTask implements IGeoCamSynchronizationTimerTask {
	
	/** The intent helper. */
	private IIntentHelper intentHelper;
	
	/** The handler. */
	private Handler handler = new Handler();
	
	/** The period. */
	private long period = 60 * 10 * 1000; // 10 minutes TODO make shared preference 

	/**
	 * Instantiates a new geo cam synchronization timer task.
	 *
	 * @param intentHelper the intent helper
	 */
	@Inject
	public GeoCamSynchronizationTimerTask(IIntentHelper intentHelper) {
		this.intentHelper = intentHelper;
		this.handler.postDelayed(this, period);
		this.handler.post(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.intentHelper.Synchronize();
		this.handler.postDelayed(this, period);
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask#resetTimer()
	 */
	@Override
	public void resetTimer() {
		this.handler.removeCallbacks(this);
		this.handler.postDelayed(this, period);
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask#stopTimer()
	 */
	@Override
	public void stopTimer() {
		this.handler.removeCallbacks(this);
	}

}
