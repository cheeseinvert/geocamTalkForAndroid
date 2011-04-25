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
	 * Post the message to the server if no audio file is present if the 
	 * user is logged in.
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
	 * Post the message to the server if there is an audio file present and we are currently 
	 * logged in.
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
	 * Creates a HTTP get call to retrieve information from the server.
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
     * Gets the audio file from a message on the server.
     *
     * @param relativePath the relative path
     * @param params the params
     * @return The filename of the audio retrieved from the server
     * @throws AuthenticationFailedException the authentication failed exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClientProtocolException the client protocol exception
     */
    public String getAudioFile(String relativePath, Map<String, String> params) 
			throws AuthenticationFailedException, IOException, ClientProtocolException;	
	
	/**
	 * Set the session cookie to null and force a new login.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void reAuthenticate() 
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Logout the user from the server and unregister from the C2DM.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void logoutAndUnregister()
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Login to the server with the supplied username and password.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void login()
			throws ClientProtocolException, AuthenticationFailedException, IOException;
	
	/**
	 * Checks if the user logged in.
	 *
	 * @return true, if the user is logged in
	 */
	public boolean isLoggedIn();
}

class AuthorizationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
}
