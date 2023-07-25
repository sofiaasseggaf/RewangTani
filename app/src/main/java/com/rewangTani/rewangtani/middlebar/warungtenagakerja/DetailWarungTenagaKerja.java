package com.rewangTani.rewangtani.middlebar.warungtenagakerja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.warungku.TambahWarungku;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.DetailWarungBibitdanPupuk;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailWarungTenagaKerja extends AppCompatActivity {

    ImageView img_tenaga_kerja;
    TextView nama_tenaga_kerja, biaya_tenaga_kerja, ket_tenaga_kerja, lokasi_tenaga_kerja, terjual_warung, deskripsi_tenaga_kerja, keahlian_tenaga_kerja;
    TextView txtload;
    String id;
    String noTelepon = "";
    ModelTenagaKerja modelTenagaKerja;
    DatumTenagaKerja dataTenagaKerja;
    ImageButton btn_whatsapp;
    DataProfilById dataProfilById;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middlebar_detail_warung_tenaga_kerja);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        img_tenaga_kerja = findViewById(R.id.img_tenaga_kerja);
        nama_tenaga_kerja = findViewById(R.id.nama_tenaga_kerja);
        biaya_tenaga_kerja = findViewById(R.id.biaya_tenaga_kerja);
        ket_tenaga_kerja = findViewById(R.id.ket_tenaga_kerja);
        lokasi_tenaga_kerja = findViewById(R.id.lokasi_tenaga_kerja);
        terjual_warung = findViewById(R.id.terjual_warung);
        deskripsi_tenaga_kerja = findViewById(R.id.deskripsi_tenaga_kerja);
        keahlian_tenaga_kerja = findViewById(R.id.keahlian_tenaga_kerja);
        btn_whatsapp = findViewById(R.id.btn_whatsapp);
        txtload = findViewById(R.id.textloading);

        getData();

        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataTenagaKerja.getIdProfil().equalsIgnoreCase("")) {
                    if (!noTelepon.equalsIgnoreCase("")){
                        updateProduk();
                    } else {
                        Toast.makeText(DetailWarungTenagaKerja.this, "No telepon belum ada", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void updateProduk(){
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
                updateDataTenagaKerja();
            }
        }).start();
    }

    public void updateDataTenagaKerja(){

        int total = dataTenagaKerja.getJmlTerjual()+1;

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTenagaKerja", dataTenagaKerja.getIdTenagaKerja());
        jsonParams.put("jmlTerjual", total);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataTenagaKerja(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                       updateDataProduk();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungTenagaKerja.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailWarungTenagaKerja.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void updateDataProduk(){

        int total = dataTenagaKerja.getJmlTerjual()+1;

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataTenagaKerja.getIdProduk());
        jsonParams.put("jmlTerjual", total);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String message = "Saya ingin memakai jasa anda : *" + dataTenagaKerja.getDeskripsi() + "*";
                                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+62" + noTelepon + "&text=" + message));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sepertinya tidak terinstall Whatsapp di handphone ini", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungTenagaKerja.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailWarungTenagaKerja.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
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
                getTenagaKerja();
            }
        }).start();
    }

    public void getTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.getDataWarungTenagaKerja();
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                modelTenagaKerja = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelTenagaKerja.getTotalData(); i++) {
                            String idtk = modelTenagaKerja.getData().get(i).getIdTenagaKerja();
                            if (id.equalsIgnoreCase(idtk)) {
                                dataTenagaKerja = modelTenagaKerja.getData().get(i);
                                if (dataTenagaKerja.getIdProfil()!=null){
                                    String idProfil = dataTenagaKerja.getIdProfil();
                                    getDataProfil(idProfil);
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungTenagaKerja.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProfil(String id) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(id);
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (response.body()!=null){
                    if (dataProfilById.getData().getTelepon()!=null){
                        noTelepon = dataProfilById.getData().getTelepon().substring(1);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setData();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungTenagaKerja.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        nama_tenaga_kerja.setText(dataTenagaKerja.getNamaTenagaKerja());
        String a = checkDesimal(dataTenagaKerja.getBiaya().toString());
        biaya_tenaga_kerja.setText("Rp " + a + " / Hari");
        ket_tenaga_kerja.setText("Menyediakan Jasa : " + dataTenagaKerja.getNamaTipeKerja());
        lokasi_tenaga_kerja.setText("Kota : " + dataTenagaKerja.getKota());
        terjual_warung.setText("Menyediakan Jasa : " + dataTenagaKerja.getJmlTerjual().toString() + " kali");
        deskripsi_tenaga_kerja.setText(dataTenagaKerja.getDeskripsi());
        keahlian_tenaga_kerja.setText(dataTenagaKerja.getKeahlian());
        if (!dataTenagaKerja.getIdFoto().equalsIgnoreCase("")){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+dataTenagaKerja.getIdFoto();
            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(img_tenaga_kerja);
        }
    }

    private String checkDesimal(String a){

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public void goToListWarungTenagaKerja(){
        Intent a = new Intent(DetailWarungTenagaKerja.this, ListWarungTenagaKerja.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListWarungTenagaKerja();
    }
}