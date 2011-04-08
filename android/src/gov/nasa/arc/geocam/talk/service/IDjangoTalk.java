package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public interface IDjangoTalk {
	public void getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException;
}
