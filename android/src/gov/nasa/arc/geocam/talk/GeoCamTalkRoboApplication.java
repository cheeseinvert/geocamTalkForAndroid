package gov.nasa.arc.geocam.talk;
import roboguice.application.RoboApplication;
import com.google.inject.Module;

import java.util.List;

public class GeoCamTalkRoboApplication extends RoboApplication{
    private Module module = new GeoCamTalkModule();

	protected void addApplicationModules(List<Module> modules) {
        modules.add(this.module);
    }
    
    public void setModule(Module module) {
        this.module = module;
        
    }
}
