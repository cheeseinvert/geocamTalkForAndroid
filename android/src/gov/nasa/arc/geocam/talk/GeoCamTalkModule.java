package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.service.AudioPlayer;
import gov.nasa.arc.geocam.talk.service.AudioRecorder;
import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.TalkServer;
import gov.nasa.arc.geocam.talk.service.TalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.GeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.IAudioRecorder;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.ITalkServer;
import gov.nasa.arc.geocam.talk.service.ITalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import gov.nasa.arc.geocam.talk.service.IntentHelper;
import gov.nasa.arc.geocam.talk.service.MessageStore;
import gov.nasa.arc.geocam.talk.service.SiteAuthCookie;
import roboguice.config.AbstractAndroidModule;

import com.google.inject.Scopes;

public class GeoCamTalkModule extends AbstractAndroidModule {
	@Override
	protected void configure() {
		bind(ITalkServer.class).to(TalkServer.class);
		bind(IDatabaseHelper.class).to(DatabaseHelper.class);
		bind(IMessageStore.class).to(MessageStore.class);
		bind(IAudioRecorder.class).to(AudioRecorder.class);
		bind(IAudioPlayer.class).to(AudioPlayer.class);
		bind(ITalkJsonConverter.class).to(TalkJsonConverter.class);
		bind(ISiteAuth.class).to(SiteAuthCookie.class).in(Scopes.SINGLETON);
		bind(IIntentHelper.class).to(IntentHelper.class);
		bind(IGeoCamSynchronizationTimerTask.class).to(GeoCamSynchronizationTimerTask.class).in(
				Scopes.SINGLETON);
	}

}
