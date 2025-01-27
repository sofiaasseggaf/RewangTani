package com.rewangTani.rewangtani.utility;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_ID = "serviceChannel";
    private WebSocketManager webSocketManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize WebSocketManager here so it starts when the app starts
        webSocketManager = new WebSocketManager(null, this);  // Pass null because listener is set in ChatActivity
        webSocketManager.init();  // Use your WebSocket URL
        webSocketManager.connect();  // Connect to the WebSocket server
        createNotificationChannel();

    }

    /**
     * Set up the notification channel.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = "Message Notifications";
            String description = "Channel for message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public WebSocketManager getWebSocketManager() {
        return webSocketManager;
    }

}
