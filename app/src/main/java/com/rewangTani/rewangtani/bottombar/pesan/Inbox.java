package com.rewangTani.rewangtani.bottombar.pesan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterInbox;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarPesanInboxBinding;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.ModelInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ModelInboxParticipant;
import com.rewangTani.rewangtani.utility.Global;
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

public class Inbox extends AppCompatActivity implements AdapterInbox.OnInboxItemClickListener
{

    BottombarPesanInboxBinding binding;
    ModelInboxParticipant modelInboxParticipant;
    List<DatumInboxParticipant> listInboxParticipant = new ArrayList<>();
    List<DatumProfil> listProfil = new ArrayList<>();
    ModelInbox modelInbox;
    List<DatumInbox> listInbox = new ArrayList<>();
    AdapterInbox itemList;

    private BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getDataProfil();
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_inbox);

        getData();

        binding.btnHome.setOnClickListener(v->{
            goToBeranda();
        });

        binding.btnWarungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
            }
        });

        binding.btnLahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });


        binding.btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

    }

    private void getData()
    {
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable()
        {
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
                getDataProfil();
            }
        }).start();
    }

    public void getDataProfil()
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>()
        {
            @Override
            public void onResponse( Call<ModelProfilAkun> call, Response<ModelProfilAkun> response )
            {
                ModelProfilAkun modelProfilAkun = response.body();
                if (response.body() != null)
                {
                    listProfil.clear();
                    for ( int i = 0; i < modelProfilAkun.getTotalData(); i++ )
                    {
                        listProfil.add(modelProfilAkun.getData().get(i));
                    }

                    if ( listProfil.size() > 0 )
                    {
                        getInboxParticipant();
                    }
                }
            }

            @Override
            public void onFailure( Call<ModelProfilAkun> call, Throwable t )
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(Inbox.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getInboxParticipant()
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInboxParticipant> dataRT = apiInterface.getDataInboxParticipant();
        dataRT.enqueue(new Callback<ModelInboxParticipant>()
        {
            @Override
            public void onResponse( Call<ModelInboxParticipant> call, Response<ModelInboxParticipant> response )
            {
                modelInboxParticipant = response.body();
                String idProfile = PreferenceUtils.getIdProfil(getApplicationContext());

                if ( response.body() != null )
                {
                    listInboxParticipant.clear();
                    for ( int i = 0; i < modelInboxParticipant.getTotalData(); i++ )
                    {
                        try
                        {
                            if ( idProfile.equalsIgnoreCase(modelInboxParticipant.getData().get(i).getIdProfilA()) ||
                            idProfile.equalsIgnoreCase(modelInboxParticipant.getData().get(i).getIdProfilB()) )
                            {
                                listInboxParticipant.add(modelInboxParticipant.getData().get(i));
                            }
                        }
                        catch ( Exception e ) { }
                    }

                    if ( listInboxParticipant.size() > 0 )
                    {
                        getInbox();
                    }
                    else
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(Inbox.this, "Anda belum memiliki pesan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure( Call<ModelInboxParticipant> call, Throwable t )
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(Inbox.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getInbox()
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInbox> dataRT = apiInterface.getDataInbox();
        dataRT.enqueue(new Callback<ModelInbox>()
        {
            @Override
            public void onResponse( Call<ModelInbox> call, Response<ModelInbox> response )
            {
                modelInbox = response.body();

                if ( response.body() != null )
                {
                    listInbox.clear();
                    for ( int i = 0; i < modelInbox.getTotalData(); i++ )
                    {
                        for ( int j = 0; j < listInboxParticipant.size(); j++ )
                        {
                            try
                            {
                                if ( modelInbox.getData().get(i).getIdInboxParticipant().equalsIgnoreCase(listInboxParticipant.get(j).getIdInboxParticipant()) )
                                {
                                    listInbox.add(modelInbox.getData().get(i));
                                }
                            }
                            catch ( Exception e ) { }
                        }
                    }

                    if ( listInbox.size() > 0 )
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                    else
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(Inbox.this, "Anda belum memiliki pesan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure( Call<ModelInbox> call, Throwable t )
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(Inbox.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setData()
    {
        itemList = new AdapterInbox(listInbox, listProfil, listInboxParticipant, this, this);
        binding.rvInbox.setLayoutManager(new LinearLayoutManager(Inbox.this));
        binding.rvInbox.setAdapter(itemList);
    }

    private void updateReadFlag( DatumInbox datumInbox )
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idInbox", datumInbox.getIdInbox());
        jsonParams.put("lastText", datumInbox.getLastText());
        jsonParams.put("lastSender", datumInbox.getLastSender());
        jsonParams.put("readFlag", "Y");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateInbox(body);
        response.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
               // do nothing
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
               // do nothing / taro di log
            }
        });
    }

    public void goToChat( String idInbox ){
        Intent a = new Intent(Inbox.this, Chat.class);
        a.putExtra("idInbox", idInbox);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(Inbox.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(Inbox.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(Inbox.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(Inbox.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                chatReceiver,
                new IntentFilter(Global.INTENT_ACTION_REFRESH_INBOX)
        );
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chatReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }

    @Override
    public void onInboxItemClick(DatumInbox datumInbox, String otherProfile)
    {
        String idProfile = PreferenceUtils.getIdProfil(this);
        if ( datumInbox.getReadFlag().equalsIgnoreCase("N") && !datumInbox.getLastSender().equalsIgnoreCase(idProfile) )
        {
            updateReadFlag(datumInbox);
        }
        // When an inbox item is clicked, navigate to the chat screen
        Intent intent = new Intent(Inbox.this, Chat.class);
        intent.putExtra(Global.ID_INBOX, datumInbox.getIdInbox());
        intent.putExtra(Global.NAMA_INBOX, otherProfile);
        startActivity(intent);
    }
}