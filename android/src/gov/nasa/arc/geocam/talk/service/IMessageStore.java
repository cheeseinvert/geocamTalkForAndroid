package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.res.Resources.NotFoundException;

public interface IMessageStore {
	public void synchronize();
	public List<GeoCamTalkMessage> getAllLocalMessages() throws SQLException;
	public List<GeoCamTalkMessage> getAllMessages() throws SQLException;
	public List<GeoCamTalkMessage> getMessagesSince(Date sinceDate) throws SQLException;
	public void addMessage(List<GeoCamTalkMessage> messages) throws SQLException;
	public void addMessage(GeoCamTalkMessage message) throws SQLException;
	public void removeMessage(GeoCamTalkMessage message) throws SQLException;
	public void updateMessage(GeoCamTalkMessage message) throws SQLException;
	public int getNewestMessageId() throws SQLException, NotFoundException;
}
