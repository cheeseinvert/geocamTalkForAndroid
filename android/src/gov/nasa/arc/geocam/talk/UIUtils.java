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
import android.util.Log;
import android.widget.Toast;

/**
 * The Class UIUtils.  Provides an interface to complete global UI functions and begin new activities.
 */
public class UIUtils {
	
    /**
     * Display an exception at the log and to the user via {@link Toast}.
     *
     * @param context the context
     * @param e the e
     * @param additionalMessage the additional message
     */
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
    
    /**
     * Show the {@link GeoCamTalkMapActivity} for a given message.
     *
     * @param context The current activity context
     * @param talkMessage The {@link GeoCamTalkMessage} that the map activity will display
     */
    public static void showMapView(Context context, GeoCamTalkMessage talkMessage) {
    	final Intent intent = new Intent(context, GeoCamTalkMapActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra(context.getString(R.string.latitude), talkMessage.getLatitude());
    	intent.putExtra(context.getString(R.string.longitude), talkMessage.getLongitude());
    	intent.putExtra(context.getString(R.string.accuracy), talkMessage.getAccuracy());
    	context.startActivity(intent);    	
    }
    
    /**
     * Logout the currently logged in user.
     *
     * @param siteAuth A {@link ISiteAuth} instance which is managing authorization.
     * @throws ClientProtocolException the client protocol exception
     * @throws AuthenticationFailedException the authentication failed exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void logout(ISiteAuth siteAuth)throws ClientProtocolException, AuthenticationFailedException, IOException
    {
    	siteAuth.logoutAndUnregister();
    }

	/**
	 * Invoke "home" action, returning to {@link GeoCamTalkActivity}.
	 *
	 * @param context The activity context to send the intent from.
	 */
	public static void goHome(Context context) {
		final Intent intent = new Intent(context, GeoCamTalkActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Go to the {@link GeoCamTalkLogon} activity.
	 *
	 * @param context The activity context to send the intent from.
	 */
	public static void goToLogin(Context context) {
		final Intent intent = new Intent(context, GeoCamTalkLogon.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Go to the {@link GeoCamTalkCreateActivity} activity to create a new GeoCam Talk Message.
	 *
	 * @param context The activity context to send the intent from.
	 */
	public static void createTalkMessage(Context context) {
		final Intent intent = new Intent(context, GeoCamTalkCreateActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Play the audio of a {@link GeoCamTalkMessage} if available.
	 *
	 * @param context The activity context to send the intent from.
	 * @param msg The {@link GeoCamTalkMessage} to play audio of.
	 * @param player An instance of an {@link IAudioPlayer}
	 * @param siteAuth The {@link ISiteAuth} to use to get attain audio from if not local
	 * @throws ClientProtocolException the client protocol exception
	 * @throws AuthenticationFailedException the authentication failed exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void playAudio(Context context, GeoCamTalkMessage msg, IAudioPlayer player,
			ISiteAuth siteAuth) throws ClientProtocolException, AuthenticationFailedException,
			IOException {

		String audioUrl = msg.getAudioUrl();

		// No audio recorded with message
		if (msg.getAudio() == null && audioUrl == null) {
			return;
		}
		// We have audio, but not locally
		else if (msg.getAudio() == null && audioUrl != null) {
			String localFileName = siteAuth.getAudioFile(audioUrl, null);
			player.startPlaying(localFileName);
			File audioFile = new File(localFileName);
			int length = (int) audioFile.length();
			byte[] audioBytes = new byte[length];

			FileInputStream fis = new FileInputStream(audioFile);
			fis.read(audioBytes, 0, length);
			msg.setAudio(audioBytes);
		}
		player.startPlaying(msg.getAudio());
	}

	/**
	 * Go to the {@link SettingsActivity} activity.
	 *
	 * @param context the context
	 */
	public static void goToSettings(Context context) {
		final Intent intent = new Intent(context, SettingsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
