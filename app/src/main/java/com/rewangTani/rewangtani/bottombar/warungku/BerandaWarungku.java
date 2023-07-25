package com.rewangTani.rewangtani.bottombar.warungku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListWarungku;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelproduk.DatumProduk;
import com.rewangTani.rewangtani.model.modelproduk.ModelProduk;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaWarungku extends AppCompatActivity {

    ImageButton btn_etalase, btn_produk_terlaris, btn_penjualan;
    ImageButton btn_beranda, btn_warungku, btn_profil_lahan, btn_profil_akun;
    ImageButton btn_tambah;
    TextView txt_nama, txt_alamat, txt_telepon, txt_terjual, txtload;
    DataProfilById dataProfilById;
    ModelProduk modelProduk;
    List<DatumProduk> listDataProduk = new ArrayList<DatumProduk>();
    List<DatumSewaMesin> sewaMesinList = new ArrayList<>();
    List<DatumTenagaKerja> tenagaKerjaList = new ArrayList<>();
    List<DatumPupukPestisida> pupukPestisidaList = new ArrayList<>();
    AdapterListWarungku itemList;
    RecyclerView rvWarungku;
    int checkKelengkapan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_warungku_berandawarungku);

        btn_etalase = findViewById(R.id.btn_etalase);
        btn_produk_terlaris = findViewById(R.id.btn_produk_terlaris);
        btn_penjualan = findViewById(R.id.btn_penjualan);
        btn_tambah = findViewById(R.id.btn_tambah);
        btn_beranda = findViewById(R.id.btn_beranda);
        btn_warungku = findViewById(R.id.btn_warungku);
        btn_profil_lahan = findViewById(R.id.btn_profil_lahan);
        btn_profil_akun = findViewById(R.id.btn_profil_akun);
        txtload = findViewById(R.id.textloading);
        txt_nama = findViewById(R.id.txt_nama);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_telepon = findViewById(R.id.txt_telepon);
        txt_terjual = findViewById(R.id.txt_terjual);
        rvWarungku = findViewById(R.id.rvWarungku);


        start();

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBeranda();
            }
        });

        btn_profil_lahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        btn_profil_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

        btn_produk_terlaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BerandaWarungku.this, "Fitur Belum Tersedia", Toast.LENGTH_SHORT).show();
                goToProdukTerlaris();
                /*Intent a = new Intent(BerandaWarungku.this, DeleteAll.class);
                startActivity(a);
                finish();*/
            }
        });

        btn_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BerandaWarungku.this, "Fitur Belum Tersedia", Toast.LENGTH_SHORT).show();
                goToPenjualan();
            }
        });

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkKelengkapan==1){
                    goToTambahWarungku();
                } else if (checkKelengkapan==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BerandaWarungku.this);
                    builder.setMessage("Lengkapi Data Profil Terlebih Dahulu")
                            .setPositiveButton("Lengkapi Data Profil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    goToProfil();
                                }
                            })
                            .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToWarungku();
                                }
                            })
                            .create()
                            .show();
                }
            }
        });

    }

    private void start(){
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
                getDataProfileWithId();
            }
        }).start();
    }

    public void getDataProfileWithId() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (dataProfilById.getData().getTelepon()!=null && dataProfilById.getData().getNik()!=null &&
                        dataProfilById.getData().getIdAlamat()!=null && dataProfilById.getData().getAlamat()!=null &&
                        dataProfilById.getData().getGender()!=null && dataProfilById.getData().getTglLahir()!=null){
                    checkKelengkapan = 1;
                    getDataEtalase();
                } else {
                    checkKelengkapan = 0;
                    getDataEtalase();

                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataEtalase(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.getDataProduk();
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                modelProduk = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelProduk.getTotalData(); i++) {
                            String idp = modelProduk.getData().get(i).getIdProfil();
                            if (idp.equalsIgnoreCase(PreferenceUtils.getIdProfil(getApplicationContext()))) {
                                listDataProduk.add(modelProduk.getData().get(i));
                            }
                        }
                    } catch (Exception e){ }
                    if (listDataProduk.size()>0){
                        getDataSewaMesin();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(BerandaWarungku.this, "Anda belum memiliki produk", Toast.LENGTH_SHORT).show();
                                setDataProfil();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                ModelSewaMesin modelSewaMesin = response.body();
                if (response.body()!=null){
                    sewaMesinList.clear();
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j=0; j<modelSewaMesin.getTotalData(); j++){
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelSewaMesin.getData().get(j).getIdProduk())){
                                sewaMesinList.add(modelSewaMesin.getData().get(j));
                            }
                        }
                    }
                    getDataTenagaKerja();
                    /*if (sewaMesinList.size()>0){
                        getDataTenagaKerja();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(BerandaWarungku.this, "Data Etalase Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.getDataWarungTenagaKerja();
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                ModelTenagaKerja modelTenagaKerja = response.body();
                if (response.body()!=null){
                    tenagaKerjaList.clear();
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j=0; j<modelTenagaKerja.getTotalData(); j++){
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelTenagaKerja.getData().get(j).getIdProduk())){
                                tenagaKerjaList.add(modelTenagaKerja.getData().get(j));
                            }
                        }
                    }
                    getDataPupukPestisida();
                    /*if (tenagaKerjaList.size()>0){
                        getDataPupukPestisida();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(BerandaWarungku.this, "Data Etalase Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataPupukPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                ModelPupukPestisida modelPupukPestisida = response.body();
                if (response.body()!=null){
                    pupukPestisidaList.clear();
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j=0; j<modelPupukPestisida.getTotalData(); j++){
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelPupukPestisida.getData().get(j).getIdProduk())){
                                pupukPestisidaList.add(modelPupukPestisida.getData().get(j));
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setAllData();
                        }
                    });
                    /*if (tenagaKerjaList.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setAllData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(BerandaWarungku.this, "Data Etalase Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/
                }
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataProfil(){
        if (dataProfilById.getData().getNamaBelakang()!=null){
            txt_nama.setText(dataProfilById.getData().getNamaDepan()+" "+dataProfilById.getData().getNamaBelakang());
        } else {
            txt_nama.setText(dataProfilById.getData().getNamaDepan());
        }
        if (dataProfilById.getData().getAlamat()!=null){
            txt_alamat.setText(dataProfilById.getData().getAlamat());
        } else {
            txt_alamat.setText("LENGKAPI DATA ALAMAT");
        }
        if (dataProfilById.getData().getTelepon()!=null){
            txt_telepon.setText(dataProfilById.getData().getTelepon());
        } else {
            txt_telepon.setText("LENGKAPI DATA NO TELEPON");
        }
        //txt_terjual.setText("Terjual : -");
    }

    public void setAllData(){

        itemList = new AdapterListWarungku(listDataProduk, sewaMesinList, tenagaKerjaList, pupukPestisidaList);
        rvWarungku.setLayoutManager(new GridLayoutManager(BerandaWarungku.this, 2));
        rvWarungku.setAdapter(itemList);
        rvWarungku.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvWarungku,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(BerandaWarungku.this, EditWarungku.class);
                        a.putExtra("id", listDataProduk.get(position).getIdProduk());
                        a.putExtra("tipe", listDataProduk.get(position).getIdTipeProduk());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
        setDataProfil();
    }

    public void goToBeranda(){
        Intent a = new Intent(BerandaWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(BerandaWarungku.this, BerandaWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(BerandaWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(BerandaWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToProdukTerlaris(){
        Intent a = new Intent(BerandaWarungku.this, ProdukTerlarisWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfil(){
        Intent a = new Intent(BerandaWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToPenjualan(){
        Intent a = new Intent(BerandaWarungku.this, PenjualanWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToTambahWarungku(){
        Intent a = new Intent(BerandaWarungku.this, TambahWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToEditWarungku(){
        Intent a = new Intent(BerandaWarungku.this, EditWarungku.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBeranda();
    }

}