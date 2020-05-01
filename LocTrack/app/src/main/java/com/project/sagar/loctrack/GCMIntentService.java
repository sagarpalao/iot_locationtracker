package com.project.sagar.loctrack;

/**
 * Created by SAGAR on 12/27/2015.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import static com.project.sagar.loctrack.CommonUtilities.SENDER_ID;
import static com.project.sagar.loctrack.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
        Log.d("NAME", "registered");
        ServerUtilities.register(context, "sagar", "palo", registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("message");

        Log.e("Message",message);
        if(message.equals("location")){
            CommonUtilities.displayLocation(context,message);
        }
        else if(message.equals("outside")){
            displayMessage(context, message);
            generateNotificationOutside(context, message);
        }
        else if(message.equals("moving")){
            displayMessage(context, message);
            generateNotificationMoving(context, message);
        }
        else{
            displayMessage(context, message);
            generateNotificationAlert(context, message);
        }

    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        //generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = com.project.sagar.loctrack.R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, HomeActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("this is ticker text");
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(intent);
        builder.setOngoing(true);
        builder.setSound(android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION));
        Notification note=builder.getNotification();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, note);

    }

    private static void generateNotificationAlert(Context context, String message) {
        int icon = com.project.sagar.loctrack.R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context,GeoMessageView.class);
        Log.i("rcvmsg",message);
        notificationIntent.putExtra("placeid",message);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("Geo Message");
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(intent);
        builder.setOngoing(true);
        builder.setSound(android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION));
        Notification note=builder.getNotification();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, note);

    }

    private static void generateNotificationOutside(Context context, String message) {
        int icon = com.project.sagar.loctrack.R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MapActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("Your car(locomotive) was found outside your prefedined locations");
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(intent);
        builder.setOngoing(true);
        builder.setSound(android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION));
        Notification note=builder.getNotification();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, note);
    }

    private static void generateNotificationMoving(Context context, String message) {
        int icon = com.project.sagar.loctrack.R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MapActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("Your car(locomotive) was found moving");
        builder.setContentTitle(title);
        builder.setContentText("Your car(locomotive) was found moving when it is freezed");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(intent);
        builder.setOngoing(true);
        builder.setSound(android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION));
        Notification note=builder.getNotification();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, note);
    }
}
