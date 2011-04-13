package gov.nasa.arc.geocam.talk.test;

public class GeocamTalkModuleTest{};

/*	TODO: Figure this out!

package gov.nasa.arc.geocam.talk.test;

import gov.nasa.arc.geocam.talk.GeoCamTalkModule;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;

import org.junit.Test;

import android.net.http.AndroidHttpClient;

import com.google.inject.Guice;
import com.google.inject.Injector;

import roboguice.*;
import roboguice.inject.InjectorProvider;

public class GeocamTalkModuleTest extends GeoCamTestCase{
    @Test
    public void shouldBindDjangoTalkInterface() throws Exception
	{
		//arrange
		GeoCamTalkModule module = new GeoCamTalkModule();
		
		Injector injector = Guice.createInjector(new GeoCamTalkModule());

		// act & assert (by non-exception)
        injector.getProvider(DjangoTalkInterface.class);
	}
	
	@Test
	public void shouldBindAwesomeJsonConverterInterface() throws Exception
	{
		//arrange
		Injector injector = Guice.createInjector(new GeoCamTalkModule());

		// act & assert (by non-exception)
		injector.getProvider(DjangoTalkJsonConverterInterface.class);
	}
	
	@Test
	public void shouldBindAndroidHttpClient() throws Exception
	{
		//arrange
		Injector injector = Guice.createInjector(new GeoCamTalkModule());

		// act & assert (by non-exception)
		injector.getProvider(AndroidHttpClient.class);
	}
}*/
