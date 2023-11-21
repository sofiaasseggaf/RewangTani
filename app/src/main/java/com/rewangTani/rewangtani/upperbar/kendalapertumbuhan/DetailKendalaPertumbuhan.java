package com.rewangTani.rewangtani.upperbar.kendalapertumbuhan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.DatumKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.ModelKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKendalaPertumbuhan extends AppCompatActivity {

    TextView txtload, txt_rencana_tanam, txt_profil_lahan, txt_hama, txt_bencana, txt_lainnya;
    String id, idRT, idPL, namaRT, namaPL;
    ModelRencanaTanam modelRencanaTanam;
    ModelKendalaPertumbuhan modelKendalaPertumbuhan;
    DatumKendalaPertumbuhan dataKendalaPertumbuhan;
    ModelProfilLahan modelProfilLahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_kp_detail_kendala_pertumbuhan);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_rencana_tanam = findViewById(R.id.txt_rencana_tanam);
        txt_profil_lahan = findViewById(R.id.txt_profil_lahan);
        txt_hama = findViewById(R.id.txt_hama);
        txt_bencana = findViewById(R.id.txt_bencana);
        txt_lainnya = findViewById(R.id.txt_lainnya);
        txtload = findViewById(R.id.textloading);

        getData();

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
                getKendalaPertumbuhan();
            }
        }).start();
    }

    public void getKendalaPertumbuhan() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKendalaPertumbuhan> dataRT = apiInterface.getDataKendalaPertumbuhan();
        dataRT.enqueue(new Callback<ModelKendalaPertumbuhan>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ModelKendalaPertumbuhan> call, Response<ModelKendalaPertumbuhan> response) {
                modelKendalaPertumbuhan = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelKendalaPertumbuhan.getTotalData(); i++) {
                            String idkp = modelKendalaPertumbuhan.getData().get(i).getIdKendalaPertumbuhan();
                            if (id.equalsIgnoreCase(idkp)) {
                                idRT = modelKendalaPertumbuhan.getData().get(i).getIdSudahTanam();
                                idPL = modelKendalaPertumbuhan.getData().get(i).getIdProfilTanah();
                                dataKendalaPertumbuhan = modelKendalaPertumbuhan.getData().get(i);
                            }
                        }
                        if (dataKendalaPertumbuhan != null) {
                            getRencanaTanam();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(DetailKendalaPertumbuhan.this, "Data kendala pertumbuhan tidak ditemukan", Toast.LENGTH_LONG).show();
                                    call.cancel();
                                }
                            });
                        }
                    } catch (Exception e) {
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DetailKendalaPertumbuhan.this, "Data kendala pertumbuhan tidak ditemukan", Toast.LENGTH_LONG).show();
                            call.cancel();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelKendalaPertumbuhan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                            String idrt = modelRencanaTanam.getData().get(i).getIdRencanaTanam();
                            if (idRT.equalsIgnoreCase(idrt)) {
                                namaRT = modelRencanaTanam.getData().get(i).getNamaRencanaTanam();
                            }
                        }
                        if (!namaRT.equalsIgnoreCase("")){
                            getDataProfilLahan();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(DetailKendalaPertumbuhan.this, "Data kendala pertumbuhan tidak ditemukan", Toast.LENGTH_LONG).show();
                                    call.cancel();
                                }
                            });
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProfilLahan() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        String idpl = modelProfilLahan.getData().get(i).getIdProfileTanah();
                        if (idPL.equalsIgnoreCase(idpl)) {
                            namaPL = modelProfilLahan.getData().get(i).getNamaProfilTanah();
                        }
                    }
                    if (!namaPL.equalsIgnoreCase("")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailKendalaPertumbuhan.this, "Data kendala pertumbuhan tidak ditemukan", Toast.LENGTH_LONG).show();
                                call.cancel();
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    public void setData(){
        txt_rencana_tanam.setText(namaRT);
        txt_profil_lahan.setText(namaPL);
        txt_hama.setText(dataKendalaPertumbuhan.getKendalaHama());
        txt_bencana.setText(dataKendalaPertumbuhan.getKendalaBencana());
        txt_lainnya.setText(dataKendalaPertumbuhan.getKendalaLainnya());
    }

    public void goToListKendalaPertumbuhan(){
        Intent a = new Intent(DetailKendalaPertumbuhan.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListKendalaPertumbuhan();
    }

}