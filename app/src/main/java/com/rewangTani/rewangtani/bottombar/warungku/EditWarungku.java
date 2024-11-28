package com.rewangTani.rewangtani.bottombar.warungku;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListWarungku;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.SwipeablePhotosAdapter;
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
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.Utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWarungku extends AppCompatActivity {

    BottombarWarungkuEditWarungkuBinding binding;
    String id, tipeWarung, txt_tipe;
    DataProdukById dataProdukById;
    ModelSewaMesin modelSewaMesin;
    DatumSewaMesin datumSewaMesin;
    ModelTenagaKerja modelTenagaKerja;
    DatumTenagaKerja datumTenagaKerja;
    ModelPupukPestisida modelPupukPestisida;
    DatumPupukPestisida datumPupukPestisida;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    Bitmap bitmap;
    String encodedImage;
    ArrayList<String> encodedImageSewaMesin = new ArrayList<>();
    ArrayList<String> encodedImageBpp = new ArrayList<>();
    ArrayList<String> encodedImageTenagaKerja = new ArrayList<>();
    ImageView[] imageViewsSewaMesin = new ImageView[3];
    ImageView[] imageViewsBpp = new ImageView[3];
    ImageView[] imageViewsTenagaKerja = new ImageView[3];
    int countFotoBPP, countFotoSewaMesin, countFotoTenagaKerja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_warungku_edit_warungku);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tipeWarung = intent.getStringExtra("tipe");

        encodedImage = Global.STRING_DEFAULT_VALUE;
        countFotoBPP = Global.INT_DEFAULT_VALUE;
        countFotoSewaMesin = Global.INT_DEFAULT_VALUE;
        countFotoTenagaKerja = Global.INT_DEFAULT_VALUE;
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        start();
        initializeImageViewSetListener();

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
        if(tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN)){
            // ini produk sewa mesin
            txt_tipe = "sewamesin";
            getDataProdukSewaMesin();
        } else if(tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA)){
            // ini produk tenaga kerja
            txt_tipe = "tenagakerja";
            getDataProdukTenagaKerja();
        } else if(tipeWarung.equalsIgnoreCase(Global.PUPUK) ||
                tipeWarung.equalsIgnoreCase(Global.PESTISIDA) ||
                tipeWarung.equalsIgnoreCase(Global.BIBIT)){
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
                                binding.viewWarungPupukPestisida.setVisibility(View.GONE);
                                binding.viewWarungSewaMesin.setVisibility(View.VISIBLE);
                                binding.viewWarungTenagaKerja.setVisibility(View.GONE);
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
        binding.namaProdukSewaMesin.setText(datumSewaMesin.getNamaProduk());
        binding.hargaProdukSewaMesin.setText("Rp. " + Utils.checkDesimal(datumSewaMesin.getHargaProduk().toString()));
        binding.deskProdukSewaMesin.setText(datumSewaMesin.getDeskProduk());
        binding.spesifikasiProdukSewaMesin.setText(datumSewaMesin.getDeskProduk());
        binding.kotaProdukSewaMesin.setText(datumSewaMesin.getKota().toString());
        if ( datumSewaMesin.getIdFoto() != null )
        {
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=" + datumSewaMesin.getIdFoto();
            SwipeablePhotosAdapter swipeablePhotosAdapter = new SwipeablePhotosAdapter(this, imageUri);
            binding.viewPagerSewaMesin.setAdapter(swipeablePhotosAdapter);
        }
        binding.btnTambahFotoSewaMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImageSewaMesin.size() < 3 )
                {
                    chooseCameraOrGallery();
                } else {
                    Toast.makeText(EditWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                                binding.viewWarungPupukPestisida.setVisibility(View.GONE);
                                binding.viewWarungSewaMesin.setVisibility(View.GONE);
                                binding.viewWarungTenagaKerja.setVisibility(View.VISIBLE);
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
        binding.namaProdukPenyediaJasa.setText(datumTenagaKerja.getNamaTenagaKerja());
        binding.hargaProdukLayananJasa.setText("Rp. " + Utils.checkDesimal(datumTenagaKerja.getBiaya().toString()));
        binding.deskProdukLayananJasa.setText(datumTenagaKerja.getDeskripsi());
        binding.keahlianPenyediaJasa.setText(datumTenagaKerja.getKeahlian());
        binding.kotaPenyediaJasa.setText(datumTenagaKerja.getKota().toString());
        if ( datumTenagaKerja.getIdFoto() != null )
        {
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=" + datumTenagaKerja.getIdFoto();
            SwipeablePhotosAdapter swipeablePhotosAdapter = new SwipeablePhotosAdapter(this, imageUri);
            binding.viewPagerTenagaKerja.setAdapter(swipeablePhotosAdapter);
        }
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
                                binding.viewWarungPupukPestisida.setVisibility(View.VISIBLE);
                                binding.viewWarungSewaMesin.setVisibility(View.GONE);
                                binding.viewWarungTenagaKerja.setVisibility(View.GONE);
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
        binding.namaProdukBpp.setText(datumPupukPestisida.getNamaProduk());
        binding.hargaProdukBpp.setText("Rp. " + Utils.checkDesimal(datumPupukPestisida.getHargaProduk().toString()));
        binding.deskProdukBpp.setText(datumPupukPestisida.getDeskProduk());
        binding.beratProdukBpp.setText(datumPupukPestisida.getBeratProduk().toString());
        binding.kotaProdukBpp.setText(datumPupukPestisida.getKota().toString());
        if ( datumPupukPestisida.getIdFoto() != null )
        {
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=" + datumPupukPestisida.getIdFoto();
            SwipeablePhotosAdapter swipeablePhotosAdapter = new SwipeablePhotosAdapter(this, imageUri);
            binding.viewPagerBpp.setAdapter(swipeablePhotosAdapter);
        }
    }

    // PHOTOS

    private void initializeImageViewSetListener()
    {
        imageViewsBpp = new ImageView[]{
                binding.imgProdukBpp1,
                binding.imgProdukBpp2,
                binding.imgProdukBpp3
        };
        imageViewsSewaMesin = new ImageView[]{
                binding.imgProdukSewaMesin1,
                binding.imgProdukSewaMesin2,
                binding.imgProdukSewaMesin3
        };
        imageViewsTenagaKerja = new ImageView[]{
                binding.imgProdukTenagaKerja1,
                binding.imgProdukTenagaKerja2,
                binding.imgProdukTenagaKerja3
        };

        for (int i = 0; i < imageViewsBpp.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsBpp[i].setOnClickListener(view ->
            {
                if ( index < encodedImageBpp.size() )
                {
                    // Photo exists, confirm deletion
                    confirmDeletePhoto(index);
                }
            });
        }

        for (int i = 0; i < imageViewsSewaMesin.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsSewaMesin[i].setOnClickListener(view ->
            {
                if ( index < encodedImageSewaMesin.size() )
                {
                    // Photo exists, confirm deletion
                    confirmDeletePhoto(index);
                }
            });
        }

        for (int i = 0; i < imageViewsTenagaKerja.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsTenagaKerja[i].setOnClickListener(view ->
            {
                if ( index < encodedImageTenagaKerja.size() )
                {
                    // Photo exists, confirm deletion
                    confirmDeletePhoto(index);
                }
            });
        }
    }

    public void chooseCameraOrGallery()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditWarungku.this);
        builder.setMessage("Kamera atau Galeri ?")
                .setCancelable(true)
                .setPositiveButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, Global.CAMERA_REQUEST);
                        } catch (Exception e) {
                            Toast.makeText(EditWarungku.this, "Kamera bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("GALERI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            startActivityForResult(galleryPhoto.openGalleryIntent(), Global.GALLERY_REQUEST);
                        } catch (Exception e) {
                            Toast.makeText(EditWarungku.this, "Galeri bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("Range")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.CAMERA_REQUEST) {
                bitmap = (Bitmap) data.getExtras().get("data");
                encodedImage = ImageBase64.encode(bitmap);
                if (!encodedImage.equalsIgnoreCase("")) {
                    addPhoto(encodedImage);
                } else {
                    Toast.makeText(EditWarungku.this, "Kamera bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == Global.GALLERY_REQUEST) {
                Uri uri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    encodedImage = ImageBase64.encode(bitmap);
                    if (!encodedImage.equalsIgnoreCase("")) {
                        addPhoto(encodedImage);
                    } else {
                        Toast.makeText(EditWarungku.this, "Galeri bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addPhoto(String encodedImage)
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) || tipeWarung.equalsIgnoreCase(Global.PUPUK) || tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            if (encodedImageBpp.size() < 3) {
                encodedImageBpp.add(encodedImage);
                updateImageViews();
            } else {
                Toast.makeText(this, "Maximum 3 photos allowed", Toast.LENGTH_SHORT).show();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN) )
        {
            if (encodedImageSewaMesin.size() < 3) {
                encodedImageSewaMesin.add(encodedImage);
                updateImageViews();
            } else {
                Toast.makeText(this, "Maximum 3 photos allowed", Toast.LENGTH_SHORT).show();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA) )
        {
            if (encodedImageTenagaKerja.size() < 3) {
                encodedImageTenagaKerja.add(encodedImage);
                updateImageViews();
            } else {
                Toast.makeText(this, "Maximum 3 photos allowed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void confirmDeletePhoto( int index )
    {
        new AlertDialog.Builder(EditWarungku.this)
                .setTitle("Hapus Photo")
                .setMessage("Apakah anda yakin ingin menghapus foto ini ?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    deletePhoto(index);
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void deletePhoto( int index )
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) || tipeWarung.equalsIgnoreCase(Global.PUPUK) || tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            if (index < encodedImageBpp.size()) {
                encodedImageBpp.remove(index);
                updateImageViews();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN) )
        {
            if (index < encodedImageSewaMesin.size()) {
                encodedImageSewaMesin.remove(index);
                updateImageViews();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA) )
        {
            if (index < encodedImageTenagaKerja.size()) {
                encodedImageTenagaKerja.remove(index);
                updateImageViews();
            }
        }
    }

    private void updateImageViews()
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) || tipeWarung.equalsIgnoreCase(Global.PUPUK) || tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            for (int i = 0; i < imageViewsBpp.length; i++) {
                if (i < encodedImageBpp.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImageBpp.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsBpp[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsBpp[i].setImageBitmap(null);
                }
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN) )
        {
            for (int i = 0; i < imageViewsSewaMesin.length; i++) {
                if (i < encodedImageSewaMesin.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImageSewaMesin.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsSewaMesin[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsSewaMesin[i].setImageBitmap(null);
                }
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA) )
        {
            for (int i = 0; i < imageViewsTenagaKerja.length; i++) {
                if (i < encodedImageTenagaKerja.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImageTenagaKerja.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsTenagaKerja[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsTenagaKerja[i].setImageBitmap(null);
                }
            }
        }
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
                if ( response.body() !=null )
                {
                    hapusFotoProdukSewaMesin(0);
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
    
    private void hapusFotoProdukSewaMesin(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                if (response.body()!=null){
                    if ( count == 2 )
                    {
                        hapusFotoSewaMesin(0);
                    }
                    else
                    {
                        hapusFotoProdukSewaMesin(count + 1);
                    }
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
    
    private void hapusFotoSewaMesin(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumSewaMesin.getIdSewaMesin() + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                if ( response.body() != null )
                {
                    if ( count == 2 )
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditWarungku.this, "Berhasil hapus produk sewa mesin", Toast.LENGTH_SHORT).show();
                                goToEtalase();
                            }
                        });
                    }
                    else
                    {
                        hapusFotoSewaMesin(count + 1);
                    }
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
                if ( response.body() != null ){
                    hapusFotoProdukTenagaKerja(0);
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

    private void hapusFotoProdukTenagaKerja(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                if ( response.body() != null )
                {
                    if ( count == 2 )
                    {
                        hapusFotoTenagaKerja(0);
                    }
                    else
                    {
                        hapusFotoProdukTenagaKerja(count + 1);
                    }
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

    private void hapusFotoTenagaKerja(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumTenagaKerja.getIdTenagaKerja() + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                if ( response.body() != null )
                {
                    if ( count == 2 )
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditWarungku.this, "Berhasil hapus produk tenaga kerja", Toast.LENGTH_SHORT).show();
                                goToEtalase();
                            }
                        });
                    }
                    else
                    {
                        hapusFotoTenagaKerja(count + 1);
                    }
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
                    hapusFotoProdukBpp(0);
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

    private void hapusFotoProdukBpp(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(id + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                if ( response.body() != null )
                {
                    if ( count == 2 )
                    {
                        hapusFotoBpp(0);
                    }
                    else
                    {
                        hapusFotoProdukBpp(count + 1);
                    }
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

    private void hapusFotoBpp(int count){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(datumPupukPestisida.getIdWarungBpp() + count);
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if ( response.body() != null )
                {
                    if ( count == 2 )
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditWarungku.this, "Berhasil hapus produk", Toast.LENGTH_SHORT).show();
                                goToEtalase();
                            }
                        });
                    }
                    else
                    {
                        hapusFotoBpp(count + 1);
                    }
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

    public static Bitmap decode(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void onBackPressed() {
        Utils.showCustomAlertDialog(
                EditWarungku.this,
                "Batal edit produk ?",
                okButton -> { goToEtalase();}
        );
    }

}