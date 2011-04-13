package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.GeoCamTalkRoboApplication;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import org.junit.runners.model.InitializationError;

import roboguice.inject.ContextScope;
import android.app.Application;

import com.google.inject.Injector;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class InjectedTestRunner extends RobolectricTestRunner {
	public InjectedTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

    @Override 
    protected Application createApplication() {
        GeoCamTalkRoboApplication application =
        	(GeoCamTalkRoboApplication)super.createApplication();
        
        application.setModule(new TestInjectedModule());
        return application;
    }
	
	@Override 
	public void prepareTest(Object test){
		 GeoCamTalkRoboApplication application =
			 (GeoCamTalkRoboApplication)Robolectric.application;
		 
	     Injector injector = application.getInjector();
	     ContextScope scope = injector.getInstance(ContextScope.class);
	     scope.enter(application);
	     
	     injector.injectMembers(test);
	}
	
}