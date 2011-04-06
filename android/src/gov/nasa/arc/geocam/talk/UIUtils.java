package gov.nasa.arc.geocam.talk;

import gov.nasa.arc.geocam.talk.activity.GeoCamTalkCreateActivity;
import gov.nasa.arc.geocam.talk.activity.GeoCamTalkActivity;
import android.content.Context;
import android.content.Intent;

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
}
