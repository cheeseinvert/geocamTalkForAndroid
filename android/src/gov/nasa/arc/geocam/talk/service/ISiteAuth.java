package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.ServerResponse;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISiteAuth.
 */
public interface ISiteAuth {
	
	/**
	 * Sets the root.
	 *
	 * @param siteRoot the new root
	 */
	public void setRoot(String siteRoot);

	/**
	 * Post.
	 *
	 * @param relativePath the relative path
	 * @param params the params
	 * @return the server response
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClientProtocolException the client protocol exception
	 * @throws InvalidParameterException the invalid parameter exception
	 */
	public ServerResponse post(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException;

	/**
	 * Post.
	 *
	 * @param relativePath the relative path
	 * @param params the params
	 * @param audioBytes the audio bytes
	 * @return the server response
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClientProtocolException the client protocol exception
	 * @throws InvalidParameterException the invalid parameter exception
	 */
	public ServerResponse post(String relativePath, Map<String, String> params, byte[] audioBytes)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException;

	/**
	 * Gets the.
	 *
	 * @param relativePath the relative path
	 * @param params the params
	 * @return the server response
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClientProtocolException the client protocol exception
	 */
	public ServerResponse get(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException;

    /**
     * Gets the audio file.
     *
     * @param relativePath the relative path
     * @param params the params
     * @return the audio file
     * @throws AuthenticationFailedException the authentication failed exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClientProtocolException the client protocol exception
     */
    public String getAudioFile(String relativePath, Map<String, String> params) 
			throws AuthenticationFailedException, IOException, ClientProtocolException;	
	
	/**
	 * Re authenticate.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void reAuthenticate() 
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Logout and unregister.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void logoutAndUnregister()
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Login.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void login()
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Checks if is logged in.
	 *
	 * @return true, if is logged in
	 */
	public boolean isLoggedIn();
}

class AuthorizationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
}
