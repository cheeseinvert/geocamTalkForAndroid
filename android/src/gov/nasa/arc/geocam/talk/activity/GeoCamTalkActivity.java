package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.IDjangoTalk;
import gov.nasa.arc.geocam.talk.service.IMessageStore;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class GeoCamTalkActivity extends RoboActivity {

	@Inject
	IDjangoTalk djangoTalk;
	@InjectView(R.id.TalkListView)
	ListView talkListView;
	@InjectResource(R.string.url_server_root)
	String serverRootUrl;
	@Inject
	GeoCamTalkMessageArrayAdapter adapter;
	@Inject
	IMessageStore messageStore;
	@Inject
	ISiteAuth siteAuth;
	@Inject
	IAudioPlayer player;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		List<GeoCamTalkMessage> talkMessages = null;
		
		talkListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
		    @Override
		    public void onItemClick (AdapterView<?> parentView, View childView, int position, long id) {
		    	GeoCamTalkMessage msg = adapter.getTalkMessage(position);
		    	
		    	try
		    	{
		    		UIUtils.playAudio(getApplicationContext(), msg, player, siteAuth);
		    	} catch (Exception e)
		    	{
		    		UIUtils.displayException(getApplicationContext(), e, "Cannot retrieve audio");
		    	}
		    }
			});
		

		try {
			talkMessages = messageStore.getAllMessages();
		} catch (Exception e) {
			Log.i("Talk", "Error:" + e.getMessage());
		}

		if (talkMessages != null) {
			adapter.setTalkMessages(talkMessages);
			talkListView.setAdapter(adapter);
		} else {
			Toast.makeText(this.getApplicationContext(), "Communication Error with Server",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.settings_menu_button:
			Log.i("Talk", "Settings Button");
			Intent intent = new Intent(this, GeoCamTalkSettings.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
			return true;
		case R.id.create_message_menu_button:
			Log.i("Talk", "Create Button");
			return false;
		case R.id.message_list_menu_button:
			Log.i("Talk", "Message List Button");
			return false;
		default:
			Log.i("Talk", "NO BUTTON!!!");
			return super.onOptionsItemSelected(item);
		}
	}

	public void onCreateTalkClick(View v) {
		UIUtils.createTalkMessage(this);
	}
	
}