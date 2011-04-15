package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void onLoginClick(View v) {
			storeCredentials();
			intentHelper.Synchronize();
			UIUtils.goHome(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		pullCredentials();
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