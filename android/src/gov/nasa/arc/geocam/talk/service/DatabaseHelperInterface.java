package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

public interface DatabaseHelperInterface {
	public Dao<GeoCamTalkMessage, Integer> getGeoCamTalkMessageDao() throws SQLException;
}
