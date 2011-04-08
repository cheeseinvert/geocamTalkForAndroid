package gov.nasa.arc.geocam.talk.injected;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.DjangoTalkImplementation;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterImplementation;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import roboguice.config.AbstractAndroidModule;

public class TestInjectedModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		requestStaticInjection(GeoCamTalkMessageArrayAdapter.class);
		requestStaticInjection(DjangoTalkImplementation.class);
		bind(IDjangoTalk.class).to(FakeDjangoTalkmplementation.class);
		bind(IDjangoTalkJsonConverter.class)
		    .to(DjangoTalkJsonConverterImplementation.class);
	}	
}
