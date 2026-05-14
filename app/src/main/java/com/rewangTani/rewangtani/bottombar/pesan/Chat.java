package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterChat;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.databinding.BottombarPesanChatBinding;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.NavigationManager;
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
    private AdapterChat adapterChat;
    private final List<ChatRequest> chatList = new ArrayList<>();
    private final List<ChatRequest> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_chat);

        Intent intent = getIntent();
        String inboxId = intent.getStringExtra(Global.ID_INBOX);
        String inboxNama = intent.getStringExtra(Global.NAMA_INBOX);

        binding.namaProfile.setText(inboxNama);
        adapterChat = new AdapterChat(chatMessages, this);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setAdapter(adapterChat);

        // Use the existing manager
        webSocketManager = WebSocketManager.getInstance(this);
        webSocketManager.setListener(this);

        if ( inboxId != null ) {
            if ( !inboxId.equalsIgnoreCase("") ) {
                webSocketManager.requestChatData(inboxId);
            }
        }

        binding.btnBack.setOnClickListener( v -> NavigationManager.startActivity(this, Inbox.class) );

        binding.btnSend.setOnClickListener(v -> {
            String message = binding.txtChat.getText().toString().trim();
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
        Log.i("SOFIA", "Chat - onMsgeReceve - " + message);
        ChatRequest chatRequest = new Gson().fromJson(message, ChatRequest.class);
        chatMessages.add(chatRequest);
        binding.rvChat.scrollToPosition(chatMessages.size() - 1);
    }

    @Override
    public void onAllChatDataReceived(List<ChatRequest> chatRequests) {
        chatMessages.clear();
        chatMessages.addAll(chatRequests);
        binding.rvChat.setNestedScrollingEnabled(false);
        binding.rvChat.scrollToPosition(adapterChat.getItemCount() - 1);
    }

    private void sendMessage(String message, String inboxId) {
        String idProfile = PreferenceUtils.getIdProfil(this);
        ChatRequest chatRequest = new ChatRequest(inboxId, idProfile, message, LocalDateTime.now().toString(), "N");
        // 2. UPDATE LOCAL UI IMMEDIATELY (The missing piece)
//        chatMessages.add(chatRequest);
//        adapterChat.notifyItemInserted(chatMessages.size() - 1);
//        binding.rvChat.scrollToPosition(chatMessages.size() - 1);

        webSocketManager.sendMessage(chatRequest);
        binding.txtChat.setText("");
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
    public void onBackPressed()
    {
        super.onBackPressed();
        NavigationManager.startActivity(this, Inbox.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocketManager.setListener(null);
    }
}
