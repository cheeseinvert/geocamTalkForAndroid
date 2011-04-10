package gov.nasa.arc.geocam.talk.activity.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.injected.FakeTalkServer;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.List;

import org.junit.Test;

import android.widget.ListView;

import com.google.inject.Inject;


public class GeoCamTalkActivityTest extends GeoCamTestCase {	
	@Inject GeoCamTalkActivity activity;
	@Inject FakeTalkServer fakeDjangoTalk;
	
	
	
	@Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new GeoCamTalkActivity().getResources().getString(R.string.hello);
        assertThat(hello, equalTo("Hello World, GeoCamTalk!"));                
    }
	
	@Test
	public void shouldDisplayTalkMessages() throws Exception {
		//arrange
		activity.onCreate(null);
		//List<GeoCamTalkMessage>fakeTalkMessageList = fakeDjangoTalk.getTalkMessages();
	    
        //act
        ListView geoCamListView = (ListView)activity.findViewById(R.id.TalkListView);
        
		//assert
        //assertThat(geoCamListView.getChildCount(), equalTo(fakeTalkMessageList.size()));
	}
}