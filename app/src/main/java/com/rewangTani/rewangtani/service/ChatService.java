package com.rewangTani.rewangtani.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatService extends Service {

    private StompClient stompClient;
    private static final String WEBSOCKET_URL = "ws://167.172.72.217:8080/ws";
    private static final String SUBSCRIBE_TOPIC = "/topic/private/"; // Replace with your subscription topic

    @Override
    public void onCreate()
    {
        super.onCreate();
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, WEBSOCKET_URL);
    }

    @Override
    public int onStartCommand( @NonNull Intent intent, int flags, int startId )
    {

        startForeground(Global.CHAT_NOTIFICATION_ID, chatChannelNotification());
        String idProfile = PreferenceUtils.getIdProfil(this);

        ArrayList<String> inboxIds = intent.getStringArrayListExtra(Global.INTENT_EXTRA_INBOX_IDS);
        if ( inboxIds != null && !inboxIds.isEmpty() )
        {
            stompClient.connect();

            for ( String id : inboxIds )
            {
                String topicDestination = SUBSCRIBE_TOPIC + id;
                stompClient.topic(topicDestination)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(message -> {
                            Gson gson = new Gson();
                            ChatRequest receivedMessage = gson.fromJson(message.getPayload(), ChatRequest.class);
                            if ( !receivedMessage.getIdSender().equalsIgnoreCase(idProfile) )
                            {
                                startForeground(Global.CHAT_NOTIFICATION_ID, chatNotification(receivedMessage.getText()));
                                Intent a = new Intent(Global.INTENT_ACTION_REFRESH_INBOX);
                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(a);
                            }
                        }, throwable -> {
                        });
            }
        }

        return START_STICKY;
    }

    private Notification chatNotification( String message )
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, Global.CHAT_CHANNEL)
                        .setContentTitle("Chat")
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_chat);
        return builder.build();
    }

    private Notification chatChannelNotification()
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, Global.CHAT_CHANNEL)
                        .setContentTitle("Rewang Tani")
                        .setContentText("Foreground service for chat")
                        .setSmallIcon(R.drawable.ic_chat);
        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind( Intent intent )
    {
        return null;
    }
}
