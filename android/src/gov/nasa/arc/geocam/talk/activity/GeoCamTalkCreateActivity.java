package gov.nasa.arc.geocam.talk.activity;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.inject.Inject;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.service.AudioPlayerInterface;
import gov.nasa.arc.geocam.talk.service.AudioRecorderInterface;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GeoCamTalkCreateActivity extends RoboActivity{
	
	@InjectView(R.id.newTalkTextInput)EditText newTalkTextView;
    @Inject AudioRecorderInterface recorder;
    @Inject AudioPlayerInterface player;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_talk_message);

	}

	public void onHomeClick(View v) {
		UIUtils.goHome(this);
	}
	
	public void onRecordClick(View v){
        // TODO: add this to call the Audio service

		if (recorder.isRecording()) {
			Log.i("TALKCREATE", "STOP recording now.");
			try {
				player.playBeep();
				String filename = recorder.stopRecording();
				Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();
				player.startPlaying(filename);
				//recorder.toggleRecordingStatus();
			} 
			catch (Exception e) {
				Log.e("TALKCREATE", "Exception: " + e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		else {	
			Log.i("TALKCREATE", "START recording now.");
			try {
				player.playBeep();
				recorder.startRecording(this.getFilesDir().toString() + "/audio_recording.mp4");
				//recorder.toggleRecordingStatus();
				Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("TALKCREATE", "Exception: " + e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
				recorder.stopRecording();
			}
		}
	}
	
	public void onSendClick(View v){
		CharSequence text = newTalkTextView.getText();
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(this, text, duration).show();		
	}
}
