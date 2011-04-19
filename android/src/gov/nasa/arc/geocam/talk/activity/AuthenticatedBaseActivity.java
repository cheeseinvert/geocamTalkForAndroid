package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.GeoCamTalkRoboApplication;
import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.TalkServerIntent;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import roboguice.activity.RoboActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.inject.Inject;

public class AuthenticatedBaseActivity extends RoboActivity {

	@Inject
	ISiteAuth siteAuth;

	protected BroadcastReceiver login_failed_receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().contentEquals(TalkServerIntent.INTENT_LOGIN_FAILED.toString())) {
				Toast.makeText(context, "Could not log in. Please provide login credentials.",
						Toast.LENGTH_LONG);
				Log.i("Talk", "AuthBase received LOGIN_FAILED intent");
				UIUtils.goToLogin(context);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TalkServerIntent.INTENT_LOGIN_FAILED.toString());
		registerReceiver(login_failed_receiver, filter);
	}
	
	protected void onPause() {
		unregisterReceiver(login_failed_receiver);
		super.onPause();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

			case R.id.logout_menu_button:
				try {
					Log.i("Talk", "Logout Menu Item");
					UIUtils.logout(siteAuth);
					this.startActivity(new Intent(this, GeoCamTalkLogon.class));
				} catch (Exception e) {
					UIUtils.displayException(getApplicationContext(), e, "You're screwed");
				}
				return false;
			case R.id.create_message_button:
				Log.i("Talk", "Create Message Menu Item");
				UIUtils.createTalkMessage(this);
				return false;
			case R.id.message_list_button:
				UIUtils.goHome(this);
				return false;
			case R.id.exit_menu_button:
				((GeoCamTalkRoboApplication) getApplication()).stopThreads();
				finish();
                moveTaskToBack(true);
				System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
				return false;				
			case R.id.settings_button:
				UIUtils.goToSettings(this);
				Log.i("Talk", "Settings Menu Item");
				return false;
			default:
				Log.i("Talk", "NO BUTTON!!!");
				return super.onOptionsItemSelected(item);
		}
	}

}
