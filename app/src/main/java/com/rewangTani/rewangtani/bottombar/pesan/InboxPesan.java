package com.rewangTani.rewangtani.bottombar.pesan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterchatdaninbox.AdapterInbox;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarPesanInboxBinding;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.ModelInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ModelInboxParticipant;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxPesan extends AppCompatActivity {

    BottombarPesanInboxBinding binding;
    ModelInboxParticipant modelInboxParticipant;
    List<DatumInboxParticipant> listInboxParticipant = new ArrayList<>();
    ModelInbox modelInbox;
    List<DatumInbox> listInbox = new ArrayList<>();
    AdapterInbox itemList;
    String clickedIdInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesan_inbox);

        //getData();

        binding.inboxOne.setOnClickListener(v->{
            goToChat();
        });

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
                getInboxParticipant();
            }
        }).start();
    }

    private void getInboxParticipant(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInboxParticipant> dataRT = apiInterface.getDataInboxParticipant();
        dataRT.enqueue(new Callback<ModelInboxParticipant>() {
            @Override
            public void onResponse(Call<ModelInboxParticipant> call, Response<ModelInboxParticipant> response) {
                modelInboxParticipant = response.body();
                String idProfile = PreferenceUtils.getIdProfil(getApplicationContext());
                if (response.body()!=null){
                    for (int i = 0; i < modelInboxParticipant.getTotalData(); i++) {
                        try {
                            if (idProfile.equalsIgnoreCase(modelInboxParticipant.getData().get(i).getIdProfilA()) ||
                            idProfile.equalsIgnoreCase(modelInboxParticipant.getData().get(i).getIdInboxParticipant())) {
                                listInboxParticipant.add(modelInboxParticipant.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (listInboxParticipant.size()>0){
                        getInbox();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InboxPesan.this, "Anda belum memiliki pesan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelInboxParticipant> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InboxPesan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getInbox(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInbox> dataRT = apiInterface.getDataInbox();
        dataRT.enqueue(new Callback<ModelInbox>() {
            @Override
            public void onResponse(Call<ModelInbox> call, Response<ModelInbox> response) {
                modelInbox = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelInbox.getTotalData(); i++) {
                        for (int j = 0; j < listInboxParticipant.size(); j++){
                            try {
                                if (modelInbox.getData().get(i).getIdInboxParticipant().equalsIgnoreCase(listInboxParticipant.get(j).getIdInboxParticipant())) {
                                    listInbox.add(modelInbox.getData().get(i));
                                }
                            } catch (Exception e){ }
                        }
                    }
                    if (listInbox.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InboxPesan.this, "Anda belum memiliki pesan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelInbox> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InboxPesan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setData(){
        String idProfil = PreferenceUtils.getIdProfil(getApplicationContext());
        List<String> idParticipants = new ArrayList<>();
        idParticipants.clear();
        for (int i=0; i<listInbox.size(); i++){
            idParticipants.add(listInbox.get(i).getIdInboxParticipant());
        }
        itemList = new AdapterInbox(listInbox, idProfil, idParticipants);
        binding.rvInbox.setLayoutManager(new LinearLayoutManager(InboxPesan.this));
        binding.rvInbox.setAdapter(itemList);
        binding.rvInbox.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvInbox,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        clickedIdInbox = listInbox.get(position).getIdInbox();
                        updateReadFlag(clickedIdInbox);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    private void updateReadFlag(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ResponseBody> dataRT = apiInterface.updateReadFlagInbox(id);
        dataRT.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    Intent a = new Intent(InboxPesan.this, Chat.class);
                    a.putExtra("id", id);
                    startActivity(a);
                } else {
                    Toast.makeText(InboxPesan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    call.cancel();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(InboxPesan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void goToChat(){
        Intent a = new Intent(InboxPesan.this, Chat.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(InboxPesan.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(InboxPesan.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }


    public void goToProfilLahan(){
        Intent a = new Intent(InboxPesan.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(InboxPesan.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}