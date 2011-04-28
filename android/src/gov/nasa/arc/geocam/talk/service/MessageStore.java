package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

/**
 * The Class MessageStore.
 */
public class MessageStore implements IMessageStore {

	/** The dao. */
	private Dao<GeoCamTalkMessage, Integer> dao;
	
	/** The intent helper. */
	private IIntentHelper intentHelper;
	
	/**
	 * Instantiates a new message store.
	 *
	 * @param dbHelper the db helper
	 * @param intentHelper the intent helper
	 */
	@Inject
	public MessageStore(IDatabaseHelper dbHelper, IIntentHelper intentHelper)
	{
		this.intentHelper = intentHelper;
		
		try {
			this.dao = dbHelper.getGeoCamTalkMessageDao();
		} catch (SQLException e) {
			Log.e("GeoCam Talk", e.toString());
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#synchronize()
	 */
	@Override
	public void synchronize() {
		this.intentHelper.Synchronize();
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#getAllLocalMessages()
	 */
	@Override
	public List<GeoCamTalkMessage> getAllLocalMessages() throws SQLException {
		QueryBuilder<GeoCamTalkMessage, Integer> qb = dao.queryBuilder();
		qb.where().eq(GeoCamTalkMessage.IS_SYNCHRONIZED_FIELD_NAME, false);
		
		SortedSet<GeoCamTalkMessage> hash = 
			new TreeSet<GeoCamTalkMessage>(Collections.reverseOrder());
		hash.addAll(dao.query(qb.prepare()));
		
		return new ArrayList<GeoCamTalkMessage>(hash);
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#getAllMessages()
	 */
	@Override
	public List<GeoCamTalkMessage> getAllMessages() throws SQLException {
		SortedSet<GeoCamTalkMessage> hash = 
			new TreeSet<GeoCamTalkMessage>(Collections.reverseOrder());
		hash.addAll(dao.queryForAll());
		return new ArrayList<GeoCamTalkMessage>(hash);
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#getMessagesSince(java.util.Date)
	 */
	@Override
	public List<GeoCamTalkMessage> getMessagesSince(Date sinceDate) throws SQLException{
		QueryBuilder<GeoCamTalkMessage, Integer> qb = dao.queryBuilder();
		SelectArg dateArg = new SelectArg();
		dateArg.setValue(sinceDate);
		qb.where().gt(GeoCamTalkMessage.DATE_FIELD_NAME, dateArg);
		List<GeoCamTalkMessage> list =
			dao.query(qb.prepare());
		return list;
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#addMessage(java.util.List)
	 */
	@Override
	public void addMessage(List<GeoCamTalkMessage> messages) throws SQLException{
		List<GeoCamTalkMessage> existingMessages = getAllMessages();
		
		for(GeoCamTalkMessage m:messages)
		{
			if(!existingMessages.contains(m))
			dao.create(m);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#addMessage(gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage)
	 */
	@Override
	public void addMessage(GeoCamTalkMessage message) throws SQLException {
		List<GeoCamTalkMessage> singleItemList = new ArrayList<GeoCamTalkMessage>();
		singleItemList.add(message);
		addMessage(singleItemList);
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#removeMessage(gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage)
	 */
	@Override
	public void removeMessage(GeoCamTalkMessage message) throws SQLException {
		dao.delete(message);
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#updateMessage(gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage)
	 */
	@Override
	public void updateMessage(GeoCamTalkMessage message) throws SQLException {
		dao.update(message);
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IMessageStore#getNewestMessageId()
	 */
	@Override
	public int getNewestMessageId() throws SQLException, NotFoundException {
		QueryBuilder<GeoCamTalkMessage, Integer> qb = dao.queryBuilder();
		
		qb.where().eq(GeoCamTalkMessage.IS_SYNCHRONIZED_FIELD_NAME, true);
		qb.selectColumns(GeoCamTalkMessage.MESSAGE_ID_FIELD_NAME);
		qb.limit(1);
		qb.orderBy(GeoCamTalkMessage.MESSAGE_ID_FIELD_NAME, false);
		
		return dao.query(qb.prepare()).get(0).getMessageId();
	}
}