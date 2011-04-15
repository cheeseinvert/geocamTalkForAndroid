package gov.nasa.arc.geocam.talk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkCreateActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkLogon;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class UIUtils {
   
	/**
     * Invoke "home" action, returning to {@link GeoCamMemoHomeActivity}.
     */
    public static void goHome(Context context) {
        final Intent intent = new Intent(context, GeoCamTalkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    public static void goToLogin(Context context) {
        final Intent intent = new Intent(context, GeoCamTalkLogon.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    public static void createTalkMessage(Context context){
        final Intent intent = new Intent(context, GeoCamTalkCreateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    public static void displayException(Context context, Exception e, String additionalMessage)
    {
    	Log.e("Talk", additionalMessage, e);
    	StringBuilder sb = new StringBuilder();
    	if(additionalMessage != null)
    	{
    		sb.append(additionalMessage + ": ");
    	}
    	if(e.getLocalizedMessage() != null){
        	sb.append(e.getLocalizedMessage());    		
    	}
    	Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
    }
    
    public static void playAudio(Context context, GeoCamTalkMessage msg, IAudioPlayer player, ISiteAuth siteAuth) throws ClientProtocolException, AuthenticationFailedException, IOException
    {	
    	String audioUrl = msg.getAudioUrl();
		//No audio recorded with message
    	if (msg.getAudio() == null && audioUrl.equals("")) {
    		Toast.makeText(context, "This message has no audio",
					Toast.LENGTH_SHORT).show();	
    	}
    	//We have audio, but not locally
    	else if (msg.getAudio() == null && !audioUrl.equals(""))
    	{
    		String localFileName = siteAuth.getAudioFile(audioUrl, null);
    		player.startPlaying(localFileName);
    		File audioFile = new File(localFileName);
    		int length = (int) audioFile.length();
    		byte[] audioBytes = new byte[(int) length];

    		FileInputStream fis = new FileInputStream(audioFile);
    		fis.read(audioBytes, 0, length);
    		msg.setAudio(audioBytes);
    	}
    	// We have audio locally
    	else
    	{
    		player.startPlaying(msg.getAudio());
    	}
    }
    
    public static void logout(ISiteAuth siteAuth)throws ClientProtocolException, AuthenticationFailedException, IOException
    {
    	siteAuth.logoutAndUnregister();
    }
}
