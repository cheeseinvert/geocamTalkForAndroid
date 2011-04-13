package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.List;
import java.util.Map;

public interface ITalkJsonConverter {
	List<GeoCamTalkMessage> deserializeList(String jsonString);
	GeoCamTalkMessage deserialize(String jsonString);
	String serialize(GeoCamTalkMessage message);
	Map<String, String> createMap(String jsonString);
	List<String> deserializeTeammates(String jsonString);
}
