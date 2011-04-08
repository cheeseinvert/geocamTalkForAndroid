package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.inject.Inject;

public class FakeDjangoTalkmplementation implements DjangoTalkInterface{

	@Inject DjangoTalkJsonConverterInterface jsonConverter;
	
	@Override
	public List<GeoCamTalkMessage> getTalkMessages() {
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
		
		return testList;
	}

	@Override
	public void setAuth(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTalkMessage(GeoCamTalkMessage message, String filename)
			throws ClientProtocolException, AuthenticationFailedException,
			IOException {
		// TODO Auto-generated method stub
		
	}
}
