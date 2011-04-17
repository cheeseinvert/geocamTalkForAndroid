package gov.nasa.arc.geocam.talk.service;

import java.util.LinkedList;
import java.util.List;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import roboguice.inject.InjectResource;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;


public class IntentHelper implements IIntentHelper {
	private Context context;

	@InjectResource(R.string.c2dm_sender_address) 
	String c2dmSenderAddress;
	
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
        registrationIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0)); 
        registrationIntent.putExtra("sender", c2dmSenderAddress);
        context.startService(registrationIntent);
	}
	
	@Override
	public void UnregisterC2dm() {
		Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
		unregIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
		context.startService(unregIntent);
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
		Intent pushedMessageIntent = new Intent(this.context, TalkServer.class);
		pushedMessageIntent.setAction(TalkServerIntent.INTENT_PUSHED_MESSAGE.toString());
		pushedMessageIntent.putExtra(
				TalkServerIntent.EXTRA_MESSAGE_ID.toString(),
				messageId);
		context.startService(pushedMessageIntent);
	}
	
	@Override public void Login()
	{
		Intent loginInent = new Intent(this.context, TalkServer.class);
		loginInent.setAction(TalkServerIntent.INTENT_LOGIN.toString());
		Log.i("Talk", "Login intent broadcast");
		context.startService(loginInent);	}
	
	@Override
	public void LoginFailed()
	{
		Intent loginFailedIntent = new Intent(TalkServerIntent.INTENT_LOGIN_FAILED.toString());
		context.sendBroadcast(loginFailedIntent);	
		Log.i("Talk", "Login failed broadcast");
	}

	@Override
	public void StopServices() {
		Intent i = new Intent(this.context, TalkServer.class);
		context.stopService(i);
	}
}
