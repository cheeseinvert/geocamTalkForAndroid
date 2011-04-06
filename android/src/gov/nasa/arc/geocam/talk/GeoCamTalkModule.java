package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.AudioPlayerImplementation;
import gov.nasa.arc.geocam.talk.service.AudioPlayerInterface;
import gov.nasa.arc.geocam.talk.service.AudioRecorderImplementation;
import gov.nasa.arc.geocam.talk.service.AudioRecorderInterface;
import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.DjangoTalkImplementation;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterImplementation;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterInterface;
import gov.nasa.arc.geocam.talk.service.SiteAuthCookieImplementation;
import gov.nasa.arc.geocam.talk.service.SiteAuthInterface;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import roboguice.config.AbstractAndroidModule;

public class GeoCamTalkModule extends AbstractAndroidModule{

	@Override
	protected void configure() {
		requestStaticInjection(GeoCamTalkMessageArrayAdapter.class);
		requestStaticInjection(DjangoTalkImplementation.class);
		requestStaticInjection(AudioRecorderImplementation.class);
		requestStaticInjection(AudioPlayerImplementation.class);
		bind(DjangoTalkInterface.class).to(DjangoTalkImplementation.class);

		bind(AudioRecorderInterface.class).to(AudioRecorderImplementation.class);
		bind(AudioPlayerInterface.class).to(AudioPlayerImplementation.class);
		bind(DjangoTalkJsonConverterInterface.class)
		    .to(DjangoTalkJsonConverterImplementation.class);
		bind(SiteAuthInterface.class).toInstance(new SiteAuthCookieImplementation());
		
		//bind(HttpClient.class).toInstance(new DefaultHttpClient());
	}
	
	
}
