package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IAudioRecorder.
 */
public interface IAudioRecorder {
	
	/**
	 * Stop recording.
	 *
	 * @return the string which is the filename of the audio file just recorded
	 */
	public String stopRecording();
	
	/**
	 * Checks if there is an active recording going on.
	 *
	 * @return true, if is recording
	 */
	public boolean isRecording();
	
	/**
	 * Start recording.
	 *
	 * @param filename the filename of the recording audio
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void startRecording(String filename) throws IllegalStateException, IOException;
	
	/**
	 * Toggle recording status.
	 */
	public void toggleRecordingStatus();
}
