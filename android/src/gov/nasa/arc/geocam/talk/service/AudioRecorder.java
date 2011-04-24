package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

import android.media.MediaRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class AudioRecorder.
 */
public class AudioRecorder implements IAudioRecorder {

	/** The is recording. */
	public boolean isRecording = false;
	
	/** The recorder. */
	private MediaRecorder recorder;
	
	/** The filename. */
	public String filename;
 
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioRecorder#startRecording(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioRecorder#stopRecording()
	 */
	@Override
	public String stopRecording() {
		recorder.stop();
		//recorder.reset();
		isRecording = false;
		return filename;
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioRecorder#isRecording()
	 */
	@Override
	public boolean isRecording() {
		return isRecording;
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioRecorder#toggleRecordingStatus()
	 */
	@Override
	public void toggleRecordingStatus() {
		if (isRecording) {
			isRecording = false;
		}
		else {
			isRecording = true;
		}
	}
	

}
