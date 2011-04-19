package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.R.xml;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import roboguice.activity.RoboPreferenceActivity;
import roboguice.inject.InjectResource;

public class SettingsActivity extends RoboPreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

}
