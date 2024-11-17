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
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
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
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
    List<String> listProfilLahan = new ArrayList<String>();
    List<String> listKomoditas = new ArrayList<String>();
    List<String> listVarietas = new ArrayList<String>();
    List<String> listRekomendasiVarietas = new ArrayList<String>();
    ArrayAdapter<String> adapterProfilLahan, adapterKomoditas, adapterVarietas;
    String namaKomoditas, idKomoditas, namaVarietas, idVarietas, potensiHasilVarietas, namaProfilLahan, idProfilLahan, idSistemIrigasi, luasLahan;
    String tipeSIa, tipeSIb, tipeSIc;
    double mdpl;
    int checkNama;
    boolean isWithPompa;

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
                for (int a = 0; a < modelProfilLahan.getTotalData(); a++) {
                    try {
                        if (modelProfilLahan.getData().get(a).getNamaProfilTanah().equalsIgnoreCase(namaProfilLahan)) {
                            idProfilLahan = modelProfilLahan.getData().get(a).getIdProfileTanah();
                            idSistemIrigasi = modelProfilLahan.getData().get(a).getIdSistemIrigasi().toString();
                            luasLahan = modelProfilLahan.getData().get(a).getLuasGarapan().toString();
                            binding.txtLuasLahan.setText("Luas lahan : " + luasLahan + " m2");
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        binding.spKomoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaKomoditas = binding.spKomoditas.getSelectedItem().toString();
                listVarietas.clear();
                for (int i = 0; i < modelKomoditas.getTotalData(); i++) {
                    if (modelKomoditas.getData().get(i).getNamaKomoditas().equalsIgnoreCase(namaKomoditas)) {
                        idKomoditas = modelKomoditas.getData().get(i).getIdKomoditas();
                    }
                }
                for (int a = 0; a < modelVarietas.getTotalData(); a++) {
                    try {
                        if (modelVarietas.getData().get(a).getIdKomoditas().toString().equalsIgnoreCase(idKomoditas)) {
                            listVarietas.add(modelVarietas.getData().get(a).getNamaVarietas());
                        }
                    } catch (Exception e) {
                    }
                }
                Collections.sort(listVarietas);
                if (listVarietas != null) {
                    adapterVarietas = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listVarietas);
                    binding.spVarietas.setThreshold(1);
                    binding.spVarietas.setAdapter(adapterVarietas);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
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
                try {
                    if (adapterVarietas != null) {
                        adapterVarietas.getFilter().filter(charSequence);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                namaVarietas = binding.spVarietas.getText().toString();
                for (int a = 0; a < modelVarietas.getTotalData(); a++) {
                    try {
                        if (modelVarietas.getData().get(a).getNamaVarietas().equalsIgnoreCase(namaVarietas)) {
                            idVarietas = modelVarietas.getData().get(a).getIdVarietas();
                            potensiHasilVarietas = modelVarietas.getData().get(a).getPotensiHasil().toString();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNama();
            }
        });

    }

    public void getandsetData() {
        binding.viewLoading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . .");
                } else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . .");
                }
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
                if (response.body() != null) {
                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRencanaTanam.add(modelRencanaTanam.getData().get(i));
                            }
                        } catch (Exception e) {
                        }
                    }
                    getDataProfilLahan();
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.frameLayout.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                if (response.body() != null) {
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelProfilLahan.getData().get(i).getIdUser())) {
                                listProfilLahan.add(modelProfilLahan.getData().get(i).getNamaProfilTanah().toString());
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (listProfilLahan.size() != 0) {
                        getDataKomoditas();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
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
                            binding.viewLoading.setVisibility(View.GONE);
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
                                binding.viewLoading.setVisibility(View.GONE);
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
                if (response.body() != null) {
                    for (int i = 0; i < modelKomoditas.getTotalData(); i++) {
                        try {
                            listKomoditas.add(modelKomoditas.getData().get(i).getNamaKomoditas());
                        } catch (Exception e) {
                        }
                    }
                    if (listKomoditas != null) {
                        getDataVarietas();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
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
                        binding.viewLoading.setVisibility(View.GONE);
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
                if (response.body() != null) {
                    getRekomendasiVarietas();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
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
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getRekomendasiVarietas() {

        for (int i = 0; i < modelVarietas.getTotalData(); i++) {
            if (modelVarietas.getData().get(i).getAnjuranKetinggian() != null) {
                if (modelVarietas.getData().get(i).getAnjuranKetinggian() > 600) {
                    listRekomendasiVarietas.add(modelVarietas.getData().get(i).getNamaVarietas());
                }
            }
        }

        if (listRekomendasiVarietas.size() > 0) {
            Collections.shuffle(listRekomendasiVarietas);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    @SuppressLint("MissingPermission")
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mdpl = location.getVerticalAccuracyMeters();
                        if (String.valueOf(mdpl) != null || String.valueOf(mdpl).equalsIgnoreCase("")) {
                            mdpl = mdpl * 10;
                            //String a = String.valueOf(mdpl).substring(0,5);
                            String a = String.valueOf(mdpl);
                            binding.txtMdpl.setText("Ketinggian lahan : " + a + " mdpl");
                        }
                    }
                    binding.txtRekVarietas.setText("Rekomendasi varietas = " + listRekomendasiVarietas.get(0) + ", " + listRekomendasiVarietas.get(1) + ", " + listRekomendasiVarietas.get(2) + ", ");
                    setDataSpinner();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    binding.txtRekVarietas.setTextColor(getResources().getColor(R.color.red));
                    binding.txtRekVarietas.setText("Rekomendasi varietas = Tidak ditemukan");
                    setDataSpinner();
                }
            });
        }
    }

    public void setDataSpinner() {

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

        checkLocalData();
    }

    private void checkLocalData(){
        if ( ListRencanaTanam.getInstance().getDatumRencanaTanam() != null && !ListRencanaTanam.getInstance().getDatumRencanaTanam().getNamaRencanaTanam().equalsIgnoreCase("") ) {
            binding.namaRencanaTanam.setText(PreferenceUtils.getRTnamaRT(getApplicationContext()));
        }
    }

    private void checkNama() {
        if (!binding.namaRencanaTanam.getText().toString().equalsIgnoreCase("")) {

            if (listRencanaTanam.size() > 0) {
                for (int i = 0; i < listRencanaTanam.size(); i++) {
                    if (binding.namaRencanaTanam.getText().toString().equalsIgnoreCase(listRencanaTanam.get(i).getNamaRencanaTanam())) {
                        Toast.makeText(this, "Nama rencana tanam sudah dipakai", Toast.LENGTH_SHORT).show();
                        checkNama = 1;
                        break;
                    }
                }
            } else {
                checkNama = 0;
            }

            if (checkNama != 1) {
                checkNama = 0;
                if (idProfilLahan != null && idKomoditas != null && idSistemIrigasi != null && idVarietas != null) {
                    saveLocalData();
                } else {
                    Toast.makeText(this, "Lengkapi fields terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            } else {
                checkNama = 0;
            }

        } else {
            Toast.makeText(this, "Isi nama rencana tanam terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLocalData() {

        if(idSistemIrigasi.equalsIgnoreCase(tipeSIb)){
            isWithPompa = false;
        } else {
            isWithPompa = true;
        }

        DatumRencanaTanam datumRencanaTanam = new DatumRencanaTanam(binding.namaRencanaTanam.getText().toString(), idProfilLahan, idKomoditas, idVarietas, "",
                "", "", "", "", "", "", "", "", "",
                "","", "", "", "", "","", "", "", isWithPompa, luasLahan, potensiHasilVarietas);
        ListRencanaTanam.getInstance().setDetailRencanaTanam(getApplicationContext(), datumRencanaTanam);
        moveToB();
    }

    public void moveToB() {
        Intent a = new Intent(InputRencanaTanamA.this, InputRencanaTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToListRT() {
        Intent a = new Intent(InputRencanaTanamA.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}