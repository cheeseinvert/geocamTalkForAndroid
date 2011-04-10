package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;

public class IntentHelper implements IIntentHelper {
	private Context context;

	@Inject
	public IntentHelper(Context context) {
		this.context = context;
	}
	
	public void Synchronize() {
		Intent synchronizeIntent = new Intent(this.context, TalkServer.class);
		synchronizeIntent.setAction(TalkServerIntent.SYNCHRONIZE.toString());
		context.startService(synchronizeIntent);
	}

	@Override
	public void BroadcastNewMessages() {
		Intent newMsgIntent = new Intent(TalkServerIntent.NEW_MESSAGES.toString());
		this.context.sendBroadcast(newMsgIntent);		
	}

	@Override
	public void RegisterC2dm() {
		
	}

	@Override
	public void StoreC2dmRegistrationId(String registrationId) {
			
	}
}
