package com.rewangTani.rewangtani.bottombar.profilelahan;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanABinding;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanBBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.DatumAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.ModelAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProfilLahanB extends FragmentActivity {

    BottombarPlTambahProfilLahanBBinding binding;
    String idAlamat;
    ModelAlamat modelAlamat;
    List<DatumAlamat> listAlamat = new ArrayList<>();
    List<String> listKabKota = new ArrayList<String>();
    List<String> listKec = new ArrayList<String>();
    List<String> listKel = new ArrayList<String>();
    List<String> listkodepos = new ArrayList<String>();
    List<String> listProvinsi = new ArrayList<String>();
    String provinsi, kabkota, kecamatan, kelurahan;
    ArrayAdapter<String> adapterProvinsi, adapterKabKota, adapterKec, adapterKel, adapterKodepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pl_tambah_profil_lahan_a);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_b);

        //getData();

        binding.spProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rlKabKota.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKabKota.setEnabled(false);
                binding.spKabKota.setText("");
                binding.rlKecamatan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKecamatan.setEnabled(false);
                binding.spKecamatan.setText("");
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");

                binding.spProvinsi.showDropDown();
            }
        });

        binding.spProvinsi.addTextChangedListener(new TextWatcher() {
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
                binding.rlKabKota.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKabKota.setEnabled(false);
                binding.spKabKota.setText("");
                binding.rlKecamatan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKecamatan.setEnabled(false);
                binding.spKecamatan.setText("");
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                provinsi = binding.spProvinsi.getText().toString();
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

                    binding.rlKabKota.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
                    binding.spKabKota.setEnabled(true);
                    adapterKabKota = new ArrayAdapter<String>(
                            TambahProfilLahanB.this, R.layout.z_spinner_list, listKabKota);
                    binding.spKabKota.setThreshold(1);
                    binding.spKabKota.setAdapter(adapterKabKota);
                }
            }
        });

        binding.spKabKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rlKecamatan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKecamatan.setEnabled(false);
                binding.spKecamatan.setText("");
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");

                binding.spKabKota.showDropDown();
            }
        });

        binding.spKabKota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    adapterKabKota.getFilter().filter(charSequence);
                } catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.rlKecamatan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKecamatan.setEnabled(false);
                binding.spKecamatan.setText("");
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kabkota = binding.spKabKota.getText().toString();
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

                    binding.rlKecamatan.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
                    binding.spKecamatan.setEnabled(true);
                    adapterKec = new ArrayAdapter<String>(
                            TambahProfilLahanB.this, R.layout.z_spinner_list, listKec);
                    binding.spKecamatan.setThreshold(1);
                    binding.spKecamatan.setAdapter(adapterKec);

                }
            }
        });

        binding.spKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");

                binding.spKecamatan.showDropDown();
            }
        });

        binding.spKecamatan.addTextChangedListener(new TextWatcher() {
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
                binding.rlKelurahan.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKelurahan.setEnabled(false);
                binding.spKelurahan.setText("");
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kecamatan = binding.spKecamatan.getText().toString();
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

                    binding.rlKelurahan.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
                    binding.spKelurahan.setEnabled(true);
                    adapterKel = new ArrayAdapter<String>(
                            TambahProfilLahanB.this, R.layout.z_spinner_list, listKel);
                    binding.spKelurahan.setThreshold(1);
                    binding.spKelurahan.setAdapter(adapterKel);

                }
            }
        });

        binding.spKelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");

                binding.spKelurahan.showDropDown();
            }
        });

        binding.spKelurahan.addTextChangedListener(new TextWatcher() {
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
                binding.rlKodePos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spKodePos.setEnabled(false);
                binding.spKodePos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kelurahan = binding.spKelurahan.getText().toString();
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

                    binding.rlKodePos.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
                    binding.spKodePos.setEnabled(true);
                    adapterKodepos = new ArrayAdapter<String>(
                            TambahProfilLahanB.this, R.layout.z_spinner_list, listkodepos);
                    binding.spKodePos.setThreshold(1);
                    binding.spKodePos.setAdapter(adapterKodepos);
                }
            }
        });

        binding.spKodePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spKodePos.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTambahProfilLahanC();
//                if (!idAlamat.equalsIgnoreCase("")){
//                    tambahDataProfilLahan();
//                } else {
//                    Toast.makeText(TambahProfilLahanB.this, "Tambah Alamat Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    private void tambahDataProfilLahan() {
        PreferenceUtils.savePLidAlamat(idAlamat, getApplicationContext());
        goToTambahProfilLahanC();
    }

    public void getData(){
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
                getDataAlamat();
            }
        }).start();
    }

    public void getDataAlamat() {
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAlamat> call, Throwable t) {
                binding.viewLoading.setVisibility(View.GONE);
                Toast.makeText(TambahProfilLahanB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(){
        adapterProvinsi = new ArrayAdapter<String>(TambahProfilLahanB.this, R.layout.z_spinner_list, listProvinsi);
        binding.spProvinsi.setThreshold(1);
        binding.spProvinsi.setAdapter(adapterProvinsi);
    }

    public void goToTambahProfilLahanC() {
        Intent a = new Intent(TambahProfilLahanB.this, TambahProfilLahanC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    public void goToTambahProfilLahanA() {
        Intent a = new Intent(TambahProfilLahanB.this, TambahProfilLahanA.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        goToTambahProfilLahanA();
    }

}