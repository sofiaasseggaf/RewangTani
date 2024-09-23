package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtDetailRencanaTanamNonEditableBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRencanaTanamNonEditable extends AppCompatActivity {

    UpperbarRtDetailRencanaTanamNonEditableBinding binding;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    String namaPL,namaKomoditas, namaVarietas, est_biaya, est_hasil;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_detail_rencana_tanam_non_editable);

        Intent intent = getIntent();
        String idRencanaTanam = intent.getStringExtra("idRencanaTanam");

        getData(idRencanaTanam);

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

    }

    public void getData(String idRencanaTanam){
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
                getRencanaTanam(idRencanaTanam);
            }
        }).start();
    }

    public void getRencanaTanam(String idRencanaTanam) {
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
                            if (idRencanaTanam.equalsIgnoreCase(idrt)) {
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                                if (dataRencanaTanam!=null){
                                    getDataProfilLahan();
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        try {
                            if (modelProfilLahan.getData().get(i).getIdProfileTanah().equalsIgnoreCase(dataRencanaTanam.getIdProfilTanah())) {
                                namaPL = modelProfilLahan.getData().get(i).getNamaProfilTanah(); }
                        } catch (Exception e){ }
                    }
                    if (!namaPL.equalsIgnoreCase("")){
                        getDataKomoditas();
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
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(DetailRencanaTanamNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    public void getDataKomoditas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKomoditas> dataK = apiInterface.getDataKomoditas();
        dataK.enqueue(new Callback<ModelKomoditas>() {
            @Override
            public void onResponse(Call<ModelKomoditas> call, Response<ModelKomoditas> response) {
                modelKomoditas = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelKomoditas.getTotalData(); i++) {
                        try {
                            if (modelKomoditas.getData().get(i).getIdKomoditas().equalsIgnoreCase(dataRencanaTanam.getIdKomoditas())) {
                                namaKomoditas = modelKomoditas.getData().get(i).getNamaKomoditas(); }
                        } catch (Exception e){ }
                    }
                    if (!namaKomoditas.equalsIgnoreCase("")){
                        getDataVarietas();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataVarietas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelVarietas> dataV = apiInterface.getDataVarietas();
        dataV.enqueue(new Callback<ModelVarietas>() {
            @Override
            public void onResponse(Call<ModelVarietas> call, Response<ModelVarietas> response) {
                modelVarietas = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelVarietas.getTotalData(); i++) {
                        try {
                            if (modelVarietas.getData().get(i).getIdVarietas().equalsIgnoreCase(dataRencanaTanam.getIdVarietas())) {
                                namaVarietas = modelVarietas.getData().get(i).getNamaVarietas().toString(); }
                        } catch (Exception e){ }
                    }
                    if (!namaVarietas.equalsIgnoreCase("")){
                        getDataOutput();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataOutput(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataV = apiInterface.getDataOutputRT();
        dataV.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                        try {
                            if (modelOutputRencanaTanam.getData().get(i).getIdRencanaTanam()
                                    .equalsIgnoreCase(dataRencanaTanam.getIdRencanaTanam())) {
                                est_biaya = modelOutputRencanaTanam.getData().get(i).getEstBiayaProduksi();
                            est_hasil = modelOutputRencanaTanam.getData().get(i).getEstHasil().toString();
                            }
                        } catch (Exception e){ }
                    }
                    if (est_biaya!=null && est_hasil!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                setDataOutput();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(DetailRencanaTanamNonEditable.this, "Data RAB tidak ditemukan", Toast.LENGTH_SHORT).show();
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamNonEditable.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private String checkDesimal(String a){
        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public void setDataOutput(){
        est_biaya = formatter.format(Double.valueOf(est_biaya).longValue());
        binding.txtEstimasiRab.setText(est_biaya);
        binding.txtEstimasiHasil.setText(est_hasil+" Kg");
        setData();
    }

    public void setData(){
        binding.txtNama.setText(dataRencanaTanam.getNamaRencanaTanam());
        binding.txtProfilLahan.setText(namaPL);
        binding.txtKomoditas.setText(namaKomoditas);
        binding.txtVarietas.setText(namaVarietas);
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        binding.txtBuruhTanam.setText(a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        binding.txtBuruhBajak.setText(b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        binding.txtBuruhSemprot.setText(c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        binding.txtBuruhMenyiangi.setText(d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        binding.txtBuruhGalengan.setText(e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        binding.txtBuruhPupuk.setText(f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        binding.txtBuruhPanen.setText(g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        binding.txtMesinBajak.setText(h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        binding.txtMesinPanen.setText(i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        binding.txtMesinTanam.setText(j);
        if (dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            binding.txtMesinPompa.setText(k);
        } else {
            binding.txtMesinPompa.setText("-");
        }
        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            binding.txtMesinPompaBbm.setText(l);
        } else {
            binding.txtMesinPompaBbm.setText("-");
        }
        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        binding.txtBibitLocal.setText(m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        binding.txtBibitSubsidi.setText(n);
        String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        binding.txtPupukKimiaLocalHet.setText(o);
//        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
//        txt_pupuk_kimia_phonska.setText(p);
//        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
//        txt_pupuk_kimia_urea.setText(q);
//        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
//        txt_pupuk_kimia_fosfat.setText(r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        binding.txtPupukOrganik.setText(s);
    }

    public  void goToListRT(){
        Intent a = new Intent(DetailRencanaTanamNonEditable.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListRT();
    }
}