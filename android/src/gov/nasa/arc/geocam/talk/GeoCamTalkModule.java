package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.AudioPlayer;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.AudioRecorder;
import gov.nasa.arc.geocam.talk.service.IAudioRecorder;
import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.SiteAuthCookie;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import roboguice.config.AbstractAndroidModule;

public class GeoCamTalkModule extends AbstractAndroidModule{

	@Override
	protected void configure() {
		bind(IDjangoTalk.class).to(DjangoTalk.class);
		bind(IDatabaseHelper.class).to(DatabaseHelper.class);
		bind(IAudioRecorder.class).to(AudioRecorder.class);
		bind(IAudioPlayer.class).to(AudioPlayer.class);
		bind(IDjangoTalkJsonConverter.class)
		    .to(DjangoTalkJsonConverter.class);
		bind(ISiteAuth.class).toInstance(new SiteAuthCookie());
	}
	
	
}
