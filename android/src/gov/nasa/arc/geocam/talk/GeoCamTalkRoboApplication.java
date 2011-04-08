package gov.nasa.arc.geocam.talk;
import java.util.List;

import roboguice.application.RoboApplication;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.inject.Module;

public class GeoCamTalkRoboApplication extends RoboApplication{
    private Module module = new GeoCamTalkModule(this);

	@Override
	public void onCreate() {
		setDefaultSettings();
        super.onCreate();
	}
    
	protected void addApplicationModules(List<Module> modules) {
        modules.add(this.module);
    }
    
    public void setModule(Module module) {
        this.module = module;
        
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
