package com.project.sagar.loctrack;

/**
 * Created by SAGAR on 12/27/2015.
 */
import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    // give your server registration url here
    static final String SERVER_URL = "http://"+ com.project.sagar.loctrack.Common.IP+"/LocTrackWeb/register.php";

    // Google project id
    static final String SENDER_ID = "1014206649155";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "LocTrack GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";
    static final String DISPLAY_LOCATION_ACTION =
            "com.androidhive.location.DISPLAY_LOCATION";

    static final String EXTRA_MESSAGE = "message";
    static final String EXTRA_LOCATION = "location";


    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        android.util.Log.i("rcvmsg1",message);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    static void displayLocation(Context context, String message) {
        Intent intent = new Intent(DISPLAY_LOCATION_ACTION);
        intent.putExtra(EXTRA_LOCATION, message);
        context.sendBroadcast(intent);
    }
}
