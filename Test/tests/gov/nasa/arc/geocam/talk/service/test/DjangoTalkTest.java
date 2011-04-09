package gov.nasa.arc.geocam.talk.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import gov.nasa.arc.geocam.talk.service.MessageStore;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class DjangoTalkTest extends GeoCamTestCase {

	@Test
	public void shouldEnsureGetTalkMessagesReturnsMessages() throws Exception {
		DjangoTalk talkImpl = 
			new DjangoTalk();
		
		IDjangoTalkJsonConverter jsonConv = 
			mock(IDjangoTalkJsonConverter.class);
		
		ISiteAuth siteauth =
			mock(ISiteAuth.class);
		when(siteauth.get(anyString(), anyMap())).thenReturn("");
		setHiddenField(talkImpl, "siteAuth", siteauth);
		
		List<GeoCamTalkMessage> expectedList = new ArrayList<GeoCamTalkMessage>();
		expectedList.add(new GeoCamTalkMessage());
		when(jsonConv.deserializeList(anyString())).thenReturn(expectedList);
		setHiddenField(talkImpl, "jsonConverter", jsonConv);
		
		MessageStore ms = mock(MessageStore.class);
		setHiddenField(talkImpl, "messageStore", ms);
		
		IIntentHelper intentHelper = mock(IIntentHelper.class);
		setHiddenField(talkImpl, "intentHelper", intentHelper);
		
		// act
		talkImpl.getTalkMessages();		
		
		// assert
		verify(ms).addMessage(anyList());
		verify(jsonConv).deserializeList(anyString());
	}
	
	@Test
	public void shouldEnsureCreateTalkMessagePostsTalkMessage() throws Exception
	{
		IDjangoTalk talkImpl = new DjangoTalk();

		IDjangoTalkJsonConverter jsonConv = mock(DjangoTalkJsonConverter.class);
		
		ISiteAuth siteauth = mock(ISiteAuth.class);
		when(siteauth.post(anyString(), anyMap(), any(byte[].class))).thenReturn(200);
		setHiddenField(talkImpl, "siteAuth", siteauth);

		when(jsonConv.serialize((GeoCamTalkMessage)anyObject())).thenReturn("");
		setHiddenField(talkImpl, "jsonConverter", jsonConv);

		// act
		talkImpl.createTalkMessage(new GeoCamTalkMessage());

		// assert
		verify(siteauth).post(anyString(), anyMap(), any(byte[].class));
	}
}
