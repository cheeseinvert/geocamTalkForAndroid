package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

import android.media.MediaRecorder;

public class AudioRecorder implements IAudioRecorder {

	public boolean isRecording = false;
	private MediaRecorder recorder;
	public String filename;
 
	@Override
	public void startRecording(String filename) throws IllegalStateException, IOException {
		this.filename = filename;
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		recorder.setOutputFile(filename);
		recorder.prepare();
		recorder.start();
		isRecording = true;
	}

	@Override
	public String stopRecording() {
		recorder.stop();
		//recorder.reset();
		isRecording = false;
		return filename;
	}

	@Override
	public boolean isRecording() {
		return isRecording;
	}

	@Override
	public void toggleRecordingStatus() {
		// TODO Auto-generated method stub
		if (isRecording) {
			isRecording = false;
		}
		else {
			isRecording = true;
		}
	}
	

}
