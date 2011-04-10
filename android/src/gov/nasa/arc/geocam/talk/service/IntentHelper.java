package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import roboguice.inject.InjectResource;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;


public class IntentHelper implements IIntentHelper {
	private Context context;

	@InjectResource(R.string.c2dm_sender_address) 
	private String c2dmSenderAddress;
	
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
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
        registrationIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0)); // boilerplate
        registrationIntent.putExtra("sender", c2dmSenderAddress);
        context.startService(registrationIntent);	
	}

	@Override
	public void StoreC2dmRegistrationId(String registrationId) {
		Intent storeRegistrationIdIntent = new Intent(this.context, TalkServer.class);
		storeRegistrationIdIntent.setAction(TalkServerIntent.INTENT_STORE_C2DM_ID.toString());
		storeRegistrationIdIntent.putExtra(
				TalkServerIntent.EXTRA_REGISTRATION_ID.toString(),
				registrationId);
		context.startService(storeRegistrationIdIntent);
	}

	@Override
	public void PushedMessage(String messageId) {
		Intent storeRegistrationIdIntent = new Intent(this.context, TalkServer.class);
		storeRegistrationIdIntent.setAction(TalkServerIntent.INTENT_PUSHED_MESSAGE.toString());
		storeRegistrationIdIntent.putExtra(
				TalkServerIntent.EXTRA_MESSAGE_ID.toString(),
				messageId);
		context.startService(storeRegistrationIdIntent);
	}
}
