package gov.nasa.arc.geocam.talk.service;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AudioRecorderImplementation implements AudioRecorderInterface {

	@Inject protected static Provider<Context> contextProvider;
	
	boolean isRecording = false;
	MediaRecorder recorder = new MediaRecorder();
	//private final String filename = contextProvider.get().getFilesDir().toString() + "/audiofile.mp4";
    
	private String getFilename() {
		return contextProvider.get().getFilesDir().toString() + "/audiofile.mp4";
		
	}
	
	@Override
	public void startRecording() {
		if (!isRecording) {
			Log.i("RECORDER", "Entered into start recording");
			recorder.reset();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			recorder.setOutputFile(getFilename());
			try {
				recorder.prepare();
				recorder.start();
				isRecording = true;
				Toast.makeText(contextProvider.get(), "Recording started", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(contextProvider.get(), e.getMessage(), Toast.LENGTH_SHORT).show();
				recorder.stop();
				recorder.reset();
			}
		}
	}

	@Override
	public String stopRecording() {
		if (isRecording) {
			Log.i("RECORDER", "Entered into stop recording");
			recorder.stop();
			recorder.reset();
			isRecording = false;
			Toast.makeText(contextProvider.get(), "Recording stopped", Toast.LENGTH_SHORT).show();
		}
		return getFilename();
	}

	@Override
	public boolean isRecording() {
		return isRecording;
	}
	

}
