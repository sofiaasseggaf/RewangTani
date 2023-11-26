package com.rewangTani.rewangtani.upperbar.rencanatanam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

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
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamABinding;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamBBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.DatumOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ResponseRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputRencanaTanamB extends AppCompatActivity {

    UpperbarRtInputRencanaTanamBBinding binding;
    ResponseRencanaTanam modelRencanaTanam;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    DatumOutputRencanaTanam dataOutputRT;
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
    int pompa, hargaBBM;
    double total, hasil, pendapatan, mdpl;
    String numberOnly, txt_pompa, txt_pompabbm;
    int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_b);


        //getandsetData();

        binding.buruhTanam.addTextChangedListener(new NumberTextWatcher(binding.buruhTanam));
        binding.buruhBajak.addTextChangedListener(new NumberTextWatcher(binding.buruhBajak));
        binding.buruhSemprot.addTextChangedListener(new NumberTextWatcher(binding.buruhSemprot));
        binding.buruhMenyiangiRumput.addTextChangedListener(new NumberTextWatcher(binding.buruhMenyiangiRumput));
        binding.buruhGalengan.addTextChangedListener(new NumberTextWatcher(binding.buruhGalengan));
        binding.buruhPupuk.addTextChangedListener(new NumberTextWatcher(binding.buruhPupuk));
        binding.buruhPanen.addTextChangedListener(new NumberTextWatcher(binding.buruhPanen));


       /* sp_profil_lahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaProfilLahan = sp_profil_lahan.getSelectedItem().toString();
                for (int a=0; a<modelProfilLahan.getTotalData(); a++){
                    try {
                        if (modelProfilLahan.getData().get(a).getNamaProfilTanah().equalsIgnoreCase(namaProfilLahan)){
                            idProfilLahan = modelProfilLahan.getData().get(a).getIdProfileTanah();
                            idSistemIrigasi = modelProfilLahan.getData().get(a).getIdSistemIrigasi().toString();
                            luasLahan = modelProfilLahan.getData().get(a).getLuasGarapan().toString();
                            setFieldSistemIrigasi();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        sp_komoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaKomoditas = sp_komoditas.getSelectedItem().toString();
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
                    sp_varietas.setThreshold(1);
                    sp_varietas.setAdapter(adapterVarietas);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        sp_varietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_varietas.showDropDown();
            }
        });

        sp_varietas.addTextChangedListener(new TextWatcher() {
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
                namaVarietas = sp_varietas.getText().toString();
                for (int a=0; a<modelVarietas.getTotalData(); a++){
                    try{
                        if (modelVarietas.getData().get(a).getNamaVarietas().equalsIgnoreCase(namaVarietas)){
                            idVarietas = modelVarietas.getData().get(a).getIdVarietas();
                            potensiHasilVarietas = modelVarietas.getData().get(a).getPotensiHasil().toString();
                        }
                    } catch (Exception e){}
                }
            }
        });*/


        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check trus ke C !
                moveToC();

                /*
                if(pompa==1){
                    if(!txt_buruh_tanam.getText().toString().equalsIgnoreCase("")&&!txt_buruh_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_semprot.getText().toString().equalsIgnoreCase("")&&!txt_buruh_menyiangi.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_galengan.getText().toString().equalsIgnoreCase("")&&!txt_buruh_pupuk.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_panen.getText().toString().equalsIgnoreCase("")&&!txt_mesin_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_tanam.getText().toString().equalsIgnoreCase("")&&!txt_mesin_panen.getText().toString().equalsIgnoreCase("")&&
                            !txt_bibit_local.getText().toString().equalsIgnoreCase("")&&!txt_bibit_subsidi.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_phonska.getText().toString().equalsIgnoreCase("")&& !txt_pupuk_kimia_urea.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_fosfat.getText().toString().equalsIgnoreCase("")&& !txt_pupuk_organik.getText().toString().equalsIgnoreCase("")) {
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
                                hitungTanpaPompa();
                            }
                        }).start();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Isi semua field terlebih dahulu", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else if(pompa==2){
                    if(!txt_buruh_tanam.getText().toString().equalsIgnoreCase("")&&!txt_buruh_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_semprot.getText().toString().equalsIgnoreCase("")&&!txt_buruh_menyiangi.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_galengan.getText().toString().equalsIgnoreCase("")&&!txt_buruh_pupuk.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_panen.getText().toString().equalsIgnoreCase("")&&!txt_mesin_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_tanam.getText().toString().equalsIgnoreCase("")&&!txt_mesin_panen.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_pompa.getText().toString().equalsIgnoreCase("")&&!txt_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")&&
                            !txt_bibit_local.getText().toString().equalsIgnoreCase("")&&!txt_bibit_subsidi.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_phonska.getText().toString().equalsIgnoreCase("")&&!txt_pupuk_kimia_urea.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_fosfat.getText().toString().equalsIgnoreCase("")&&!txt_pupuk_organik.getText().toString().equalsIgnoreCase("")&&
                            !et_nama_rt.getText().toString().equalsIgnoreCase("")&&!txt_durasi_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")){
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
                                hitung();
                            }
                        }).start();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Isi semua field terlebih dahulu", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamA.this, "Pilih profil lahan terlebih dahulu !", Toast.LENGTH_LONG).show();
                        }
                    });
                }

*/
            }
        });

    }

