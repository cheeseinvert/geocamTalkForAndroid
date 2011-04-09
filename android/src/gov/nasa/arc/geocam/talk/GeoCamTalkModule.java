package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.service.AudioPlayer;
import gov.nasa.arc.geocam.talk.service.AudioRecorder;
import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.GeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.IAudioRecorder;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import gov.nasa.arc.geocam.talk.service.IntentHelper;
import gov.nasa.arc.geocam.talk.service.MessageStore;
import gov.nasa.arc.geocam.talk.service.SiteAuthCookie;
import roboguice.application.RoboApplication;
import roboguice.config.AbstractAndroidModule;

public class GeoCamTalkModule extends AbstractAndroidModule{

	private RoboApplication application;
	
	public GeoCamTalkModule(final RoboApplication applicaiton)
	{
		this.application = applicaiton;
	}
	
	@Override
	protected void configure() {
		bind(IDjangoTalk.class).to(DjangoTalk.class);
		bind(IDatabaseHelper.class).to(DatabaseHelper.class);
		bind(IMessageStore.class).to(MessageStore.class);
		bind(IAudioRecorder.class).to(AudioRecorder.class);
		bind(IAudioPlayer.class).to(AudioPlayer.class);
		bind(IDjangoTalkJsonConverter.class).to(DjangoTalkJsonConverter.class);
		bind(ISiteAuth.class).toInstance(new SiteAuthCookie(application.getApplicationContext()));
		bind(IIntentHelper.class).to(IntentHelper.class);
		bind(IGeoCamSynchronizationTimerTask.class).to(GeoCamSynchronizationTimerTask.class);
	}
	
	
}
