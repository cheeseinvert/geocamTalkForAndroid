package gov.nasa.arc.geocam.talk.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalkImplementation;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;

import android.net.http.AndroidHttpClient;


public class DjangoTalkImplementationTest extends GeoCamTestCase {

	@Test
	public void shouldEnsureGetTalkMessagesReturnsMessages() throws Exception {
		DjangoTalkImplementation talkImpl = new DjangoTalkImplementation();
		
		DjangoTalkJsonConverterInterface jsonConv = 
			mock(DjangoTalkJsonConverterInterface.class);
		
		HttpClient httpClient = mock(AndroidHttpClient.class);
		HttpResponse response = mock(HttpResponse.class);
		HttpEntity entity = mock(HttpEntity.class);
		when(response.getEntity()).thenReturn(entity);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
		setHiddenField(talkImpl, "httpClient", httpClient);
		
		List<GeoCamTalkMessage> expectedList = new ArrayList<GeoCamTalkMessage>();
		when(jsonConv.deserializeList(anyString())).thenReturn(expectedList);
		setHiddenField(talkImpl, "jsonConverter", jsonConv);
		
		setHiddenField(talkImpl, "talkUrl", "http://xyz.com/");
		setHiddenField(talkImpl, "talkMessagesJson", "dude.json");
		
		// act
		List<GeoCamTalkMessage> talkMessages = talkImpl.getTalkMessages();
		
		// assert
		assertEquals(expectedList, talkMessages);
		verify(jsonConv).deserializeList(anyString());
	}
}
