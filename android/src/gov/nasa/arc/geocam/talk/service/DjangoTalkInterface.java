package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public interface DjangoTalkInterface {
	public List<GeoCamTalkMessage> getTalkMessages();
	public void setAuth(String username, String password);
	void createTalkMessage(GeoCamTalkMessage message, String filename)
			throws ClientProtocolException, AuthenticationFailedException,
			IOException;
}