/*    public void getandsetData(){
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputRencanaTanamB.this);
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
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamB.this, "Data profil lahan tidak ditemukan", Toast.LENGTH_SHORT).show();
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamB.this, "Data komoditas tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamB.this, "Data varietas tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getLoc(){

        if(ActivityCompat.checkSelfPermission(InputRencanaTanamB.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(InputRencanaTanamB.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                    ActivityCompat.requestPermissions(InputRencanaTanamB.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
                }
            });
        }

        if(ActivityCompat.checkSelfPermission(InputRencanaTanamB.this,Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(InputRencanaTanamB.this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                        PackageManager.PERMISSION_GRANTED)
        {
            getRekomendasiVarietas();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
//                    Toast.makeText(InputRencanaTanamA.this, "GPS bermasalah", Toast.LENGTH_SHORT).show();
//                    txt_rek_varietas.setTextColor(getResources().getColor(R.color.red));
//                    txt_rek_varietas.setText("Rekomendasi varietas = GPS bermasalah");
//                    setDataSpinner();
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
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    @SuppressLint("MissingPermission")
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mdpl = location.getVerticalAccuracyMeters();
                        if (String.valueOf(mdpl)!=null || String.valueOf(mdpl).equalsIgnoreCase("")){
                            mdpl = mdpl*10;
                            //String a = String.valueOf(mdpl).substring(0,5);
                            String a = String.valueOf(mdpl);
                            txt_mdpl.setText("Ketinggian lahan : "+a+" mdpl");
                        }
                    }
                    txt_rek_varietas.setText("Rekomendasi varietas = " + listRekomendasiVarietas.get(0) + ", " + listRekomendasiVarietas.get(1) + ", " + listRekomendasiVarietas.get(2) + ", ");
                    setDataSpinner();
                }
            });
        }
    }

    public void setFieldSistemIrigasi(){

        txt_luas_lahan.setText("Luas lahan : "+luasLahan+" m2");
        if(idSistemIrigasi.equalsIgnoreCase(tipeSIb)){
            ll_pompa.setVisibility(View.GONE);
            pompa = 1;
        } else {
            ll_pompa.setVisibility(View.VISIBLE);
            pompa = 2;
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
        sp_profil_lahan.setAdapter(adapterProfilLahan);

        adapterKomoditas = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listKomoditas);
        adapterKomoditas.setDropDownViewResource(R.layout.z_spinner_list);
        sp_komoditas.setAdapter(adapterKomoditas);
    }

    public void hitungTanpaPompa(){

        int a = Integer.valueOf(txt_buruh_tanam.getText().toString().replaceAll("[^0-9]", ""));
        int b = Integer.valueOf(txt_buruh_bajak.getText().toString().replaceAll("[^0-9]", ""));
        int c = Integer.valueOf(txt_buruh_semprot.getText().toString().replaceAll("[^0-9]", ""));
        int d = Integer.valueOf(txt_buruh_menyiangi.getText().toString().replaceAll("[^0-9]", ""));
        int e = Integer.valueOf(txt_buruh_galengan.getText().toString().replaceAll("[^0-9]", ""));
        int f = Integer.valueOf(txt_buruh_pupuk.getText().toString().replaceAll("[^0-9]", ""));
        int g = Integer.valueOf(txt_buruh_panen.getText().toString().replaceAll("[^0-9]", ""));

        int h = Integer.valueOf(txt_mesin_bajak.getText().toString().replaceAll("[^0-9]", ""));
        int i = Integer.valueOf(txt_mesin_tanam.getText().toString().replaceAll("[^0-9]", ""));
        int j = Integer.valueOf(txt_mesin_panen.getText().toString().replaceAll("[^0-9]", ""));
        //int k = Integer.valueOf(txt_mesin_pompa.getText().toString());
        //int l = Integer.valueOf(txt_mesin_pompa_bbm.getText().toString());

        int m = Integer.valueOf(txt_bibit_local.getText().toString().replaceAll("[^0-9]", ""));
        int n = Integer.valueOf(txt_bibit_subsidi.getText().toString().replaceAll("[^0-9]", ""));

        //int o = Integer.valueOf(txt_pupuk_kimia_local.getText().toString().replaceAll("[^0-9]", ""));
        int p = Integer.valueOf(txt_pupuk_kimia_phonska.getText().toString().replaceAll("[^0-9]", ""));
        int q = Integer.valueOf(txt_pupuk_kimia_urea.getText().toString().replaceAll("[^0-9]", ""));
        int r = Integer.valueOf(txt_pupuk_kimia_fosfat.getText().toString().replaceAll("[^0-9]", ""));
        int s = Integer.valueOf(txt_pupuk_organik.getText().toString().replaceAll("[^0-9]", ""));

        int obat = 1000000;
        int luaslahan = Integer.valueOf(luasLahan.replaceAll("[^0-9]", ""));
        //double hektar = luaslahan/10000;

        total = a+b+c+d+e+f+g+h+i+j+m+n+p+q+r+s+obat;
        Double estimasihasil = Double.valueOf(potensiHasilVarietas);
        hasil = estimasihasil*luaslahan/10;
        pendapatan = 10000*hasil;

        txt_pompa = "0";
        txt_pompabbm = "0";

        sendDataRencanaTanam();
    }

    public void hitung(){

        int a = Integer.valueOf(txt_buruh_tanam.getText().toString().replaceAll("[^0-9]", ""));
        int b = Integer.valueOf(txt_buruh_bajak.getText().toString().replaceAll("[^0-9]", ""));
        int c = Integer.valueOf(txt_buruh_semprot.getText().toString().replaceAll("[^0-9]", ""));
        int d = Integer.valueOf(txt_buruh_menyiangi.getText().toString().replaceAll("[^0-9]", ""));
        int e = Integer.valueOf(txt_buruh_galengan.getText().toString().replaceAll("[^0-9]", ""));
        int f = Integer.valueOf(txt_buruh_pupuk.getText().toString().replaceAll("[^0-9]", ""));
        int g = Integer.valueOf(txt_buruh_panen.getText().toString().replaceAll("[^0-9]", ""));

        int h = Integer.valueOf(txt_mesin_bajak.getText().toString().replaceAll("[^0-9]", ""));
        int i = Integer.valueOf(txt_mesin_tanam.getText().toString().replaceAll("[^0-9]", ""));
        int j = Integer.valueOf(txt_mesin_panen.getText().toString().replaceAll("[^0-9]", ""));
        int k = Integer.valueOf(txt_mesin_pompa.getText().toString().replaceAll("[^0-9]", ""));
        int l = Integer.valueOf(txt_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""));

        int m = Integer.valueOf(txt_bibit_local.getText().toString().replaceAll("[^0-9]", ""));
        int n = Integer.valueOf(txt_bibit_subsidi.getText().toString().replaceAll("[^0-9]", ""));

        //int o = Integer.valueOf(txt_pupuk_kimia_local.getText().toString().replaceAll("[^0-9]", ""));
        int p = Integer.valueOf(txt_pupuk_kimia_phonska.getText().toString().replaceAll("[^0-9]", ""));
        int q = Integer.valueOf(txt_pupuk_kimia_urea.getText().toString().replaceAll("[^0-9]", ""));
        int r = Integer.valueOf(txt_pupuk_kimia_fosfat.getText().toString().replaceAll("[^0-9]", ""));
        int s = Integer.valueOf(txt_pupuk_organik.getText().toString().replaceAll("[^0-9]", ""));

        int obat = 1000000;
        int luaslahan = Integer.valueOf(luasLahan.replaceAll("[^0-9]", ""));
        double hektar = Double.valueOf(luasLahan)/10000;

        Double estimasihasil = Double.valueOf(potensiHasilVarietas);
        hasil = estimasihasil*luaslahan/10;
        pendapatan = 10000*hasil;

        int durasi = Integer.valueOf(txt_durasi_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""))*2;
        double bbm = l*durasi*hektar;
        txt_pompa = txt_mesin_pompa.getText().toString().replaceAll("[^0-9]", "");
        txt_pompabbm = String.valueOf(bbm);
        total = a+b+c+d+e+f+g+h+i+j+k+bbm+m+n+p+q+r+s+obat;

        sendDataRencanaTanam();
    }

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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                        Toast.makeText(InputRencanaTanamA.this, "Rencana tanam tidak ditemukan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e){ }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

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
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    cekRT();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }*/

    public void moveToC(){
        Intent a = new Intent(InputRencanaTanamB.this, InputRencanaTanamC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void moveToA(){
        Intent a = new Intent(InputRencanaTanamB.this, InputRencanaTanamA.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void cekRT(){
        Intent a = new Intent(InputRencanaTanamB.this, DetailRencanaTanamRAB.class);
        startActivity(a);
    }

    public void goToListRT(){
        Intent a = new Intent(InputRencanaTanamB.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(InputRencanaTanamB.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToA();
    }

}