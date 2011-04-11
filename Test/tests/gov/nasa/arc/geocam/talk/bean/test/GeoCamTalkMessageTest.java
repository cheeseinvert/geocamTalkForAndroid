package gov.nasa.arc.geocam.talk.bean.test;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.junit.Test;


public class GeoCamTalkMessageTest extends TestCase {
	@Test
	public void testEqualsMethodTrue() throws Exception
	{
		
		// arrange
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
				
		GeoCamTalkMessage message1 = new GeoCamTalkMessage();
		message1.setAuthorUsername("rhornsby");
		message1.setAuthorFullname("Rufus Hornsby");
		message1.setLongitude(-122.057954);
		//message1.setRecipients();
		message1.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.");
		message1.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message1.setLatitude(37.411629);
		message1.setMessageId(15);
		message1.setAccuracy(60);

		GeoCamTalkMessage message2 = new GeoCamTalkMessage();
		message2.setAuthorUsername("rhornsby");
		message2.setAuthorFullname("Rufus Hornsby");
		message2.setLongitude(-122.057954);
		message2.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.");
		message2.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message2.setLatitude(37.411629);
		message2.setMessageId(15);
		message2.setAccuracy(60);
				
		// assert
		assertTrue(message1.equals(message2));
	}
	@Test
	public void testEqualsMethodFalse() throws Exception
	{ 
		// arrange
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
				
		GeoCamTalkMessage message1 = new GeoCamTalkMessage();
		message1.setAuthorUsername("rhornsby");
		message1.setAuthorFullname("Rufus Hornsby");
		message1.setLongitude(-122.057954);
		message1.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.");
		message1.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message1.setLatitude(37.411629);
		message1.setMessageId(15);
		message1.setAccuracy(60);

		GeoCamTalkMessage message2 = new GeoCamTalkMessage();
		message2.setAuthorUsername("rhornsby");
		message2.setAuthorFullname("Rufus Hornsby");
		message2.setLongitude(-122.057954);
		message2.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted. Blah"); // changed here
		message2.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message2.setLatitude(37.411629);
		message2.setMessageId(15);
		message2.setAccuracy(60);
				
		// assert
		assertFalse(message1.equals(message2));
	}
}
