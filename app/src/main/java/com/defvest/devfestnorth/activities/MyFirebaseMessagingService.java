package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.fragments.Feeds;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG = "FirebaseMessagingService" ;

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());



        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            shownotification(remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    //For Android O and Above
    public void shownotification(String Message){
        Intent sintent = new Intent(this,Feeds.class);
        //sintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),sintent,0);
        //Todo: Change notification icon
        Bitmap rawmap = BitmapFactory.decodeResource(getResources(), R.drawable.calendar);
        //Set sound of notification
        Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.calendar)
                .setLargeIcon(rawmap)
                .setContentTitle("DevFest North")
                .setContentText(Message)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true)
                .setSound(notificationsound)
                .setLights(Color.BLUE,500,500)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setContentIntent(pendingIntent);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    notifiBuilder.setChannelId("43002");
                    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel notificationChannel = new NotificationChannel("43002","ChannelName",NotificationManager.IMPORTANCE_HIGH);
                        if (notificationManager != null) {
                            notificationManager.createNotificationChannel(notificationChannel);
                        }
                    }
                    Notification n = notifiBuilder.build();
                    if(notificationManager != null){
                        notificationManager.notify(0,n);
                    }
                }

    }

    //For Android N and Below
    private void sendNotification(String body){

        Intent sintent = new Intent(this,Feeds.class);
        sintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,sintent,PendingIntent.FLAG_ONE_SHOT);

        //Set sound of notification
        Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Todo: Change notification icon
        Bitmap rawmap = BitmapFactory.decodeResource(getResources(), R.drawable.calendar);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.calendar)
                .setLargeIcon(rawmap)
                .setContentTitle("DevFest North")
                .setContentText(body)
                .setPriority(Notification.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true)
                .setSound(notificationsound)
                .setLights(Color.BLUE,500,500)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*ID of notification*/, notifiBuilder.build());

    }


}
