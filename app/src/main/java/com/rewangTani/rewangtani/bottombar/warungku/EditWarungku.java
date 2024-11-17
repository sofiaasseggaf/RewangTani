package com.rewangTani.rewangtani.bottombar.warungku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.InboxPesan;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.BottombarWarungkuEditWarungkuBinding;
import com.rewangTani.rewangtani.model.modelphoto.DatumPhoto;
import com.rewangTani.rewangtani.model.modelproduk.DataProdukById;
import com.rewangTani.rewangtani.model.modelproduk.ModelProduk;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWarungku extends AppCompatActivity {

    BottombarWarungkuEditWarungkuBinding binding;
    String id, tipe, txt_tipe;
    DataProdukById dataProdukById;
    ModelSewaMesin modelSewaMesin;
    DatumSewaMesin datumSewaMesin;
    ModelTenagaKerja modelTenagaKerja;
    DatumTenagaKerja datumTenagaKerja;
    ModelPupukPestisida modelPupukPestisida;
    DatumPupukPestisida datumPupukPestisida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_warungku_edit_warungku);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tipe = intent.getStringExtra("tipe");

        start();

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBeranda();
            }
        });

        binding.btnLahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        binding.btnPesan.setOnClickListener(v->{
            goToPesan();
        });

        binding.btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });



        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditWarungku.this, "Fitur edit belum tersedia", Toast.LENGTH_SHORT).show();
                /*if(txt_tipe.equalsIgnoreCase("sewamesin")){
                    simpanProdukSewaMesin();
                }else if(txt_tipe.equalsIgnoreCase("tenagakerja")){
                    simpanProdukTenagaKerja();
                }else if(txt_tipe.equalsIgnoreCase("bpp")){
                    simpanProdukBPP();
                }*/
            }
        });

        binding.btnHapusProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditWarungku.this);
                builder.setMessage("Hapus Produk ?")
                        .setCancelable(false)
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(txt_tipe.equalsIgnoreCase("sewamesin")){
                                    hapusProdukSewaMesin();
                                }else if(txt_tipe.equalsIgnoreCase("tenagakerja")){
                                    hapusProdukTenagaKerja();
                                }else if(txt_tipe.equalsIgnoreCase("bpp")){
                                    hapusProdukBPP();
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

    public void start(){
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
                getDataProdukWithId();
            }
        }).start();
    }

    public void getDataProdukWithId(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProdukById> dataRT = apiInterface.getProdukById(id);
        dataRT.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> response) {
                dataProdukById = response.body();
                if (response.body()!=null){
                    setLayoutPage();
                }
            }
            @Override
            public void onFailure(Call<DataProdukById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setLayoutPage(){
        if(tipe.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")){
            // ini produk sewa mesin
            txt_tipe = "sewamesin";
            getDataProdukSewaMesin();
        } else if(tipe.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")){
            // ini produk tenaga kerja
            txt_tipe = "tenagakerja";
            getDataProdukTenagaKerja();
        } else if(tipe.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c") ||
                tipe.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                tipe.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")){
            // ini produk bibit, pupuk, pestisida
            txt_tipe = "bpp";
            getdataProdukBibitPupukPestisida();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                    Toast.makeText(EditWarungku.this, "Data produk tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getDataProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                modelSewaMesin = response.body();
                if (response.body()!=null){

                    try{
                        for (int i = 0; i < modelSewaMesin.getTotalData(); i++) {
                            if(modelSewaMesin.getData().get(i).getIdProduk().equalsIgnoreCase(id)){
                                datumSewaMesin = modelSewaMesin.getData().get(i);
                            }
                        }
                    } catch (Exception e){}

                    if (datumSewaMesin!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                binding.viewWarungPupukPestisida1.setVisibility(View.GONE);
                                binding.viewWarungSewaMesin2.setVisibility(View.VISIBLE);
                                binding.viewWarungTenagaKerja3.setVisibility(View.GONE);
                                setDataProdukSewaMesin();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataProdukSewaMesin(){
        binding.namaProduk2.setText(datumSewaMesin.getNamaProduk());
        binding.hargaProduk2.setText(datumSewaMesin.getHargaProduk().toString());
        binding.deskProduk2.setText(datumSewaMesin.getDeskProduk());
        binding.spesifikasiProduk2.setText(datumSewaMesin.getDeskProduk());
//        if (!datumSewaMesin.getIdFoto().equalsIgnoreCase("")){
//            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+datumSewaMesin.getIdFoto();
//            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .into(ll2_imgproduk);
//        }
    }

    public void getDataProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.getDataWarungTenagaKerja();
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                modelTenagaKerja = response.body();
                if (response.body()!=null){

                    try{
                        for (int i = 0; i < modelTenagaKerja.getTotalData(); i++) {
                            if(modelTenagaKerja.getData().get(i).getIdProduk().equalsIgnoreCase(id)){
                                datumTenagaKerja = modelTenagaKerja.getData().get(i);
                            }
                        }
                    } catch (Exception e){}

                    if (datumTenagaKerja!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                binding.viewWarungPupukPestisida1.setVisibility(View.GONE);
                                binding.viewWarungSewaMesin2.setVisibility(View.GONE);
                                binding.viewWarungTenagaKerja3.setVisibility(View.VISIBLE);
                                setDataProdukTenagaKerja();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataProdukTenagaKerja(){
        binding.namaProdukPenyediaJasa3.setText(datumTenagaKerja.getNamaTenagaKerja());
        binding.hargaProdukLayananJasa3.setText(datumTenagaKerja.getBiaya().toString());
        binding.deskProdukLayananJasa3.setText(datumTenagaKerja.getDeskripsi());
        binding.keahlianPenyediaJasa3.setText(datumTenagaKerja.getKeahlian());
//        if (!datumTenagaKerja.getIdFoto().equalsIgnoreCase("")){
//            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+datumTenagaKerja.getIdFoto();
//            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .into(ll3_imgproduk);
//        }
    }

    public void getdataProdukBibitPupukPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                modelPupukPestisida = response.body();
                if (response.body()!=null){

                    try{
                        for (int i = 0; i < modelPupukPestisida.getTotalData(); i++) {
                            if(modelPupukPestisida.getData().get(i).getIdProduk().equalsIgnoreCase(id)){
                                datumPupukPestisida = modelPupukPestisida.getData().get(i);
                            }
                        }
                    } catch (Exception e){}

                    if (datumPupukPestisida!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                binding.viewWarungPupukPestisida1.setVisibility(View.VISIBLE);
                                binding.viewWarungSewaMesin2.setVisibility(View.GONE);
                                binding.viewWarungTenagaKerja3.setVisibility(View.GONE);
                                setDataProdukBibitPupukPestisida();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataProdukBibitPupukPestisida(){
        binding.namaProduk1.setText(datumPupukPestisida.getNamaProduk());
        binding.hargaProduk1.setText(datumPupukPestisida.getHargaProduk().toString());
        binding.deskProduk1.setText(datumPupukPestisida.getDeskProduk());
        binding.beratProduk1.setText(datumPupukPestisida.getBeratProduk().toString());
//        if (!datumPupukPestisida.getIdFoto().equalsIgnoreCase("")){
//            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+datumPupukPestisida.getIdFoto();
//            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .into(ll1_imgproduk);
//        }
    }
    
    // SEWA MESIN

    public void hapusProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.deleteProduk(id);
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                if (response.body()!=null){
                    hapusSewaMesin();
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void hapusSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.deleteSewaMesin(datumSewaMesin.getIdSewaMesin());
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                if (response.body()!=null){
                    hapusFotoProdukSewaMesin();
                }
            }
            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    
    private void hapusFotoProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    hapusFotoSewaMesin();
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    
    private void hapusFotoSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumSewaMesin.getIdSewaMesin());
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(EditWarungku.this, "Berhasil hapus produk sewa mesin", Toast.LENGTH_SHORT).show();
                            goToEtalase();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    
    // TENAGA KERJA

    public void hapusProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.deleteProduk(id);
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                if (response.body()!=null){
                    hapusTenagaKerja();
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void hapusTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.deleteTenagaKerja(datumTenagaKerja.getIdTenagaKerja());
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                if (response.body()!=null){
                    hapusFotoProdukTenagaKerja();
                }
            }
            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void hapusFotoProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    hapusFotoTenagaKerja();
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void hapusFotoTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumTenagaKerja.getIdTenagaKerja());
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(EditWarungku.this, "Berhasil hapus produk tenaga kerja", Toast.LENGTH_SHORT).show();
                            goToEtalase();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    
    // BPP

    public void hapusProdukBPP(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.deleteProduk(id);
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                if (response.body()!=null){
                    hapusBpp();
                }
            }
            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void hapusBpp(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.deleteBibitPestisida(datumPupukPestisida.getIdWarungBpp());
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                if (response.body()!=null){
                    hapusFotoProdukBpp();
                }
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void hapusFotoProdukBpp(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    hapusFotoBpp();
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void hapusFotoBpp(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumPupukPestisida.getIdWarungBpp());
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(EditWarungku.this, "Berhasil hapus produk", Toast.LENGTH_SHORT).show();
                            goToEtalase();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    
    

    
    public void simpanProdukSewaMesin(){
        // simpan hasil edit produk
        // update ke table produk
        // update ke table sewa mesin
    }

    public void simpanProdukTenagaKerja(){
        // simpan hasil edit produk
        // update ke table produk
        // update ke table tenaga kerja
    }

    public void simpanProdukBPP(){
        // simpan hasil edit produk
        // update ke table produk
        // update ke table tenaga kerja
    }

    public void goToEtalase(){
        Intent a = new Intent(EditWarungku.this, EtalaseWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda() {
        Intent a = new Intent(EditWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(EditWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToPesan() {
        Intent a = new Intent(EditWarungku.this, InboxPesan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun() {
        Intent a = new Intent(EditWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal edit produk ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToEtalase();
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

}