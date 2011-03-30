package gov.nasa.arc.geocam.talk.injected;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterImplementation;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;
import roboguice.config.AbstractAndroidModule;

public class TestInjectedModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		requestStaticInjection(GeoCamTalkMessageArrayAdapter.class);
		bind(DjangoTalkInterface.class).to(FakeDjangoTalkmplementation.class);
		bind(DjangoTalkJsonConverterInterface.class)
		    .to(DjangoTalkJsonConverterImplementation.class);
	}	
}
