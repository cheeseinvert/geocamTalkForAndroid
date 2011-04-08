package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.inject.Inject;

public class FakeDjangoTalk implements IDjangoTalk{

	@Inject IDjangoTalkJsonConverter jsonConverter;
	
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
}
