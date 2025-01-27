package com.rewangTani.rewangtani.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.TambahInfoPeringatanCuaca;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        /*if (PreferenceUtils.getIdProfil(getApplicationContext()).equalsIgnoreCase("")){
            // do nothing
        } else {
            Toast.makeText(this, "From: " + remoteMessage.getFrom(), Toast.LENGTH_SHORT).show();
        }

        // Check if message contains a data payload.
       *//* if (remoteMessage.getData().size() > 0) {
            Toast.makeText(this, "Message data payload: " + remoteMessage.getData(), Toast.LENGTH_SHORT).show();

            if (*//**//* Check if data needs to be processed by long running job *//**//* true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }*//*

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            if (PreferenceUtils.getIdProfil(getApplicationContext()).equalsIgnoreCase("")){
                // do nothing
            } else {
                Toast.makeText(this, "Message Notification Body: " + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            }
        }*/

        if (PreferenceUtils.getIdProfil(getApplicationContext()).equalsIgnoreCase("")){
            // do nothing
        } else {
            sendNotification(remoteMessage.getData().toString(), remoteMessage.getNotification().getBody());
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /*private void sendNotification(String from, String body){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyFirebaseMessagingService.this.getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void sendNotification(String from, String msg){
        Intent intent = new Intent(MyFirebaseMessagingService.this, TambahInfoPeringatanCuaca.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }

        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, Global.CHANNEL_ID)
                        //.setContentTitle(from)
                        .setContentTitle(PreferenceUtils.getTitle(getApplicationContext()))
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.icon_logo_alert)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Global.CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }


}