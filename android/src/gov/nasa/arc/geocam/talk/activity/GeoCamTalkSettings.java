package gov.nasa.arc.geocam.talk.activity;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
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
		addPreferencesFromResource(R.layout.settings);

	}
}