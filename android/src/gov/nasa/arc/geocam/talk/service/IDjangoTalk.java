package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

public interface IDjangoTalk {
	public void getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException;
}
