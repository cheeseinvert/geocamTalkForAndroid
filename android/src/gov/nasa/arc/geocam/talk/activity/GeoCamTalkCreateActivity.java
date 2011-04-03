package gov.nasa.arc.geocam.talk.activity;

import java.io.FileInputStream;

import com.google.inject.Inject;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.service.AudioRecorderInterface;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GeoCamTalkCreateActivity extends RoboActivity{
	
	@InjectView(R.id.newTalkTextInput)EditText newTalkTextView;
    @Inject AudioRecorderInterface recorder;
    MediaPlayer player = new MediaPlayer();
	
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
			try {
				FileInputStream f = new FileInputStream(recorder.stopRecording());
			    player.setDataSource(f.getFD());
                player.prepare();
                player.start();
			} 
			catch(Exception e) {
				Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
	            player.reset();
			}
		}
		else {
			recorder.startRecording();
		}
	}
	
	public void onSendClick(View v){
		CharSequence text = newTalkTextView.getText();
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(this, text, duration).show();		
	}
}
