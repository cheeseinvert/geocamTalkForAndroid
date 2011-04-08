package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.DjangoTalkIntent;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;

public class DjangoTalk extends IntentService implements IDjangoTalk {
	
	@Inject IDjangoTalkJsonConverter jsonConverter;
	@InjectResource(R.string.url_message_list) String talkMessagesJson;
	@Inject ISiteAuth siteAuth;
	@Inject IMessageStore messageStore;
	
	private Context context;
	
	@Inject
	public DjangoTalk(Context context, @InjectResource(R.string.django_talk_service_name) String serviceName) {
		super(serviceName);
		this.context = context;
	}
	
	@Override
	public void getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException {

		// let's check the server and add any new messages to the database
		String jsonString = null;
		
		jsonString = siteAuth.get(talkMessagesJson, null);
		List<GeoCamTalkMessage> newMessages =  jsonConverter.deserializeList(jsonString);
		
		if(newMessages.size() > 0)
		{
			messageStore.addMessage(newMessages);
			Intent newMsgIntent = new Intent(DjangoTalkIntent.NEW_MESSAGES.toString());
			context.sendBroadcast(newMsgIntent);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(intent.getAction().equals(DjangoTalkIntent.SYNCHRONIZE))
		{
			try{
				this.getTalkMessages();
			} catch (Exception e) {
				Log.e("GeoCam Talk", "Comm Error", e);
				// TODO: Display this to the user (Toast or notification bar)
			}
		}
	}
}
