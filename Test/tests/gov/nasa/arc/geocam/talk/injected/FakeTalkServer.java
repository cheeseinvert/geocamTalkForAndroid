package gov.nasa.arc.geocam.talk.injected;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.ITalkServer;
import gov.nasa.arc.geocam.talk.service.ITalkJsonConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.inject.Inject;

public class FakeTalkServer implements ITalkServer{

	@Inject ITalkJsonConverter jsonConverter;
	

}
