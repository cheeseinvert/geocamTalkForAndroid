package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.service.DatabaseHelper;
import gov.nasa.arc.geocam.talk.service.DjangoTalk;
import gov.nasa.arc.geocam.talk.service.DjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IDatabaseHelper;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.IDjangoTalkJsonConverter;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.MessageStore;
import roboguice.config.AbstractAndroidModule;

public class TestInjectedModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		requestStaticInjection(GeoCamTalkMessageArrayAdapter.class);
		requestStaticInjection(DjangoTalk.class);
		bind(IDjangoTalk.class).to(FakeDjangoTalk.class);
		bind(IDjangoTalkJsonConverter.class)
		    .to(DjangoTalkJsonConverter.class);
		bind(IMessageStore.class).to(MessageStore.class);
		bind(IDatabaseHelper.class).to(DatabaseHelper.class);
		
	}	
}
