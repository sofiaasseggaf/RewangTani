package com.rewangTani.rewangtani.bottombar.profilelahan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanCBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
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

public class TambahProfilLahanC extends FragmentActivity {

    BottombarPlTambahProfilLahanCBinding binding;
    int ph;
    ModelSistemIrigasi modelSistemIrigasi;
    List<String> listSistemIrigasi = new ArrayList<String>();
    int checkPh;
    String idSistemIrigasi = "";
    ArrayAdapter<String> adapterSistemIrigasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_c);

        getData();

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnLokasi.getLeft(), binding.btnLokasi.getTop());
            }
        });

        if(Build.VERSION.SDK_INT >= 26){
            binding.sbPhtanah.setClickable(true);
            binding.sbPhtanah.setVisibility(View.VISIBLE);
            binding.rlPhTanah.setClickable(true);
            binding.rlPhTanah.setVisibility(View.VISIBLE);

            binding.etPhtanah.setVisibility(View.GONE);
            binding.rlPhTanah.setClickable(false);
            binding.rlPhTanahTv.setVisibility(View.GONE);
            binding.rlPhTanahTv.setClickable(false);
            binding.panjangPhTanah.setVisibility(View.GONE);
            binding.panjangPhTanah.setClickable(false);

            binding.sbPhtanah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    double value = ((double) progress / 10.0);
                    binding.etPhtanah.setText(String.valueOf(value));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    ph = Integer.valueOf(binding.etPhtanah.getText().toString().replaceAll("[^0-9]", ""));
                }
            });
        } else {

            binding.sbPhtanah.setClickable(false);
            binding.sbPhtanah.setVisibility(View.GONE);
            binding.rlPhTanah.setClickable(false);
            binding.rlPhTanah.setVisibility(View.GONE);

            binding.etPhtanah.setVisibility(View.VISIBLE);
            binding.etPhtanah.setClickable(true);
            binding.rlPhTanahTv.setVisibility(View.VISIBLE);
            binding.rlPhTanahTv.setClickable(false);
            binding.panjangPhTanah.setVisibility(View.GONE);
            binding.panjangPhTanah.setClickable(false);

            binding.etPhtanah.addTextChangedListener(new TextWatcher() {
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
                        binding.textPhTanah.setText("");
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {
                    value = ((double) a / 10.0);
                    binding.textPhTanah.setText(String.valueOf(value));
                    if (value>14.0){
                        binding.panjangPhTanah.setVisibility(View.VISIBLE);
                        checkPh = 1;
                    } else {
                        binding.panjangPhTanah.setVisibility(View.GONE);
                        checkPh = 0;
                    }
                    ph = Integer.valueOf(binding.textPhTanah.getText().toString().replaceAll("[^0-9]", ""));
                }
            });
        }

        binding.spSistemIrigasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spSistemIrigasi.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        binding.spSistemIrigasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (adapterSistemIrigasi!=null){
                        adapterSistemIrigasi.getFilter().filter(charSequence);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String sistemIrigasi = binding.spSistemIrigasi.getText().toString();
                for (int a=0; a<listSistemIrigasi.size(); a++){
                    if (modelSistemIrigasi.getData().get(a).getNamaSistemIrigasi().equalsIgnoreCase(sistemIrigasi)){
                        idSistemIrigasi = modelSistemIrigasi.getData().get(a).getIdSistemIrigasi();
                        break;
                    }
                }
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToTambahProfilLahanD();

                if (checkPh==1){
                    Toast.makeText(TambahProfilLahanC.this, "pH tanah tidak boleh lebih dari 14.0", Toast.LENGTH_SHORT).show();
                } else if(checkPh==0){
                    if(!idSistemIrigasi.equalsIgnoreCase("")&&(!binding.etPhtanah.getText().toString().equalsIgnoreCase(""))&&
                            (!binding.kemiringanTanah.getText().toString().equalsIgnoreCase(""))&&(!binding.luasGarapan.getText().toString().equalsIgnoreCase(""))){
                        tambahProfilTanah();
                    } else {
                        Toast.makeText(TambahProfilLahanC.this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
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
                getDataSistemIrigasi();
            }
        }).start();
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSistemIrigasi> call, Throwable t) {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(TambahProfilLahanC.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(){
        adapterSistemIrigasi = new ArrayAdapter<>(TambahProfilLahanC.this, R.layout.z_spinner_list, listSistemIrigasi);
        binding.spSistemIrigasi.setAdapter(adapterSistemIrigasi);
    }

    public void tambahProfilTanah(){
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
                sendDataProfilTanah();
            }
        }).start();
    }

    private void sendDataProfilTanah(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idUser", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("idAlamat", PreferenceUtils.getPLidAlamat(getApplicationContext()));
        jsonParams.put("latitude", PreferenceUtils.getPLlatitude(getApplicationContext())); // gak boleh kosong
        jsonParams.put("longitude", PreferenceUtils.getPLlongitude(getApplicationContext())); // gak boleh kosong
        jsonParams.put("luasGarapan", binding.luasGarapan.getText().toString());
        jsonParams.put("idSistemIrigasi", idSistemIrigasi);
        jsonParams.put("kemiringanTanah", binding.kemiringanTanah.getText().toString());
        jsonParams.put("phTanah", ph);
        jsonParams.put("namaProfilTanah", PreferenceUtils.getPLnamaProfilLahan(getApplicationContext()));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataProfilLahan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahanC.this, "Berhasil tambah profil lahan", Toast.LENGTH_LONG).show();
                                clearLocalData();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahanC.this, "Gagal tambah profil lahan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahProfilLahanC.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void clearLocalData(){
        PreferenceUtils.savePLnamaProfilLahan("", getApplicationContext());
        PreferenceUtils.savePLlatitude("", getApplicationContext());
        PreferenceUtils.savePLlongitude("", getApplicationContext());
        PreferenceUtils.savePLidAlamat("", getApplicationContext());
        PreferenceUtils.savePLProvinsi("", getApplicationContext());
        PreferenceUtils.savePLKabupaten("", getApplicationContext());
        PreferenceUtils.savePLKecamatan("", getApplicationContext());
        PreferenceUtils.savePLKelurahan("", getApplicationContext());
        PreferenceUtils.savePLKodepos("", getApplicationContext());
        goToListProfilLahan();
    }

    public void goToListProfilLahan(){
        Intent a = new Intent(TambahProfilLahanC.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToTambahProfilLahanB() {
        Intent a = new Intent(TambahProfilLahanC.this, TambahProfilLahanB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public void goToTambahProfilLahanD() {
        Intent a = new Intent(TambahProfilLahanC.this, TambahProfilLahanD.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }



    @Override
    public void onBackPressed() {
        goToTambahProfilLahanB();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}