package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStUpdateSudahTanamABinding;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSudahTanamRT extends AppCompatActivity {

    UpperbarStUpdateSudahTanamABinding binding;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    ModelAkun modelAkun;
    List<DatumAkun> listAkunwithToken = new ArrayList<>();
    ModelProfilAkun modelProfilAkun;
    List<String> listIDAkunwithAlamat = new ArrayList<>();
    List<String> listToken = new ArrayList<String>();
    String namaRT, idRT, namaPL, idPL, namaKomoditas, idK, namaVarietas, idV;
    String tipeSIa, tipeSIb, tipeSIc, idSistemIrigasi;
    DecimalFormat formatter;
    boolean isWithPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_update_sudah_tanam_a);

        Intent intent = getIntent();
        String idRencanaTanam = intent.getStringExtra("idRencanaTanam");

        getData(idRencanaTanam);

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idRT!=null && idPL!=null && idK!=null && idV!=null) {
                    saveLocalData();
                } else {
                    Toast.makeText(UpdateSudahTanamRT.this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getData(String idRencanaTanam){
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
                                namaRT = modelRencanaTanam.getData().get(i).getNamaRencanaTanam();
                                idRT = modelRencanaTanam.getData().get(i).getIdRencanaTanam();
                                idPL = modelRencanaTanam.getData().get(i).getIdProfilTanah();
                                idK = modelRencanaTanam.getData().get(i).getIdKomoditas();
                                idV = modelRencanaTanam.getData().get(i).getIdVarietas();
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                            }
                        }
                        if (dataRencanaTanam!=null){
                            getDataProfilLahan();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(UpdateSudahTanamRT.this, "Data rencana tanam tidak ada", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProfilLahan() {

        // sumur bor
        tipeSIa = "10a9631e-6add-459e-b7e2-aed3a0c907df";
        // permukaan
        tipeSIb = "26b145d3-632b-4f59-8571-b85a993169b3";
        // tadah hujan
        tipeSIc = "570ca522-6cca-4c9a-9b83-adc7d3cc0389";


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
                                namaPL = modelProfilLahan.getData().get(i).getNamaProfilTanah();
                                idSistemIrigasi = modelProfilLahan.getData().get(i).getIdSistemIrigasi().toString();
                            }
                        } catch (Exception e){ }
                    }
                    if (!namaPL.equalsIgnoreCase("")){
                        getDataAkun();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamRT.this, "Data profil lahan tidak ada", Toast.LENGTH_LONG).show();
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    public void getDataAkun(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> data = apiInterface.getDataAkun();
        data.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body() != null) {
                    for (int i=0; i<modelAkun.getTotalData(); i++){
                        if (modelAkun.getData().get(i).getToken()!=null){
                            listAkunwithToken.add(modelAkun.getData().get(i));
//                            listToken.add(modelAkun.getData().get(i).getToken());
                        }
                    }
                    if (listAkunwithToken!=null){
                        getDataAlamat();
                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
//                                Toast.makeText(UpdateSudahTanamA.this, "Data token tidak ditemukan", Toast.LENGTH_SHORT).show();
//                                setFieldSistemIrigasi();
//
//                            }
//                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void getDataAlamat(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body()!=null){
                    String idAlamat = PreferenceUtils.getIdAlamat(getApplicationContext());
                    try{
                        for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                            if(modelProfilAkun.getData().get(i).getIdAlamat()!=null){
                                if(modelProfilAkun.getData().get(i).getIdAlamat().equalsIgnoreCase(idAlamat)){
                                    listIDAkunwithAlamat.add(modelProfilAkun.getData().get(i).getIdAkun());
                                }
                            }
                        }
                        if (listIDAkunwithAlamat.size()>0){
                            for (int a=0; a<listAkunwithToken.size(); a++){
                                for (int j=0; j<listIDAkunwithAlamat.size(); j++){
                                    if(listAkunwithToken.get(a).getIdAkun().equalsIgnoreCase(listIDAkunwithAlamat.get(j))){
                                        listToken.add(listAkunwithToken.get(a).getToken());
                                    }
                                }
                            }
                        }

                        if (listToken.size()>0){
                            for (int a=0; a<modelAkun.getTotalData(); a++){
                                for (int b=0; b>listToken.size(); b++){
                                    if (modelAkun.getData().get(a).getIdAkun().equalsIgnoreCase(PreferenceUtils.getIdAkun(getApplicationContext()))){
                                        String thistoken = modelAkun.getData().get(a).getToken();
                                        if (listToken.get(b).equalsIgnoreCase(thistoken)){
                                            listToken.remove(listToken.get(b));
                                        }
                                    }
                                }
                            }
                            if (listToken.size()>0){
                                getDataKomoditas();
                            }
                        } else {
                            getDataKomoditas();
                        }

                    } catch (Exception e){ }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(UpdateSudahTanamRT.this, "Data Alamat Tidak Ditemukan", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
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
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamRT.this, "Data komoditas tidak ada", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(UpdateSudahTanamRT.this, "Data varietas tidak ada", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamRT.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData() {
        binding.txtRencanaTanam.setText(namaRT);
        binding.txtProfilLahan.setText(namaPL);
        binding.txtKomoditas.setText(namaKomoditas);
        binding.txtVarietas.setText(namaVarietas);
        /*
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam2.setText("Biaya Sebelumnya : Rp. "+a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak2.setText("Biaya Sebelumnya : Rp. "+b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot2.setText("Biaya Sebelumnya : Rp. "+c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi2.setText("Biaya Sebelumnya : Rp. "+d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan2.setText("Biaya Sebelumnya : Rp. "+e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk2.setText("Biaya Sebelumnya : Rp. "+f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen2.setText("Biaya Sebelumnya : Rp. "+g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak2.setText("Biaya Sebelumnya : Rp. "+h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen2.setText("Biaya Sebelumnya : Rp. "+i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam2.setText("Biaya Sebelumnya : Rp. "+j);

        if(dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa2.setText("Biaya Sebelumnya : Rp. "+k);
        } else {
            txt_mesin_pompa2.setText("-");
        }

        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm2.setText("Biaya Sebelumnya : Rp. "+l);
        } else {
            txt_mesin_pompa_bbm2.setText("-");
        }

        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local2.setText("Biaya Sebelumnya : Rp. "+m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi2.setText("Biaya Sebelumnya : Rp. "+n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local.setText(o);
        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska2.setText("Biaya Sebelumnya : Rp. "+p);
        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea2.setText("Biaya Sebelumnya : Rp. "+q);
        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat2.setText("Biaya Sebelumnya : Rp. "+r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik2.setText("Biaya Sebelumnya : Rp. "+s);
*/
    }

    private void saveLocalData() {

        if(idSistemIrigasi.equalsIgnoreCase(tipeSIb)){
            isWithPompa = false;
        } else {
            isWithPompa = true;
        }

        DatumSudahTanam datumSudahTanam = new DatumSudahTanam(idRT, idPL, "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "","", "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToB();
    }

    public void moveToB(){
        Intent a = new Intent(UpdateSudahTanamRT.this, InputSudahTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(UpdateSudahTanamRT.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal update sudah tanam ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListST();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }
}