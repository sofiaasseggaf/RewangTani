package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterChat;
import com.rewangTani.rewangtani.databinding.BottombarPesanChatBinding;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.WebSocketManager;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity implements WebSocketManager.OnMessageReceivedListener
{
    BottombarPesanChatBinding binding;
    private WebSocketManager webSocketManager;
    private RecyclerView recyclerView;
    private AdapterChat adapterChat;
    private List<ChatRequest> chatList = new ArrayList<>();
    private EditText messageEditText;
    private RelativeLayout sendButton;
    private List<ChatRequest> chatMessages = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_chat);

        Intent intent = getIntent();
        String inboxId = intent.getStringExtra("idInbox");

        recyclerView = findViewById(R.id.rvChat);
        messageEditText = findViewById(R.id.txtChat);
        sendButton = findViewById(R.id.btnSend);

        adapterChat = new AdapterChat(chatMessages, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterChat);

        webSocketManager = new WebSocketManager(this, this);
        webSocketManager.init();
        webSocketManager.connect();
        webSocketManager.subscribeToInbox(inboxId);

        if ( inboxId != null )
        {
            if ( !inboxId.equalsIgnoreCase("") )
            {
                webSocketManager.requestChatData(inboxId);
            }
        }


        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.equalsIgnoreCase(""))
            {
                if ( inboxId != null )
                {
                    if ( !inboxId.equalsIgnoreCase("") )
                    {
                        sendMessage(message, inboxId);
                    }
                }
            }
        });

    }

    @Override
    public void onNewMessageReceived(String message) {
        ChatRequest chatRequest = new Gson().fromJson(message, ChatRequest.class);
        chatMessages.add(chatRequest);
        adapterChat.notifyItemInserted(chatMessages.size() - 1);
        recyclerView.scrollToPosition(chatMessages.size() - 1);
    }

    @Override
    public void onChatDataReceived(List<ChatRequest> chatRequests) {
        chatMessages.clear();
        chatMessages.addAll(chatRequests);
        recyclerView.post(() -> recyclerView.scrollToPosition(chatMessages.size() - 1));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendMessage(String message, String inboxId) {
        String idProfile = PreferenceUtils.getIdProfil(this);
        ChatRequest chatRequest = new ChatRequest(inboxId, idProfile, message, LocalDateTime.now().toString(), "N");
        webSocketManager.sendMessage(chatRequest);

        chatMessages.add(chatRequest);
        adapterChat.notifyItemInserted(chatMessages.size() - 1);
        recyclerView.scrollToPosition(chatMessages.size() - 1); // Scroll to the latest message

        messageEditText.setText("");
        udpateInbox(inboxId, message);
    }

    private void udpateInbox(String inboxId, String message)
    {
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInbox", inboxId);
        jsonParams.put("lastText", message);
        jsonParams.put("lastSender", idSender);
        jsonParams.put("readFlag", "N");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateInbox(body);
        response.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse( Call<ResponseBody> call, Response<ResponseBody> rawResponse )
            {

            }
            @Override
            public void onFailure( Call<ResponseBody> call, Throwable throwable )
            {
                Toast.makeText(Chat.this, "Gagal update inbox", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketManager.disconnect();
    }

}
