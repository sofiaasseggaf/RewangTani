package com.rewangTani.rewangtani.utility;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class WebSocketManager {

    private static final String TAG = "WebSocketManager";
    private static final String WEBSOCKET_URL = "ws://167.172.72.217:8080/ws"; // Replace with your WebSocket URL
    private static final String SUBSCRIBE_TOPIC = "/topic/private/"; // Replace with your subscription topic
    private static final String SEND_ENDPOINT = "/app/sendMessage"; // Replace with your backend's send endpoint
    private StompClient stompClient;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Gson gson = new Gson();
    private OnMessageReceivedListener messageListener;
    public static final String CHANNEL_ID = "serviceChannel";
    private Context context;

    public WebSocketManager( OnMessageReceivedListener listener, Context context )
    {
        this.messageListener = listener;
        this.context = context.getApplicationContext();
    }

    public void init()
    {
        if ( stompClient == null )
        {
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
        }
    }

    public void connect()
    {
        stompClient.connect();
    }

    public void subscribeToInbox( String inboxId )
    {
        String destination = SUBSCRIBE_TOPIC + inboxId;
        stompClient.topic(destination) // Subscribe to the topic
                .subscribeOn(Schedulers.io()) // Handle the subscription on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Handle UI updates on the main thread
                .subscribe(message -> {
                    ChatRequest receivedMessage = gson.fromJson(message.getPayload(), ChatRequest.class);
                    if (messageListener != null) {
                        messageListener.onNewMessageReceived(message.getPayload());
                    }
                    }, throwable -> {
                    // Handle any errors that occur during message reception
                    Log.e(TAG, "Error receiving message", throwable);
                });
    }

    public void sendMessage(ChatRequest chatMessage) {
        String jsonMessage = gson.toJson(chatMessage);
        Disposable sendSubscription = stompClient.send(SEND_ENDPOINT, jsonMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Log.i(TAG, "Success sending message = " + jsonMessage);
                }, throwable -> {
                    Log.i(TAG, "Error sending message = " + throwable.getMessage());
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

