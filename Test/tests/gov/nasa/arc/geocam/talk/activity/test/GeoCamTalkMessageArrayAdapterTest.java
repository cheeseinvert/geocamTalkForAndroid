package gov.nasa.arc.geocam.talk.activity.test;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMessageArrayAdapter;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.injected.FakeGeoCamTalkMessageFactory;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xtremelabs.robolectric.Robolectric;

public class GeoCamTalkMessageArrayAdapterTest extends GeoCamTestCase{
	
	@Test
	public void shouldProperlyDisplayGeolocaionStatus() throws Exception {
		//arrange
		List<GeoCamTalkMessage> msgs = new ArrayList<GeoCamTalkMessage>();
		
		msgs.add(FakeGeoCamTalkMessageFactory.getMessage("testing", "Patrick", true));
		msgs.add(FakeGeoCamTalkMessageFactory.getMessage("testing2", "Not Patrick", false));

		GeoCamTalkMessageArrayAdapter adapter = new GeoCamTalkMessageArrayAdapter(
				Robolectric.application.getApplicationContext()); 
		adapter.setTalkMessages(msgs);
		
		View temp1 = View.inflate(Robolectric.application.getApplicationContext(), R.layout.list_item, null);		
		View temp2 = View.inflate(Robolectric.application.getApplicationContext(), R.layout.list_item, null);		
		
        //act
		LinearLayout geoLocatedMsgView = (LinearLayout) adapter.getView(0, temp1, null);
		LinearLayout nonGeoLocatedMsgView = (LinearLayout) adapter.getView(1, temp2, null);
        
		//assert
		assertTrue(((ImageView) geoLocatedMsgView.findViewById(R.id.hasGeoLocation)).getVisibility() == View.VISIBLE);
		assertTrue(((ImageView) nonGeoLocatedMsgView.findViewById(R.id.hasGeoLocation)).getVisibility() == View.INVISIBLE);
	}

}
