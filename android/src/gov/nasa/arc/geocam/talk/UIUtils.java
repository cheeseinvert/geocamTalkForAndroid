package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkCreateActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkLogon;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkMapActivity;
import gov.nasa.arc.geocam.talk.activity.SettingsActivity;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.exception.AuthenticationFailedException;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class UIUtils {
	
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
    
    public static void showMapView(Context context, GeoCamTalkMessage talkMessage) {
    	final Intent intent = new Intent(context, GeoCamTalkMapActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra(context.getString(R.string.latitude), talkMessage.getLatitude());
    	intent.putExtra(context.getString(R.string.longitude), talkMessage.getLongitude());
    	intent.putExtra(context.getString(R.string.accuracy), talkMessage.getAccuracy());
    	context.startActivity(intent);    	
    }
    
    public static void logout(ISiteAuth siteAuth)throws ClientProtocolException, AuthenticationFailedException, IOException
    {
    	siteAuth.logoutAndUnregister();
    }

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

	public static void createTalkMessage(Context context) {
		final Intent intent = new Intent(context, GeoCamTalkCreateActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void playAudio(Context context, GeoCamTalkMessage msg, IAudioPlayer player,
			ISiteAuth siteAuth) throws ClientProtocolException, AuthenticationFailedException,
			IOException {

		String intro = "Message from " + msg.getAuthorFullname() + ", " + msg.getContent();
		String audioUrl = msg.getAudioUrl();

		boolean useTTS = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				"prefUseTts", false);

		// No audio recorded with message
		if (msg.getAudio() == null && audioUrl == null) {
			if (useTTS) {
				player.speak(intro);
			}
			return;
		}
		// We have audio, but not locally
		else if (msg.getAudio() == null && audioUrl != null) {
			String localFileName = siteAuth.getAudioFile(audioUrl, null);
			player.startPlaying(localFileName);
			File audioFile = new File(localFileName);
			int length = (int) audioFile.length();
			byte[] audioBytes = new byte[(int) length];

			FileInputStream fis = new FileInputStream(audioFile);
			fis.read(audioBytes, 0, length);
			msg.setAudio(audioBytes);
		}
		if (useTTS) {
			player.startPlayingWithTtsIntro(intro, msg.getAudio());
		} else {
			player.startPlaying(msg.getAudio());
		}
	}

	public static void goToSettings(Context context) {
		final Intent intent = new Intent(context, SettingsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
