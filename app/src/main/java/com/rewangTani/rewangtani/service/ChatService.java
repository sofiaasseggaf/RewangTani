package com.rewangTani.rewangtani.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatService extends Service {

    private StompClient stompClient;
    private static final String TAG = "WebSocketManager";
    private static final String WEBSOCKET_URL = "ws://167.172.72.217:8080/ws";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSocketConnection();
        return START_STICKY;
    }

    private void startSocketConnection() {

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, WEBSOCKET_URL);
        // Handle lifecycle events
        stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG, "WebSocket connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "WebSocket error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.d(TAG, "WebSocket connection closed");
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.e(TAG, "WebSocket server heartbeat failed");
                            break;
                    }
                });

        stompClient.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
