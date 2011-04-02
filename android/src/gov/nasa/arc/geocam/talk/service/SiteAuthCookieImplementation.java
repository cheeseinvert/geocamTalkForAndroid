package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import roboguice.inject.InjectResource;

public class SiteAuthCookieImplementation implements SiteAuthInterface {

	@InjectResource(R.string.url_server_root) String serverRootUrl;
	@InjectResource(R.string.url_relative_app) String appPath;
	
	private HttpClient httpClient;
	private HttpCookie sessionIdCookie;
	
	private String username;
	private String password;
	
	@Override
	public void setRoot(String siteRoot) {
		serverRootUrl = siteRoot;	
	}

	@Override
	public void setAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String post(String relativePath, Map<String, String> params)
			throws AuthorizationFailedException, IOException,
			ClientProtocolException {
		ensureAuthenticated();
		httpClient = new DefaultHttpClient();

		return null;
	}

	@Override
	public String get(String relativePath, Map<String, String> params)
			throws AuthorizationFailedException, IOException,
			ClientProtocolException {
		ensureAuthenticated();
		httpClient = new DefaultHttpClient();
		
		HttpGet get = new HttpGet(this.serverRootUrl + "/" + appPath + "/" + relativePath);
		
		// TODO: add param parsing and query string construction as necessary
		
		get.setHeader("Cookie", sessionIdCookie.toString());

		HttpResponse r = httpClient.execute(get);

		InputStream content = r.getEntity().getContent();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(content));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}

		br.close();
		return sb.toString();
	}
	
	private void ensureAuthenticated() throws AuthorizationFailedException, ClientProtocolException, IOException
	{
		if(username == null || password == null)
		{
			throw new AuthorizationFailedException();
		} 
		else
		{
			Date now = new Date();
			if(sessionIdCookie == null || sessionIdCookie.hasExpired())
			{
				// we're not logged in (at least we think. Let's log in)
				login();				
			}
		}
	}
	
	private void login() throws ClientProtocolException, IOException, AuthorizationFailedException
	{
		httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpClientParams.setRedirecting(params, false);
		//params.setParameter("http.protocol.handle-redirects",false);
		
		HttpPost p = new HttpPost(serverRootUrl + "/accounts/login/");
		p.setParams(params);
		
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		
		p.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.ASCII));
		
		HttpResponse r = httpClient.execute(p);
		if(302 == r.getStatusLine().getStatusCode())
		{
			for(Header h:r.getHeaders("Set-Cookie"))
			{
				HttpCookie temp = HttpCookie.parse(h.toString()).get(0);
				if(temp.getName().contentEquals("sessionid"))
				{
					sessionIdCookie = temp;
					return;
				}
			}
			throw new AuthorizationFailedException();
		}
		else
		{
			throw new AuthorizationFailedException();
		}
	}
}
