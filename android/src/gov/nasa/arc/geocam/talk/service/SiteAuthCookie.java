package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.ServerResponse;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import roboguice.inject.InjectResource;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.inject.Inject;

public class SiteAuthCookie implements ISiteAuth {

	@InjectResource(R.string.url_server_root)
	String serverRootUrl;
	
	@InjectResource(R.string.url_relative_app)
	String appPath;
	
	private SharedPreferences sharedPreferences;
	private DefaultHttpClient httpClient;
	private Cookie sessionIdCookie;
	private Context context;
	
	private String username;
	private String password;		
	
	@Inject
	public SiteAuthCookie(Context context) {
		this.context = context;
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public void setRoot(String siteRoot) {
		serverRootUrl = siteRoot;
		
	}

	@Override
	public ServerResponse post(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException {
		return post(relativePath, params, null);
	}

	@Override
	public ServerResponse post(String relativePath, Map<String, String> params, byte[] audioBytes)
			throws AuthenticationFailedException, IOException, ClientProtocolException,
			InvalidParameterException {
		if (params == null) {
			throw new InvalidParameterException("Post parameters are required");
		}
		
		ensureAuthenticated();

		httpClient = new DefaultHttpClient();

		HttpParams httpParams = httpClient.getParams();
		HttpClientParams.setRedirecting(httpParams, false);

		HttpPost post = new HttpPost(this.serverRootUrl + "/" + appPath + "/" + relativePath);
		post.setParams(httpParams);

		HttpEntity httpEntity;
		
		if (audioBytes != null) {
			httpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

			for (String key : params.keySet()) {
				((MultipartEntity) httpEntity).addPart(key, new StringBody(params.get(key)));
			}
			if (audioBytes != null) {
				((MultipartEntity) httpEntity).addPart("audio", new ByteArrayBody(audioBytes,
						"audio/mpeg", "audio.mp4"));
			}
		} else {
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

			for (String key : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
			}

			httpEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.ASCII);
		}
		
		post.setEntity(httpEntity);
		
		httpClient.getCookieStore().addCookie(sessionIdCookie);
		// post.setHeader("Cookie", sessionIdCookie.toString());

		return new ServerResponse(httpClient.execute(post));
		
	}

	@Override
	public ServerResponse get(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException {
		ensureAuthenticated();
		httpClient = new DefaultHttpClient();

		HttpGet get = new HttpGet(this.serverRootUrl + "/" + appPath + "/" + relativePath);

		// TODO: add param parsing and query string construction as necessary

		httpClient.getCookieStore().addCookie(sessionIdCookie);
		// get.setHeader("Cookie", sessionIdCookie.toString());

		return new ServerResponse(httpClient.execute(get));
	}
	
	@Override
	public String getAudioFile(String relativePath, Map<String, String> params)
			throws AuthenticationFailedException, IOException, ClientProtocolException {
		ensureAuthenticated();

		httpClient = new DefaultHttpClient();
		
		HttpPost post = new HttpPost(this.serverRootUrl + relativePath);
		// TODO: add param parsing and query string construction as necessary

		httpClient.getCookieStore().addCookie(sessionIdCookie);

		HttpResponse r = httpClient.execute(post);
		          
        if(r.getFirstHeader("Content-Type").getValue().contains("mp4"))
        {
        	FileOutputStream ostream = new FileOutputStream(context.getFilesDir().toString() + "/tempfile.mp4");
            r.getEntity().writeTo(ostream);
        }
        return (context.getFilesDir().toString() + "/tempfile.mp4");
        
	}

	private void ensureAuthenticated() throws AuthenticationFailedException,
			ClientProtocolException, IOException {
		
		String new_username = sharedPreferences.getString("webapp_username", null);
		String new_password = sharedPreferences.getString("webapp_password", null);

		if (username == null || password == null || new_username == null || new_password == null) {
			username = new_username;
			password = new_password;
			reAuthenticate();
		} else if ( !username.contentEquals(new_username) || !password.contentEquals(new_password))
		{
			reAuthenticate();
		}
		
		if (username == null || password == null) {
			throw new AuthenticationFailedException("Username and/or password not set.");
		} else {
			Date now = new Date();
			if (sessionIdCookie == null || sessionIdCookie.isExpired(now)) {
				// we're not logged in (at least we think. Let's log in)
				login(username, password);
			}
		}
	}

	public void login(String username, String password) throws ClientProtocolException,
			IOException, AuthenticationFailedException {
		httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpClientParams.setRedirecting(params, false);

		HttpPost p = new HttpPost(serverRootUrl + "/accounts/login/");
		p.setParams(params);

		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		p.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.ASCII));

		HttpResponse r = httpClient.execute(p);
		if (302 == r.getStatusLine().getStatusCode()) {
			for (Cookie c : httpClient.getCookieStore().getCookies()) {
				if (c.getName().contains("sessionid")) {
					sessionIdCookie = c;
					return;
				}
			}
			throw new AuthenticationFailedException(
					"Session cookie was missing from server login response.");
		} else if (200 == r.getStatusLine().getStatusCode()){
			return;
		} else {
			throw new AuthenticationFailedException("Got unexpected response code from server: "
					+ r.getStatusLine().getStatusCode());
		}
	}
	
	public void logout()throws AuthenticationFailedException, ClientProtocolException, IOException
	{
		httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpClientParams.setRedirecting(params, false);

		HttpPost p = new HttpPost(serverRootUrl + "/accounts/logout/");
		p.setParams(params);

		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		p.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.ASCII));

		HttpResponse r = httpClient.execute(p);
		if (302 == r.getStatusLine().getStatusCode()) {
			sessionIdCookie = null;
			Editor  editor = sharedPreferences.edit();
			editor.clear();
			editor.commit();
			return;

		} else {
			throw new AuthenticationFailedException("Got unexpected response code from server: "
					+ r.getStatusLine().getStatusCode());
		}
	}

	@Override
	public void reAuthenticate() throws ClientProtocolException, AuthenticationFailedException, IOException {
		sessionIdCookie = null;
	}
}
