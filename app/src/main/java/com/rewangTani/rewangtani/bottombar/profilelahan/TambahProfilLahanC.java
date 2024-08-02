package com.rewangTani.rewangtani.bottombar.profilelahan;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanCBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.DatumAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.ModelAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProfilLahanC extends FragmentActivity {

    BottombarPlTambahProfilLahanCBinding binding;
    private GoogleMap mMap;
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
    int checkNama, checkLatLong, checkPh;
    ArrayAdapter<String> adapterProvinsi, adapterKabKota, adapterKec, adapterKel, adapterKodepos, adapterSistemIrigasi;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat,longt;
    String lat2, longt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_c);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnLokasi.getLeft(), binding.btnLokasi.getTop());
            }
        });

       /* if(Build.VERSION.SDK_INT >= 26){
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
        }*/


       // getData();


        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToTambahProfilLahanD();
                Toast.makeText(TambahProfilLahanC.this, "SIMPAN DATA !", Toast.LENGTH_SHORT).show();
                // SIMPAN !!

//                if (checkPh==1){
//                    Toast.makeText(TambahProfilLahanC.this, "pH tanah tidak boleh lebih dari 14.0", Toast.LENGTH_SHORT).show();
//                } else if(checkPh==0){
//                    if(!et_koordinatlahan.getText().toString().equalsIgnoreCase("")&&(!et_phtanah.getText().toString().equalsIgnoreCase(""))&&
//                            (!et_kemiringantanah.getText().toString().equalsIgnoreCase(""))&&(!et_luasgarapan.getText().toString().equalsIgnoreCase(""))){
//                        checkNama();
//                        //tambahProfilTanah();
//                    } else {
//                        Toast.makeText(TambahProfilLahanC.this, "Isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
//                    }
//                }

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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahanC.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(TambahProfilLahanC.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


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
                //sendDataProfilTanah();
            }
        }).start();
    }

    /*private void sendDataProfilTanah(){
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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahanC.this, "Berhasil tambah profil lahan", Toast.LENGTH_LONG).show();
                                goToListProfilLahan();

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
    }*/

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