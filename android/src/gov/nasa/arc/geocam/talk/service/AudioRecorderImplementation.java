package gov.nasa.arc.geocam.talk.service;

import java.io.FileInputStream;

import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import com.google.inject.Inject;

public class AudioRecorderImplementation implements AudioRecorderInterface {

	private Context context = new Activity();

	@Inject
	public AudioRecorderImplementation(Context context) {
		this.context = context;
	}
	
	boolean isRecording = false;
	MediaRecorder recorder = new MediaRecorder();
	private final String filename = context.getFilesDir().toString() + "/audiofile.mp4";

	@Override
	public void startRecording() {
		if (!isRecording) {
			Log.i("RECORDER", "Entered into start recording");
			recorder.reset();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			recorder.setOutputFile(filename);
			try {
				recorder.prepare();
				recorder.start();
				isRecording = true;
				Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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
			try {
				FileInputStream f = new FileInputStream(filename);
				// TODO: call player to replay the file here?
				Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		return filename;
	}

	@Override
	public boolean isRecording() {
		return isRecording;
	}
	

}
