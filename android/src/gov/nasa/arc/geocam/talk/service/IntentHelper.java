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
		synchronizeIntent.setAction(TalkServerIntent.INTENT_SYNCHRONIZE.toString());
		context.startService(synchronizeIntent);
	}

	@Override
	public void BroadcastNewMessages() {
		Intent newMsgIntent = new Intent(TalkServerIntent.INTENT_NEW_MESSAGES.toString());
		this.context.sendBroadcast(newMsgIntent);
	}

	@Override
	public void RegisterC2dm() {
		Intent registerIntent = new Intent(this.context, TalkServer.class);
		registerIntent.setAction(TalkServerIntent.INTENT_C2DM_REGISTER.toString());
		context.startService(registerIntent);
	}

	@Override
	public void StoreC2dmRegistrationId(String registrationId) {
		Intent storeRegistrationIdIntent = new Intent(this.context, TalkServer.class);
		storeRegistrationIdIntent.setAction(TalkServerIntent.INTENT_STORE_C2DM_ID.toString());
		storeRegistrationIdIntent.putExtra(
				TalkServerIntent.EXTRA_MESSAGE_ID.toString(),
				registrationId);
		context.startService(storeRegistrationIdIntent);
	}
}
