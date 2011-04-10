package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import roboguice.service.RoboIntentService;
import android.content.Intent;
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
	

	@Inject
	ISiteAuth siteAuth;

	@Inject
	IMessageStore messageStore;

	@Inject
	IIntentHelper intentHelper;

	@Inject
	IGeoCamSynchronizationTimerTask geoCamSynchronizationTimerTask;

	public TalkServer() {
		super("DjangoTalkService");
	}

	@Override
	public void getTalkMessages() throws SQLException, ClientProtocolException,
			AuthenticationFailedException, IOException {

		// let's check the server and add any new messages to the database
		String jsonString = null;

		jsonString = siteAuth.get(urlMessageList, null);
		List<GeoCamTalkMessage> newMessages = jsonConverter.deserializeList(jsonString);

		if (newMessages.size() > 0) {
			for(GeoCamTalkMessage message : newMessages) {
				message.setSynchronized(true); // TODO re factor this to not suck, as we 
				                               // iterate again in addMessage
			}
			
			messageStore.addMessage(newMessages);
			intentHelper.BroadcastNewMessages();
		}
	}

	@Override
	public void createTalkMessage(GeoCamTalkMessage message) throws ClientProtocolException,
			AuthenticationFailedException, IOException, SQLException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("message", jsonConverter.serialize(message));
		int responseCode = siteAuth.post(urlCreateMessage, map, message.getAudio());
		if (responseCode != 200) {
			throw new ClientProtocolException("Message could not be created (HTTP error "
					+ responseCode + ")");
		}
		
		messageStore.removeMessage(message);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.getAction().contentEquals(TalkServerIntent.INTENT_SYNCHRONIZE.toString())) {
			handleSynchronizeIntent();
		} else if (intent.getAction().contentEquals(TalkServerIntent.INTENT_STORE_C2DM_ID.toString())) {
			String registrationId = intent.getStringExtra(TalkServerIntent.EXTRA_REGISTRATION_ID.toString());
			handleStoreRegistrationIdIntent(registrationId);
		} else if (intent.getAction().contentEquals(TalkServerIntent.INTENT_PUSHED_MESSAGE.toString())) {
			String messageId = intent.getStringExtra(TalkServerIntent.EXTRA_MESSAGE_ID.toString());
			handlePushedMessageIntent(messageId);
		}
	}

	private void handleSynchronizeIntent() {
		try {
			for (GeoCamTalkMessage message : messageStore.getAllLocalMessages()) {
				this.createTalkMessage(message);
			}

			this.getTalkMessages();
			this.geoCamSynchronizationTimerTask.resetTimer();
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Comm Error", e);
			// TODO: Display this to the user (Toast or notification bar)
		}
	}

	private void handleStoreRegistrationIdIntent(String registrationId) { // assumed this is not null
		Map<String, String> params = new HashMap<String, String>();
		params.put(TalkServerIntent.EXTRA_REGISTRATION_ID.toString(), registrationId);
		try{
			siteAuth.post(urlRegistration, params);
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Comm Error", e);
		}
	}

	private void handlePushedMessageIntent(String messageId) { // assumed this is not null
		Log.i("Talk", "Received notification for message:" + messageId);
		String url = String.format(urlMessageFormatString, messageId);
		
		String jsonString;		
		try{
			
			jsonString = siteAuth.get(url, null);
			GeoCamTalkMessage pushedMessage = 
				jsonConverter.deserialize(jsonString);
			messageStore.addMessage(pushedMessage); // TODO: go get audio if avaialable
			
			intentHelper.BroadcastNewMessages();
		} catch (Exception e) {
			Log.e("GeoCam Talk", "Error on single message get", e);
		}
	}

}


