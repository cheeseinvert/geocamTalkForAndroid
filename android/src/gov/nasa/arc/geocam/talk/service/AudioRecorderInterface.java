package gov.nasa.arc.geocam.talk.service;

public interface AudioRecorderInterface {
	public void startRecording();
	public String stopRecording();
	public boolean isRecording();
}
