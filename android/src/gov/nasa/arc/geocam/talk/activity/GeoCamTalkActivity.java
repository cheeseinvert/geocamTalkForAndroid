package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class GeoCamTalkActivity extends RoboActivity {
	
	@Inject DjangoTalkInterface djangoTalk;
	@InjectView(R.id.TalkListView) ListView talkListView;
	@Inject GeoCamTalkMessageArrayAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setDefaultSettings();
        djangoTalk.setAuth("root", "geocam");
        
        List<GeoCamTalkMessage> talkMessages = djangoTalk.getTalkMessages();
        if (talkMessages != null)
        {
        	adapter.setTalkMessages(talkMessages); 
            talkListView.setAdapter(adapter);
        } else {
        	Toast.makeText(this.getApplicationContext(), "Communication Error with Server", Toast.LENGTH_SHORT).show();
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
        	Intent intent = new Intent(this,
        			GeoCamTalkSettings.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    
    private void setDefaultSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(null == prefs.getString("webapp_username", null))
            prefs.edit().putString("webapp_username", getString(R.string.default_username));
        if(null == prefs.getString("webapp_password", null))
            prefs.edit().putString("webapp_password", getString(R.string.default_password));
        djangoTalk.setAuth(
        		prefs.getString("webapp_username", null),
        		prefs.getString("webapp_password", null)        		
        );
    }
    
    public void onCreateTalkClick(View v) {
    	UIUtils.createTalkMessage(this);
    }

}