package com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.SwipeablePhotosAdapter;
import com.rewangTani.rewangtani.bottombar.pesan.Chat;
import com.rewangTani.rewangtani.databinding.MiddlebarDetailWarungPupukBinding;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DataBppById;
import com.rewangTani.rewangtani.utility.ChatUtils;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

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

public class DetailWarungBibitdanPupuk extends AppCompatActivity {

    MiddlebarDetailWarungPupukBinding binding;
    DataBppById dataBppById;
    DataProfilById dataProfilById;
    DecimalFormat formatter;
    ChatUtils chatUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_detail_warung_pupuk);
        chatUtils = new ChatUtils(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        if ( id != null )
        {
            if ( !id.equalsIgnoreCase("") )
            {
                getData(id);
            }
        }

        binding.btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProfile = PreferenceUtils.getIdProfil(getApplicationContext());
                if ( idProfile.equalsIgnoreCase(dataProfilById.getData().getIdProfile()) )
                {
                    Toast.makeText(DetailWarungBibitdanPupuk.this, "Ini Produk Anda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    chatUtils.goToInbox(idProfile, dataProfilById.getData().getIdProfile(), dataProfilById.getData().getNamaDepan());
                }
            }
        });
    }

    public void getData(String id){
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
                getBibitdanPupuk(id);
            }
        }).start();
    }

    public void getBibitdanPupuk(String id) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataBppById> dataRT = apiInterface.getDataWarungBibitPupukPestisidaById(id);
        dataRT.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, Response<DataBppById> response) {
                dataBppById = response.body();
                if (response.body()!=null){
                    String idProfil = dataBppById.getData().getIdProfil();
                    getDataProfil(idProfil);
                }
            }
            @Override
            public void onFailure(Call<DataBppById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailWarungBibitdanPupuk.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailWarungBibitdanPupuk.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        binding.namaWarung.setText(dataBppById.getData().getNamaProduk());
        String a = checkDesimal(dataBppById.getData().getHargaProduk().toString());
        binding.biayaWarung.setText("Rp " + a);
        binding.beratWarung.setText("Berat : " + dataBppById.getData().getBeratProduk().toString()+" Kg");
        binding.lokasiWarung.setText("Kota : " + dataBppById.getData().getKota());
        binding.terjualWarung.setText("Terjual : " + dataBppById.getData().getJmlTerjual().toString() + " kali");
        binding.txtKet.setText(dataBppById.getData().getDeskProduk());
        if ( dataBppById.getData().getIdFoto() != null )
        {
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=" + dataBppById.getData().getIdFoto();
            Log.i("SOFIA", "DetailWarungBIBITPUPUK - setData() - uri = " + imageUri);
            SwipeablePhotosAdapter swipeablePhotosAdapter = new SwipeablePhotosAdapter(this, imageUri);
            binding.viewPager.setAdapter(swipeablePhotosAdapter);
        }
    }

    public void updateProduk(){
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
                updateDataBPP();
            }
        }).start();
    }

    public void updateDataBPP(){

        int total = dataBppById.getData().getJmlTerjual()+1;

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("jmlTerjual", total);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
//                        updateDataProduk();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungBibitdanPupuk.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailWarungBibitdanPupuk.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void updateDataProduk(){

        int total = dataBppById.getData().getJmlTerjual()+1;
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataBppById.getData().getIdProduk());
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
                                //                        TODO : GO TO PAGE PESAN
                                /*try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String message = "Saya ingin membeli produk anda : *" + dataBppById.getData().getNamaProduk() + "*";
                                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+62" + noTelepon + "&text=" + message));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sepertinya tidak terinstall Whatsapp di handphone ini", Toast.LENGTH_LONG).show();
                                }*/
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(DetailWarungBibitdanPupuk.this, "Gagal membeli produk ini", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailWarungBibitdanPupuk.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void goToListWarungPupuk(){
        Intent a = new Intent(DetailWarungBibitdanPupuk.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        finish();
    }

    private void goToChat() {
        Intent a = new Intent(DetailWarungBibitdanPupuk.this, Chat.class);
        startActivity(a);
        finish();
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

    @Override
    public void onBackPressed() {
        goToListWarungPupuk();
    }
}