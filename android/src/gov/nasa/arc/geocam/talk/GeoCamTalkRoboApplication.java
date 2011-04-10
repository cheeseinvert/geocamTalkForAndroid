package gov.nasa.arc.geocam.talk;
import gov.nasa.arc.geocam.talk.service.GeoLocationListener;
import gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;

import java.util.List;

import roboguice.application.RoboApplication;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import com.google.inject.Injector;
import com.google.inject.Module;

public class GeoCamTalkRoboApplication extends RoboApplication{
	private GeoLocationListener listener;
	
	private Module module = new GeoCamTalkModule();

	@Override
	public void onCreate() {
		listener = new GeoLocationListener();
		
		final Injector injector = getInjector();
		LocationManager locationManager = injector.getInstance(LocationManager.class);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 1, listener);		
		
		IGeoCamSynchronizationTimerTask timerTask = injector.getInstance(IGeoCamSynchronizationTimerTask.class);
		IIntentHelper intentHelper = injector.getInstance(IIntentHelper.class);
		intentHelper.RegisterC2dm();
		
		injector.injectMembers(this);

		setDefaultSettings();
        super.onCreate();
	}
    
	protected void addApplicationModules(List<Module> modules) {
        modules.add(this.module);
    }
    
    public void setModule(Module module) {
        this.module = module;
        
    }
    
    public Location getLocation() {
    	return listener.getLocation();
    }
    
    private void setDefaultSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Editor editor = prefs.edit();
        
        if(null == prefs.getString("webapp_username", null))
            editor.putString("webapp_username", getString(R.string.default_username));
        if(null == prefs.getString("webapp_password", null))
        	editor.putString("webapp_password", getString(R.string.default_password));
        editor.commit();
    }    
}
