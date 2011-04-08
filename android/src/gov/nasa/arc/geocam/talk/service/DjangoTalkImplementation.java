package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;
import gov.nasa.arc.geocam.talk.service.SiteAuthInterface;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import android.content.Context;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DjangoTalkImplementation implements DjangoTalkInterface{
// TODO: Review as to whether we should be binding a class or an instance of this class
	@Inject DjangoTalkJsonConverterInterface jsonConverter;
	@InjectResource(R.string.url_server_root) String serverRootUrl;
	@InjectResource(R.string.url_relative_app) String appPath;
	@InjectResource(R.string.url_message_list) String talkMessagesJson;
	@InjectResource(R.string.url_create_message) String createTalkMessageJson;
	@Inject protected static Provider<Context> contextProvider;
	@Inject SiteAuthInterface siteAuthImplementation;
	
	@Override
	public List<GeoCamTalkMessage> getTalkMessages() {
		//String jsonString = 
		//	"[{\"authorUsername\": \"rhornsby\", \"longitude\": -122.057954, \"content\": \"Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.\", \"contentTimestamp\": \"03/13/11 10:48:44\", \"latitude\": 37.411629, \"messageId\": 15, \"accuracy\":60.0}]";
		String jsonString = null;
		
		try {
			jsonString = siteAuthImplementation.get(talkMessagesJson, null);
		} catch (Exception e) {
			Toast.makeText(contextProvider.get(), "Cannot access Talk Web", Toast.LENGTH_SHORT).show();			
		}
        
		return jsonConverter.deserializeList(jsonString);
	}

	@Override
	public void setAuth(String username, String password) {
		siteAuthImplementation.setAuth(username, password);		
	}
	
	@Override
	public void createTalkMessage(GeoCamTalkMessage message, String filename) throws ClientProtocolException, AuthenticationFailedException, IOException {
		HashMap<String,String>map = new HashMap<String,String>();
		map.put("message", jsonConverter.serialize(message));
		int responseCode = siteAuthImplementation.post(createTalkMessageJson, map, filename);
		if(responseCode != 200)
		{
			throw new ClientProtocolException("Message could not be created (HTTP error "+responseCode+")");
		}
	}
}
