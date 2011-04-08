package gov.nasa.arc.geocam.talk.injected;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import roboguice.config.AbstractAndroidModule;

public class TestInjectedModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		requestStaticInjection(GeoCamTalkMessageArrayAdapter.class);
		requestStaticInjection(DjangoTalk.class);
		bind(IDjangoTalk.class).to(FakeDjangoTalk.class);
		bind(IDjangoTalkJsonConverter.class)
		    .to(DjangoTalkJsonConverter.class);
	}	
}
