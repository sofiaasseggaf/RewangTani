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
import android.util.ArrayMap;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.BottombarWarungkuTambahWarungkuBinding;
import com.rewangTani.rewangtani.model.modelphoto.DataPhotoById;
import com.rewangTani.rewangtani.model.modelproduk.DataProdukById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DataBppById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DataSewaMesinById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DataTenagaKerjaById;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahWarungku extends AppCompatActivity {

    BottombarWarungkuTambahWarungkuBinding binding;
    ArrayAdapter<String> adapterTP;
    String tipeWarung, idTipeProduk, encodedImage;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    Bitmap bitmap;
    DataProdukById dataProdukById;
    DataSewaMesinById dataSewaMesinById;
    DataTenagaKerjaById dataTenagaKerjaById;
    DataPhotoById dataPhotoById;
    DataBppById dataBppById;
    String[] listNamaTipeProduk = {Global.SEWA_MESIN, Global.TENAGA_KERJA, Global.BIBIT, Global.PUPUK, Global.PESTISIDA};
    ArrayList<String> encodedImageBibit = new ArrayList<>();
    ArrayList<String> encodedImagePupuk = new ArrayList<>();
    ArrayList<String> encodedImagePestisida = new ArrayList<>();
    ArrayList<String> encodedImageSewaMesin = new ArrayList<>();
    ArrayList<String> encodedImageTenagaKerja = new ArrayList<>();

    ImageView[] imageViewsBibit = new ImageView[3];
    ImageView[] imageViewsPupuk = new ImageView[3];
    ImageView[] imageViewsPestisida = new ImageView[3];
    ImageView[] imageViewsSewaMesin = new ImageView[3];
    ImageView[] imageViewsTenagaKerja = new ImageView[3];
    int countFotoBPP, countFotoSewaMesin, countFotoTenagaKerja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_warungku_tambah_warungku);

        encodedImage = Global.STRING_DEFAULT_VALUE;
        countFotoBPP = Global.INT_DEFAULT_VALUE;
        countFotoSewaMesin = Global.INT_DEFAULT_VALUE;
        countFotoTenagaKerja = Global.INT_DEFAULT_VALUE;
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        setSpinner();
        initializeImageViewSetListener();

        binding.hargaProdukBibit.addTextChangedListener(new NumberTextWatcher(binding.hargaProdukBibit));
        binding.hargaProdukPupuk.addTextChangedListener(new NumberTextWatcher(binding.hargaProdukPupuk));
        binding.hargaProdukPestisida.addTextChangedListener(new NumberTextWatcher(binding.hargaProdukPestisida));
        binding.hargaProdukSewaMesin.addTextChangedListener(new NumberTextWatcher(binding.hargaProdukSewaMesin));
        binding.hargaProdukLayananJasaTenagaKerja.addTextChangedListener(new NumberTextWatcher(binding.hargaProdukLayananJasaTenagaKerja));

        binding.spinnerTipeProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                tipeWarung = binding.spinnerTipeProduk.getSelectedItem().toString();
                updateViewTipeWarung(tipeWarung);

                /*
                for (int a = 0; a < modelTipeProduk.getTotalData(); a++) {
                    try {
                        if (modelTipeProduk.getData().get(a).getNamaTipeProduk().equalsIgnoreCase(tipeWarung)) {
                            idTipeProduk = modelTipeProduk.getData().get(a).getIdTipeProduk();
                        }
                    } catch (Exception e) {}
                }
                if (idTipeProduk.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")) {
                    // ini produk sewa mesin
                    binding.viewWarungPupukPestisida1.setVisibility(View.GONE);
                    binding.viewWarungSewaMesin2.setVisibility(View.VISIBLE);
                    binding.viewWarungTenagaKerja3.setVisibility(View.GONE);
                } else if (idTipeProduk.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")) {
                    // ini produk tenaga kerja
                    binding.viewWarungPupukPestisida1.setVisibility(View.GONE);
                    binding.viewWarungSewaMesin2.setVisibility(View.GONE);
                    binding.viewWarungTenagaKerja3.setVisibility(View.VISIBLE);
                } else if (idTipeProduk.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c") ||
                        idTipeProduk.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                        idTipeProduk.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")) {
                    // ini produk bibit, pupuk, pestisida
                    binding.viewWarungPupukPestisida1.setVisibility(View.VISIBLE);
                    binding.viewWarungSewaMesin2.setVisibility(View.GONE);
                    binding.viewWarungTenagaKerja3.setVisibility(View.GONE);
                } else {
                    binding.viewWarungPupukPestisida1.setVisibility(View.GONE);
                    binding.viewWarungSewaMesin2.setVisibility(View.GONE);
                    binding.viewWarungTenagaKerja3.setVisibility(View.GONE);
                    Toast.makeText(TambahWarungku.this, "Tipe Produk Bermasalah", Toast.LENGTH_SHORT).show();
                }
                */

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        binding.btnTambahFotoBibit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImageBibit.size() < 3 )
                {
                    choose();
                } else {
                    Toast.makeText(TambahWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnTambahFotoPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImagePupuk.size() < 3 )
                {
                    choose();
                } else {
                    Toast.makeText(TambahWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnTambahFotoPestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImagePestisida.size() < 3 )
                {
                    choose();
                } else {
                    Toast.makeText(TambahWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnTambahFotoSewaMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImageSewaMesin.size() < 3 )
                {
                    choose();
                } else {
                    Toast.makeText(TambahWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnTambahFotoTenagaKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( encodedImageTenagaKerja.size() < 3 )
                {
                    choose();
                } else {
                    Toast.makeText(TambahWarungku.this, "Maksimal 3 Foto, Klik Foto Untuk Hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) )
                {
                    validateBibit();
                }
                else if ( tipeWarung.equalsIgnoreCase(Global.PUPUK) )
                {
                    validatePupuk();
                }
                else if ( tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
                {
                    validatePestisida();
                }
                else if ( tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN) )
                {
                    validateSewaMesin();
                }
                else if ( tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA) )
                {
                    validateTenagaKerja();
                }
                else
                {
                    Toast.makeText(TambahWarungku.this, "Tipe Produk Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        binding.btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPesanan();
            }
        });

        binding.btnEtalase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEtalase();
            }
        });

    }

    private void setSpinner() {
        adapterTP = new ArrayAdapter<String>(TambahWarungku.this, R.layout.z_spinner_list, listNamaTipeProduk);
        adapterTP.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spinnerTipeProduk.setAdapter(adapterTP);
    }

    private void initializeImageViewSetListener()
    {
        imageViewsBibit = new ImageView[]{
                binding.imgProdukBibit1,
                binding.imgProdukBibit2,
                binding.imgProdukBibit3
        };
        imageViewsPupuk = new ImageView[]{
                binding.imgProdukPupuk1,
                binding.imgProdukPupuk2,
                binding.imgProdukPupuk3
        };
        imageViewsPestisida = new ImageView[]{
                binding.imgProdukPestisida1,
                binding.imgProdukPestisida2,
                binding.imgProdukPestisida3
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

        for (int i = 0; i < imageViewsBibit.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsBibit[i].setOnClickListener(view ->
            {
                if ( index < encodedImageBibit.size() )
                {
                    // Photo exists, confirm deletion
                    confirmDeletePhoto(index);
                }
            });
        }

        for (int i = 0; i < imageViewsPupuk.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsPupuk[i].setOnClickListener(view ->
            {
                if ( index < encodedImagePupuk.size() )
                {
                    // Photo exists, confirm deletion
                    confirmDeletePhoto(index);
                }
            });
        }

        for (int i = 0; i < imageViewsPestisida.length; i++)
        {
            final int index = i; // Capture the index for the listener
            imageViewsPestisida[i].setOnClickListener(view ->
            {
                if ( index < encodedImagePestisida.size() )
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

    private void updateViewTipeWarung(String tipeWarung)
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) )
        {
            binding.viewWarungBibit.setVisibility(View.VISIBLE);
            binding.viewWarungPupuk.setVisibility(View.GONE);
            binding.viewWarungPestisida.setVisibility(View.GONE);
            binding.viewWarungSewaMesin.setVisibility(View.GONE);
            binding.viewWarungTenagaKerja.setVisibility(View.GONE);
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PUPUK) )
        {
            binding.viewWarungBibit.setVisibility(View.GONE);
            binding.viewWarungPupuk.setVisibility(View.VISIBLE);
            binding.viewWarungPestisida.setVisibility(View.GONE);
            binding.viewWarungSewaMesin.setVisibility(View.GONE);
            binding.viewWarungTenagaKerja.setVisibility(View.GONE);
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            binding.viewWarungBibit.setVisibility(View.GONE);
            binding.viewWarungPupuk.setVisibility(View.GONE);
            binding.viewWarungPestisida.setVisibility(View.VISIBLE);
            binding.viewWarungSewaMesin.setVisibility(View.GONE);
            binding.viewWarungTenagaKerja.setVisibility(View.GONE);
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.SEWA_MESIN) )
        {
            binding.viewWarungBibit.setVisibility(View.GONE);
            binding.viewWarungPupuk.setVisibility(View.GONE);
            binding.viewWarungPestisida.setVisibility(View.GONE);
            binding.viewWarungSewaMesin.setVisibility(View.VISIBLE);
            binding.viewWarungTenagaKerja.setVisibility(View.GONE);
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.TENAGA_KERJA) )
        {
            binding.viewWarungBibit.setVisibility(View.GONE);
            binding.viewWarungPupuk.setVisibility(View.GONE);
            binding.viewWarungPestisida.setVisibility(View.GONE);
            binding.viewWarungSewaMesin.setVisibility(View.GONE);
            binding.viewWarungTenagaKerja.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.viewWarungBibit.setVisibility(View.GONE);
            binding.viewWarungPupuk.setVisibility(View.GONE);
            binding.viewWarungPestisida.setVisibility(View.GONE);
            binding.viewWarungSewaMesin.setVisibility(View.GONE);
            binding.viewWarungTenagaKerja.setVisibility(View.GONE);
            Toast.makeText(TambahWarungku.this, "Tipe Produk Bermasalah", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeletePhoto(int index) {
        new AlertDialog.Builder(TambahWarungku.this)
                .setTitle("Hapus Photo")
                .setMessage("Apakah anda yakin ingin menghapus foto ini ?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    deletePhoto(index);
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void choose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahWarungku.this);
        builder.setMessage("Kamera atau Galeri ?")
                .setCancelable(true)
                .setPositiveButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, Global.CAMERA_REQUEST);
                        } catch (Exception e) {
                            Toast.makeText(TambahWarungku.this, "Kamera bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("GALERI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            startActivityForResult(galleryPhoto.openGalleryIntent(), Global.GALLERY_REQUEST);
                        } catch (Exception e) {
                            Toast.makeText(TambahWarungku.this, "Galeri bermasalah", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.CAMERA_REQUEST) {
                bitmap = (Bitmap) data.getExtras().get("data");
                encodedImage = ImageBase64.encode(bitmap);
                if (!encodedImage.equalsIgnoreCase("")) {
                    addPhoto(encodedImage);
                } else {
                    Toast.makeText(TambahWarungku.this, "Kamera bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TambahWarungku.this, "Galeri bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showPic() {
        if (idTipeProduk.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")) {
            // cek ada berapa foto yg di lokal, set di tempat yg kosong dari 1 ke 2 ke 3
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(binding.imgProdukSewaMesin1);
        } else if (idTipeProduk.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")) {
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(binding.imgProdukTenagaKerja1);
        } else if (idTipeProduk.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c") ||
                idTipeProduk.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                idTipeProduk.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")) {
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(binding.imgProdukBibit1);
        }
    }

    private void addPhoto(String encodedImage)
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) )
        {
            if (encodedImageBibit.size() < 3) {
                encodedImageBibit.add(encodedImage);
                updateImageViews();
            } else {
                Toast.makeText(this, "Maximum 3 photos allowed", Toast.LENGTH_SHORT).show();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PUPUK) )
        {
            if (encodedImagePupuk.size() < 3) {
                encodedImagePupuk.add(encodedImage);
                updateImageViews();
            } else {
                Toast.makeText(this, "Maximum 3 photos allowed", Toast.LENGTH_SHORT).show();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            if (encodedImagePestisida.size() < 3) {
                encodedImagePestisida.add(encodedImage);
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

    private void deletePhoto(int index)
    {
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) )
        {
            if (index < encodedImageBibit.size()) {
                encodedImageBibit.remove(index);
                updateImageViews();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PUPUK) )
        {
            if (index < encodedImagePupuk.size()) {
                encodedImagePupuk.remove(index);
                updateImageViews();
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            if (index < encodedImagePestisida.size()) {
                encodedImagePestisida.remove(index);
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
        if ( tipeWarung.equalsIgnoreCase(Global.BIBIT) )
        {
            for (int i = 0; i < imageViewsBibit.length; i++) {
                if (i < encodedImageBibit.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImageBibit.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsBibit[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsBibit[i].setImageBitmap(null);
                }
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PUPUK) )
        {
            for (int i = 0; i < imageViewsPupuk.length; i++) {
                if (i < encodedImagePupuk.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImagePupuk.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsPupuk[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsPupuk[i].setImageBitmap(null);
                }
            }
        }
        else if ( tipeWarung.equalsIgnoreCase(Global.PESTISIDA) )
        {
            for (int i = 0; i < imageViewsPestisida.length; i++) {
                if (i < encodedImagePestisida.size()) {
                    // Decode Base64 string to Bitmap and set it in ImageView
                    String encodedImage = encodedImagePestisida.get(i);
                    Bitmap bitmap = decode(encodedImage); // Assuming ImageBase64.decode() exists
                    imageViewsPestisida[i].setImageBitmap(bitmap);
                } else {
                    // Clear the ImageView or set a placeholder
                    imageViewsPestisida[i].setImageBitmap(null);
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


    // BIBIT

    private void validateBibit() {
        if ( !binding.namaProdukBibit.getText().toString().equalsIgnoreCase("") && !binding.hargaProdukBibit.getText().toString().equalsIgnoreCase("") &&
                !binding.deskProdukBibit.getText().toString().equalsIgnoreCase("") && !binding.beratProdukBibit.getText().toString().equalsIgnoreCase("") &&
                !binding.kotaProdukBibit.getText().toString().equalsIgnoreCase("") && !binding.jumlahProdukBibit.getText().toString().equalsIgnoreCase("") )
        {
            if ( encodedImageBibit.size() == 3)
            {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count == 1) {
                            binding.textLoading.setText("Tunggu sebentar ya .");
                        } else if (count == 2) {
                            binding.textLoading.setText("Tunggu sebentar ya . .");
                        } else if (count == 3) {
                            binding.textLoading.setText("Tunggu sebentar ya . . .");
                        }
                        if (count == 3)
                            count = 0;
                        handler.postDelayed(this, 1500);
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataProdukBibit();
                    }
                }).start();
            }
            else
            {
                Toast.makeText(TambahWarungku.this, getString(R.string.photos_not_completed), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.field_not_comleted), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukBibit() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", Global.BIBIT);
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", binding.hargaProdukBibit.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", binding.kotaProdukBibit.getText().toString());
        jsonParams.put("jmlProduk", binding.jumlahProdukBibit.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById != null) {
                            sendDataBibit();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataProdukById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataBibit() {
        Double a = Double.parseDouble(binding.beratProdukBibit.getText().toString());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", Global.BIBIT);
        jsonParams.put("namaProduk", binding.namaProdukBibit.getText().toString());
        jsonParams.put("hargaProduk", binding.hargaProdukBibit.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", binding.deskProdukBibit.getText().toString());
        jsonParams.put("beratProduk", a);
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", binding.kotaProdukBibit.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById != null) {
                            sendPhotoProdukBibit(0);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk warung bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataBppById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukBibit(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk()+count);
        jsonParams.put("image", encodedImageBibit.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                sendPhotoBibit(0);
                            }
                            else
                            {
                                sendPhotoProdukBibit(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoBibit(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp()+count);
        jsonParams.put("image", encodedImageBibit.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                updateDataProdukBibit();
                            }
                            else
                            {
                                sendPhotoBibit(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukBibit() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataBibit();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data produk bibit", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataBibit() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, getString(R.string.add_product_successed), Toast.LENGTH_LONG).show();
                                goToEtalase();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data warung bibit", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // PUPUK

    private void validatePupuk() {
        if ( !binding.namaProdukPupuk.getText().toString().equalsIgnoreCase("") && !binding.hargaProdukPupuk.getText().toString().equalsIgnoreCase("") &&
                !binding.deskProdukPupuk.getText().toString().equalsIgnoreCase("") && !binding.beratProdukPupuk.getText().toString().equalsIgnoreCase("") )
        {
            if ( encodedImagePupuk.size() == 3)
            {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count == 1) {
                            binding.textLoading.setText("Tunggu sebentar ya .");
                        } else if (count == 2) {
                            binding.textLoading.setText("Tunggu sebentar ya . .");
                        } else if (count == 3) {
                            binding.textLoading.setText("Tunggu sebentar ya . . .");
                        }
                        if (count == 3)
                            count = 0;
                        handler.postDelayed(this, 1500);
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataProdukPupuk();
                    }
                }).start();
            }
            else
            {
                Toast.makeText(TambahWarungku.this, getString(R.string.photos_not_completed), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.field_not_comleted), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukPupuk() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", Global.PUPUK);
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", binding.hargaProdukPupuk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", binding.kotaProdukPupuk.getText().toString());
        jsonParams.put("jmlProduk", binding.jumlahProdukPupuk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById != null) {
                            sendDataPupuk();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataProdukById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataPupuk() {
        Double a = Double.parseDouble(binding.beratProdukPupuk.getText().toString());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", Global.PUPUK);
        jsonParams.put("namaProduk", binding.namaProdukPupuk.getText().toString());
        jsonParams.put("hargaProduk", binding.hargaProdukPupuk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", binding.deskProdukPupuk.getText().toString());
        jsonParams.put("beratProduk", a);
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", binding.kotaProdukPupuk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById != null) {
                            sendPhotoProdukPupuk(0);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk warung pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataBppById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukPupuk(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk()+count);
        jsonParams.put("image", encodedImagePupuk.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                sendPhotoPupuk(0);
                            }
                            else
                            {
                                sendPhotoProdukPupuk(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoPupuk(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp()+count);
        jsonParams.put("image", encodedImagePupuk.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                updateDataProdukPupuk();
                            }
                            else
                            {
                                sendPhotoPupuk(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukPupuk() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataPupuk();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data produk pupuk", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataPupuk() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, getString(R.string.add_product_successed), Toast.LENGTH_LONG).show();
                                goToEtalase();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data warung pupuk", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // PESTISIDA

    private void validatePestisida() {
        if ( !binding.namaProdukPestisida.getText().toString().equalsIgnoreCase("") && !binding.hargaProdukPestisida.getText().toString().equalsIgnoreCase("") &&
                !binding.deskProdukPestisida.getText().toString().equalsIgnoreCase("") && !binding.beratProdukPestisida.getText().toString().equalsIgnoreCase("") &&
                !binding.kotaProdukPestisida.getText().toString().equalsIgnoreCase("") && !binding.jumlahProdukPestisida.getText().toString().equalsIgnoreCase("") )
        {
            if ( encodedImagePestisida.size() == 3)
            {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count == 1) {
                            binding.textLoading.setText("Tunggu sebentar ya .");
                        } else if (count == 2) {
                            binding.textLoading.setText("Tunggu sebentar ya . .");
                        } else if (count == 3) {
                            binding.textLoading.setText("Tunggu sebentar ya . . .");
                        }
                        if (count == 3)
                            count = 0;
                        handler.postDelayed(this, 1500);
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataProdukPestisida();
                    }
                }).start();
            }
            else
            {
                Toast.makeText(this, getString(R.string.photos_not_completed), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.field_not_comleted), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukPestisida() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", Global.PESTISIDA);
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", binding.hargaProdukPestisida.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", binding.kotaProdukPestisida.getText().toString());
        jsonParams.put("jmlProduk", binding.jumlahProdukPestisida.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById != null) {
                            sendDataPestisida();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataProdukById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataPestisida() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", Global.PESTISIDA);
        jsonParams.put("namaProduk", binding.namaProdukPestisida.getText().toString());
        jsonParams.put("hargaProduk", binding.hargaProdukPestisida.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", binding.deskProdukPestisida.getText().toString());
        jsonParams.put("beratProduk", binding.beratProdukPestisida.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", binding.kotaProdukPestisida.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById != null) {
                            sendPhotoProdukPestisida(0);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk warung pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataBppById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukPestisida(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk()+count);
        jsonParams.put("image", encodedImagePestisida.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                sendPhotoPestisida(0);
                            }
                            else
                            {
                                sendPhotoProdukPestisida(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoPestisida(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp()+count);
        jsonParams.put("image", encodedImagePestisida.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                updateDataProdukPestisida();
                            }
                            else
                            {
                                sendPhotoPestisida(count+1);
                            }

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukPestisida() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataPestisida();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data produk pestisida", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataPestisida() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, getString(R.string.add_product_successed), Toast.LENGTH_LONG).show();
                                goToEtalase();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data warung pupuk", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // SEWA MESIN

    private void validateSewaMesin() {
        if ( !binding.namaProdukSewaMesin.getText().toString().equalsIgnoreCase("") && !binding.hargaProdukSewaMesin.getText().toString().equalsIgnoreCase("") &&
                !binding.deskProdukSewaMesin.getText().toString().equalsIgnoreCase("") && !binding.spesifikasiProdukSewaMesin.getText().toString().equalsIgnoreCase("") &&
                !binding.kotaProdukSewaMesin.getText().toString().equalsIgnoreCase("") && !binding.jumlahProdukSewaMesin.getText().toString().equalsIgnoreCase("") )
        {
            if ( encodedImageSewaMesin.size() == 3)
            {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count == 1) {
                            binding.textLoading.setText("Tunggu sebentar ya .");
                        } else if (count == 2) {
                            binding.textLoading.setText("Tunggu sebentar ya . .");
                        } else if (count == 3) {
                            binding.textLoading.setText("Tunggu sebentar ya . . .");
                        }
                        if (count == 3)
                            count = 0;
                        handler.postDelayed(this, 1500);
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataProdukSewaMesin();
                    }
                }).start();
            }
            else
            {
                Toast.makeText(TambahWarungku.this, getString(R.string.photos_not_completed), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(TambahWarungku.this, getString(R.string.field_not_comleted), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", Global.SEWA_MESIN);
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", binding.hargaProdukSewaMesin.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", binding.kotaProdukSewaMesin.getText().toString());
        jsonParams.put("jmlProduk", binding.jumlahProdukSewaMesin.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById != null) {
                            sendDataSewaMesin();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk sewa mesin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataProdukById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", Global.SEWA_MESIN);
        jsonParams.put("namaProduk", binding.namaProdukSewaMesin.getText().toString());
        jsonParams.put("hargaProduk", binding.hargaProdukSewaMesin.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", binding.deskProdukSewaMesin.getText().toString());
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", binding.kotaProdukSewaMesin.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataSewaMesinById> response = apiInterface.sendSewaMesin(body);
        response.enqueue(new Callback<DataSewaMesinById>() {
            @Override
            public void onResponse(Call<DataSewaMesinById> call, Response<DataSewaMesinById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataSewaMesinById = rawResponse.body();
                        if (dataSewaMesinById != null) {
                            sendPhotoProdukSewaMesin(0);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk warung sewa mesin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSewaMesinById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukSewaMesin(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk()+count);
        jsonParams.put("image", encodedImageSewaMesin.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                sendPhotoSewaMesin(0);
                            }
                            else
                            {
                                sendPhotoProdukSewaMesin(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk sewa mesin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoSewaMesin(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataSewaMesinById.getData().getIdSewaMesin()+count);
        jsonParams.put("image", encodedImageSewaMesin.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                updateDataProdukSewaMesin();
                            }
                            else
                            {
                                sendPhotoSewaMesin(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung sewa mesin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataSewaMesin();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data produk sewa mesin", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataSewaMesin() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSewaMesin", dataSewaMesinById.getData().getIdSewaMesin());
        jsonParams.put("idFoto", dataSewaMesinById.getData().getIdSewaMesin());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataSewaMesin(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, getString(R.string.add_product_successed), Toast.LENGTH_LONG).show();
                                goToEtalase();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data warung sewa mesin", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // TENAGA KERJA

    private void validateTenagaKerja() {
        if ( !binding.namaProdukPenyediaJasaTenagaKerja.getText().toString().equalsIgnoreCase("") && !binding.hargaProdukLayananJasaTenagaKerja.getText().toString().equalsIgnoreCase("") &&
                !binding.deskProdukLayananJasaTenagaKerja.getText().toString().equalsIgnoreCase("") && !binding.keahlianPenyediaJasaTenagaKerja.getText().toString().equalsIgnoreCase("") &&
                !binding.kotaProdukTenagaKerja.getText().toString().equalsIgnoreCase("") )
        {
            if ( encodedImageTenagaKerja.size() == 3)
            {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        if (count == 1) {
                            binding.textLoading.setText("Tunggu sebentar ya .");
                        } else if (count == 2) {
                            binding.textLoading.setText("Tunggu sebentar ya . .");
                        } else if (count == 3) {
                            binding.textLoading.setText("Tunggu sebentar ya . . .");
                        }
                        if (count == 3)
                            count = 0;
                        handler.postDelayed(this, 1500);
                    }
                };
                handler.postDelayed(runnable, 1 * 1000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataProdukTenagaKerja();
                    }
                }).start();
            }
            else
            {
                Toast.makeText(TambahWarungku.this, getString(R.string.photos_not_completed), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.field_not_comleted), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", Global.TENAGA_KERJA);
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", binding.hargaProdukLayananJasaTenagaKerja.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", binding.kotaProdukTenagaKerja.getText().toString());
        jsonParams.put("jmlProduk", "0");
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById != null) {
                            sendDataTenagaKerja();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataProdukById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", Global.TENAGA_KERJA);
        jsonParams.put("namaTenagaKerja", binding.namaProdukPenyediaJasaTenagaKerja.getText().toString());
        jsonParams.put("namaTipeKerja", "");
        jsonParams.put("biaya", binding.hargaProdukLayananJasaTenagaKerja.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskripsi", binding.deskProdukLayananJasaTenagaKerja.getText().toString());
        jsonParams.put("keahlian", binding.keahlianPenyediaJasaTenagaKerja.getText().toString());
        jsonParams.put("pengalamanKerja", "");
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", binding.kotaProdukTenagaKerja.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataTenagaKerjaById> response = apiInterface.sendTenagaKerja(body);
        response.enqueue(new Callback<DataTenagaKerjaById>() {
            @Override
            public void onResponse(Call<DataTenagaKerjaById> call, Response<DataTenagaKerjaById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataTenagaKerjaById = rawResponse.body();
                        if (dataTenagaKerjaById != null) {
                            sendPhotoProdukTenagaKerja(0);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal tambah produk warung tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataTenagaKerjaById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukTenagaKerja(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk()+count);
        jsonParams.put("image", encodedImageTenagaKerja.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                sendPhotoTenagaKerja(0);
                            }
                            else
                            {
                                sendPhotoProdukTenagaKerja(count+1);
                            }

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoTenagaKerja(int count) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataTenagaKerjaById.getData().getIdTenagaKerja()+count);
        jsonParams.put("image", encodedImageTenagaKerja.get(count));
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            if ( count == 2 )
                            {
                                updateDataProdukTenagaKerja();
                            }
                            else
                            {
                             sendPhotoTenagaKerja(count+1);
                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataTenagaKerja();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data produk tenaga kerja", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataTenagaKerja() {

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTenagaKerja", dataTenagaKerjaById.getData().getIdTenagaKerja());
        jsonParams.put("idFoto", dataTenagaKerjaById.getData().getIdTenagaKerja());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataTenagaKerja(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, getString(R.string.add_product_successed), Toast.LENGTH_LONG).show();
                                goToEtalase();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal update data warung tenaga kerja", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void goToBeranda() {
        Intent a = new Intent(TambahWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(TambahWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToPesan() {
        Intent a = new Intent(TambahWarungku.this, Inbox.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun() {
        Intent a = new Intent(TambahWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToPesanan() {
        Intent a = new Intent(TambahWarungku.this, PesananWarungku.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToEtalase() {
//        Intent a = new Intent(TambahWarungku.this, EtalaseWarungku.class);
        // TODO : ini ganti jadi ke etalase, kalo etalase dah diperbaiki
        Intent a = new Intent(TambahWarungku.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public static Bitmap decode(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal tambah produk ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToBeranda();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}