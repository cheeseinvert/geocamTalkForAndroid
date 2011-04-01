package gov.nasa.arc.geocam.talk.activity;

import roboguice.activity.RoboPreferenceActivity;
import gov.nasa.arc.geocam.talk.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class GeoCamTalkSettings extends RoboPreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultSettings();
        addPreferencesFromResource(R.layout.settings);

    }

    private void setDefaultSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        if(null == prefs.getString("webapp_username", null))
            prefs.edit().putString("webapp_username", getString(R.string.default_username));
        if(null == prefs.getString("webapp_password", null))
            prefs.edit().putString("webapp_password", getString(R.string.default_password));

    }

}