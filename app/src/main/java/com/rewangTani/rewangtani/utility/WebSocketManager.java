package com.rewangTani.rewangtani.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.InboxDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketManager {

    private static final String TAG = "WebSocketManager";
    private static final String WEBSOCKET_URL = "ws://167.172.72.217:8080/ws"; // Replace with your WebSocket URL
    private static final String SUBSCRIBE_TOPIC = "/topic/private/"; // Replace with your subscription topic
    private static final String SEND_ENDPOINT = "/app/sendMessage"; // Replace with your backend's send endpoint
    private static WebSocketManager instance;
    private StompClient stompClient;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    private OnMessageReceivedListener messageListener;
    public static final String CHANNEL_ID = "serviceChannel";
    private final Context context;
    private InboxDao inboxDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public WebSocketManager( Context context )
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);

        this.context = context.getApplicationContext();
        this.inboxDao = db.inboxDao();
    }

    public static synchronized WebSocketManager getInstance(Context context) {
        if (instance == null) {
            instance = new WebSocketManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setListener(OnMessageReceivedListener listener) {
        this.messageListener = listener;
    }

    public void init() {

        if (stompClient == null) {

            stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, WEBSOCKET_URL);

            Flowable<LifecycleEvent> v3Lifecycle = RxJavaBridge.toV3Flowable(stompClient.lifecycle());

            Disposable lifecycleDisposable = v3Lifecycle
                    .subscribe(eventObj -> {

                        switch (eventObj.getType()) {

                            case OPENED:
                                Log.d(TAG, "✅ WebSocket OPENED");
                                break;

                            case ERROR:
                                Log.e(TAG, "❌ WebSocket ERROR", eventObj.getException());
                                break;

                            case CLOSED:
                                Log.d(TAG, "🔌 WebSocket CLOSED");
                                break;

                            case FAILED_SERVER_HEARTBEAT:
                                Log.e(TAG, "💔 HEARTBEAT FAILED");
                                break;
                        }

                    }, throwable -> {
                        Log.e(TAG, "Lifecycle error", throwable);
                    });

            compositeDisposable.add(lifecycleDisposable);
        }
    }

    public void connect()
    {
        if (stompClient != null) {
            stompClient.connect();
        }
    }

    public void subscribeToInbox( String inboxId )
    {
//        compositeDisposable.clear();
        String destination = SUBSCRIBE_TOPIC + inboxId;

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Flowable<StompMessage> flowable = RxJavaBridge.toV3Flowable(stompClient.topic(destination));
        Disposable disposable = flowable.subscribe(messageObj -> {

                    StompMessage message = (StompMessage) messageObj;
                    String payload = message.getPayload();
                    handleGlobalNotification(payload);

                    try {

                        mainHandler.post(() -> {
                            if (messageListener != null) {
                                messageListener.onNewMessageReceived(payload);
                            }
                        });

                    } catch (Exception e) {
                        Log.e(TAG, "Parse error", e);
                    }

                }, throwable -> {
                    Log.e(TAG, "Error receiving message", throwable);
                });

        compositeDisposable.add(disposable);
    }

    private void handleGlobalNotification(String payload)
    {
        ChatRequest chatRequest = new Gson().fromJson(payload, ChatRequest.class);
        executor.execute(() -> {
            inboxDao.updateIsChecked(chatRequest.getIdInbox());
        });

        if (messageListener == null) {
            showStatusBarNotification(chatRequest.getText());
        }
    }

    public void sendMessage(ChatRequest chatMessage) {
        String jsonMessage = gson.toJson(chatMessage);
        Disposable sendSubscription = RxJavaBridge
                .toV3Completable(stompClient.send(SEND_ENDPOINT, jsonMessage)) // 🔥 convert
                .subscribe(() -> {

                    Log.i(TAG, "✅ Success sending message = " + jsonMessage);

                }, throwable -> {

                    Log.e(TAG, "❌ Error sending message", throwable);

                });

        compositeDisposable.add(sendSubscription);
    }

    public void requestChatData(String idInbox) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<List<ChatRequest>> datachat = apiInterface.getChatHistory(idInbox);
        datachat.enqueue(new Callback<List<ChatRequest>>() {
            @Override
            public void onResponse(Call<List<ChatRequest>> call, Response<List<ChatRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatRequest> chatHistory = response.body();

                        Collections.sort(chatHistory, (o1, o2) -> {
                            String d1 = o1.getSentAt();
                            Log.i("SOFIA", "d1 = " + d1);
                            String d2 = o2.getSentAt();
                            Log.i("SOFIA", "d2 = " + d2);
                            return d1.compareTo(d2);
                        });

                    if (messageListener != null) {
                        messageListener.onAllChatDataReceived(chatHistory);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ChatRequest>> call, Throwable t) {
            }
        });
    }

    private void showStatusBarNotification(String message)
    {
        Intent intent = new Intent(context, Inbox.class); // Or Chat.class
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, "ChatNotifications")
                .setContentTitle("New Chat")
                .setContentText(message)
                .setSmallIcon(R.drawable.icon_logo_alert)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }

    public void disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        compositeDisposable.clear(); // Clear all subscriptions
        Log.d(TAG, "WebSocket disconnected");
    }

    public interface OnMessageReceivedListener {
        void onNewMessageReceived(String message);
        void onAllChatDataReceived(List<ChatRequest> chatRequests);
    }

}