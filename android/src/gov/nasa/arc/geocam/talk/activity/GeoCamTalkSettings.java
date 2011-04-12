package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;

import com.google.inject.Inject;

public class GeoCamTalkSettings extends RoboPreferenceActivity {
	@Inject
	ISiteAuth siteAuth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_preferences);
		addPreferencesFromResource(R.xml.prefs);
	}
}