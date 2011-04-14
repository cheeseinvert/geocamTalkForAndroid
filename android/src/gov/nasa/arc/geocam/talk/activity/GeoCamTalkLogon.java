package gov.nasa.arc.geocam.talk.activity;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;



public class GeoCamTalkLogon extends RoboActivity {
	@Inject
	ISiteAuth siteAuth;
	
	@Inject
	SharedPreferences prefs;
	
	@InjectView(R.id.username)
	TextView usernameEditText;
	
	@InjectView(R.id.password)
	TextView passwordEditText;
	
	@InjectResource(R.string.url_message_list)
	String urlMessageList;
	
	@Inject
	IIntentHelper intentHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon);
		
		pullCredentials();
		
		try{
			attemptLogon(); // probabaly a better way to do this, but this works.
			intentHelper.RegisterC2dm();
			UIUtils.goHome(this);
		}
		catch(Exception e)
		{
			// couldn't log on with default username password, do nothing
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void onLoginClick(View v) {
	

		try 
		{
			attemptLogon();
			intentHelper.RegisterC2dm();
			storeCredentials();
			UIUtils.goHome(this);
		} catch (AuthenticationFailedException f) {
			Toast.makeText(this, "Bad Username/Password combination",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.i("Talk", "Error:" + e.getMessage());
		}
		
	}

	private void attemptLogon() throws ClientProtocolException,
			AuthenticationFailedException, IOException {
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		siteAuth.login(username, password);
	}
	
	@Override
	protected void onResume() {
		pullCredentials();
		super.onResume();
	}

	private void pullCredentials() {
		String username = prefs.getString("username", null);
		String password = prefs.getString("password", null);
		
		if(username != null){
			usernameEditText.setText(username);
		}
		if(password != null){
			passwordEditText.setText(password);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		storeCredentials();
		
	}

	private void storeCredentials() {
		prefs.edit().remove("username").commit();
		prefs.edit().remove("password").commit();
		prefs.edit().putString("username", usernameEditText.getText().toString()).commit();
		prefs.edit().putString("password", passwordEditText.getText().toString()).commit();
	}
}