package com.rewangTani.rewangtani.middlebar.warungsewamesin;

import android.content.Intent;
import android.net.Uri;
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
import com.rewangTani.rewangtani.databinding.MiddlebarDetailWarungMesinBinding;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
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

public class DetailWarungSewaMesin extends AppCompatActivity {

    MiddlebarDetailWarungMesinBinding binding;
    String id;
    String noTelepon="";
    ModelSewaMesin modelSewaMesin;
    DatumSewaMesin dataSewaMesin;
    DataProfilById dataProfilById;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_detail_warung_mesin);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        getData();

        binding.btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailWarungSewaMesin.this, "PESAN !", Toast.LENGTH_SHORT).show();
//                if (!dataSewaMesin.getIdProfil().equalsIgnoreCase("")) {
//                    if (!noTelepon.equalsIgnoreCase("")){
//                       updateProduk();
//                    } else {
//                        Toast.makeText(DetailWarungSewaMesin.this, "No telepon belum ada", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });
    }

    public void updateProduk(){
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textloading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textloading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textloading.setText("Tunggu sebentar ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataSewaMesin();
            }
        }).start();
    }

    public void updateDataSewaMesin(){

        int total = dataSewaMesin.getJmlTerjual()+1;

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSewaMesin", dataSewaMesin.getIdSewaMesin());
        jsonParams.put("jmlTerjual", total);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataSewaMesin(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataProduk();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungSewaMesin.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungSewaMesin.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void updateDataProduk(){

        int total = dataSewaMesin.getJmlTerjual()+1;

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataSewaMesin.getIdProduk());
        jsonParams.put("jmlTerjual", total);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String message = "Saya ingin menyewa produk anda : *" + dataSewaMesin.getNamaProduk() + "*";
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungSewaMesin.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungSewaMesin.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getData(){
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textloading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textloading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textloading.setText("Tunggu sebentar ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getSewaMesin();
            }
        }).start();
    }

    public void getSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                modelSewaMesin = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelSewaMesin.getTotalData(); i++) {
                            String idtk = modelSewaMesin.getData().get(i).getIdSewaMesin();
                            if (id.equalsIgnoreCase(idtk)) {
                                dataSewaMesin = modelSewaMesin.getData().get(i);
                                if (dataSewaMesin.getIdProfil()!=null){
                                    String idProfil = dataSewaMesin.getIdProfil();
                                    getDataProfil(idProfil);
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungSewaMesin.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungSewaMesin.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        binding.namaWarung.setText(dataSewaMesin.getNamaProduk());
        String a = checkDesimal(dataSewaMesin.getHargaProduk().toString());
        binding.biayaWarung.setText("Rp " + a);
        binding.txtKet.setText(dataSewaMesin.getDeskProduk());
        binding.lokasiWarung.setText("Kota : " + dataSewaMesin.getKota());
        if (dataSewaMesin.getJmlTerjual()==null){
            binding.terjualWarung.setText("Terpakai : - kali");
        } else {
            binding.terjualWarung.setText("Terpakai : " + String.valueOf(dataSewaMesin.getJmlTerjual()) + " kali");
        }
        if (!dataSewaMesin.getIdFoto().equalsIgnoreCase("")){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+dataSewaMesin.getIdFoto();
            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(binding.imgWarung);
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

    public void goToListWarungSewaMesin(){
        Intent a = new Intent(DetailWarungSewaMesin.this, ListWarungSewaMesin.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListWarungSewaMesin();
    }
}