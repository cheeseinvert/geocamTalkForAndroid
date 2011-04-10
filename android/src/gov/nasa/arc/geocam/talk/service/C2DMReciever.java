package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * This BroadcastReciever is filtered to only receive c2dm-related messages and
 * delegate them to the appropriate activity / service.
 */
public class C2DMReciever extends BroadcastReceiver {

	private IIntentHelper intentHelper;

	public C2DMReciever(IIntentHelper intentHelper) {
		this.intentHelper = intentHelper;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			// we've received a registration response
			handleRegistration(context, intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			// we've received a push notification w/ paylaod
			handleMessage(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		String registration = intent.getStringExtra(TalkServerIntent.EXTRA_REGISTRATION_ID
				.toString());
		if (intent.getStringExtra("error") != null) {
			Log.e("Talk", "Registration Error");
		} else if (intent.getStringExtra("unregistered") != null) {
			Log.e("Talk", "Registration not active!");
		} else if (registration != null) {
			Log.i("Talk", "Registration received!");
			intentHelper.StoreC2dmRegistrationId(registration);
		}
	}

	private void handleMessage(Context context, Intent intent) {
		String messageId = intent.getStringExtra(TalkServerIntent.EXTRA_MESSAGE_ID.toString());
		if (messageId != null) {
			Log.i("Talk", "Registration received!");
			intentHelper.PushedMessage(messageId);
		} else {
			Log.e("Talk", "Message received without a message id!");
		}
	}
}
