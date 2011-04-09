package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.util.Log;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

public class MessageStore implements IMessageStore {

	private Dao<GeoCamTalkMessage, Integer> dao;
	private IIntentHelper intentHelper;
	
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
	
	@Override
	public void synchronize() {
		this.intentHelper.Synchronize();
	}
	
	@Override
	public List<GeoCamTalkMessage> getAllLocalMessages() throws SQLException {
		QueryBuilder<GeoCamTalkMessage, Integer> qb = dao.queryBuilder();
		qb.where().eq(GeoCamTalkMessage.IS_SYNCHRONIZED_FIELD_NAME, false);
		
		SortedSet<GeoCamTalkMessage> hash = 
			new TreeSet<GeoCamTalkMessage>(Collections.reverseOrder());
		hash.addAll(dao.query(qb.prepare()));
		
		return new ArrayList<GeoCamTalkMessage>(hash);
	}

	@Override
	public List<GeoCamTalkMessage> getAllMessages() throws SQLException {
		SortedSet<GeoCamTalkMessage> hash = 
			new TreeSet<GeoCamTalkMessage>(Collections.reverseOrder());
		hash.addAll(dao.queryForAll());
		return new ArrayList<GeoCamTalkMessage>(hash);
	}

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

	@Override
	public void addMessage(List<GeoCamTalkMessage> messages) throws SQLException{
		List<GeoCamTalkMessage> existingMessages = getAllMessages();
		
		for(GeoCamTalkMessage m:messages)
		{
			if(!existingMessages.contains(m))
			dao.create(m);
		}
	}

	@Override
	public void addMessage(GeoCamTalkMessage message) throws SQLException {
		List<GeoCamTalkMessage> singleItemList = new ArrayList<GeoCamTalkMessage>();
		singleItemList.add(message);
		addMessage(singleItemList);
	}
	
	@Override
	public void removeMessage(GeoCamTalkMessage message) throws SQLException {
		dao.delete(message);
	}
	
	@Override
	public void updateMessage(GeoCamTalkMessage message) throws SQLException {
		dao.update(message);
	}
}