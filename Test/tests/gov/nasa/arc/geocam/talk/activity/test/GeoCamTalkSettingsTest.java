package gov.nasa.arc.geocam.talk.activity.test;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkSettings;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import org.junit.Test;

import com.xtremelabs.robolectric.Robolectric;

import roboguice.application.RoboApplication;
import roboguice.inject.InjectResource;
import roboguice.inject.RoboApplicationProvider;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class GeoCamTalkSettingsTest extends GeoCamTestCase {
	
	@InjectResource(R.string.default_username) String defaultUsername;
	@InjectResource(R.string.default_password) String defaultPassword;
	
	@Test
	public void testDefaultValuesSetWhenNull()
	{
		GeoCamTalkActivity activity = new GeoCamTalkActivity();
		
		SharedPreferences sharedPrefs = 
			PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		// TODO: Need to find a way to get the proper context here. Moving on as the functionality was proven in the c2dm prototype		
		
		assertNull(sharedPrefs.getString("webapp_username", null));
		assertNull(sharedPrefs.getString("webapp_password", null));
		
		activity.onCreate(null);

		sharedPrefs = 
			PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());		
		
		assertEquals(
				defaultUsername,
				sharedPrefs.getString("webapp_username", null));
		assertEquals(
				defaultPassword,
				sharedPrefs.getString("webapp_password", null));
		
	}
}
