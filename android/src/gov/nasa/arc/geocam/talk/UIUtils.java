package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkCreateActivity;
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    
    public static void createTalkMessage(Context context){
        final Intent intent = new Intent(context, GeoCamTalkCreateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}
