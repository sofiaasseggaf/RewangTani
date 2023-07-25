package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListProfilLahan;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProfileLahan extends AppCompatActivity {

    AdapterListProfilLahan itemList;
    RecyclerView rvProfilLahan;
    ImageButton btn_tambah;
    ModelProfilLahan modelProfilLahan;
    DataProfilById dataProfilById;
    List<DatumProfilLahan> listProfilLahan = new ArrayList<DatumProfilLahan>();
    TextView txtload;
    int checkKelengkapan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pl_list_profile_lahan);

        rvProfilLahan = findViewById(R.id.rvProfilLahan);
        btn_tambah = findViewById(R.id.btn_tambah);
        txtload = findViewById(R.id.textloading);

        getData();

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkKelengkapan==1){
                    goToTambahPL();
                } else if (checkKelengkapan==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListProfileLahan.this);
                    builder.setMessage("Lengkapi data profil terlebih dahulu")
                            .setPositiveButton("Lengkapi Data Profil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    goToProfil();
                                }
                            })
                            .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToBeranda();
                                }
                            })
                            .create()
                            .show();
                }
            }
        });

    }

    public void getData(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    txtload.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    txtload.setText("Tunggu sebentar ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfil();
            }
        }).start();
    }

    public void getDataProfil() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (response.body()!=null){
                    if (dataProfilById.getData().getTelepon()!=null && dataProfilById.getData().getNik()!=null &&
                            dataProfilById.getData().getIdAlamat()!=null && dataProfilById.getData().getAlamat()!=null &&
                    dataProfilById.getData().getGender()!=null && dataProfilById.getData().getTglLahir()!=null){
                        checkKelengkapan = 1;
                        getProfilLahan();
                    } else {
                        checkKelengkapan = 0;
                        getProfilLahan();

                    }
                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListProfileLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getProfilLahan() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelProfilLahan.getData().get(i).getIdUser())) {
                                listProfilLahan.add(modelProfilLahan.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (modelProfilLahan!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListProfileLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        itemList = new AdapterListProfilLahan(listProfilLahan);
        rvProfilLahan.setLayoutManager(new LinearLayoutManager(ListProfileLahan.this));
        rvProfilLahan.setAdapter(itemList);
        rvProfilLahan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvProfilLahan,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListProfileLahan.this, DetailProfilLahan.class);
                        a.putExtra("id", listProfilLahan.get(position).getIdProfileTanah());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToTambahPL(){
        Intent a = new Intent(ListProfileLahan.this, TambahProfilLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfil(){
        Intent a = new Intent(ListProfileLahan.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListProfileLahan.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}