package com.rewangTani.rewangtani.utility;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;

import java.util.List;

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
    private StompClient stompClient;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    private final OnMessageReceivedListener messageListener;
    public static final String CHANNEL_ID = "serviceChannel";
    private final Context context;

    public WebSocketManager( OnMessageReceivedListener listener, Context context )
    {
        this.messageListener = listener;
        this.context = context.getApplicationContext();
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
        compositeDisposable.clear();
        String destination = SUBSCRIBE_TOPIC + inboxId;

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Flowable<StompMessage> flowable = RxJavaBridge.toV3Flowable(stompClient.topic(destination));
        Disposable disposable = flowable.subscribe(messageObj -> {

                    StompMessage message = (StompMessage) messageObj;
                    String payload = message.getPayload();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestChatData(String idInbox) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<List<ChatRequest>> datachat = apiInterface.getChatHistory(idInbox);
        datachat.enqueue(new Callback<List<ChatRequest>>() {
            @Override
            public void onResponse(Call<List<ChatRequest>> call, Response<List<ChatRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatRequest> chatHistory = response.body();
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