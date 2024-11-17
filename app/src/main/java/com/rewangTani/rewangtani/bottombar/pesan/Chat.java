package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterchatdaninbox.AdapterChat;
import com.rewangTani.rewangtani.databinding.BottombarPesanChatBinding;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.DatumChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.ModelChat;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    BottombarPesanChatBinding binding;
    String idInbox;
    ModelChat modelChat;
    List<DatumChat> listChat = new ArrayList<>();
    AdapterChat itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_chat);

//        Intent intent = getIntent();
//        idInbox = intent.getStringExtra("id");
//
//        if (!idInbox.equalsIgnoreCase("") || idInbox!=null){
//            getData();
//        }

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.txtChat.getText().toString();
                if (!text.equalsIgnoreCase("")){
                    sendChat(text);
                }
            }
        });
    }

    private void getData(){
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getChat();
            }
        }).start();
    }

    private void getChat(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelChat> dataRT = apiInterface.getDataChatByIdInbox(idInbox);
        dataRT.enqueue(new Callback<ModelChat>() {
            @Override
            public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {
                modelChat = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelChat.getTotalData(); i++) {
                        try {
                            if (modelChat.getData().get(i).getIdInbox().equalsIgnoreCase(idInbox)) {
                                listChat.add(modelChat.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (listChat.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(Chat.this, "Anda belum memiliki pesan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelChat> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(Chat.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setData(){
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());
        itemList = new AdapterChat(listChat, idSender);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(Chat.this));
        binding.rvChat.setAdapter(itemList);
        binding.rvChat.smoothScrollToPosition(listChat.size());
    }

    private void sendChat(String text){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());

        jsonParams.put("idInbox", idInbox);
        jsonParams.put("idSender", idSender);
        jsonParams.put("text", text);
        jsonParams.put("isRead", "");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendChat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        binding.txtChat.setText("");
                        binding.rvChat.setAdapter(null);
                        listChat.clear();
                        String text2;
                        if(text.length()>=50){
                            text2 = text.substring(0,50);
                        } else {
                            text2 = text;
                        }
                        udpateInbox(text2);
                    } else {
                        Toast.makeText(Chat.this, "Gagal mengirim pesan", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(Chat.this, "Gagal mengirim pesan", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void udpateInbox(String text2){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());

        jsonParams.put("idInbox", idInbox);
        jsonParams.put("lastText", text2);
        jsonParams.put("lastSender", idSender);
        jsonParams.put("readFlag", "X");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateInbox(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                if(rawResponse.body()!=null){
                    getData();
                } else {
                    Toast.makeText(Chat.this, "Gagal update inbox", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(Chat.this, "Gagal update inbox", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Chat.this, InboxPesan.class);
        startActivity(a);
        finish();
    }
}