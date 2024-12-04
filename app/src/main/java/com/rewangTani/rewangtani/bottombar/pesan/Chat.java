package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity
{

    BottombarPesanChatBinding binding;
    String idInbox;
    ModelChat modelChat;
    List<DatumChat> listChat = new ArrayList<>();
    AdapterChat itemList;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_chat);

        Intent intent = getIntent();
        idInbox = intent.getStringExtra("idInbox");

        if ( idInbox != null )
        {
            if ( !idInbox.equalsIgnoreCase("") )
            {
                getData(idInbox);
            }
        }

        binding.btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                String text = binding.txtChat.getText().toString();
                if ( !text.equalsIgnoreCase("") )
                {
                    sendChat(text);
                }
            }
        });

    }

    private void getData( String idInbox )
    {
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
            public void run()
            {
                getChat(idInbox);
            }
        }).start();
    }

    private void getChat( String idInbox )
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelChat> dataRT = apiInterface.getDataChat();
        dataRT.enqueue(new Callback<ModelChat>()
        {
            @Override
            public void onResponse(Call<ModelChat> call, Response<ModelChat> response)
            {
                if ( response.body() != null )
                {
                    modelChat = response.body();

                    for (int i = 0; i < modelChat.getTotalData(); i++)
                    {
                        try
                        {
                            if ( modelChat.getData().get(i).getIdInbox().equalsIgnoreCase(idInbox) )
                            {
                                listChat.add(modelChat.getData().get(i));
                            }
                        }
                        catch ( Exception e ) {}
                    }

                    if ( listChat.size() > 0 )
                    {
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

    private void setData()
    {
        // Sort by date (descending for newest first)
        Collections.sort(listChat, (chat1, chat2) -> {
            Instant instant1 = chat1.getParsedDate();
            Instant instant2 = chat2.getParsedDate();
            if ( instant1 == null || instant2 == null ) return 0; // Handle null dates
            return instant1.compareTo(instant2); // Sort newest first
        });

        itemList = new AdapterChat(listChat, this);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(Chat.this));
        binding.rvChat.setAdapter(itemList);
        binding.rvChat.smoothScrollToPosition(listChat.size());
    }

    private void sendChat( String text )
    {
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInbox", idInbox);
        jsonParams.put("idSender", idSender);
        jsonParams.put("text", text);
        jsonParams.put("isRead", "N");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendChat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse( Call<ResponseBody> call, Response<ResponseBody> rawResponse )
            {
                try {
                    if (rawResponse.body() != null) {
                        binding.txtChat.setText("");
                        binding.rvChat.setAdapter(null);
                        listChat.clear();
                        udpateInbox(text);
                    } else {
                        Toast.makeText(Chat.this, "Gagal mengirim pesan", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure( Call<ResponseBody> call, Throwable throwable )
            {
                Toast.makeText(Chat.this, "Gagal mengirim pesan", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    void callApi(JSONObject jsonObject)
    {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String url = "https://fcm.googleapis.com/fcm/send";
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization","Bearer YOUR_API_KEY")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

            }
        });

    }

    private void udpateInbox( String text )
    {
        String idSender = PreferenceUtils.getIdProfil(getApplicationContext());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInbox", idInbox);
        jsonParams.put("lastText", text);
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
                if( rawResponse.body() != null )
                {
                    getData(idInbox);
                }
                else
                {
                    Toast.makeText(Chat.this, "Gagal update inbox", Toast.LENGTH_LONG).show();
                }
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
        Intent a = new Intent(Chat.this, InboxPesan.class);
        startActivity(a);
        finish();
    }
}