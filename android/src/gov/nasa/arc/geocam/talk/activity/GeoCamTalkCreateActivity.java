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

import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;

/**
 * The Class GeoCamTalkCreateActivity.
 */
public class GeoCamTalkCreateActivity extends AuthenticatedBaseActivity {

	/** The app state. */
	@Inject
	GeoCamTalkRoboApplication appState;

	/** The new talk text view. */
	@InjectView(R.id.newTalkTextInput)
	EditText newTalkTextView;
	
	/** The record button. */
	@InjectView(R.id.record_button)
	Button recordButton;

	/** The recorder. */
	@Inject
	IAudioRecorder recorder;

	/** The player. */
	@Inject
	IAudioPlayer player;

	/** The message store. */
	@Inject
	IMessageStore messageStore;

	/** The intent helper. */
	@Inject
	IIntentHelper intentHelper;
	
	/** The shared preferences. */
	SharedPreferences sharedPreferences;

	/** The filename. */
	private String filename = null;
	
	/** The record image. */
	private Drawable recordImage = null;
	
	/** The stop image. */
	private Drawable stopImage = null;

	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_talk_message);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		recordImage = getApplicationContext().getResources().getDrawable(R.drawable.record);
		stopImage = getApplicationContext().getResources().getDrawable(R.drawable.stop);
	}

	/**
	 * On go home click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onGoHomeClick(View v) {
		UIUtils.goHome(this);
	}

	/**
	 * On playback click.
	 *
	 * @param v the View from which the click was initiated
	 */
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

	/**
	 * On record click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onRecordClick(View v) {
		sharedPreferences.edit().putBoolean("audio_blocked", true).commit();
		if (recorder.isRecording()) {
			Log.i("TALKCREATE", "STOP recording now.");
			stopRecording();
		} else {
			Log.i("TALKCREATE", "START recording now.");
			try {
				player.playBeepA();
				recordButton.setCompoundDrawablesWithIntrinsicBounds(null, null, stopImage, null);
				recordButton.setText("Recording...");
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
	
	/**
	 * Stop recording.
	 */
	private void stopRecording() {
		sharedPreferences.edit().putBoolean("audio_blocked", false).commit();
		try {
			player.playBeepB();
			recordButton.setCompoundDrawablesWithIntrinsicBounds(null, null, recordImage, null);
			recordButton.setText("Record Audio");
			filename = recorder.stopRecording();
			Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();
			player.startPlaying(filename);
			// recorder.toggleRecordingStatus();
		} catch (Exception e) {
			Log.e("TALKCREATE", "Exception: " + e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * On send click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onSendClick(View v) {
		
		//If for some reason we are still recording, stop now
		if (recorder.isRecording())
		{
			filename = recorder.stopRecording();
		}
		
		GeoCamTalkMessage message = new GeoCamTalkMessage();
		message.setContent(newTalkTextView.getText().toString());
		message.setContentTimestamp(new Date());
		message.setLocation(appState.getLocation());
		message.setAuthorUsername(sharedPreferences.getString("webapp_username", null));

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

	/**
	 * Creates the byte array for a recorded audio message.
	 *
	 * @return the byte[]
	 */
	private byte[] createByteArray() {
		byte[] audioBytes = null;

		try {
			File audioFile = new File(filename);
			int length = (int) audioFile.length();
			audioBytes = new byte[length];

			FileInputStream fis;

			fis = new FileInputStream(audioFile);
			fis.read(audioBytes, 0, length);
		} catch (FileNotFoundException e) {
			UIUtils.displayException(this, e, "Could not find audio file");
		} catch (IOException e) {
			UIUtils.displayException(this, e, "Could not encode audio file");
		}

		return audioBytes;
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.activity.AuthenticatedBaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		if (recorder.isRecording()) {
			stopRecording();
		}
		sharedPreferences.edit().putBoolean("audio_blocked", false).commit();
		super.onPause();
	}
}
