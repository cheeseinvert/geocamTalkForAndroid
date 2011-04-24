package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITalkJsonConverter.
 */
public interface ITalkJsonConverter {
	
	/**
	 * Deserialize list.
	 *
	 * @param jsonString the json string
	 * @return the list
	 */
	List<GeoCamTalkMessage> deserializeList(String jsonString);
	
	/**
	 * Deserialize.
	 *
	 * @param jsonString the json string
	 * @return the geo cam talk message
	 */
	GeoCamTalkMessage deserialize(String jsonString);
	
	/**
	 * Serialize.
	 *
	 * @param message the message
	 * @return the string
	 */
	String serialize(GeoCamTalkMessage message);
	
	/**
	 * Creates the map.
	 *
	 * @param jsonString the json string
	 * @return the map
	 */
	Map<String, String> createMap(String jsonString);
}
