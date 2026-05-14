package com.rewangTani.rewangtani.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.InboxDao;
import com.rewangTani.rewangtani.utility.WebSocketManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatService extends Service {

    private static final String CHANNEL_ID = "ChatNotifications";
    private WebSocketManager webSocketManager;
    private InboxDao inboxDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

//    private StompClient stompClient;
//    private static final String WEBSOCKET_URL = "ws://167.172.72.217:8080/ws";
//    private static final String SUBSCRIBE_TOPIC = "/topic/private/"; // Replace with your subscription topic

    @Override
    public void onCreate()
    {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, getNotification("Chat is active"));

        RewangTaniDB db = RewangTaniDB.getInstance(this);
        inboxDao = db.inboxDao();

        webSocketManager = WebSocketManager.getInstance(this);
        webSocketManager.init();
        webSocketManager.connect();

        subscribeToAll();
    }

    private void subscribeToAll()
    {
        executor.execute(() -> {
            List<DatumInbox> list = inboxDao.getAllInboxLocal();
            for (DatumInbox inbox : list) {
                webSocketManager.subscribeToInbox(inbox.getIdInbox());
            }
        });
    }

    @Override
    public int onStartCommand( @NonNull Intent intent, int flags, int startId )
    {
        return START_STICKY;
    }

    private void createNotificationChannel()
    {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID, "Chat Service Channel", NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) manager.createNotificationChannel(serviceChannel);
    }

    private Notification getNotification(String content)
    {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Rewang Tani")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_chat)
                .build();
    }

    @Nullable
    @Override
    public IBinder onBind( Intent intent )
    {
        return null;
    }
}
