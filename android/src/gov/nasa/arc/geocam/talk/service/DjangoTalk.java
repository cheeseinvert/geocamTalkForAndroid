package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;

import com.google.inject.Inject;

public class DjangoTalk implements IDjangoTalk{
// TODO: Review as to whether we should be binding a class or an instance of this class
	@Inject IDjangoTalkJsonConverter jsonConverter;
	@InjectResource(R.string.url_message_list) String talkMessagesJson;
	@Inject ISiteAuth siteAuth;
	@Inject IMessageStore messageStore;
	
	@Override
	public void getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException {

		// let's check the server and add any new messages to the database
		String jsonString = null;
		
		jsonString = siteAuth.get(talkMessagesJson, null);
		List<GeoCamTalkMessage> newMessages =  jsonConverter.deserializeList(jsonString);
		
		messageStore.addMessage(newMessages);
	}
}
