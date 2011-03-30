package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.List;

public interface DjangoTalkInterface {
	public List<GeoCamTalkMessage> getTalkMessages();
}
