package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

public interface IAudioRecorder {
	public String stopRecording();
	public boolean isRecording();
	void startRecording(String filename) throws IllegalStateException, IOException;
	public void toggleRecordingStatus();
}
