package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.GeoCamTalkRoboApplication;
import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import gov.nasa.arc.geocam.talk.service.C2DMReciever;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.ITalkServer;

import java.util.List;

import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.inject.Inject;

public class GeoCamTalkActivity extends AuthenticatedBaseActivity {

	@Inject
	ITalkServer djangoTalk;

	@InjectView(R.id.TalkListView)
	ListView talkListView;

	@InjectResource(R.string.url_server_root)
	String serverRootUrl;

	@Inject
	GeoCamTalkMessageAdapter adapter;

	@Inject
	IMessageStore messageStore;

	@Inject
	IAudioPlayer player;
	@Inject
	SharedPreferences prefs;
	@Inject
	IIntentHelper intentHelper;


	List<GeoCamTalkMessage> talkMessages;
	private EditText username;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().contentEquals(
					TalkServerIntent.INTENT_NEW_MESSAGES.toString())) {
				GeoCamTalkActivity.this.newMessages();
			}
		}
	};
	
	private PowerManager.WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "DimScreenALittle");
		setUsername();
	}

	private void setUsername() {
		username = (EditText) findViewById(R.id.username_label);
		Editor editor = prefs.edit();
		editor.commit();
		if (prefs.getString("webapp_username", null) != null) {
			username.setText(prefs.getString("webapp_username", null));
		}
	}

	private void populateListView() {
		try {
			talkMessages = messageStore.getAllMessages();
		} catch (Exception e) {
			Log.i("Talk", "Error:" + e.getMessage());
		}

		if (talkMessages != null) {
			adapter.setTalkMessages(talkMessages);
			talkListView.setAdapter(adapter);
		}
	}

	public void onGoHomeClick(View v) {
		populateListView();
	}

	public void onCreateTalkClick(View v) {
		UIUtils.createTalkMessage(this);
	}
	
	public void onExitClick(View v) {
		((GeoCamTalkRoboApplication) getApplication()).stopThreads();
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void newMessages() {
		populateListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TalkServerIntent.INTENT_NEW_MESSAGES.toString());
		registerReceiver(receiver, filter);

		populateListView();
		setUsername();
		talkListView.setItemsCanFocus(true);
		talkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parentView,
							View childView, int position, long id) {
						Log.i("OnItemClick", "Position: " + position + ", ID:" + id);
						GeoCamTalkMessage msg = adapter.getTalkMessage(position);
						
						if (msg.hasGeolocation()) {
							UIUtils.showMapView(childView.getContext(), msg);
						}											
					}
				});
	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		wakeLock.release();
		super.onPause();
	}
	
	public void audioButtonClickHandler(View v) {
		int position = (Integer) v.getTag();
		GeoCamTalkMessage msg = adapter.getTalkMessage(position);		
		if (msg.hasAudio()) {
			try {
				UIUtils.playAudio(getApplicationContext(), msg,	player, siteAuth);
			} catch (Exception e) {
				UIUtils.displayException(getApplicationContext(), e, "Cannot retrieve audio");
			}
		}		
	}
}