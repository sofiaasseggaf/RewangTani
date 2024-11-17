package com.rewangTani.rewangtani.bottombar.profilelahan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlDetailProfilLahanBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelprofillahan.DataProfilLahanById;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfilLahan extends AppCompatActivity {

    BottombarPlDetailProfilLahanBinding binding;
    String namaSistemIrigasi;
    ModelProfilLahan modelProfilLahan;
    DatumProfilLahan dataProfilLahan;
    ModelSistemIrigasi modelSistemIrigasi;
    DataProfilLahanById dataProfilLahanById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_detail_profil_lahan);

        Intent intent = getIntent();
        String idProfilLahan = intent.getStringExtra("idProfilLahan");

        getData(idProfilLahan);

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfilLahan();
            }
        });

    }

    public void getData(String idProfilLahan){
        binding.viewLoading.setVisibility(View.VISIBLE);
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
                getProfilLahan(idProfilLahan);
            }
        }).start();
    }

    public void getProfilLahan(String idProfilLahan) {
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
                            if (idProfilLahan.equalsIgnoreCase(idpl)) {
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
                        binding.viewLoading.setVisibility(View.GONE);
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
                                binding.viewLoading.setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSistemIrigasi> call, Throwable t) {
                binding.viewLoading.setVisibility(View.GONE);
                Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(){
        binding.namaProfilLahan.setText(dataProfilLahan.getNamaProfilTanah());
        binding.koordinatLahan.setText(dataProfilLahan.getLatitude()+", "+dataProfilLahan.getLongitude());
        binding.luasGarapan.setText(dataProfilLahan.getLuasGarapan().toString() + " m2");


        String ph2 = dataProfilLahan.getPhTanah().toString();
        double ph = Double.valueOf(ph2.substring(0, ph2.length() - 2))/10;
        binding.phTanah.setText(String.valueOf(ph));

        String kt2 = dataProfilLahan.getKemiringanTanah().toString();
        double kt = Double.valueOf(kt2.substring(0, kt2.length() - 2))/10;
        binding.kemiringanTanah.setText(String.valueOf(kt));

        binding.sistemIrigasi.setText(namaSistemIrigasi);
    }

    public void updateProfilLahan(){
        binding.viewLoading.setVisibility(View.VISIBLE);
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
                sendDataProfilTanah();
            }
        }).start();
    }

    private void sendDataProfilTanah(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfileTanah", dataProfilLahan.getIdProfileTanah());
        jsonParams.put("namaProfilTanah", binding.namaProfilLahan.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProfilLahan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        getProfilLahanTerbaru(dataProfilLahan.getIdProfileTanah());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
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
                        binding.viewLoading.setVisibility(View.GONE);
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
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(DetailProfilLahan.this, "Berhasil ubah nama profil lahan", Toast.LENGTH_SHORT).show();
                            binding.namaProfilLahan.setText(dataProfilLahanById.getData().getNamaProfilTanah());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DataProfilLahanById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
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