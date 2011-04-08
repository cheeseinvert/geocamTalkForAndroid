package gov.nasa.arc.geocam.talk.service.test;

import static org.mockito.Matchers.anyMap;
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

import com.j256.ormlite.dao.Dao;
import com.xtremelabs.robolectric.Robolectric;


public class DjangoTalkImplementationTest extends GeoCamTestCase {

	@Test
	public void shouldEnsureGetTalkMessagesReturnsMessages() throws Exception {
		Dao<GeoCamTalkMessage, Integer> dao = mock(Dao.class);
		
		when(dao.queryForAll()).thenReturn(new ArrayList<GeoCamTalkMessage>());
		
		DjangoTalkImplementation talkImpl = new DjangoTalkImplementation(
				Robolectric.application.getApplicationContext());
		setHiddenField(talkImpl, "dao", dao);
		
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
}
