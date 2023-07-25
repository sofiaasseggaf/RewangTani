package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.DetailWarungSewaMesin;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.DatumAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.ModelAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProfilLahan extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText et_namaprofillahan, et_koordinatlahan, et_luasgarapan, et_kemiringantanah, et_ph_tanah;
    TextView et_phtanah, panjang_ph_tanah, txt_ph_tanah;
    AutoCompleteTextView sp_provinsi, sp_kab_kota, sp_kec, sp_kel, sp_kodepos;
    RelativeLayout rl_provinsi, rl_kab_kota, rl_kec, rl_kel, rl_kodepos, rl_ph_tanah, rl_ph_tanah_tv;
    Spinner sp_statuspekerja, sp_sistemirigasi;
    ImageButton btn_tambah;
    String idAlamat, idSistemIrigasi;
    int ph;
    ModelAlamat modelAlamat;
    ModelSistemIrigasi modelSistemIrigasi;
    ModelProfilLahan modelProfilLahan;
    List<String> listProfilLahan = new ArrayList<>();
    List<DatumAlamat> listAlamat = new ArrayList<>();
    List<String> listKabKota = new ArrayList<String>();
    List<String> listKec = new ArrayList<String>();
    List<String> listKel = new ArrayList<String>();
    List<String> listkodepos = new ArrayList<String>();
    List<String> listProvinsi = new ArrayList<String>();
    List<String> listSistemIrigasi = new ArrayList<String>();
    String provinsi, kabkota, kecamatan, kelurahan;
    ScrollView sv;
    TextView txtload;
    int checkNama, checkLatLong, checkPh;
    ArrayAdapter<String> adapterProvinsi, adapterKabKota, adapterKec, adapterKel, adapterKodepos, adapterSistemIrigasi;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat,longt;
    String lat2, longt2;
    SeekBar sb_phtanah;
    DecimalFormat form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pl_tambah_profil_lahan);

        et_namaprofillahan = findViewById(R.id.et_namaprofillahan);
        et_koordinatlahan = findViewById(R.id.et_koordinatlahan);
        et_luasgarapan = findViewById(R.id.et_luasgarapan);
        et_kemiringantanah = findViewById(R.id.et_kemiringantanah);
        et_phtanah = findViewById(R.id.et_phtanah);
        sp_sistemirigasi = findViewById(R.id.sp_sistemirigasi);
        rl_provinsi = findViewById(R.id.rl_provinsi);
        rl_kab_kota = findViewById(R.id.rl_kab_kota);
        rl_kec = findViewById(R.id.rl_kec);
        rl_kel = findViewById(R.id.rl_kel);
        rl_kodepos = findViewById(R.id.rl_kodepos);
        btn_tambah = findViewById(R.id.btn_tambah);
        sp_provinsi = findViewById(R.id.sp_provinsi);
        sp_kab_kota = findViewById(R.id.sp_kab_kota);
        sp_kec = findViewById(R.id.sp_kec);
        sp_kel = findViewById(R.id.sp_kel);
        sp_kodepos = findViewById(R.id.sp_kodepos);
        sv = findViewById(R.id.sv);
        sb_phtanah = findViewById(R.id.sb_phtanah);
        txtload = findViewById(R.id.textloading);
        et_ph_tanah = findViewById(R.id.et_ph_tanah);
        rl_ph_tanah = findViewById(R.id.rl_ph_tanah);
        panjang_ph_tanah = findViewById(R.id.panjang_ph_tanah);
        txt_ph_tanah = findViewById(R.id.txt_ph_tanah);
        rl_ph_tanah_tv = findViewById(R.id.rl_ph_tanah_tv);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(TambahProfilLahan.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TambahProfilLahan.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TambahProfilLahan.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }



        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        lat = location.getLatitude();
        longt = location.getLongitude();
        lat2 = String.valueOf(lat).substring(0,8);
        longt2 = String.valueOf(longt).substring(0,7);


        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }


        if(Build.VERSION.SDK_INT >= 26){
            sb_phtanah.setClickable(true);
            sb_phtanah.setVisibility(View.VISIBLE);
            rl_ph_tanah.setClickable(true);
            rl_ph_tanah.setVisibility(View.VISIBLE);

            et_ph_tanah.setVisibility(View.GONE);
            et_ph_tanah.setClickable(false);
            rl_ph_tanah_tv.setVisibility(View.GONE);
            rl_ph_tanah_tv.setClickable(false);
            panjang_ph_tanah.setVisibility(View.GONE);
            panjang_ph_tanah.setClickable(false);

            sb_phtanah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    double value = ((double) progress / 10.0);
                    et_phtanah.setText(String.valueOf(value));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    ph = Integer.valueOf(et_phtanah.getText().toString().replaceAll("[^0-9]", ""));
                }
            });
        } else {
            sb_phtanah.setClickable(false);
            sb_phtanah.setVisibility(View.GONE);
            rl_ph_tanah.setClickable(false);
            rl_ph_tanah.setVisibility(View.GONE);

            et_ph_tanah.setVisibility(View.VISIBLE);
            et_ph_tanah.setClickable(true);
            rl_ph_tanah_tv.setVisibility(View.VISIBLE);
            rl_ph_tanah_tv.setClickable(false);
            panjang_ph_tanah.setVisibility(View.GONE);
            panjang_ph_tanah.setClickable(false);

            et_ph_tanah.addTextChangedListener(new TextWatcher() {
                int a;
                double value;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equalsIgnoreCase("")){
                        a = Integer.valueOf(s.toString());
                    } else {
                        txt_ph_tanah.setText("");
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    value = ((double) a / 10.0);
                    txt_ph_tanah.setText(String.valueOf(value));
                    if (value>14.0){
                        panjang_ph_tanah.setVisibility(View.VISIBLE);
                        checkPh = 1;
                    } else {
                        panjang_ph_tanah.setVisibility(View.GONE);
                        checkPh = 0;
                    }
                    ph = Integer.valueOf(txt_ph_tanah.getText().toString().replaceAll("[^0-9]", ""));
                }
            });
        }


        getData();

        sp_provinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kab_kota.setEnabled(false);
                sp_kab_kota.setText("");
                rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kec.setEnabled(false);
                sp_kec.setText("");
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");

                sp_provinsi.showDropDown();
            }
        });

        sp_provinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (adapterProvinsi!=null){
                        adapterProvinsi.getFilter().filter(charSequence);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kab_kota.setEnabled(false);
                sp_kab_kota.setText("");
                rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kec.setEnabled(false);
                sp_kec.setText("");
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                provinsi = sp_provinsi.getText().toString();
                listKabKota.clear();
                for (int a=0; a<listAlamat.size(); a++) {
                    if (listAlamat.get(a).getProvinsi().equalsIgnoreCase(provinsi)) {
                        listKabKota.add(listAlamat.get(a).getKota());
                    }
                }
                if (listKabKota.size()>0){
                    for (int i = 0; i < listKabKota.size(); i++) {
                        for (int j = i + 1; j < listKabKota.size(); j++) {
                            if (listKabKota.get(i).equalsIgnoreCase(listKabKota.get(j))) {
                                listKabKota.remove(j);
                                j--;
                            }
                        }
                    }

                    rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner);
                    sp_kab_kota.setEnabled(true);
                    adapterKabKota = new ArrayAdapter<String>(
                            TambahProfilLahan.this, R.layout.z_spinner_list, listKabKota);
                    sp_kab_kota.setThreshold(1);
                    sp_kab_kota.setAdapter(adapterKabKota);
                }
            }
        });

        sp_kab_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kec.setEnabled(false);
                sp_kec.setText("");
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");

                sp_kab_kota.showDropDown();
            }
        });

        sp_kab_kota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    adapterKabKota.getFilter().filter(charSequence);
                } catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kec.setEnabled(false);
                sp_kec.setText("");
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kabkota = sp_kab_kota.getText().toString();
                listKec.clear();
                for (int a=0; a<listAlamat.size(); a++){
                    if (listAlamat.get(a).getKota().equalsIgnoreCase(kabkota)){
                        listKec.add(listAlamat.get(a).getKecamatan());
                    }
                }

                if (listKec.size()>0){
                    for (int i = 0; i < listKec.size(); i++) {
                        for (int j = i + 1; j < listKec.size(); j++) {
                            if (listKec.get(i).equalsIgnoreCase(listKec.get(j))) {
                                listKec.remove(j);
                                j--;
                            }
                        }
                    }

                    rl_kec.setBackgroundResource(R.drawable.bg_spinner);
                    sp_kec.setEnabled(true);
                    adapterKec = new ArrayAdapter<String>(
                            TambahProfilLahan.this, R.layout.z_spinner_list, listKec);
                    sp_kec.setThreshold(1);
                    sp_kec.setAdapter(adapterKec);

                }
            }
        });

        sp_kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");

                sp_kec.showDropDown();
            }
        });

        sp_kec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (adapterKec!=null){
                        adapterKec.getFilter().filter(charSequence);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kel.setEnabled(false);
                sp_kel.setText("");
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kecamatan = sp_kec.getText().toString();
                listKel.clear();
                for (int a=0; a<listAlamat.size(); a++){
                    if (listAlamat.get(a).getKecamatan().equalsIgnoreCase(kecamatan)){
                        listKel.add(listAlamat.get(a).getKelurahan());
                    }
                }

                if (listKel.size()>0){
                    for (int i = 0; i < listKel.size(); i++) {
                        for (int j = i + 1; j < listKel.size(); j++) {
                            if (listKel.get(i).equalsIgnoreCase(listKel.get(j))) {
                                listKel.remove(j);
                                j--;
                            }
                        }
                    }

                    rl_kel.setBackgroundResource(R.drawable.bg_spinner);
                    sp_kel.setEnabled(true);
                    adapterKel = new ArrayAdapter<String>(
                            TambahProfilLahan.this, R.layout.z_spinner_list, listKel);
                    sp_kel.setThreshold(1);
                    sp_kel.setAdapter(adapterKel);

                }
            }
        });

        sp_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");

                sp_kel.showDropDown();
            }
        });

        sp_kel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (adapterKel!=null){
                        adapterKel.getFilter().filter(charSequence);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                sp_kodepos.setEnabled(false);
                sp_kodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kelurahan = sp_kel.getText().toString();
                for (int a=0; a<listAlamat.size(); a++){
                    if (listAlamat.get(a).getKelurahan().equalsIgnoreCase(kelurahan)){
                        idAlamat = listAlamat.get(a).getIdAlamat();
                        break;
                    }
                }

                listkodepos.clear();
                for (int a=0; a<listAlamat.size(); a++){
                    if (listAlamat.get(a).getKelurahan().equalsIgnoreCase(kelurahan)){
                        listkodepos.add(listAlamat.get(a).getKodepos().toString());
                    }
                }

                if (listkodepos.size()>0){
                    for (int i = 0; i < listkodepos.size(); i++) {
                        for (int j = i + 1; j < listkodepos.size(); j++) {
                            if (listkodepos.get(i).equalsIgnoreCase(listkodepos.get(j))) {
                                listkodepos.remove(j);
                                j--;
                            }
                        }
                    }

                    rl_kodepos.setBackgroundResource(R.drawable.bg_spinner);
                    sp_kodepos.setEnabled(true);
                    adapterKodepos = new ArrayAdapter<String>(
                            TambahProfilLahan.this, R.layout.z_spinner_list, listkodepos);
                    sp_kodepos.setThreshold(1);
                    sp_kodepos.setAdapter(adapterKodepos);
                }
            }
        });

        sp_kodepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_kodepos.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        sp_sistemirigasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String namaSI = sp_sistemirigasi.getSelectedItem().toString();
                for (int a=0; a<modelSistemIrigasi.getTotalData(); a++){
                    try{
                        if (modelSistemIrigasi.getData().get(a).getNamaSistemIrigasi().toString().equalsIgnoreCase(namaSI)){
                            idSistemIrigasi = modelSistemIrigasi.getData().get(a).getIdSistemIrigasi();
                        }
                    } catch (Exception e){}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPh==1){
                    Toast.makeText(TambahProfilLahan.this, "pH tanah tidak boleh lebih dari 14.0", Toast.LENGTH_SHORT).show();
                } else if(checkPh==0){
                    if(!et_koordinatlahan.getText().toString().equalsIgnoreCase("")&&(!et_phtanah.getText().toString().equalsIgnoreCase(""))&&
                            (!et_kemiringantanah.getText().toString().equalsIgnoreCase(""))&&(!et_luasgarapan.getText().toString().equalsIgnoreCase(""))){
                        checkNama();
                        //tambahProfilTanah();
                    } else {
                        Toast.makeText(TambahProfilLahan.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void getData(){
        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);
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
                    getDataAlamat();
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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    public void getDataAlamat() {
        // masih di new thread
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAlamat> data = apiInterface.getDataAlamat();
        data.enqueue(new Callback<ModelAlamat>() {
            @Override
            public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                modelAlamat = response.body();
                if (response.body()!=null){
                    listAlamat.clear();
                    for (int i = 0; i < modelAlamat.getTotalData(); i++) {
                        listAlamat.add(modelAlamat.getData().get(i));
                        listProvinsi.add(modelAlamat.getData().get(i).getProvinsi());
                    }
                    if (listAlamat!=null && listProvinsi!=null){
                        for (int i = 0; i < listProvinsi.size(); i++) {
                            for (int j = i + 1; j < listProvinsi.size(); j++) {
                                if (listProvinsi.get(i).equalsIgnoreCase(listProvinsi.get(j))) {
                                    listProvinsi.remove(j);
                                    j--;
                                }
                            }
                        }
                        getDataSistemIrigasi();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAlamat> call, Throwable t) {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(TambahProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void getDataSistemIrigasi(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSistemIrigasi> data = apiInterface.getDataSistemIrigasi();
        data.enqueue(new Callback<ModelSistemIrigasi>() {
            @Override
            public void onResponse(Call<ModelSistemIrigasi> call, Response<ModelSistemIrigasi> response) {
                modelSistemIrigasi = response.body();
                if (response.body()!=null){
                    listSistemIrigasi.clear();
                    for (int i = 0; i < modelSistemIrigasi.getTotalData(); i++) {
                        listSistemIrigasi.add(modelSistemIrigasi.getData().get(i).getNamaSistemIrigasi());
                    }
                    if (listSistemIrigasi!=null){
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
            @Override
            public void onFailure(Call<ModelSistemIrigasi> call, Throwable t) {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(TambahProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(){
        adapterProvinsi = new ArrayAdapter<String>(TambahProfilLahan.this, R.layout.z_spinner_list, listProvinsi);
        sp_provinsi.setThreshold(1);
        sp_provinsi.setAdapter(adapterProvinsi);

        adapterSistemIrigasi = new ArrayAdapter<>(TambahProfilLahan.this, R.layout.z_spinner_list, listSistemIrigasi);
        sp_sistemirigasi.setAdapter(adapterSistemIrigasi);

        et_koordinatlahan.setText(lat+", "+longt);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);


        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });

    }

    private void checkNama(){
        if (listProfilLahan.size() == 0){
            checkLatLong();
        } else if (listProfilLahan.size()>0) {
            for(int i=0; i<listProfilLahan.size(); i++){
                if (et_namaprofillahan.getText().toString().equalsIgnoreCase(listProfilLahan.get(i))){
                    Toast.makeText(this, "Nama profil lahan sudah dipakai", Toast.LENGTH_SHORT).show();
                    checkNama = 1;
                    break;
                }
            }

            if(checkNama!=1){
                checkNama=0;
                checkLatLong();
            }else{
                checkNama=0;
            }
        }
    }

    private void checkLatLong(){
        if (!lat2.equalsIgnoreCase("") && !longt2.equalsIgnoreCase("")){
            for (int i=0; i<modelProfilLahan.getTotalData(); i++){
                if (modelProfilLahan.getData().get(i).getLatitude().equalsIgnoreCase(lat2) &&
                        modelProfilLahan.getData().get(i).getLongitude().equalsIgnoreCase(longt2)) {
                    Toast.makeText(this, "Lokasi lahan sudah terpakai", Toast.LENGTH_SHORT).show();
                    checkLatLong = 1;
                    break;
                }
            }

            if(checkLatLong!=1){
                checkLatLong=0;
                tambahProfilTanah();
            }else{
                checkLatLong=0;
            }

        }

    }

    public void tambahProfilTanah(){
        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);
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
                sendDataProfilTanah();
            }
        }).start();
    }

    private void sendDataProfilTanah(){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idUser", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("idAlamat", idAlamat);
        jsonParams.put("latitude", lat2); // gak boleh kosong
        jsonParams.put("longitude", longt2); // gak boleh kosong
        jsonParams.put("luasGarapan", et_luasgarapan.getText().toString());
        jsonParams.put("idSistemIrigasi", idSistemIrigasi);
        jsonParams.put("kemiringanTanah", et_kemiringantanah.getText().toString());
        jsonParams.put("phTanah", ph);
        jsonParams.put("namaProfilTanah", et_namaprofillahan.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataProfilLahan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahan.this, "Berhasil tambah profil lahan", Toast.LENGTH_LONG).show();
                                goToListProfilLahan();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahan.this, "Gagal tambah profil lahan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void goToListProfilLahan(){
        Intent a = new Intent(TambahProfilLahan.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Tambah Profil Lahan ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListProfilLahan();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}