package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.ServerResponse;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface ISiteAuth {
	public void setRoot(String siteRoot);

	public ServerResponse post(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException;

	public ServerResponse post(String relativePath, Map<String, String> params, byte[] audioBytes)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException;

	public ServerResponse get(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException;

	public void reAuthenticate() 
			throws ClientProtocolException, AuthenticationFailedException, IOException;
}

class AuthorizationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
}
