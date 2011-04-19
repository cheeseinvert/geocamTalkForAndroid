package gov.nasa.arc.geocam.talk;
import gov.nasa.arc.geocam.talk.service.C2DMReciever;
import gov.nasa.arc.geocam.talk.service.GeoLocationListener;
import gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;

import java.util.List;

import roboguice.application.RoboApplication;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GeoCamTalkRoboApplication extends RoboApplication{
	private GeoLocationListener listener;
	private IGeoCamSynchronizationTimerTask timerTask;
	private LocationManager locationManager;
	private IIntentHelper intentHelper;
	
	private Module module = new GeoCamTalkModule();
	
	@Inject
	C2DMReciever c2dmreceiver;
	
	@Override
	public void onCreate() {
		listener = new GeoLocationListener();
		
		final Injector injector = getInjector();
		locationManager = injector.getInstance(LocationManager.class);	
		
		timerTask = injector.getInstance(IGeoCamSynchronizationTimerTask.class);
		intentHelper = injector.getInstance(IIntentHelper.class);
		
		injector.injectMembers(this);

		setDefaultSettings();
        super.onCreate();

		Log.i("Talk", "Called App onCreate!!!!");
		this.startThreads();
	}
	
	public void startThreads() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
		intentFilter.addCategory("gov.nasa.arc.geocam.talk");
		intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");
		intentFilter.addCategory("gov.nasa.arc.geocam.talk");
		registerReceiver(c2dmreceiver, intentFilter, "com.google.android.c2dm.permission.SEND", null); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 1, listener);
		timerTask.run();
	}
	
	public void stopThreads() {
		unregisterReceiver(c2dmreceiver);
		intentHelper.StopServices();
		intentHelper.UnregisterC2dm();
		timerTask.stopTimer();
		locationManager.removeUpdates(listener);
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
            editor.putString("webapp_username", "");
        if(null == prefs.getString("webapp_password", null))
        	editor.putString("webapp_password", "");
        editor.commit();
    }    
}