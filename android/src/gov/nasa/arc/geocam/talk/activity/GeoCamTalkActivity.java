package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.DjangoTalkInterface;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ListView;

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
        
        List<GeoCamTalkMessage> talkMessages = djangoTalk.getTalkMessages();
        adapter.setTalkMessages(talkMessages);
        
        talkListView.setAdapter(adapter);
    }
}