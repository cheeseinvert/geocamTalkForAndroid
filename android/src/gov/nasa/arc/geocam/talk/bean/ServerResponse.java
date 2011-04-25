package gov.nasa.arc.geocam.talk.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerResponse is an object which provides 
 * easy access to the HTTP response code and content 
 * from a HTTP response.
 */
public class ServerResponse {
	
	/** The response code for the HTTP call. */
	private int responseCode;
	
	/** The content of the HTTP call. */
	private String content;
	
	/**
	 * Instantiates a new server response.
	 *
	 * @param responseCode the response code
	 * @param content the content
	 */
	public ServerResponse(int responseCode, String content)
	{
		this.responseCode = responseCode;
		this.content = content;
	}
	
	/**
	 * Instantiates a new server response from the httpResponse passed in.
	 *
	 * @param httpResponse the http response
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ServerResponse(HttpResponse httpResponse) throws IllegalStateException, IOException
	{
		this.responseCode = httpResponse.getStatusLine().getStatusCode();
		
		InputStream content = httpResponse.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(content));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {sb.append(line + "\n");}
		
		this.content = sb.toString();
	}
	
	/**
	 * Gets the response code value from the ServerResponse object.
	 *
	 * @return the response code
	 */
	public int getResponseCode() {
		return responseCode;
	}
	
	/**
	 * Gets the content value from the ServerResponse object.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
}
