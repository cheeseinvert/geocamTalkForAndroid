package gov.nasa.arc.geocam.talk.service.test;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.j256.ormlite.dao.Dao;
import com.xtremelabs.robolectric.Robolectric;


public class DjangoTalkTest extends GeoCamTestCase {

	@Test
	public void shouldEnsureGetTalkMessagesReturnsMessages() throws Exception {
		Dao<GeoCamTalkMessage, Integer> dao = mock(Dao.class);
		
		when(dao.queryForAll()).thenReturn(new ArrayList<GeoCamTalkMessage>());
		
		DjangoTalk talkImpl = new DjangoTalk(
				Robolectric.application.getApplicationContext());
		setHiddenField(talkImpl, "dao", dao);
		
		IDjangoTalkJsonConverter jsonConv = 
			mock(IDjangoTalkJsonConverter.class);
		
		ISiteAuth siteauth =
			mock(ISiteAuth.class);
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
