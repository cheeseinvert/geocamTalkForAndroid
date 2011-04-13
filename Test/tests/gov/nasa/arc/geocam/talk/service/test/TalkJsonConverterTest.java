package gov.nasa.arc.geocam.talk.service.test;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.TalkJsonConverter;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class TalkJsonConverterTest extends GeoCamTestCase {
	
	@Test
	public void ensureProperParsingOfMessageListFeed() throws Exception
	{
		// arrange
		String jsonString = 
			"{\"ts\": 1, \"ms\": [{\"authorUsername\": \"rhornsby\", \"authorFullname\":\"Rufus Hornsby\", \"longitude\": null, \"content\": \"Crap, my geolocation service crashed and I am not providing geoloc with this message. This message should be the latest to make sure we gracefully fall back to the next available geolocated message.\", \"contentTimestamp\": \"03/13/11 11:23:21\",\"latitude\": null, \"messageId\": 19, \"accuracy\": null}, {\"authorUsername\": \"rhornsby\", \"longitude\": -122.057954, \"content\": \"Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.\", \"contentTimestamp\": \"03/13/11 10:48:44\", \"latitude\": 37.411629, \"messageId\": 15, \"accuracy\":60.0, \"hasGeolocation\":true}]}";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		TalkJsonConverter converter =
			new TalkJsonConverter();
		
		GeoCamTalkMessage message1 = new GeoCamTalkMessage();
		message1.setAuthorUsername("rhornsby");
		message1.setAuthorFullname("Rufus Hornsby");
		// don't set longitude
		message1.setContent("Crap, my geolocation service crashed and I am not providing geoloc with this message. This message should be the latest to make sure we gracefully fall back to the next available geolocated message.");
		message1.setContentTimestamp(sdf.parse("03/13/11 11:23:21"));
		// don't set latitude
		message1.setMessageId(19);
		// don't set accuracy

		GeoCamTalkMessage message2 = new GeoCamTalkMessage();
		message2.setAuthorUsername("rhornsby");
		message2.setLongitude(-122.057954);
		message2.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.");
		message2.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message2.setLatitude(37.411629);
		message2.setMessageId(15);
		message2.setAccuracy(60);
		
		// act
		List<GeoCamTalkMessage> resolvedList =
			converter.deserializeList(jsonString);
		
		// assert
		assertTrue(resolvedList.contains(message1));
		assertTrue(resolvedList.contains(message2));
	}
	
	@Test
	public void ensureSingularDeserializationWorks() throws Exception {
		// arrange
		TalkJsonConverter converter =
			new TalkJsonConverter();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		
		String jsonString = 
			"{\"authorUsername\": \"rhornsby\", \"longitude\": -122.057954, \"content\": \"Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.\", \"contentTimestamp\": \"03/13/11 10:48:44\", \"latitude\": 37.411629, \"messageId\": 15, \"accuracy\":60.0}";
		
		GeoCamTalkMessage message = new GeoCamTalkMessage();
		message.setAuthorUsername("rhornsby");
		message.setLongitude(-122.057954);
		message.setContent("Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.");
		message.setContentTimestamp(sdf.parse("03/13/11 10:48:44"));
		message.setLatitude(37.411629);
		message.setMessageId(15);
		message.setAccuracy(60);
		
		// act
		GeoCamTalkMessage convertedMessage = converter.deserialize(jsonString);
		
		// arrange
		assertEquals(message, convertedMessage);
	}
	
	@Test
	public void ensureSerializeReturnsProperString() throws Exception
	{
		TalkJsonConverter converter =
			new TalkJsonConverter();
		
		GeoCamTalkMessage msg = new GeoCamTalkMessage();
		msg.setContent("contentTest");
		msg.setContentTimestamp(new Date());
		msg.setAccuracy(10);
		msg.setLatitude(11);
		msg.setLongitude(20.5);
		msg.setAudio(new byte[10]);
		
		String jsonString = converter.serialize(msg);
		
		assertTrue(jsonString.contains("content"));	
		assertTrue(jsonString.contains("contentTest"));
		assertTrue(jsonString.contains("contentTimestamp"));
		assertTrue(jsonString.contains("accuracy"));
		assertTrue(jsonString.contains("10"));
		assertTrue(jsonString.contains("latitude"));
		assertTrue(jsonString.contains("11"));
		assertTrue(jsonString.contains("longitude"));
		assertTrue(jsonString.contains("20.5"));
		assertFalse(jsonString.contains("audio"));
	}
	
	@Test
	public void testMapConversion() throws Exception
	{
		// arrange
		String jsonString = "{'key1':'value1', 'key2':'value2'}";
		Map<String, String> expectedMap = new HashMap<String, String>();
		
		expectedMap.put("key1", "value1");
		expectedMap.put("key2", "value2");
		
		TalkJsonConverter converter =
			new TalkJsonConverter();
		
		//act
		Map<String, String> results = converter.createMap(jsonString);
		
		//assert
		assertEquals(expectedMap.get("key1"), results.get("key1"));		
		assertEquals(expectedMap.get("key2"), results.get("key2"));		
	}
	
}
