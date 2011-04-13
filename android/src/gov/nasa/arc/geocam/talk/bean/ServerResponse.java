package gov.nasa.arc.geocam.talk.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class ServerResponse {
	
	private int responseCode;
	private String content;
	
	public ServerResponse(int responseCode, String content)
	{
		this.responseCode = responseCode;
		this.content = content;
	}
	
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
	
	public int getResponseCode() {
		return responseCode;
	}
	public String getContent() {
		return content;
	}
}
