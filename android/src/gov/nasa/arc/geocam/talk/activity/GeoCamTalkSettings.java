package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.ServerResponse;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import roboguice.activity.RoboPreferenceActivity;
import roboguice.inject.InjectResource;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.inject.Inject;

public class GeoCamTalkSettings extends RoboPreferenceActivity {
	@Inject
	ISiteAuth siteAuth;
	
	@Inject
	SharedPreferences prefs;
	@InjectResource(R.string.url_message_list)
	String urlMessageList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_preferences);
		addPreferencesFromResource(R.xml.prefs);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void onLoginClick(View v) {
		String url = String.format(urlMessageList,0);
		
		try 
		{
			ServerResponse r = siteAuth.get(url, null);
			if (403 != r.getResponseCode())
			{
				Intent intent = new Intent(this, GeoCamTalkActivity.class);
				this.startActivity(intent);
			}		
		} catch (AuthenticationFailedException f) {
			Toast.makeText(this.getApplicationContext(), "Bad Username/Password combination",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.i("Talk", "Error:" + e.getMessage());
		}
		


	}
}