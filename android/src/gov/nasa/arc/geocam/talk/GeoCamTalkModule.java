package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.AudioPlayerImplementation;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.AudioRecorderImplementation;
import gov.nasa.arc.geocam.talk.service.IAudioRecorder;
import gov.nasa.arc.geocam.talk.service.DatabaseHelperImplementation;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.DjangoTalkImplementation;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverterImplementation;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.SiteAuthCookieImplementation;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import roboguice.config.AbstractAndroidModule;

public class GeoCamTalkModule extends AbstractAndroidModule{

	@Override
	protected void configure() {
		bind(IDjangoTalk.class).to(DjangoTalkImplementation.class);
		bind(IDatabaseHelper.class).to(DatabaseHelperImplementation.class);
		bind(IAudioRecorder.class).to(AudioRecorderImplementation.class);
		bind(IAudioPlayer.class).to(AudioPlayerImplementation.class);
		bind(IDjangoTalkJsonConverter.class)
		    .to(DjangoTalkJsonConverterImplementation.class);
		bind(ISiteAuth.class).toInstance(new SiteAuthCookieImplementation());
	}
	
	
}
