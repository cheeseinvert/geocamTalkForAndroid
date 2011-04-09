package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.DjangoTalkIntent;
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
		Intent synchronizeIntent = new Intent(this.context, DjangoTalk.class);
		synchronizeIntent.setAction(DjangoTalkIntent.SYNCHRONIZE.toString());
		context.startService(synchronizeIntent);
	}

	@Override
	public void BroadcastNewMessages() {
		Intent newMsgIntent = new Intent(DjangoTalkIntent.NEW_MESSAGES.toString());
		this.context.sendBroadcast(newMsgIntent);		
	}
}
