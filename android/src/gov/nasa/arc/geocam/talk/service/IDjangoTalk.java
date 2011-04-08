package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public interface IDjangoTalk {
	public List<GeoCamTalkMessage> getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException;
	public void setAuth(String username, String password);
}
