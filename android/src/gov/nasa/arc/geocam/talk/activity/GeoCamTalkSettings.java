package gov.nasa.arc.geocam.talk.activity;

import com.google.inject.Inject;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.service.SiteAuthInterface;
import roboguice.activity.RoboPreferenceActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class GeoCamTalkSettings extends RoboPreferenceActivity {
    @Inject SiteAuthInterface siteAuthInterface;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings);
    }
    
	/*
    @Override
    public void onContentChanged()
    {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

    	siteAuthInterface.setAuth(
    			prefs.getString("webapp_username", null), 
    			prefs.getString("webapp_password", null));
    }
*/

}