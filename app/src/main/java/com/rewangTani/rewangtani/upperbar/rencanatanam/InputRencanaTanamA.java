package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamABinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputRencanaTanamA extends AppCompatActivity {

    UpperbarRtInputRencanaTanamABinding binding;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    List<String> listProfilLahan = new ArrayList<String>();
    List<String> listKomoditas = new ArrayList<String>();
    List<String> listVarietas = new ArrayList<String>();
    List<String> listRekomendasiVarietas = new ArrayList<String>();
    ArrayAdapter<String> adapterProfilLahan, adapterKomoditas, adapterVarietas;
    String namaKomoditas, idKomoditas, namaVarietas, idVarietas, potensiHasilVarietas, namaProfilLahan, idProfilLahan, idSistemIrigasi, luasLahan;
    String tipeSIa, tipeSIb, tipeSIc;
    double mdpl;
    int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_a);

        getandsetData();

        binding.spProfilLahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaProfilLahan = binding.spProfilLahan.getSelectedItem().toString();
                for (int a=0; a<modelProfilLahan.getTotalData(); a++){
                    try {
                        if (modelProfilLahan.getData().get(a).getNamaProfilTanah().equalsIgnoreCase(namaProfilLahan)){
                            idProfilLahan = modelProfilLahan.getData().get(a).getIdProfileTanah();
                            idSistemIrigasi = modelProfilLahan.getData().get(a).getIdSistemIrigasi().toString();
                            luasLahan = modelProfilLahan.getData().get(a).getLuasGarapan().toString();
                            binding.txtLuasLahan.setText("Luas lahan : "+luasLahan+" m2");
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        binding.spKomoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaKomoditas = binding.spKomoditas.getSelectedItem().toString();
                listVarietas.clear();
                for (int i=0; i<modelKomoditas.getTotalData(); i++){
                    if (modelKomoditas.getData().get(i).getNamaKomoditas().equalsIgnoreCase(namaKomoditas)){
                        idKomoditas = modelKomoditas.getData().get(i).getIdKomoditas();
                    }
                }
                for (int a=0; a<modelVarietas.getTotalData(); a++) {
                    try {
                        if (modelVarietas.getData().get(a).getIdKomoditas().toString().equalsIgnoreCase(idKomoditas)) {
                            listVarietas.add(modelVarietas.getData().get(a).getNamaVarietas());
                        }
                    } catch (Exception e){}
                }
                Collections.sort(listVarietas);
                if (listVarietas!=null){
                    adapterVarietas = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listVarietas);
                    binding.spVarietas.setThreshold(1);
                    binding.spVarietas.setAdapter(adapterVarietas);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        binding.spVarietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spVarietas.showDropDown();
            }
        });

        binding.spVarietas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (adapterVarietas!=null){
                        adapterVarietas.getFilter().filter(charSequence);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                namaVarietas = binding.spVarietas.getText().toString();
                for (int a=0; a<modelVarietas.getTotalData(); a++){
                    try{
                        if (modelVarietas.getData().get(a).getNamaVarietas().equalsIgnoreCase(namaVarietas)){
                            idVarietas = modelVarietas.getData().get(a).getIdVarietas();
                            potensiHasilVarietas = modelVarietas.getData().get(a).getPotensiHasil().toString();
                        }
                    } catch (Exception e){}
                }
            }
        });



        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check dulu baru move ke B !
                moveToB();

            }
        });

    }

    public void getandsetData(){
        binding.frameLoading.setVisibility(View.VISIBLE);
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
                getDataProfilLahan();
            }
        }).start();
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
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelProfilLahan.getData().get(i).getIdUser())) {
                                listProfilLahan.add(modelProfilLahan.getData().get(i).getNamaProfilTanah().toString());
                            }
                        } catch (Exception e){ }
                    }
                    if (listProfilLahan.size()!=0){
                        getDataKomoditas();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.frameLoading.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputRencanaTanamA.this);
                                builder.setMessage("Buat profil lahan terlebih dahulu")
                                        .setPositiveButton("Buat Profil Lahan", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                goToProfilLahan();
                                            }
                                        })
                                        .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                goToListRT();
                                            }
                                        })
                                        .create()
                                        .show();

                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.frameLoading.setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamA.this, "Data profil lahan tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                                binding.frameLoading.setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            listKomoditas.add(modelKomoditas.getData().get(i).getNamaKomoditas());
                        } catch (Exception e){ }
                    }
                    if (listKomoditas!=null){
                        getDataVarietas();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.frameLoading.setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamA.this, "Data komoditas tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.frameLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    getLoc();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.frameLoading.setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamA.this, "Data varietas tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.frameLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getLoc(){

        if(ActivityCompat.checkSelfPermission(InputRencanaTanamA.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(InputRencanaTanamA.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.frameLoading.setVisibility(View.GONE);
                    ActivityCompat.requestPermissions(InputRencanaTanamA.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
                }
            });
        }

        if(ActivityCompat.checkSelfPermission(InputRencanaTanamA.this,Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(InputRencanaTanamA.this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                        PackageManager.PERMISSION_GRANTED)
        {
            getRekomendasiVarietas();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.frameLoading.setVisibility(View.GONE);
                    Toast.makeText(InputRencanaTanamA.this, "GPS bermasalah", Toast.LENGTH_SHORT).show();
                    binding.txtRekVarietas.setTextColor(getResources().getColor(R.color.red));
                    binding.txtRekVarietas.setText("Rekomendasi varietas = GPS bermasalah");
                    setDataSpinner();
                }
            });
        }
    }

    private void getRekomendasiVarietas(){
        for (int i=0; i<modelVarietas.getTotalData(); i++){
            if (modelVarietas.getData().get(i).getAnjuranKetinggian()!=null){
                if (modelVarietas.getData().get(i).getAnjuranKetinggian()>600){
                    listRekomendasiVarietas.add(modelVarietas.getData().get(i).getNamaVarietas());
                }
            }
        }

        if (listRekomendasiVarietas.size()>0){
            Collections.shuffle(listRekomendasiVarietas);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.frameLoading.setVisibility(View.GONE);
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    @SuppressLint("MissingPermission")
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mdpl = location.getVerticalAccuracyMeters();
                        if (String.valueOf(mdpl)!=null || String.valueOf(mdpl).equalsIgnoreCase("")){
                            mdpl = mdpl*10;
                            //String a = String.valueOf(mdpl).substring(0,5);
                            String a = String.valueOf(mdpl);
                            binding.txtMdpl.setText("Ketinggian lahan : "+a+" mdpl");
                        }
                    }
                    binding.txtRekVarietas.setText("Rekomendasi varietas = " + listRekomendasiVarietas.get(0) + ", " + listRekomendasiVarietas.get(1) + ", " + listRekomendasiVarietas.get(2) + ", ");
                    setDataSpinner();
                }
            });
        }
    }

    public void setDataSpinner(){

        // sumur bor
        tipeSIa = "10a9631e-6add-459e-b7e2-aed3a0c907df";
        // permukaan
        tipeSIb = "26b145d3-632b-4f59-8571-b85a993169b3";
        // tadah hujan
        tipeSIc = "570ca522-6cca-4c9a-9b83-adc7d3cc0389";

        adapterProfilLahan = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listProfilLahan);
        adapterProfilLahan.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spProfilLahan.setAdapter(adapterProfilLahan);

        adapterKomoditas = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listKomoditas);
        adapterKomoditas.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spKomoditas.setAdapter(adapterKomoditas);
    }

    /*
    private void sendDataRencanaTanam(){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idUser", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProfilTanah", idProfilLahan);
        jsonParams.put("idKomoditas", idKomoditas);
        jsonParams.put("idVarietas", idVarietas);
        jsonParams.put("idBiayaBuruhTanam", txt_buruh_tanam.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhBajak", txt_buruh_bajak.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhSemprot", txt_buruh_semprot.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhMenyiangirumput", txt_buruh_menyiangi.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhGalangan", txt_buruh_galengan.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhPupuk", txt_buruh_pupuk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaBuruhPanen", txt_buruh_panen.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewaMesinBajak", txt_mesin_bajak.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewaMesinTanam", txt_mesin_tanam.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewaMesinPanen", txt_mesin_panen.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayabibitLocalHet", txt_bibit_local.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayabibitSubsidi", txt_bibit_subsidi.getText().toString().replaceAll("[^0-9]", ""));
        //jsonParams.put("idBiayapupukKimiaLocalHet", txt_pupuk_kimia_local.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukKimiaLocalHet", "0");
        jsonParams.put("idBiayapupukKimiaPhonska", txt_pupuk_kimia_phonska.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukOrganik", txt_pupuk_organik.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("namaRencanaTanam", et_nama_rt.getText().toString());
        jsonParams.put("idBiayapupukKimiaUrea", txt_pupuk_kimia_urea.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukKimiaFosfat", txt_pupuk_kimia_fosfat.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinPompa", txt_pompa);
        //jsonParams.put("idSewaMesinPompa", txt_mesin_pompa.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinPompaBbm", txt_pompabbm);
        //jsonParams.put("idSewaMesinPompaBbm", txt_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseRencanaTanam> response = apiInterface.sendDataRencanaTanam(body);
        response.enqueue(new Callback<ResponseRencanaTanam>() {
            @Override
            public void onResponse(Call<ResponseRencanaTanam> call, retrofit2.Response<ResponseRencanaTanam> rawResponse) {
                try {
                    Log.d("tag", String.valueOf(rawResponse.body()));
                    if (rawResponse.body() != null) {
                        modelRencanaTanam = rawResponse.body();
                        try{
                            String idRT = modelRencanaTanam.getData().getIdRencanaTanam();
                            if (idRT!=null){
                                PreferenceUtils.saveIdRt(modelRencanaTanam.getData().getIdRencanaTanam(), getApplicationContext());
                                sendOutput();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.frameLoading.setVisibility(View.GONE);
                                        Toast.makeText(InputRencanaTanamA.this, "Rencana tanam tidak ditemukan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e){ }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              binding.frameLoading.setVisibility(View.GONE);
                              Toast.makeText(InputRencanaTanamA.this, "Gagal buat rencana tanam", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseRencanaTanam> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       binding.frameLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }
    */

    /*
    private void sendOutput(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idRencanaTanam", PreferenceUtils.getIdRt(getApplicationContext()));
        jsonParams.put("estBiayaProduksi", String.valueOf(total));
        jsonParams.put("estHasil", hasil);
        jsonParams.put("estPendapatan", pendapatan);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataOutputRT(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getOutput();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.frameLoading.setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Gagal buat rencana tanam", Toast.LENGTH_LONG).show();
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
                        binding.frameLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }
    */

    /*
    public void getOutput() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.getDataOutputRT();
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                            if(modelOutputRencanaTanam.getData().get(i).getIdRencanaTanam()
                                    .equalsIgnoreCase(PreferenceUtils.getIdRt(getApplicationContext()))){
                                dataOutputRT = modelOutputRencanaTanam.getData().get(i);
                                PreferenceUtils.saveOIdRt(dataOutputRT.getIdOutputRencanaTanam(), getApplicationContext());
                            }
                        }
                        if (dataOutputRT!=null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.frameLoading.setVisibility(View.GONE);
                                    cekRT();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.frameLoading.setVisibility(View.GONE);
                                    Toast.makeText(InputRencanaTanamA.this, "Rencana tanam tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.frameLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    */

    public void moveToB(){
        Intent a = new Intent(InputRencanaTanamA.this, InputRencanaTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToListRT(){
        Intent a = new Intent(InputRencanaTanamA.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(InputRencanaTanamA.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Rencana Tanam ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListRT();
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