package gov.nasa.arc.geocam.talk.service;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGeoCamSynchronizationTimerTask.
 */
public interface IGeoCamSynchronizationTimerTask extends Runnable {
	
	/**
	 * Reset timer.
	 */
	void resetTimer();

	/**
	 * Stop timer.
	 */
	void stopTimer();
}
