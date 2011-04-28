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



/**
 * The Class GeoCamTalkLogon.
 */
public class GeoCamTalkLogon extends RoboActivity {
	
	/** The site auth. */
	@Inject
	ISiteAuth siteAuth;
	
	/** The prefs. */
	@Inject
	SharedPreferences prefs;
	
	/** The username edit text. */
	@InjectView(R.id.username)
	TextView usernameEditText;
	
	/** The password edit text. */
	@InjectView(R.id.password)
	TextView passwordEditText;
	
	/** The url message list. */
	@InjectResource(R.string.url_message_list)
	String urlMessageList;
	
	/** The intent helper. */
	@Inject
	IIntentHelper intentHelper;

	/* (non-Javadoc)
	 * @see roboguice.activity.RoboActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon);
		pullCredentials();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	/**
	 * On login click.
	 *
	 * @param v the View from which the click was initiated
	 */
	public void onLoginClick(View v) {
			storeCredentials();
			intentHelper.Synchronize();
			UIUtils.goHome(this);
	}

	/* (non-Javadoc)
	 * @see roboguice.activity.RoboActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		pullCredentials();
	}

	/**
	 * Pull credentials.
	 */
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
	
	/* (non-Javadoc)
	 * @see roboguice.activity.RoboActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		storeCredentials();
	}

	/**
	 * Store credentials.
	 */
	private void storeCredentials() {
		prefs.edit().remove("username").commit();
		prefs.edit().remove("password").commit();
		prefs.edit().putString("username", usernameEditText.getText().toString()).commit();
		prefs.edit().putString("password", passwordEditText.getText().toString()).commit();
	}
}