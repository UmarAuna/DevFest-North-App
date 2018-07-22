package com.defvest.devfestnorth.activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.defvest.devfestnorth.R;
import com.defvest.devfestnorth.fragments.Feeds;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private final String Tag = "MyFirebaseMessagingService";

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(Tag, "FROM:"+remoteMessage.getFrom());
        //Check if the message contains date
        if(remoteMessage.getData().size()>0){
            Log.d(Tag,"Message data:" + remoteMessage.getData());
        }

        //Check if the message contains notification
        if(remoteMessage.getNotification() != null){
            Log.d(Tag,"Message body:"+ remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }


    /**
     * Display the Notification
     * @param body
     */


    private void sendNotification(String body){

        Intent sintent = new Intent(this,Feeds.class);
        sintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,sintent,PendingIntent.FLAG_ONE_SHOT);

        //Set sound of notification
        Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Todo: Change notification icon
        Bitmap rawmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
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
