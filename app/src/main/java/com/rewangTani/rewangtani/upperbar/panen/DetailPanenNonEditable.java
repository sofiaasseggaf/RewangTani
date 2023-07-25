package com.rewangTani.rewangtani.upperbar.panen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.rewangTani.rewangtani.model.modelupperbar.panen.DatumPanen;
import com.rewangTani.rewangtani.model.modelupperbar.panen.ModelPanen;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPanenNonEditable extends AppCompatActivity {

    TextView txt_tujuan_jual, txt_jenis_hasil_panen, txt_jumlah_hasil_panen, txt_harga_jual, txtload;
    ImageButton btn_simpan, btn_batal;
    String id;
    ModelPanen modelPanen;
    DatumPanen dataPanen;
    DecimalFormat formatter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_p_detail_panen_non_editable);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_tujuan_jual = findViewById(R.id.txt_tujuan_jual);
        txt_jenis_hasil_panen = findViewById(R.id.txt_jenis_hasil_panen);
        txt_jumlah_hasil_panen = findViewById(R.id.txt_jumlah_hasil_panen);
        txt_harga_jual = findViewById(R.id.txt_harga_jual);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);
        txtload = findViewById(R.id.textloading);

        getData();

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

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
                getPanen();
            }
        }).start();
    }

    public void getPanen() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPanen> dataRT = apiInterface.getDataPanen();
        dataRT.enqueue(new Callback<ModelPanen>() {
            @Override
            public void onResponse(Call<ModelPanen> call, Response<ModelPanen> response) {
                modelPanen = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelPanen.getTotalData(); i++) {
                            String idp = modelPanen.getData().get(i).getIdPanen();
                            if (id.equalsIgnoreCase(idp)) {
                                dataPanen = modelPanen.getData().get(i);
                                if (dataPanen!=null){
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
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelPanen> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailPanenNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        txt_tujuan_jual.setText(dataPanen.getTujuanJual());
        txt_jenis_hasil_panen.setText(dataPanen.getJenisHasilPanen());
//        String delim = ".";   // or "-" or "?" or ...
//        String[] st = dataPanen.getHasilPanen().toString().split(java.util.regex.Pattern.quote(delim));
//        String b = checkDesimal(st[0]);
//        txt_jumlah_hasil_panen.setText(b+st[1]+" Kg");
        txt_jumlah_hasil_panen.setText(dataPanen.getHasilPanen().toString()+" Kg");
        String a = checkDesimal(dataPanen.getHargaAktual().toString());
        txt_harga_jual.setText("Rp. "+a);
    }

    private String checkDesimal(String a){
        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public void goToListPanen(){
        Intent a = new Intent(DetailPanenNonEditable.this, ListPanen.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToListPanen();
    }
}