package com.rewangTani.rewangtani.bottombar.profilakun;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.bottombar.profilakun.pesanan.PesananDiproses;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarPaBerandaprofileBinding;
import com.rewangTani.rewangtani.model.modelphoto.DatumPhoto;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaProfile extends AppCompatActivity {

    BottombarPaBerandaprofileBinding binding;
    AlertDialog alertDialog;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 12345;
    final int GALLERY_REQUEST = 54321;
    Bitmap bitmap;
    String encodedImage, googleid;
    int photoExist;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    GoogleSignInAccount googleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_berandaprofile);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878909563548-6a6d4mj5uqmqm4hlea3sa73agsv10fhm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

        setData();

        binding.btnPesanan.setOnClickListener(v->{
            Toast.makeText(BerandaProfile.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//            goToPesanan();
        });

        binding.btnUbahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfil();
            }
        });

        binding.btnGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaProfile.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//                goToEditPassword();
            }
        });

        binding.btnKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToKontak();
            }
        });

        binding.btnTentangAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTentang();
            }
        });

        binding.btnHubungkanGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BerandaProfile.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//                hubungkanGoogle();
            }
        });

        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToKeluar();
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

        binding.btnWarungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
            }
        });


        binding.btnPesan.setOnClickListener(v->{
            goToPesan();
        });

        binding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoExist==2){
                    choose();
                } else if(photoExist==1){
                    deleteExistedPhoto();
                }
            }
        });

    }

    public void setData(){
        binding.namaProfile.setText(PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext()));
        if (!PreferenceUtils.getIDPhoto(getApplicationContext()).equalsIgnoreCase("")){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+PreferenceUtils.getIDPhoto(getApplicationContext());
            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(binding.imageProfile);
            photoExist = 1;
        } else {
            photoExist = 2;
            binding.imageProfile.setImageDrawable(getDrawable(R.drawable.elips_profil));
        }
    }

    public void  deleteExistedPhoto(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumPhoto> dataRT = apiInterface.deletePhoto(PreferenceUtils.getIDPhoto(getApplicationContext()));
        dataRT.enqueue(new Callback<DatumPhoto>() {
            @Override
            public void onResponse(Call<DatumPhoto> call, Response<DatumPhoto> response) {
                DatumPhoto datumPhoto = response.body();
                if (response.body()!=null){
                    PreferenceUtils.saveIDPhoto("", getApplicationContext());
                    choose();
                } else {
                    photoExist = 2;
                    PreferenceUtils.saveIDPhoto("", getApplicationContext());
                    choose();
                }
            }
            @Override
            public void onFailure(Call<DatumPhoto> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(BerandaProfile.this, "Terjadi gangguan koneksi saat menghapus foto", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void choose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BerandaProfile.this);
        builder.setMessage("Kamera atau Galeri ?")
                .setCancelable(true)
                .setPositiveButton("KAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        } catch (Exception e){
                            Toast.makeText(BerandaProfile.this, "Kamera bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("GALERI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try{
                            startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                        } catch (Exception e){
                            Toast.makeText(BerandaProfile.this, "Galeri bermasalah", Toast.LENGTH_SHORT).show();
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
                if (!encodedImage.equalsIgnoreCase("")) {
                    go();
                } else {
                    Toast.makeText(BerandaProfile.this, "Kamera bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    encodedImage = ImageBase64.encode(bitmap);
                    if (!encodedImage.equalsIgnoreCase("")) {
                        go();
                    } else {
                        Toast.makeText(BerandaProfile.this, "Galeri bermasalah ketika mengambil foto", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 100) {
                // When request code is equal to 100
                // Initialize task
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                        .getSignedInAccountFromIntent(data);

                // check condition
                if (signInAccountTask.isSuccessful()) {
                    // Initialize sign in account
                    try {
                        // Initialize sign in account
                        googleSignInAccount = signInAccountTask
                                .getResult(ApiException.class);
                        // Check condition
                        if (googleSignInAccount != null) {
                            // Initialize firebase auth
                            firebaseAuth = FirebaseAuth.getInstance();
                            // When sign in account is not equal to null
                            // Initialize auth credential

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
                                    pilihAkunHubungkanGoogle();
                                }
                            }).start();

                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void go(){
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
                sendPhoto();
            }
        }).start();
    }
    
    public void sendPhoto(){
            final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
            Map<String, Object> jsonParams = new ArrayMap<>();
            jsonParams.put("idPhoto", PreferenceUtils.getIdProfil(getApplicationContext()));
            jsonParams.put("image", encodedImage);
            jsonParams.put("imageType", "01");
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(jsonParams)).toString());

            Call<ResponseBody> response = apiInterface.sendPhoto(body);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                    try {
                        Log.d("tag", rawResponse.body().string());
                        if (rawResponse.body() != null) {
                            PreferenceUtils.saveIDPhoto(PreferenceUtils.getIdProfil(getApplicationContext()), getApplicationContext());
                            updateProfile();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(BerandaProfile.this, "Gagal ganti foto", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(BerandaProfile.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        }
                    });
                    call.cancel();
                }
            });
    }

    public void updateProfile(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idProfile", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("foto", PreferenceUtils.getIDPhoto(getApplicationContext()));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.updateDataProfilAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setNewPhoto();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(BerandaProfile.this, "Gagal ubah profil", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(BerandaProfile.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void setNewPhoto(){
        String img = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+PreferenceUtils.getIDPhoto(getApplicationContext());
        Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageProfile);
        Toast.makeText(BerandaProfile.this, "Berhasil ganti foto", Toast.LENGTH_LONG).show();
    }


    public void hubungkanGoogle(){
        if (!PreferenceUtils.getIDGoogle(getApplicationContext()).equalsIgnoreCase("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(BerandaProfile.this);
            builder.setMessage("Akun Google sudah terhubung")
                    .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    })
                    .create()
                    .show();
        } else {
            firebaseAuth.signOut();
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent,100);
        }
    }

    public void pilihAkunHubungkanGoogle(){
        AuthCredential authCredential = GoogleAuthProvider
                .getCredential(googleSignInAccount.getIdToken()
                        , null);
        // Check credential
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Check condition
                        if (task.isSuccessful()) {
                            // When task is successful
                            // Redirect to profile activity

                            googleid = googleSignInAccount.getId();
                            if (!googleid.equalsIgnoreCase("")) {
                                updateProfileIdGoogle();
                            }

                        } else {
                            // When task is unsuccessful
                            // Display Toast
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(BerandaProfile.this, "Authentication Failed :" + task.getException()
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
    }

    public void updateProfileIdGoogle(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("idGoogle", googleid );
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        saveData();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(BerandaProfile.this, "Gagal gabungkan dengan Google", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(BerandaProfile.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    public void saveData(){
        PreferenceUtils.saveIDGoogle(googleid, getApplicationContext());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(BerandaProfile.this, "Berhasil gabungkan dengan Google", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void goToBeranda(){
        Intent a = new Intent(BerandaProfile.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToPesanan(){
        Intent a = new Intent(BerandaProfile.this, PesananDiproses.class);
        startActivity(a);
        finish();
    }

    public void goToEditProfil(){
        Intent a = new Intent(BerandaProfile.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToEditPassword(){
        Intent a = new Intent(BerandaProfile.this, EditPassword.class);
        startActivity(a);
        finish();
    }

    public void goToKontak(){
        Intent a = new Intent(BerandaProfile.this, Kontak.class);
        startActivity(a);
        finish();
    }

    public void goToTentang(){
        Intent a = new Intent(BerandaProfile.this, Tentang.class);
        startActivity(a);
        finish();
    }

    public void goToKeluar(){
        Intent a = new Intent(BerandaProfile.this, Keluar.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(BerandaProfile.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPesan(){
        Intent a = new Intent(BerandaProfile.this, Inbox.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(BerandaProfile.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
       goToBeranda();
    }
}