package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.ITalkServer;
import gov.nasa.arc.geocam.talk.service.ITalkJsonConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.inject.Inject;

public class FakeTalkServer implements ITalkServer{

	@Inject ITalkJsonConverter jsonConverter;
	
	@Override
	public void getTalkMessages() {
		// TODO Auto-generated method stub
		List<GeoCamTalkMessage>testList = new ArrayList<GeoCamTalkMessage>();
		
		testList.add(
				FakeGeoCamTalkMessageFactory.getMessage(
						"Hey Guys, I'm the first message!", 
						"Ted Johnson", 
						true)
		);
		testList.add(
				FakeGeoCamTalkMessageFactory.getMessage(
						"I'm message #2", 
						"Ted Johnson", 
						false)
		);		
		testList.add(
				FakeGeoCamTalkMessageFactory.getMessage(
						"And I'm 3rd in line!", 
						"Rufus Hornsby", 
						true)
		);		
		testList.add(
				FakeGeoCamTalkMessageFactory.getMessage(
						"I should be at the top of the list", 
						"Ted Johnson", 
						true)
		);		
	}

	@Override
	public void createTalkMessage(GeoCamTalkMessage message)
			throws ClientProtocolException, AuthenticationFailedException,
			IOException {
		// TODO Auto-generated method stub
		
	}
}
