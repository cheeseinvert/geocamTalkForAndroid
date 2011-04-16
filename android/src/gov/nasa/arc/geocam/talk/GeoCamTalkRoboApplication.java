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
	C2DMReciever cd2mReciever;
	
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
		intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");
		intentFilter.addCategory("gov.nasa.arc.geocam.talk");
		registerReceiver(cd2mReciever, intentFilter);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 1, listener);
		intentHelper.RegisterC2dm();
		timerTask.resetTimer();
	}
	
	public void stopThreads() {
		unregisterReceiver(cd2mReciever);
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
            editor.putString("webapp_username", getString(R.string.default_username));
        if(null == prefs.getString("webapp_password", null))
        	editor.putString("webapp_password", getString(R.string.default_password));
        editor.commit();
    }    
}
