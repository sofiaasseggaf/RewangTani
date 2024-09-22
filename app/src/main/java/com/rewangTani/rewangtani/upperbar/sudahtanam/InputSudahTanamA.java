package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamABinding;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
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

public class InputSudahTanamA extends AppCompatActivity {

    UpperbarStInputSudahTanamABinding binding;
    ModelRencanaTanam modelRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    DatumRencanaTanam datumRencanaTanam;
    ModelAkun modelAkun;
    List<String> listToken = new ArrayList<String>();
    List<DatumAkun> listAkunwithToken = new ArrayList<>();
    ModelProfilAkun modelProfilAkun;
    List<String> listIDAkunwithAlamat = new ArrayList<>();
    List<String> listRT = new ArrayList<String>();
    ArrayAdapter<String> adapterRT;
    String namaRT, idRT, namaPL, idPL, namaK, idK, namaV, idV;
    String tipeSIa, tipeSIb, tipeSIc, idSistemIrigasi;
    DecimalFormat formatter;
    boolean isWithPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_a);

        getData();

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        binding.spRencanaTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaRT = binding.spRencanaTanam.getSelectedItem().toString();
                for (int a=0; a<modelRencanaTanam.getTotalData(); a++){
                    try {
                        if (modelRencanaTanam.getData().get(a).getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                            idRT = modelRencanaTanam.getData().get(a).getIdRencanaTanam();
                            idPL = modelRencanaTanam.getData().get(a).getIdProfilTanah();
                            idK = modelRencanaTanam.getData().get(a).getIdKomoditas();
                            idV = modelRencanaTanam.getData().get(a).getIdVarietas();
                            datumRencanaTanam = modelRencanaTanam.getData().get(a);
                            setDataPL();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idRT!=null && idPL!=null && idK!=null && idV!=null) {
                    saveLocalData();
                } else {
                    Toast.makeText(InputSudahTanamA.this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                }

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
                getRencanaTanam();
            }
        }).start();
    }

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRT.add(modelRencanaTanam.getData().get(i).getNamaRencanaTanam());
                                //listProfilLahan.add(modelRencanaTanam.getData().get(i).getIdProfilTanah());
                            }
                        } catch (Exception e){ }
                    }
                    if (listRT.size()!=0){
                        getDataProfilLahan();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputSudahTanamA.this);
                                builder.setMessage("Buat rencana tanam terlebih dahulu")
                                        .setPositiveButton("Buat Rencana Tanam", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                goToRencanaTanam();
                                            }
                                        })
                                        .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                goToListST();
                                            }
                                        })
                                        .create()
                                        .show();
                               }

                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    getDataKomoditas();
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
                                Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    getDataVarietas();
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    getDataAkun();
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
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
                            //listToken.add(modelAkun.getData().get(i).getToken());
                        }
                    }
                    if (listAkunwithToken!=null){
                        getDataAlamat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Data token tidak ditemukan", Toast.LENGTH_SHORT).show();
                                setDataSpinnerRT();

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                        setDataSpinnerRT();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    setDataSpinnerRT();
                                    //Toast.makeText(InputSudahTanam.this, "Data Token Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (Exception e){ }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            //Toast.makeText(TambahInfoPeringatanCuaca.this, "Data Profil Tidak Ditemukan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    public void setDataSpinnerRT(){

        // sumur bor
        tipeSIa = "10a9631e-6add-459e-b7e2-aed3a0c907df";
        // permukaan
        tipeSIb = "26b145d3-632b-4f59-8571-b85a993169b3";
        // tadah hujan
        tipeSIc = "570ca522-6cca-4c9a-9b83-adc7d3cc0389";

        adapterRT = new ArrayAdapter<String>(InputSudahTanamA.this, R.layout.z_spinner_list, listRT);
        adapterRT.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spRencanaTanam.setAdapter(adapterRT);

    }

    private void setDataPL(){
        for (int a=0; a<=modelProfilLahan.getTotalData(); a++){
            if(modelProfilLahan.getData().get(a).getIdProfileTanah().equalsIgnoreCase(idPL)){
                namaPL = modelProfilLahan.getData().get(a).getNamaProfilTanah();
                idSistemIrigasi = modelProfilLahan.getData().get(a).getIdSistemIrigasi().toString();
                binding.txtProfilLahan.setText(namaPL);
                setDataK();
            }
        }
    }

    private void setDataK(){
        for (int a=0; a<=modelKomoditas.getTotalData(); a++){
            if(modelKomoditas.getData().get(a).getIdKomoditas().equalsIgnoreCase(idK)){
                namaK = modelKomoditas.getData().get(a).getNamaKomoditas();
                binding.txtKomoditas.setText(namaK);
               setDataV();
            }
        }
    }

    private void setDataV(){
        for (int a=0; a<=modelVarietas.getTotalData(); a++){
            if(modelVarietas.getData().get(a).getIdVarietas().equalsIgnoreCase(idV)){
                namaV = modelVarietas.getData().get(a).getNamaVarietas().toString();
                binding.txtVarietas.setText(namaV);
            }
        }
    }

    /*
    public void setHargaSebelumnya(){

        String a = checkDesimal(datumRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam2.setText("Biaya Sebelumnya : Rp. "+a);
        String b = checkDesimal(datumRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak2.setText("Biaya Sebelumnya : Rp. "+b);
        String c = checkDesimal(datumRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot2.setText("Biaya Sebelumnya : Rp. "+c);
        String d = checkDesimal(datumRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi2.setText("Biaya Sebelumnya : Rp. "+d);
        String e = checkDesimal(datumRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan2.setText("Biaya Sebelumnya : Rp. "+e);
        String f = checkDesimal(datumRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk2.setText("Biaya Sebelumnya : Rp. "+f);
        String g = checkDesimal(datumRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen2.setText("Biaya Sebelumnya : Rp. "+g);
        String h = checkDesimal(datumRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak2.setText("Biaya Sebelumnya : Rp. "+h);
        String i = checkDesimal(datumRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen2.setText("Biaya Sebelumnya : Rp. "+i);
        String j = checkDesimal(datumRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam2.setText("Biaya Sebelumnya : Rp. "+j);

        if(datumRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(datumRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa2.setText("Biaya Sebelumnya : Rp. "+k);
        } else {
            txt_mesin_pompa2.setText("-");
        }

        if (datumRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(datumRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm2.setText("Biaya Sebelumnya : Rp. "+l);
        } else {
            txt_mesin_pompa_bbm2.setText("-");
        }

        String m = checkDesimal(datumRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local2.setText("Biaya Sebelumnya : Rp. "+m);
        String n = checkDesimal(datumRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi2.setText("Biaya Sebelumnya : Rp. "+n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local.setText(o);
        String p = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska2.setText("Biaya Sebelumnya : Rp. "+p);
        String q = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea2.setText("Biaya Sebelumnya : Rp. "+q);
        String r = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat2.setText("Biaya Sebelumnya : Rp. "+r);
        String s = checkDesimal(datumRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik2.setText("Biaya Sebelumnya : Rp. "+s);

    }
    */

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
        Intent a = new Intent(InputSudahTanamA.this, InputSudahTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(InputSudahTanamA.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    public  void goToRencanaTanam(){
        Intent a = new Intent(InputSudahTanamA.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal input sudah tanam ?")
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