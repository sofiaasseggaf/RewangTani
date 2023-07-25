package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.DetailWarungTenagaKerja;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelprofillahan.DataProfilLahanById;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfilLahan extends AppCompatActivity {

    String id, namaSistemIrigasi;
    TextView txt_koordinatlahan, txt_luasgarapan, txt_kemiringantanah, txt_phtanah, txt_sistemirigasi;
    EditText txt_nama_profil_lahan;
    ModelProfilLahan modelProfilLahan;
    DatumProfilLahan dataProfilLahan;
    ModelSistemIrigasi modelSistemIrigasi;
    TextView txtload;
    ImageButton btn_simpan;
    DataProfilLahanById dataProfilLahanById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pl_detail_profil_lahan);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_koordinatlahan = findViewById(R.id.txt_koordinat_lahan);
        txt_luasgarapan = findViewById(R.id.txt_luas_garapan);
        txt_kemiringantanah = findViewById(R.id.txt_kemiringantanah);
        txt_phtanah = findViewById(R.id.txt_pHtanah);
        txt_sistemirigasi = findViewById(R.id.txt_sistemirigasi);
        txt_nama_profil_lahan = findViewById(R.id.txt_nama_profil_lahan);
        btn_simpan = findViewById(R.id.btn_simpan);
        txtload = findViewById(R.id.textloading);

        getData();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfilLahan();
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
                getProfilLahan();
            }
        }).start();
    }

    public void getProfilLahan() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                            String idpl = modelProfilLahan.getData().get(i).getIdProfileTanah();
                            if (id.equalsIgnoreCase(idpl)) {
                                dataProfilLahan = modelProfilLahan.getData().get(i);
                                if (dataProfilLahan!=null){
                                    getStatusPekerja();
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getStatusPekerja(){
        getSistemIrigasi();

    }

    public void getSistemIrigasi(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSistemIrigasi> data = apiInterface.getDataSistemIrigasi();
        data.enqueue(new Callback<ModelSistemIrigasi>() {
            @Override
            public void onResponse(Call<ModelSistemIrigasi> call, Response<ModelSistemIrigasi> response) {
                modelSistemIrigasi = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelSistemIrigasi.getTotalData(); i++) {
                            if(modelSistemIrigasi.getData().get(i).getIdSistemIrigasi().equalsIgnoreCase(dataProfilLahan.getIdSistemIrigasi().toString())){
                                namaSistemIrigasi = modelSistemIrigasi.getData().get(i).getNamaSistemIrigasi();
                            }
                        }
                    } catch (Exception e){}
                    if (!namaSistemIrigasi.equalsIgnoreCase("")){
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
            public void onFailure(Call<ModelSistemIrigasi> call, Throwable t) {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(){
        txt_nama_profil_lahan.setText(dataProfilLahan.getNamaProfilTanah());
        txt_koordinatlahan.setText(dataProfilLahan.getLatitude()+", "+dataProfilLahan.getLongitude());
        txt_luasgarapan.setText(dataProfilLahan.getLuasGarapan().toString() + " m2");
        txt_kemiringantanah.setText(dataProfilLahan.getKemiringanTanah().toString());

        //String ph2 = String.valueOf(ph);
        String ph2 = dataProfilLahan.getPhTanah().toString();
        double ph = Double.valueOf(ph2.substring(0, ph2.length() - 2))/10;
        txt_phtanah.setText(String.valueOf(ph));
        // get data sistem irigasi
        txt_sistemirigasi.setText(namaSistemIrigasi);
    }

    public void updateProfilLahan(){
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
                sendDataProfilTanah();
            }
        }).start();
    }

    private void sendDataProfilTanah(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfileTanah", dataProfilLahan.getIdProfileTanah());
        jsonParams.put("namaProfilTanah", txt_nama_profil_lahan.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProfilLahan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        getProfilLahanTerbaru(dataProfilLahan.getIdProfileTanah());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailProfilLahan.this, "Gagal ubah nama profil lahan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getProfilLahanTerbaru(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilLahanById> dataRT = apiInterface.getDatumProfilLahan(id);
        dataRT.enqueue(new Callback<DataProfilLahanById>() {
            @Override
            public void onResponse(Call<DataProfilLahanById> call, Response<DataProfilLahanById> response) {
                dataProfilLahanById = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DetailProfilLahan.this, "Berhasil ubah nama profil lahan", Toast.LENGTH_SHORT).show();
                            txt_nama_profil_lahan.setText(dataProfilLahanById.getData().getNamaProfilTanah());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DataProfilLahanById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void goToListProfilLahan(){
        Intent a = new Intent(DetailProfilLahan.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListProfilLahan();
    }
}