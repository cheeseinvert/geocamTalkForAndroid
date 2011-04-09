package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface ISiteAuth {
    public void setRoot(String siteRoot);
    public String post(String relativePath, Map<String, String> params) 
            throws AuthorizationFailedException, IOException, ClientProtocolException;
    public String get(String relativePath, Map<String, String> params) 
            throws AuthorizationFailedException, IOException, ClientProtocolException;
}

class AuthorizationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
}
