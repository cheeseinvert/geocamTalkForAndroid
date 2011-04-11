package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.GeoCamTalkRoboApplication;
import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.IAudioRecorder;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.IMessageStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;

public class GeoCamTalkCreateActivity extends RoboActivity {

	@Inject
	GeoCamTalkRoboApplication appState;

	@InjectView(R.id.newTalkTextInput)
	EditText newTalkTextView;

	@Inject
	IAudioRecorder recorder;

	@Inject
	IAudioPlayer player;

	@Inject
	IMessageStore messageStore;

	@Inject
	IIntentHelper intentHelper;

	private String filename = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_talk_message);

	}

	public void onHomeClick(View v) {
		UIUtils.goHome(this);
	}

	public void onPlaybackClick(View v) {
		// TODO: add this to call the Audio service

		Log.i("TALKCREATE", "Playback recording now.");
		try {
			Toast.makeText(this, "Recording playback", Toast.LENGTH_SHORT).show();
			player.startPlaying(this.getFilesDir().toString() + "/audio_recording.mp4");
			// recorder.toggleRecordingStatus();
		} catch (Exception e) {
			Log.e("TALKCREATE", "Exception: " + e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void onRecordClick(View v) {
		// TODO: add this to call the Audio service

		if (recorder.isRecording()) {
			Log.i("TALKCREATE", "STOP recording now.");
			try {
				player.playBeepB();
				filename = recorder.stopRecording();
				Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();
				player.startPlaying(filename);
				// recorder.toggleRecordingStatus();
			} catch (Exception e) {
				Log.e("TALKCREATE", "Exception: " + e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		} else {
			Log.i("TALKCREATE", "START recording now.");
			try {
				player.playBeepA();
				recorder.startRecording(this.getFilesDir().toString() + "/audio_recording.mp4");
				// recorder.toggleRecordingStatus();
				Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("TALKCREATE", "Exception: " + e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
				recorder.stopRecording();
			}
		}
	}

	public void onSendClick(View v) {
		GeoCamTalkMessage message = new GeoCamTalkMessage();
		message.setContent(newTalkTextView.getText().toString());
		message.setContentTimestamp(new Date());
		message.setLocation(appState.getLocation());

		if (filename != null) {
			message.setAudio(createByteArray());
		} else
		{
			//TODO: Is this the best way to prevent pulling null AudioUrl on click
			//	before django update? (still local)
			message.setAudioUrl("");
		}

		try {
			messageStore.addMessage(message);
			intentHelper.Synchronize();
			UIUtils.goHome(this);
		} catch (Exception e) {
			UIUtils.displayException(this, e, "Communication with the server failed");
		}
	}

	private byte[] createByteArray() {
		byte[] audioBytes = null;

		try {
			File audioFile = new File(filename);
			int length = (int) audioFile.length();
			audioBytes = new byte[(int) length];

			FileInputStream fis;

			fis = new FileInputStream(audioFile);
			fis.read(audioBytes, 0, length); // TODO GHETTO we should be better
												// about big files
		} catch (FileNotFoundException e) {
			UIUtils.displayException(this, e, "Could not find audio file");
		} catch (IOException e) {
			UIUtils.displayException(this, e, "Could not encode audio file");
		}

		return audioBytes;
	}
}
