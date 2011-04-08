package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.Date;
import java.util.List;

public interface IMessageStore {
	public void synchronize();
	public List<GeoCamTalkMessage> getAllMessages();
	public List<GeoCamTalkMessage> getMessagesSince(Date sinceDate);
	public void addMessage(List<GeoCamTalkMessage> messages);
	public void addMessage(GeoCamTalkMessage message);
}
