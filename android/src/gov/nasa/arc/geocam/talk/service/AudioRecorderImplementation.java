package gov.nasa.arc.geocam.talk.service;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

public class AudioRecorderImplementation implements AudioRecorderInterface {

	boolean isRecording = false;
	MediaRecorder recorder = new MediaRecorder();
	MediaPlayer player = new MediaPlayer();
	
	@Override
	public void startRecording() {
		if (!isRecording) {
			player.stop();
			recorder.start();
			isRecording = true;
			
		}
		// TODO: else Exception?
		
		
	}

	@Override
	public String stopRecording() {
		if (isRecording) {
			recorder.stop();
			recorder.reset();
			player.start();
			isRecording = false;
			
		}
		return null;
	}

	@Override
	public boolean isRecording() {
		return isRecording;
	}
	

}
