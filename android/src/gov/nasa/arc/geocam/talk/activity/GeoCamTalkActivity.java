package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
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
import android.widget.EditText;
import android.widget.ListView;

import com.google.inject.Inject;

/**
 * The "home" activity for the application. Displays the message list to the user.
 */
public class GeoCamTalkActivity extends AuthenticatedBaseActivity {

	/** The {@link ITalkServer} implemenation to use. */
	@Inject
	ITalkServer djangoTalk;

	/** The talk list view. */
	@InjectView(R.id.TalkListView)
	ListView talkListView;

	/** The server root url. */
	@InjectResource(R.string.url_server_root)
	String serverRootUrl;

	/** The message list adapter. */
	@Inject
	GeoCamTalkMessageAdapter adapter;

	/** The message store. */
	@Inject
	IMessageStore messageStore;

	/** The player. */
	@Inject
	IAudioPlayer player;
	
	/** The shared prefs. */
	@Inject
	SharedPreferences prefs;
	
	/** The intent helper. */
	@Inject
	IIntentHelper intentHelper;


	/** The talk messages. */
	private List<GeoCamTalkMessage> talkMessages;
	
	/** The username. */
	private EditText username;

	/** The {@link BroadcastReceiver} used to receive a NEW_MESSAGES intent. */
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().contentEquals(
					TalkServerIntent.INTENT_NEW_MESSAGES.toString())) {
				GeoCamTalkActivity.this.newMessages();
			}
		}
	};
	
	/** The wake lock. */
	private PowerManager.WakeLock wakeLock;

	/* (non-Javadoc)
	 * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "DimScreenALittle");
		setUsername();
	}

	/**
	 * Sets the current username.
	 */
	private void setUsername() {
		username = (EditText) findViewById(R.id.username_label);
		Editor editor = prefs.edit();
		editor.commit();
		if (prefs.getString("webapp_username", null) != null) {
			username.setText(prefs.getString("webapp_username", null));
		}
	}

	/**
	 * Populate list view.
	 */
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

	/**
	 * On go home button click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onGoHomeClick(View v) {
		populateListView();
	}

	/**
	 * On create talk click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onCreateTalkClick(View v) {
		UIUtils.createTalkMessage(this);
	}

	/**
	 * Act on a new messages intent receipt
	 */
	public void newMessages() {
		populateListView();
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.activity.AuthenticatedBaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		prefs.edit().putBoolean("audio_blocked", false).commit();
		wakeLock.acquire();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TalkServerIntent.INTENT_NEW_MESSAGES.toString());
		registerReceiver(receiver, filter);

		populateListView();
		setUsername();
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.activity.AuthenticatedBaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		wakeLock.release();
		super.onPause();
	}
	
	/**
	 * Audio button click handler.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void audioButtonClickHandler(View v) {		
		GeoCamTalkMessage msg = (GeoCamTalkMessage)v.getTag();		
		if (msg.hasAudio()) {
			try {
				UIUtils.playAudio(this, msg,	player, siteAuth);
			} catch (Exception e) {
				UIUtils.displayException(getApplicationContext(), e, "Cannot retrieve audio");
			}
		}		
	}
}