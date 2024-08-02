package com.rewangTani.rewangtani.utility;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelproduk.ModelProduk;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.panen.ModelPanen;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.ModelSudahTanam;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAll extends AppCompatActivity {

    Button del_akun, del_profil, del_rt, del_st, del_p, del_ort, del_pt;
    Button del_produk, del_tenaga_kerja, del_sewa_mesin, del_bpp;
    TextView txtload;
    ModelRencanaTanam modelRencanaTanam;
    ModelSudahTanam modelSudahTanam;
    ModelAkun modelAkun;
    ModelProfilAkun modelProfilAkun;
    ModelProfilLahan modelProfilLahan;
    ModelPanen modelPanen;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    ModelProduk modelProduk;
    ModelTenagaKerja modelTenagaKerja;
    ModelSewaMesin modelSewaMesin;
    ModelPupukPestisida modelPupukPestisida;
    List<String> idAkun = new ArrayList<>();
    List<String> idProfil = new ArrayList<>();
    List<String> idRT = new ArrayList<>();
    List<String> idST = new ArrayList<>();
    List<String> idP = new ArrayList<>();
    List<String> idORT = new ArrayList<>();
    List<String> idPT = new ArrayList<>();
    List<String> idProduk = new ArrayList<>();
    List<String> idTK = new ArrayList<>();
    List<String> idSM = new ArrayList<>();
    List<String> idBPP = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaaa_delete_all);

        del_akun = findViewById(R.id.del_akun);
        del_profil = findViewById(R.id.del_profil);
        del_rt = findViewById(R.id.del_rt);
        del_st = findViewById(R.id.del_st);
        del_p = findViewById(R.id.del_p);
        del_ort = findViewById(R.id.del_ort);
        del_pt = findViewById(R.id.del_pt);
        del_produk = findViewById(R.id.del_produk);
        del_tenaga_kerja = findViewById(R.id.del_tenaga_kerja);
        del_sewa_mesin = findViewById(R.id.del_sewa_mesin);
        del_bpp = findViewById(R.id.del_bpp);
        txtload = findViewById(R.id.textloading);

        getData();

        del_akun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Akun ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelAkun.getTotalData(); x++){
                                    idAkun.add(modelAkun.getData().get(x).getIdAkun());
                                }
                                for(int a=0; a<idAkun.size(); a++){
                                    delAllAKun(idAkun.get(a));
                                }
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
        });

        del_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Profil ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelProfilAkun.getTotalData(); x++){
                                    idProfil.add(modelProfilAkun.getData().get(x).getIdProfile());
                                }
                                for(int a=0; a<idProfil.size(); a++){
                                    delAllProfil(idProfil.get(a));
                                }
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
        });

        del_rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Rencana Tanam ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelRencanaTanam.getTotalData(); x++){
                                    idRT.add(modelRencanaTanam.getData().get(x).getIdRencanaTanam());
                                }
                                for(int a=0; a<idRT.size(); a++){
                                    delAllRT(idRT.get(a));
                                }
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
        });

        del_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Sudah Tanam ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelSudahTanam.getTotalData(); x++){
                                    idST.add(modelSudahTanam.getData().get(x).getIdSudahTanam());
                                }
                                for(int a=0; a<idST.size(); a++){
                                    delAllST(idST.get(a));
                                }
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
        });

        del_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Panen ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelPanen.getTotalData(); x++){
                                    idP.add(modelPanen.getData().get(x).getIdPanen());
                                }
                                for(int a=0; a<idP.size(); a++){
                                    delAllP(idP.get(a));
                                }
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
        });

        del_ort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Output Rencana Tanam ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelOutputRencanaTanam.getTotalData(); x++){
                                    idORT.add(modelOutputRencanaTanam.getData().get(x).getIdOutputRencanaTanam());
                                }
                                for(int a=0; a<idORT.size(); a++){
                                    delAllORT(idORT.get(a));
                                }
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
        });

        del_pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Profil Tanah ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelProfilLahan.getTotalData(); x++){
                                    idPT.add(modelProfilLahan.getData().get(x).getIdProfileTanah());
                                }
                                for(int a=0; a<idPT.size(); a++){
                                    delAllPT(idPT.get(a));
                                }
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
        });

        del_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Produk ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelProduk.getTotalData(); x++){
                                    idProduk.add(modelProduk.getData().get(x).getIdProduk());
                                }
                                for(int a=0; a<idProduk.size(); a++){
                                    delAllProduk(idProduk.get(a));
                                }
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
        });

        del_tenaga_kerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Tenaga Kerja ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelTenagaKerja.getTotalData(); x++){
                                    idTK.add(modelTenagaKerja.getData().get(x).getIdTenagaKerja());
                                }
                                for(int a=0; a<idTK.size(); a++){
                                    delAllTenagaKerja(idTK.get(a));
                                }
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
        });

        del_sewa_mesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Sewa Mesin?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelSewaMesin.getTotalData(); x++){
                                    idSM.add(modelSewaMesin.getData().get(x).getIdSewaMesin());
                                }
                                for(int a=0; a<idSM.size(); a++){
                                    delAllSewaMesin(idSM.get(a));
                                }
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
        });

        del_bpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAll.this);
                builder.setMessage("Yakin Hapus Semua Bibit Pupuk Pestisida?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int x=0; x<modelPupukPestisida.getTotalData(); x++){
                                    idBPP.add(modelPupukPestisida.getData().get(x).getIdWarungBpp());
                                }
                                for(int a=0; a<idBPP.size(); a++){
                                    delAllBpp(idBPP.get(a));
                                }
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
        });

    }


    public void getData(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu Sebentar Ya ."); }
                else if (count == 2) {
                    txtload.setText("Tunggu Sebentar Ya . ."); }
                else if (count == 3) {
                    txtload.setText("Tunggu Sebentar Ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getAkun();
            }
        }).start();
    }

    public void getAkun(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> dataRT = apiInterface.getDataAkun();
        dataRT.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body()!=null){
                    getProfilAkun();
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getProfilAkun(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataRT = apiInterface.getDataProfilAkun();
        dataRT.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body()!=null){
                    getRencanaTanam();
                }
            }
            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    getSudahTanam();
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getSudahTanam(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSudahTanam> dataRT = apiInterface.getDataSudahTanam();
        dataRT.enqueue(new Callback<ModelSudahTanam>() {
            @Override
            public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                modelSudahTanam = response.body();
                if (response.body()!=null){
                    getPanen();
                }
            }
            @Override
            public void onFailure(Call<ModelSudahTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getPanen(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPanen> dataRT = apiInterface.getDataPanen();
        dataRT.enqueue(new Callback<ModelPanen>() {
            @Override
            public void onResponse(Call<ModelPanen> call, Response<ModelPanen> response) {
                modelPanen = response.body();
                if (response.body()!=null){
                    getOutputRT();
                }
            }
            @Override
            public void onFailure(Call<ModelPanen> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getOutputRT(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.getDataOutputRT();
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    getProfilLahan();
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getProfilLahan(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataRT = apiInterface.getDataProfilLahan();
        dataRT.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    getDataProduk();
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProduk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.getDataProduk();
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                modelProduk = response.body();
                if (response.body()!=null){
                    getDataTenagaKerja();
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                modelTenagaKerja = response.body();
                if (response.body()!=null){
                    getDataSewaMesin();
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                modelSewaMesin = response.body();
                if (response.body()!=null){
                    getDataBpp();
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataBpp(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                modelPupukPestisida = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                    }
                });
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }




    public void delAllAKun(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> dataRT = apiInterface.deleteAkun(id);
        dataRT.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Akun " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllProfil(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataRT = apiInterface.deleteProfilAkun(id);
        dataRT.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Profil " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllRT(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.deleteRencanaTanam(id);
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Rencana Tanam " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllST(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSudahTanam> dataRT = apiInterface.deleteSudahTanam(id);
        dataRT.enqueue(new Callback<ModelSudahTanam>() {
            @Override
            public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                modelSudahTanam = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Sudah Tanam " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelSudahTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllP(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPanen> dataRT = apiInterface.deletePanen(id);
        dataRT.enqueue(new Callback<ModelPanen>() {
            @Override
            public void onResponse(Call<ModelPanen> call, Response<ModelPanen> response) {
                modelPanen = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Panen " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelPanen> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllORT(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.deleteOutputRT(id);
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Output RT " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllPT(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataRT = apiInterface.deleteProfilLahan(id);
        dataRT.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Profil Lahan " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllProduk(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.deleteProduk(id);
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                modelProduk = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Produk " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllTenagaKerja(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.deleteTenagaKerja(id);
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                modelTenagaKerja = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Tenaga Kerja " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllSewaMesin(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.deleteSewaMesin(id);
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                modelSewaMesin = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus Sewa Mesin " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void delAllBpp(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.deleteBibitPestisida(id);
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                modelPupukPestisida = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(DeleteAll.this, "Berhasil Hapus BPP " +id, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DeleteAll.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }


}