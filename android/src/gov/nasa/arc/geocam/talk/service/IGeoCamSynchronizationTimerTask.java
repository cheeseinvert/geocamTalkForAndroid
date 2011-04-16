package gov.nasa.arc.geocam.talk.service;

public interface IGeoCamSynchronizationTimerTask extends Runnable {
	void resetTimer();

	void stopTimer();
}
