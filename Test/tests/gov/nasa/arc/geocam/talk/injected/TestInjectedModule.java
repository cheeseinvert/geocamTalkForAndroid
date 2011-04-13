package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.TalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.GeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.ITalkServer;
import gov.nasa.arc.geocam.talk.service.ITalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IGeoCamSynchronizationTimerTask;
import gov.nasa.arc.geocam.talk.service.IIntentHelper;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.IntentHelper;
import gov.nasa.arc.geocam.talk.service.MessageStore;
import roboguice.config.AbstractAndroidModule;

public class TestInjectedModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(ITalkServer.class).to(FakeTalkServer.class);
		bind(ITalkJsonConverter.class).to(TalkJsonConverter.class);
		bind(IMessageStore.class).to(MessageStore.class);
		bind(IDatabaseHelper.class).to(DatabaseHelper.class);
		bind(IIntentHelper.class).to(IntentHelper.class);
		bind(IGeoCamSynchronizationTimerTask.class).to(GeoCamSynchronizationTimerTask.class);
	}	
}
