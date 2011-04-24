package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.res.Resources.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageStore.
 */
public interface IMessageStore {
	
	/**
	 * Synchronize.
	 */
	public void synchronize();
	
	/**
	 * Gets the all local messages.
	 *
	 * @return the all local messages
	 * @throws SQLException the sQL exception
	 */
	public List<GeoCamTalkMessage> getAllLocalMessages() throws SQLException;
	
	/**
	 * Gets the all messages.
	 *
	 * @return the all messages
	 * @throws SQLException the sQL exception
	 */
	public List<GeoCamTalkMessage> getAllMessages() throws SQLException;
	
	/**
	 * Gets the messages since.
	 *
	 * @param sinceDate the since date
	 * @return the messages since
	 * @throws SQLException the sQL exception
	 */
	public List<GeoCamTalkMessage> getMessagesSince(Date sinceDate) throws SQLException;
	
	/**
	 * Adds the message.
	 *
	 * @param messages the messages
	 * @throws SQLException the sQL exception
	 */
	public void addMessage(List<GeoCamTalkMessage> messages) throws SQLException;
	
	/**
	 * Adds the message.
	 *
	 * @param message the message
	 * @throws SQLException the sQL exception
	 */
	public void addMessage(GeoCamTalkMessage message) throws SQLException;
	
	/**
	 * Removes the message.
	 *
	 * @param message the message
	 * @throws SQLException the sQL exception
	 */
	public void removeMessage(GeoCamTalkMessage message) throws SQLException;
	
	/**
	 * Update message.
	 *
	 * @param message the message
	 * @throws SQLException the sQL exception
	 */
	public void updateMessage(GeoCamTalkMessage message) throws SQLException;
	
	/**
	 * Gets the newest message id.
	 *
	 * @return the newest message id
	 * @throws SQLException the sQL exception
	 * @throws NotFoundException the not found exception
	 */
	public int getNewestMessageId() throws SQLException, NotFoundException;
}
