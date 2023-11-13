package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListProfilLahan;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.databinding.BottombarPlListProfileLahanBinding;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProfileLahan extends AppCompatActivity {

    BottombarPlListProfileLahanBinding binding;
    AdapterListProfilLahan itemList;
    ModelProfilLahan modelProfilLahan;
    DataProfilById dataProfilById;
    List<DatumProfilLahan> listProfilLahan = new ArrayList<DatumProfilLahan>();
    int checkKelengkapan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_list_profile_lahan);

        //getData();

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTambahPL();
//                if (checkKelengkapan==1){
//                    goToTambahPL();
//                } else if (checkKelengkapan==0){
//                    binding.frameDataNotFound.setVisibility(View.GONE);
//                    View customLayout = getLayoutInflater().inflate(R.layout.dialog_lengkapi_profil, null);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ListProfileLahan.this);
//                    builder.setView(customLayout);
//                    RelativeLayout buttonOk = customLayout.findViewById(R.id.btn_lengkapi_data_profil);
//                    RelativeLayout buttonCancel = customLayout.findViewById(R.id.btn_kembali);
//                    buttonOk.setOnClickListener(v->{
//                        goToEditProfil();
//                            });
//                    buttonCancel.setOnClickListener(v->{
//                        goToBeranda();
//                            });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
            }
        });

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
                getDataProfil();
            }
        }).start();
    }

    public void getDataProfil() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (response.body()!=null){
                    if (dataProfilById.getData().getTelepon()!=null && dataProfilById.getData().getNik()!=null &&
                            dataProfilById.getData().getIdAlamat()!=null && dataProfilById.getData().getAlamat()!=null &&
                    dataProfilById.getData().getGender()!=null && dataProfilById.getData().getTglLahir()!=null){
                        checkKelengkapan = 1;
                        getProfilLahan();
                    } else {
                        checkKelengkapan = 0;
                        getProfilLahan();

                    }
                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListProfileLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getProfilLahan() {
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
                                listProfilLahan.add(modelProfilLahan.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (listProfilLahan!=null&&listProfilLahan.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                binding.scrollview.setVisibility(View.VISIBLE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                binding.frameDataNotFound.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            binding.frameDataNotFound.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListProfileLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        itemList = new AdapterListProfilLahan(listProfilLahan);
        binding.rvProfilLahan.setLayoutManager(new LinearLayoutManager(ListProfileLahan.this));
        binding.rvProfilLahan.setAdapter(itemList);
        binding.rvProfilLahan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvProfilLahan,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListProfileLahan.this, DetailProfilLahan.class);
                        a.putExtra("id", listProfilLahan.get(position).getIdProfileTanah());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToTambahPL(){
        Intent a = new Intent(ListProfileLahan.this, TambahProfilLahanA.class);
        startActivity(a);
        finish();
    }

    public void goToEditProfil(){
        Intent a = new Intent(ListProfileLahan.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListProfileLahan.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}