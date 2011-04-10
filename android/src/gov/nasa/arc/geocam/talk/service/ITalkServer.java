package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

public interface ITalkServer {
	void getTalkMessages() throws SQLException, ClientProtocolException,
			AuthenticationFailedException, IOException;

	void createTalkMessage(GeoCamTalkMessage message) throws ClientProtocolException,
			AuthenticationFailedException, IOException, SQLException;
}
