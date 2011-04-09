package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.DjangoTalkIntent;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;

public class DjangoTalk extends RoboIntentService implements IDjangoTalk {

	@Inject
	IDjangoTalkJsonConverter jsonConverter;
	
	@InjectResource(R.string.url_message_list)
	String talkMessagesJson;
	
	@Inject
	ISiteAuth siteAuth;
	
	@Inject
	IMessageStore messageStore;
	
	@Inject
	IIntentHelper intentHelper;
	
	@Inject
	GeoCamSynchronizationTimerTask geoCamSynchronizationTimerTask;

	public DjangoTalk() {
		super("DjangoTalkService");
		Timer timer = new Timer();
		timer.schedule(geoCamSynchronizationTimerTask, 0, 60 * 10 * 1000); // delay 0 period 10 min.
	}

	@Override
	public void getTalkMessages() throws SQLException, ClientProtocolException,
			AuthorizationFailedException, IOException {

		// let's check the server and add any new messages to the database
		String jsonString = null;

		jsonString = siteAuth.get(talkMessagesJson, null);
		List<GeoCamTalkMessage> newMessages = jsonConverter.deserializeList(jsonString);

		if (newMessages.size() > 0) {
			messageStore.addMessage(newMessages);
			intentHelper.BroadcastNewMessages();
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.getAction().contentEquals(DjangoTalkIntent.SYNCHRONIZE.toString())) {
			try {
				this.getTalkMessages();
			} catch (Exception e) {
				Log.e("GeoCam Talk", "Comm Error", e);
				// TODO: Display this to the user (Toast or notification bar)
			}
		}
	}
}
