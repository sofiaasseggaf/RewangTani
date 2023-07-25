package com.rewangTani.rewangtani.bottombar.warungku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukTerlarisWarungku extends AppCompatActivity {

    ImageButton btn_etalase, btn_produk_terlaris, btn_penjualan;
    ImageButton btn_beranda, btn_warungku, btn_profil_lahan, btn_profil_akun;
    TextView txt_nama, txt_alamat, txt_telepon, txt_terjual, txtload;
    DataProfilById dataProfilById;
    ModelProduk modelProduk;
    List<DatumProduk> listDataProduk = new ArrayList<DatumProduk>();
    List<DatumProduk> listDataProdukSorted = new ArrayList<DatumProduk>();
    List<DatumProduk> listDataProdukSortednoDuplicate = new ArrayList<DatumProduk>();
    List<DatumSewaMesin> sewaMesinList = new ArrayList<>();
    List<DatumTenagaKerja> tenagaKerjaList = new ArrayList<>();
    List<DatumPupukPestisida> pupukPestisidaList = new ArrayList<>();
    List<Integer> urutanJumlah = new ArrayList<>();
    AdapterListWarungku itemList;
    RecyclerView rvWarungkuTerlaris, rvWarungkuTerlarisSorted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_warungku_produk_terlaris_warungku);

        btn_etalase = findViewById(R.id.btn_etalase);
        btn_produk_terlaris = findViewById(R.id.btn_produk_terlaris);
        btn_penjualan = findViewById(R.id.btn_penjualan);

        btn_beranda = findViewById(R.id.btn_beranda);
        btn_warungku = findViewById(R.id.btn_warungku);
        btn_profil_lahan = findViewById(R.id.btn_profil_lahan);
        btn_profil_akun = findViewById(R.id.btn_profil_akun);
        txtload = findViewById(R.id.textloading);
        txt_nama = findViewById(R.id.txt_nama);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_telepon = findViewById(R.id.txt_telepon);
        txt_terjual = findViewById(R.id.txt_terjual);
        rvWarungkuTerlaris = findViewById(R.id.rvWarungkuTerlaris);

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

        btn_etalase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEtalase();
            }
        });

        btn_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPenjualan();
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
                if (dataProfilById!=null){
                    getDataEtalase();
                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ProdukTerlarisWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        sortData();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(ProdukTerlarisWarungku.this, "Anda belum memiliki produk", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ProdukTerlarisWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
                        for (int j=0; j<modelSewaMesin.getTotalData(); j++){
                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelSewaMesin.getData().get(j).getIdProduk())){
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
                        Toast.makeText(ProdukTerlarisWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
                        for (int j=0; j<modelTenagaKerja.getTotalData(); j++){
                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelTenagaKerja.getData().get(j).getIdProduk())){
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
                        Toast.makeText(ProdukTerlarisWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
                        for (int j=0; j<modelPupukPestisida.getTotalData(); j++){
                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelPupukPestisida.getData().get(j).getIdProduk())){
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
                        Toast.makeText(ProdukTerlarisWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }


    public void sortData(){

        urutanJumlah.clear();
        listDataProdukSorted.clear();

        listDataProdukSorted = new ArrayList<>();

        if (listDataProduk.size()>0) {
            for (int a = 0; a < listDataProduk.size(); a++) {
                urutanJumlah.add(listDataProduk.get(a).getJmlTerjual());
            }

            Collections.sort(urutanJumlah);
            Collections.reverse(urutanJumlah);

            for (int z = 0; z < urutanJumlah.size(); z++) {
                for (int x = 0; x < listDataProduk.size(); x++) {
                    if (listDataProduk.get(x).getJmlTerjual() == urutanJumlah.get(z)) {
                        listDataProdukSorted.add(listDataProduk.get(x));
                    }
                }
            }
        }
        if (listDataProdukSorted.size()>0){
            delDuplicate();
        }
    }

    private void delDuplicate(){
        List<String> listId = new ArrayList<>();
        listId.clear();
        for (int i = 0; i<listDataProdukSorted.size(); i++){
            listId.add(listDataProdukSorted.get(i).getIdProduk());
        }
        Set<String> s = new LinkedHashSet<String>(listId);
        List<String> listIdNew = new ArrayList<>();
        listIdNew.addAll(s);
        for (int j=0; j<listDataProduk.size(); j++){
            for (int k=0; k<listIdNew.size(); k++){
                if (listDataProduk.get(j).getIdProduk()==listIdNew.get(k)){
                    listDataProdukSortednoDuplicate.add(listDataProduk.get(j));
                }
            }
        }
        if (listDataProdukSortednoDuplicate.size()>0){
            getDataSewaMesin();
        }
    }

    public void setAllData(){

        itemList = new AdapterListWarungku(listDataProdukSortednoDuplicate, sewaMesinList, tenagaKerjaList, pupukPestisidaList);
        rvWarungkuTerlaris.setLayoutManager(new GridLayoutManager(ProdukTerlarisWarungku.this, 2));
        rvWarungkuTerlaris.setAdapter(itemList);
        rvWarungkuTerlaris.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvWarungkuTerlaris,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ProdukTerlarisWarungku.this, EditWarungku.class);
                        a.putExtra("id", listDataProdukSortednoDuplicate.get(position).getIdProduk());
                        a.putExtra("tipe", listDataProdukSortednoDuplicate.get(position).getIdTipeProduk());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
        setDataProfil();
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

    public void goToDetailWarung(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, EditWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, BerandaWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToEtalase(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, BerandaWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPenjualan(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, PenjualanWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToEditWarungku(){
        Intent a = new Intent(ProdukTerlarisWarungku.this, EditWarungku.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToWarungku();
    }
}