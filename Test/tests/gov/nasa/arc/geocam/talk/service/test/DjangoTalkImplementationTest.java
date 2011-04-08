package gov.nasa.arc.geocam.talk.service.test;

import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalkImplementation;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;
import gov.nasa.arc.geocam.talk.service.SiteAuthInterface;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class DjangoTalkImplementationTest extends GeoCamTestCase {

	@Test
	public void shouldEnsureGetTalkMessagesReturnsMessages() throws Exception {
		DjangoTalkImplementation talkImpl = new DjangoTalkImplementation();
		
		DjangoTalkJsonConverterInterface jsonConv = 
			mock(DjangoTalkJsonConverterInterface.class);
		
		SiteAuthInterface siteauth =
			mock(SiteAuthInterface.class);
		when(siteauth.get(anyString(), anyMap())).thenReturn("");
		setHiddenField(talkImpl, "siteAuthImplementation", siteauth);
		
		List<GeoCamTalkMessage> expectedList = new ArrayList<GeoCamTalkMessage>();
		when(jsonConv.deserializeList(anyString())).thenReturn(expectedList);
		setHiddenField(talkImpl, "jsonConverter", jsonConv);
		
		// act
		List<GeoCamTalkMessage> talkMessages = talkImpl.getTalkMessages();
		
		// assert
		assertEquals(expectedList, talkMessages);
		verify(jsonConv).deserializeList(anyString());
	}
	
	@Test
	public void shouldEnsureCreateTalkMessagePostsTalkMessage() throws Exception
	{
		DjangoTalkImplementation memoImpl = new DjangoTalkImplementation();

		DjangoTalkJsonConverterInterface jsonConv = 
			mock(DjangoTalkJsonConverterInterface.class);

		SiteAuthInterface siteauth =
			mock(SiteAuthInterface.class);
		when(siteauth.post(anyString(), anyMap())).thenReturn(200);
		setHiddenField(memoImpl, "siteAuthImplementation", siteauth);

		when(jsonConv.serialize((GeoCamTalkMessage)anyObject())).thenReturn("");
		setHiddenField(memoImpl, "jsonConverter", jsonConv);

		// act
		memoImpl.createTalkMessage(new GeoCamTalkMessage());

		// assert
		verify(siteauth).post(anyString(), anyMap());
	}
}
