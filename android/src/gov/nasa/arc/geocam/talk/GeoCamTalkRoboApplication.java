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

/**
 * The Class GeoCamTalkRoboApplication. Used to initiate the application and
 * provide various global utilities and helpers.
 */
public class GeoCamTalkRoboApplication extends RoboApplication {

	/** The listener. */
	private GeoLocationListener listener;

	/** The timer task. */
	private IGeoCamSynchronizationTimerTask timerTask;

	/** The location manager. */
	private LocationManager locationManager;

	/** The intent helper. */
	private IIntentHelper intentHelper;

	/** The {@link GeoCamTalkModule} to use for bindingd. */
	private Module module = new GeoCamTalkModule();

	/** The c2dmreceiver. */
	@Inject
	C2DMReciever c2dmreceiver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
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

	/**
	 * Start other non-main threads that may continue running after the current activity pauses.
	 */
	public void startThreads() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
		intentFilter.addCategory("gov.nasa.arc.geocam.talk");
		intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");
		intentFilter.addCategory("gov.nasa.arc.geocam.talk");
		registerReceiver(c2dmreceiver, intentFilter,
				"com.google.android.c2dm.permission.SEND", null);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60000, 1, listener);
		timerTask.run();
	}

	/**
	 * Stop other non-main threads that may continue running after the current activity pauses.
	 */
	public void stopThreads() {
		unregisterReceiver(c2dmreceiver);
		intentHelper.StopServices();
		intentHelper.UnregisterC2dm();
		timerTask.stopTimer();
		locationManager.removeUpdates(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * roboguice.application.RoboApplication#addApplicationModules(java.util
	 * .List)
	 */
	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(this.module);
	}

	/**
	 * Sets the module.
	 * 
	 * @param module
	 *            the new module
	 */
	public void setModule(Module module) {
		this.module = module;

	}

	/**
	 * Gets the most recent geoLocation broadcast by the location manager.
	 * 
	 * @return A Location object containing the most recent location captured by the phone.
	 */
	public Location getLocation() {
		return listener.getLocation();
	}

	/**
	 * Sets the default settings.
	 */
	private void setDefaultSettings() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		Editor editor = prefs.edit();

		if (null == prefs.getString("webapp_username", null))
			editor.putString("webapp_username", "");
		if (null == prefs.getString("webapp_password", null))
			editor.putString("webapp_password", "");
		editor.commit();
	}
}