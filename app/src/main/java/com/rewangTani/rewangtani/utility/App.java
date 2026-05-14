package com.rewangTani.rewangtani.utility;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {

    private WebSocketManager webSocketManager;

    @Override
    public void onCreate()
    {
        super.onCreate();
        webSocketManager = new WebSocketManager(this);
        createNotificationChannel();
    }

    public WebSocketManager getWebSocketManager() {
        return webSocketManager;
    }

    /**
     * Set up the notification channel.
     */
    private void createNotificationChannel()
    {
        CharSequence name = "Chat Channel";
        String description = "Channel for foreground service";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(Global.CHAT_CHANNEL, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}
