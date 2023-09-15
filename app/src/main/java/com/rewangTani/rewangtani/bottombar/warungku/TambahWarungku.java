package com.rewangTani.rewangtani.bottombar.warungku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelnoneditable.tipeproduk.DatumTipeProduk;
import com.rewangTani.rewangtani.model.modelnoneditable.tipeproduk.ModelTipeProduk;
import com.rewangTani.rewangtani.model.modelphoto.DataPhotoById;
import com.rewangTani.rewangtani.model.modelphoto.DatumPhoto;
import com.rewangTani.rewangtani.model.modelproduk.DataProdukById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DataBppById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DataSewaMesinById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DataTenagaKerjaById;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahWarungku extends AppCompatActivity {

    Spinner sp_jeniswarung;
    LinearLayout ll1_warung_pupuk_pestisida, ll2_warung_sewa_mesin, ll3_warung_tenaga_kerja;
    EditText ll1_et_namaproduk, ll1_et_hargaproduk, ll1_et_deskripsiproduk, ll1_et_beratproduk, ll1_et_kotaproduk, ll1_et_jmlproduk;
    EditText ll2_et_namaproduk, ll2_et_hargasewa, ll2_et_deskripsiproduk, ll2_et_spesifikasiproduk, ll2_et_kotaproduk, ll2_et_jmlproduk;
    EditText ll3_et_namapenyediajasa, ll3_et_biayajasa, ll3_et_deskripsijasa, ll3_et_keahlianjasa, ll3_et_kotaproduk;
    ImageView ll1_imgproduk, ll2_imgproduk, ll3_imgproduk;
    ImageButton  btn_ll1_imgproduk, btn_ll2_imgproduk, btn_ll3_imgproduk, btn_simpan;
    ModelTipeProduk modelTipeProduk;
    List<DatumTipeProduk> listTipeProduk = new ArrayList<DatumTipeProduk>();
    List<String> listNamaTipeProduk = new ArrayList<String>();
    ArrayAdapter<String> adapterTP;
    String namaTipeProduk, idTipeProduk;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    TextView txtload;
    final int CAMERA_REQUEST = 12345;
    final int GALLERY_REQUEST = 54321;
    Bitmap bitmap;
    String encodedImage;
    DataProdukById dataProdukById;
    DataSewaMesinById dataSewaMesinById;
    DataTenagaKerjaById dataTenagaKerjaById;
    DataPhotoById dataPhotoById;
    DataBppById dataBppById;
    int photoExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_warungku_tambah_warungku);

        sp_jeniswarung = findViewById(R.id.sp_jeniswarung);
        txtload = findViewById(R.id.textloading);

        ll1_warung_pupuk_pestisida = findViewById(R.id.ll1_warung_pupuk_pestisida);
        ll2_warung_sewa_mesin = findViewById(R.id.ll2_warung_sewa_mesin);
        ll3_warung_tenaga_kerja = findViewById(R.id.ll3_warung_tenaga_kerja);

        ll1_et_namaproduk = findViewById(R.id.ll1_et_namaproduk);
        ll1_et_hargaproduk = findViewById(R.id.ll1_et_hargaproduk);
        ll1_et_deskripsiproduk = findViewById(R.id.ll1_et_deskripsiproduk);
        ll1_et_beratproduk = findViewById(R.id.ll1_et_beratproduk);
        ll1_imgproduk = findViewById(R.id.ll1_imgproduk);
        ll1_et_kotaproduk = findViewById(R.id.ll1_et_kotaproduk);
        ll1_et_jmlproduk = findViewById(R.id.ll1_et_jmlproduk);

        ll2_et_namaproduk = findViewById(R.id.ll2_et_namaproduk);
        ll2_et_hargasewa = findViewById(R.id.ll2_et_hargasewa);
        ll2_et_deskripsiproduk = findViewById(R.id.ll2_et_deskripsiproduk);
        ll2_et_spesifikasiproduk = findViewById(R.id.ll2_et_spesifikasiproduk);
        ll2_imgproduk = findViewById(R.id.ll2_imgproduk);
        ll2_et_kotaproduk = findViewById(R.id.ll2_et_kotaproduk);
        ll2_et_jmlproduk = findViewById(R.id.ll2_et_jmlproduk);

        ll3_et_namapenyediajasa = findViewById(R.id.ll3_et_namapenyediajasa);
        ll3_et_biayajasa = findViewById(R.id.ll3_et_biayajasa);
        ll3_et_deskripsijasa = findViewById(R.id.ll3_et_deskripsijasa);
        ll3_et_keahlianjasa = findViewById(R.id.ll3_et_keahlianjasa);
        ll3_imgproduk = findViewById(R.id.ll3_imgproduk);
        ll3_et_kotaproduk = findViewById(R.id.ll3_et_kotaproduk);

        btn_simpan = findViewById(R.id.btn_simpan);

        btn_ll1_imgproduk = findViewById(R.id.btn_ll1_imgproduk);
        btn_ll2_imgproduk = findViewById(R.id.btn_ll2_imgproduk);
        btn_ll3_imgproduk = findViewById(R.id.btn_ll3_imgproduk);

        encodedImage = "";
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        start();

        ll1_et_hargaproduk.addTextChangedListener(new NumberTextWatcher(ll1_et_hargaproduk));
        ll2_et_hargasewa.addTextChangedListener(new NumberTextWatcher(ll2_et_hargasewa));
        ll3_et_biayajasa.addTextChangedListener(new NumberTextWatcher(ll3_et_biayajasa));
        //ll1_et_beratproduk.addTextChangedListener(new NumberTextWatcher(ll1_et_beratproduk));

        sp_jeniswarung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaTipeProduk = sp_jeniswarung.getSelectedItem().toString();
                for (int a=0; a<modelTipeProduk.getTotalData(); a++){
                    try {
                        if (modelTipeProduk.getData().get(a).getNamaTipeProduk().equalsIgnoreCase(namaTipeProduk)){
                            idTipeProduk = modelTipeProduk.getData().get(a).getIdTipeProduk();
                        }
                    } catch (Exception e){}
                }
                if(idTipeProduk.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")){
                    // ini produk sewa mesin
                    ll2_warung_sewa_mesin.setVisibility(View.VISIBLE);
                    ll1_warung_pupuk_pestisida.setVisibility(View.GONE);
                    ll3_warung_tenaga_kerja.setVisibility(View.GONE);
                } else if(idTipeProduk.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")){
                    // ini produk tenaga kerja
                    ll2_warung_sewa_mesin.setVisibility(View.GONE);
                    ll1_warung_pupuk_pestisida.setVisibility(View.GONE);
                    ll3_warung_tenaga_kerja.setVisibility(View.VISIBLE);
                } else if(idTipeProduk.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c") ||
                        idTipeProduk.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                        idTipeProduk.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")){
                    // ini produk bibit, pupuk, pestisida
                    ll2_warung_sewa_mesin.setVisibility(View.GONE);
                    ll1_warung_pupuk_pestisida.setVisibility(View.VISIBLE);
                    ll3_warung_tenaga_kerja.setVisibility(View.GONE);
                } else {
                    ll2_warung_sewa_mesin.setVisibility(View.GONE);
                    ll1_warung_pupuk_pestisida.setVisibility(View.GONE);
                    ll3_warung_tenaga_kerja.setVisibility(View.GONE);
                    Toast.makeText(TambahWarungku.this, "Tipe Produk Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idTipeProduk.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")){
                    // ini produk sewa mesin
                    simpanProdukSewaMesin();
                } else if(idTipeProduk.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")) {
                    // ini produk tenaga kerja
                    simpanProdukTenagaKerja();
                }else if(idTipeProduk.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c")){
                    // ini produk bibit
                    simpanProdukBibit();
                } else if(idTipeProduk.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b")){
                    // ini produk pupuk
                    simpanProdukPupuk();
                } else if(idTipeProduk.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")){
                    // ini produk pestisida
                    simpanProdukPestisida();
                }
            }
        });

        btn_ll1_imgproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });

        btn_ll2_imgproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });

        btn_ll3_imgproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });



    }

    public void start(){
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
                getDataTipeProduk();
            }
        }).start();
    }

    public void getDataTipeProduk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTipeProduk> datasp = apiInterface.getDataTipeProduk();
        datasp.enqueue(new Callback<ModelTipeProduk>() {
            @Override
            public void onResponse(Call<ModelTipeProduk> call, Response<ModelTipeProduk> response) {
                modelTipeProduk = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelTipeProduk.getTotalData(); i++) {
                        listNamaTipeProduk.add(modelTipeProduk.getData().get(i).getNamaTipeProduk());
                        listTipeProduk.add(modelTipeProduk.getData().get(i));
                    }
                    if(listNamaTipeProduk!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setSpinner();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelTipeProduk> call, Throwable t) {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void setSpinner(){
        adapterTP = new ArrayAdapter<String>(TambahWarungku.this, R.layout.z_spinner_list, listNamaTipeProduk);
        adapterTP.setDropDownViewResource(R.layout.z_spinner_list);
        sp_jeniswarung.setAdapter(adapterTP);
    }

    public void choose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahWarungku.this);
        builder.setMessage("Kamera atau Galeri ?")
                .setCancelable(true)
                .setPositiveButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        } catch (Exception e){
                            Toast.makeText(TambahWarungku.this, "Kamera bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("GALERI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try{
                            startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                        } catch (Exception e){
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
            if (requestCode == CAMERA_REQUEST) {
                bitmap = (Bitmap) data.getExtras().get("data");
                encodedImage = ImageBase64.encode(bitmap);
                if (!encodedImage.equalsIgnoreCase("")){
                    //Toast.makeText(TambahWarungku.this, "Foto Berhasil Diambil", Toast.LENGTH_SHORT).show();
                    sendPic();
                } else {
                    Toast.makeText(TambahWarungku.this, "Kamera bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                }

            }
            else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    encodedImage = ImageBase64.encode(bitmap);
                    if (!encodedImage.equalsIgnoreCase("")){
                        //Toast.makeText(TambahWarungku.this, "Foto Berhasil Diambil", Toast.LENGTH_SHORT).show();
                        sendPic();
                    } else {
                        Toast.makeText(TambahWarungku.this, "Galeri bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void sendPic(){
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
                sendPhoto();
            }
        }).start();
    }

    public void sendPhoto(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", "a");
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "03");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendPhoto(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                showPic();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal ambil foto", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void showPic(){
        if(idTipeProduk.equalsIgnoreCase("2d5f06cf-358c-4bd4-acde-2626498b391a")){
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(ll2_imgproduk);
        } else if(idTipeProduk.equalsIgnoreCase("6a1b827e-3037-42e6-87aa-b1a9578fd45f")){
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(ll3_imgproduk);
        } else if(idTipeProduk.equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c") ||
                idTipeProduk.equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                idTipeProduk.equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28")){
            String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id=a";
            Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(ll1_imgproduk);
        }
    }

    // SEWA MESIN

    private void simpanProdukSewaMesin(){
        if (!ll2_et_namaproduk.getText().toString().equalsIgnoreCase("") && !ll2_et_hargasewa.getText().toString().equalsIgnoreCase("") &&
                !ll2_et_deskripsiproduk.getText().toString().equalsIgnoreCase("") && !ll2_et_spesifikasiproduk.getText().toString().equalsIgnoreCase("") &&
                !ll2_et_kotaproduk.getText().toString().equalsIgnoreCase("") && !ll2_et_jmlproduk.getText().toString().equalsIgnoreCase("") &&
                !encodedImage.equalsIgnoreCase("")){
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
                    sendDataProdukSewaMesin();
                }
            }).start();
        } else {
            Toast.makeText(this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", "2d5f06cf-358c-4bd4-acde-2626498b391a");
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", ll2_et_hargasewa.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", ll2_et_kotaproduk.getText().toString());
        jsonParams.put("jmlProduk", ll2_et_jmlproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, retrofit2.Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById!=null){
                            sendDataSewaMesin();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", "2d5f06cf-358c-4bd4-acde-2626498b391a");
        jsonParams.put("namaProduk", ll2_et_namaproduk.getText().toString());
        jsonParams.put("hargaProduk", ll2_et_hargasewa.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", ll2_et_deskripsiproduk.getText().toString());
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", ll2_et_kotaproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataSewaMesinById> response = apiInterface.sendSewaMesin(body);
        response.enqueue(new Callback<DataSewaMesinById>() {
            @Override
            public void onResponse(Call<DataSewaMesinById> call, retrofit2.Response<DataSewaMesinById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataSewaMesinById = rawResponse.body();
                        if (dataSewaMesinById!=null){
                            sendPhotoProdukSewaMesin();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            sendPhotoSewaMesin();
                        }
                    } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(TambahWarungku.this, "Gagal upload foto produk sewa mesin", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataSewaMesinById.getData().getIdSewaMesin());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            updateDataProdukSewaMesin();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung sewa mesin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukSewaMesin(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                       updateDataSewaMesin();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataSewaMesin(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSewaMesin", dataSewaMesinById.getData().getIdSewaMesin());
        jsonParams.put("idFoto", dataSewaMesinById.getData().getIdSewaMesin());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataSewaMesin(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        delPic();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // TENAGA KERJA

    private void simpanProdukTenagaKerja(){
        if (!ll3_et_namapenyediajasa.getText().toString().equalsIgnoreCase("") && !ll3_et_biayajasa.getText().toString().equalsIgnoreCase("") &&
                !ll3_et_deskripsijasa.getText().toString().equalsIgnoreCase("") && !ll3_et_keahlianjasa.getText().toString().equalsIgnoreCase("") &&
                !ll3_et_kotaproduk.getText().toString().equalsIgnoreCase("") && !encodedImage.equalsIgnoreCase("")){
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
                    sendDataProdukTenagaKerja();
                }
            }).start();
        } else {
            Toast.makeText(this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", "6a1b827e-3037-42e6-87aa-b1a9578fd45f");
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", ll3_et_biayajasa.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", ll3_et_kotaproduk.getText().toString());
        jsonParams.put("jmlProduk", "0");
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, retrofit2.Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById!=null){
                            sendDataTenagaKerja();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        // ll3_et_namapenyediajasa.getText().toString().equalsIgnoreCase("") && !ll3_et_biayajasa.getText().toString().equalsIgnoreCase("") &&
        //                !ll3_et_deskripsijasa.getText().toString().equalsIgnoreCase("") && !ll3_et_keahlianjasa.getText().toString().equalsIgnoreCase("") &&
        //                !ll3_et_kotaproduk

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", "6a1b827e-3037-42e6-87aa-b1a9578fd45f");
        jsonParams.put("namaTenagaKerja", ll3_et_namapenyediajasa.getText().toString());
        jsonParams.put("namaTipeKerja", "");
        jsonParams.put("biaya", ll3_et_biayajasa.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskripsi", ll3_et_deskripsijasa.getText().toString());
        jsonParams.put("keahlian", ll3_et_keahlianjasa.getText().toString());
        jsonParams.put("pengalamanKerja", "");
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", ll3_et_kotaproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataTenagaKerjaById> response = apiInterface.sendTenagaKerja(body);
        response.enqueue(new Callback<DataTenagaKerjaById>() {
            @Override
            public void onResponse(Call<DataTenagaKerjaById> call, retrofit2.Response<DataTenagaKerjaById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataTenagaKerjaById = rawResponse.body();
                        if (dataTenagaKerjaById!=null){
                            sendPhotoProdukTenagaKerja();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            sendPhotoTenagaKerja();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataTenagaKerjaById.getData().getIdTenagaKerja());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            updateDataProdukTenagaKerja();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung tenaga kerja", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukTenagaKerja(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataTenagaKerja();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataTenagaKerja(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTenagaKerja", dataTenagaKerjaById.getData().getIdTenagaKerja());
        jsonParams.put("idFoto", dataTenagaKerjaById.getData().getIdTenagaKerja());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataTenagaKerja(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        delPic();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // BIBIT

    private void simpanProdukBibit(){
        if (!ll1_et_namaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_hargaproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_deskripsiproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_beratproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_kotaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_jmlproduk.getText().toString().equalsIgnoreCase("") &&
                !encodedImage.equalsIgnoreCase("")){
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
                    sendDataProdukBibit();
                }
            }).start();
        }  else {
            Toast.makeText(this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukBibit(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", "49944852-6f8c-4185-aa08-4407d99f3f8c");
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlProduk", ll1_et_jmlproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, retrofit2.Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById!=null){
                            sendDataBibit();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataBibit(){
        Double a = Double.parseDouble(ll1_et_beratproduk.getText().toString());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", "49944852-6f8c-4185-aa08-4407d99f3f8c");
        jsonParams.put("namaProduk", ll1_et_namaproduk.getText().toString());
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", ll1_et_deskripsiproduk.getText().toString());
        jsonParams.put("beratProduk", a);
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, retrofit2.Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById!=null){
                            sendPhotoProdukBibit();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukBibit(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            sendPhotoBibit();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoBibit(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            updateDataProdukBibit();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung bibit", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukBibit(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataBibit();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataBibit(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        delPic();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // PUPUK

    private void simpanProdukPupuk(){
        if (!ll1_et_namaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_hargaproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_deskripsiproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_beratproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_kotaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_jmlproduk.getText().toString().equalsIgnoreCase("") &&
                !encodedImage.equalsIgnoreCase("")){
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
                    sendDataProdukPupuk();
                }
            }).start();
        }  else {
            Toast.makeText(this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukPupuk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", "4f54e40a-04a2-4569-8a82-860f193e321b");
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlProduk", ll1_et_jmlproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, retrofit2.Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById!=null){
                            sendDataPupuk();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataPupuk(){
        Double a = Double.parseDouble(ll1_et_beratproduk.getText().toString());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", "4f54e40a-04a2-4569-8a82-860f193e321b");
        jsonParams.put("namaProduk", ll1_et_namaproduk.getText().toString());
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", ll1_et_deskripsiproduk.getText().toString());
        jsonParams.put("beratProduk", a);
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, retrofit2.Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById!=null){
                            sendPhotoProdukPupuk();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukPupuk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            sendPhotoPupuk();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoPupuk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            updateDataProdukPupuk();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung pupuk", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukPupuk(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataPupuk();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataPupuk(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        delPic();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    // PESTISIDA

    private void simpanProdukPestisida(){
        if (!ll1_et_namaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_hargaproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_deskripsiproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_beratproduk.getText().toString().equalsIgnoreCase("") &&
                !ll1_et_kotaproduk.getText().toString().equalsIgnoreCase("") && !ll1_et_jmlproduk.getText().toString().equalsIgnoreCase("") &&
                !encodedImage.equalsIgnoreCase("")){
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
                    sendDataProdukPestisida();
                }
            }).start();
        }  else {
            Toast.makeText(this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataProdukPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idTipeProduk", "ad211570-6943-4e4c-88b2-c7837a0a3b28");
        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("foto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlProduk", ll1_et_jmlproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataProdukById> response = apiInterface.sendProduk(body);
        response.enqueue(new Callback<DataProdukById>() {
            @Override
            public void onResponse(Call<DataProdukById> call, retrofit2.Response<DataProdukById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataProdukById = rawResponse.body();
                        if (dataProdukById!=null){
                            sendDataPestisida();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfil", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("idTipeProduk", "ad211570-6943-4e4c-88b2-c7837a0a3b28");
        jsonParams.put("namaProduk", ll1_et_namaproduk.getText().toString());
        jsonParams.put("hargaProduk", ll1_et_hargaproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("deskProduk", ll1_et_deskripsiproduk.getText().toString());
        jsonParams.put("beratProduk", ll1_et_beratproduk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idFoto", "");
        jsonParams.put("kota", ll1_et_kotaproduk.getText().toString());
        jsonParams.put("jmlTerjual", 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataBppById> response = apiInterface.sendBibitPestisida(body);
        response.enqueue(new Callback<DataBppById>() {
            @Override
            public void onResponse(Call<DataBppById> call, retrofit2.Response<DataBppById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataBppById = rawResponse.body();
                        if (dataBppById!=null){
                            sendPhotoProdukPestisida();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoProdukPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idPhoto", dataProdukById.getData().getIdProduk());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            sendPhotoPestisida();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendPhotoPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idPhoto", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("image", encodedImage);
        jsonParams.put("imageType", "02");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<DataPhotoById> response = apiInterface.sendPhoto2(body);
        response.enqueue(new Callback<DataPhotoById>() {
            @Override
            public void onResponse(Call<DataPhotoById> call, retrofit2.Response<DataPhotoById> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        dataPhotoById = rawResponse.body();
                        if (dataPhotoById != null) {
                            updateDataProdukPestisida();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahWarungku.this, "Gagal upload foto produk warung pestisida", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DataPhotoById> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataProdukPestisida(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProduk", dataProdukById.getData().getIdProduk());
        jsonParams.put("foto", dataProdukById.getData().getIdProduk());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProduk(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        updateDataPestisida();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataPestisida(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idWarungBpp", dataBppById.getData().getIdWarungBpp());
        jsonParams.put("idFoto", dataBppById.getData().getIdWarungBpp());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataBibitPestisida(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        delPic();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void delPic(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto("a");
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(TambahWarungku.this, "Berhasil tambah produk", Toast.LENGTH_LONG).show();
                            goToWarungku();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(TambahWarungku.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void goToWarungku(){
        Intent a = new Intent(TambahWarungku.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal tambah produk ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToWarungku();
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