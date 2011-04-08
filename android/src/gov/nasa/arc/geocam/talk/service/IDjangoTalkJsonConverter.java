package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.List;

public interface IDjangoTalkJsonConverter {
	List<GeoCamTalkMessage> deserializeList(String jsonString);
	GeoCamTalkMessage deserialize(String jsonString);
	String serialize(GeoCamTalkMessage message);
}
