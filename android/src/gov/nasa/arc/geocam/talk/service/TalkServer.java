package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.bean.ServerResponse;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

import com.google.inject.Inject;

public class TalkServer extends RoboIntentService implements ITalkServer {

	@Inject
	ITalkJsonConverter jsonConverter;

	@InjectResource(R.string.url_message_list)
	String urlMessageList;

	@InjectResource(R.string.url_create_message)
	String urlCreateMessage;

	@InjectResource(R.string.url_registration)
	String urlRegistration;

	@InjectResource(R.string.url_message_format)
	String urlMessageFormatString;

	@InjectResource(R.string.pref_auto_play_on_push)
	String prefAutoPlayOnPush;

	@Inject
	ISiteAuth siteAuth;

	@Inject
	IMessageStore messageStore;

	@Inject
	IIntentHelper intentHelper;

	@Inject
	IAudioPlayer audioPlayer;

	@Inject
	IGeoCamSynchronizationTimerTask geoCamSynchronizationTimerTask;
	
	@Inject SharedPreferences sharedPrefs;

	public TalkServer() {
		super("DjangoTalkService");
	}

	private static int maxMessageId = 0;

	public void getTalkMessages() throws SQLException, ClientProtocolException,
			AuthenticationFailedException, IOException {

		// let's check the server and add any new messages to the database
		String jsonString = null;

		String url = String.format(urlMessageList, maxMessageId);

		ServerResponse sr = siteAuth.get(url, null);

		if (sr.getResponseCode() == 200) {
			jsonString = sr.getContent();
			List<GeoCamTalkMessage> newMessages = jsonConverter.deserializeList(jsonString);
			List<GeoCamTalkMessage> existingMessages = messageStore.getAllMessages();
			newMessages.removeAll(existingMessages);

			if (newMessages.size() > 0) {
				for (GeoCamTalkMessage message : newMessages) {
					message.setSynchronized(true);
				}
				messageStore.addMessage(newMessages);
				intentHelper.BroadcastNewMessages();
			}
		} else {
			Log.e("Talk", "Could not get messages, invalid response");
		}

		maxMessageId = messageStore.getNewestMessageId();
		Log.i("Talk", "MaxMessageIdNow:" + maxMessageId);
	}

	public void createTalkMessage(GeoCamTalkMessage message) throws ClientProtocolException,
			AuthenticationFailedException, IOException, SQLException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", jsonConverter.serialize(message));
		message.setSynchronized(true);
		messageStore.updateMessage(message);
		ServerResponse sr = siteAuth.post(urlCreateMessage, map, message.getAudio());
		if (sr.getResponseCode() == 401) {
			siteAuth.reAuthenticate();
			sr = siteAuth.post(urlCreateMessage, map, message.getAudio());
		}
		if (sr.getResponseCode() == 200) {
			// read content to string
			Map<String, String> result = jsonConverter.createMap(sr.getContent());
			try {
				message.setMessageId(Integer.parseInt(result.get("messageId")));
				message.setAuthorFullname(result.get("authorFullname"));
				if (result.containsKey("audioUrl")) {
					message.setAudioUrl(result.get("audioUrl"));
				}
			} catch (Exception e) {
				Log.e("Talk", "", e);
			}
			message.setSynchronized(true);
			messageStore.updateMessage(message);
			intentHelper.BroadcastNewMessages();
		} else {
			message.setSynchronized(false);
			messageStore.updateMessage(message);
			throw new ClientProtocolException("Message could not be created (HTTP error "
					+ sr.getResponseCode() + ")");
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.getAction().contentEquals(TalkServerIntent.INTENT_SYNCHRONIZE.toString())) {
			handleSynchronizeIntent();
		} else if (intent.getAction().contentEquals(
				TalkServerIntent.INTENT_STORE_C2DM_ID.toString())) {
			String registrationId = intent.getStringExtra(TalkServerIntent.EXTRA_REGISTRATION_ID
					.toString());
			handleStoreRegistrationIdIntent(registrationId);
		} else if (intent.getAction().contentEquals(
				TalkServerIntent.INTENT_PUSHED_MESSAGE.toString())) {
			String messageId = intent.getStringExtra(TalkServerIntent.EXTRA_MESSAGE_ID.toString());
			handlePushedMessageIntent(messageId);
		} else if (intent.getAction().contentEquals(TalkServerIntent.INTENT_LOGIN.toString())) {
			handleLogin();
		}

	}

	private void handleLogin() {
		try {
			siteAuth.login();
		} catch (AuthenticationFailedException e) {
			intentHelper.LoginFailed();
		} catch (Exception e) {
			Log.e("Talk", "Comm error while logging in.", e);
		}

	}

	private void handleSynchronizeIntent() {
		try {
			this.getTalkMessages();
			this.geoCamSynchronizationTimerTask.resetTimer();
			for (GeoCamTalkMessage message : messageStore.getAllLocalMessages()) {
				this.createTalkMessage(message);
			}
		} catch (AuthenticationFailedException e) {
			intentHelper.LoginFailed();
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Comm Error", e);
		}
	}

	private void handleStoreRegistrationIdIntent(String registrationId) { // assumed
																			// this
																			// is
																			// not
																			// null
		Map<String, String> params = new HashMap<String, String>();
		params.put(TalkServerIntent.EXTRA_REGISTRATION_ID.toString(), registrationId);
		try {
			siteAuth.post(urlRegistration, params);
		} catch (AuthenticationFailedException e) {
			intentHelper.LoginFailed();
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Comm Error", e);
		}
	}

	private void handlePushedMessageIntent(String messageId) { // assumed this
																// is not null
		Log.i("Talk", "Received notification for message:" + messageId);
		String url = String.format(urlMessageFormatString, messageId);

		String jsonString;
		try {
			ServerResponse sr = siteAuth.get(url, null);
			if (sr.getResponseCode() == 401) {
				siteAuth.reAuthenticate();
				sr = siteAuth.get(url, null);
			}
			jsonString = sr.getContent();
			GeoCamTalkMessage pushedMessage = jsonConverter.deserialize(jsonString);
			// TODO: We're assuming all is well, may need to check for existing
			// message id first

			pushedMessage.setSynchronized(true);
			messageStore.addMessage(pushedMessage); // TODO: go get audio if
													// avaialable

			if (sharedPrefs.getBoolean(prefAutoPlayOnPush, false)) {
				UIUtils.playAudio(getApplicationContext(), pushedMessage, audioPlayer, siteAuth);
			}

			intentHelper.BroadcastNewMessages();
		} catch (AuthenticationFailedException e) {
			intentHelper.LoginFailed();
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Error on single message get", e);
		}
	}

}
