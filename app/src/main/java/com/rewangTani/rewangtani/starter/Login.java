package com.rewangTani.rewangtani.starter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.messaging.FirebaseMessaging;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilakun.EditPassword;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    //LOGIN
    EditText txt_username, txt_password;
    ImageButton btn_masuk, btn_back, btn_password;
    TextView btn_daftar;
    String token;
    int tokenis=0;
    // LOGIN W GOOGLE
    ImageButton btnLoginWithGoogle;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    GoogleSignInAccount googleSignInAccount;

    // RETROFIT
    ModelAkun modelAkun;
    ModelProfilAkun modelProfilAkun;
    DatumAkun dataAkun;
    DatumProfil dataProfil;
    String username, password;
    int ok;
    TextView txtload;
    int pw = 0;
    Task<GoogleSignInAccount> signInAccountTask;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_login);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        btn_masuk = findViewById(R.id.btn_masuk);
        btn_back = findViewById(R.id.btn_back);
        btn_daftar = findViewById(R.id.btn_daftar);
        //btnLoginWithGoogle = findViewById(R.id.btn_signin);
        txtload = findViewById(R.id.textloading);
        btn_password = findViewById(R.id.btn_password);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878909563548-6a6d4mj5uqmqm4hlea3sa73agsv10fhm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        first();

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDaftar();
            }
        });

        btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mGoogleSignInClient!=null){
                    signOutGoogle();
                }
                // Initialize sign in intent
                Intent intent = mGoogleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent,100);
                //signInWithGoogle();
                //Toast.makeText(Login.this, "Fitur Belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });


        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw==0){
                    pw = 1;
                    btn_password.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    txt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw==1){
                    pw = 0;
                    btn_password.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    txt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSplashScreen();
            }
        });

    }

    private void first(){
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
                getDataAkun();
            }
        }).start();
    }

    public void getDataAkun() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
            final Call<ModelAkun> dataAkun = apiInterface.getDataAkun();
            dataAkun.enqueue(new Callback<ModelAkun>() {
                @Override
                public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                    modelAkun = response.body();
                    if (response.body()!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Data akun tidak ditemukan", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Login.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                            call.cancel();
                        }
                    });
                }
            });
    }

    public void login(){
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
                check();
            }
        }).start();
    }

    public void check(){
        if (!txt_username.getText().toString().isEmpty() && !txt_password.getText().toString().isEmpty()) {
            try {
                for (int i = 0; i < modelAkun.getTotalData(); i++) {
                    username = modelAkun.getData().get(i).getUserName();
                    password = modelAkun.getData().get(i).getPassword();
                    //idUser = dataModelUser.getData().getAauthUsers().get(i).getId();
                    if (username.equalsIgnoreCase(txt_username.getText().toString()) &&
                            password.equalsIgnoreCase(txt_password.getText().toString())) {
                        dataAkun = modelAkun.getData().get(i);
                        ok=10;
                        break;
                        }
                    }
            } catch (Exception e){ }
            if (ok!=10){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Akun belum terdaftar", Toast.LENGTH_SHORT).show();

                    }
                });
            }else if (ok==10){
                getDataProfilAkun();
            }
        } else if (txt_username.getText().toString().isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Silahkan isi kolom yang kosong", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (txt_password.getText().toString().isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Silahkan isi kolom yang kosong", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (txt_username.getText().toString().isEmpty() && txt_password.getText().toString().isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Silahkan isi kolom yang kosong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getDataProfilAkun() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                            String idAkun = dataAkun.getIdAkun();
                            if (modelProfilAkun.getData().get(i).getIdAkun().equalsIgnoreCase(idAkun)) {
                                dataProfil = modelProfilAkun.getData().get(i);
                                if (dataProfil!=null){
                                    getToken();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                                            Toast.makeText(Login.this, "Data akun tidak ditemukan", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                    tokenis = 0;
                                    return;
                                }
                            });
                        } else {
                            // Get new FCM registration token
                            token = task.getResult();
                        }
                        if (token!=null){
                            tokenis = 1;
                            updateDataAkunToken();
                        } else {
                            tokenis = 0;
                            getToken();
                        }
                    }
                });
    }

    public void updateDataAkunToken(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", dataAkun.getIdAkun());
        jsonParams.put("token", token );
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        saveDataTokenBaru();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Gagal update token", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Login.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void saveDataTokenBaru(){
        PreferenceUtils.saveIdAkun(dataAkun.getIdAkun(), getApplicationContext());
        PreferenceUtils.saveToken(token, getApplicationContext());
        PreferenceUtils.saveIdProfil(dataProfil.getIdProfile(), getApplicationContext());
        PreferenceUtils.saveIdAlamat(dataProfil.getIdAlamat(), getApplicationContext());
        PreferenceUtils.savePassword(dataAkun.getPassword(), getApplicationContext());
        PreferenceUtils.saveUsername(dataAkun.getUserName(), getApplicationContext());
        PreferenceUtils.saveNamaDepan(dataProfil.getNamaDepan(), getApplicationContext());
        PreferenceUtils.saveNamaBelakang(dataProfil.getNamaBelakang(), getApplicationContext());
        PreferenceUtils.saveIDGoogle(dataAkun.getIdGoogle(), getApplicationContext());
        if (!dataProfil.getFoto().equalsIgnoreCase("")){
            PreferenceUtils.saveIDPhoto(dataProfil.getFoto(), getApplicationContext());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(Login.this, "Berhasil masuk", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        });

    }

    /*public void saveData(){
        PreferenceUtils.saveIdAkun(dataAkun.getIdAkun(), getApplicationContext());
        PreferenceUtils.saveToken(dataAkun.getToken(), getApplicationContext());
        PreferenceUtils.saveIdProfil(dataProfil.getIdProfile(), getApplicationContext());
        PreferenceUtils.saveIdAlamat(dataProfil.getIdAlamat(), getApplicationContext());
        PreferenceUtils.savePassword(dataAkun.getPassword(), getApplicationContext());
        PreferenceUtils.saveUsername(dataAkun.getUserName(), getApplicationContext());
        PreferenceUtils.saveNamaDepan(dataProfil.getNamaDepan(), getApplicationContext());
        PreferenceUtils.saveNamaBelakang(dataProfil.getNamaBelakang(), getApplicationContext());
        PreferenceUtils.saveIDGoogle(dataAkun.getIdGoogle(), getApplicationContext());
        if (!dataProfil.getFoto().equalsIgnoreCase("")){
            PreferenceUtils.saveIDPhoto(dataProfil.getFoto(), getApplicationContext());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(Login.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        });

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100
            // Initialize task
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
                    getGoogle(data);
                }
            }).start();

        }
    }

    private void getGoogle(Intent data){
        signInAccountTask = GoogleSignIn
                .getSignedInAccountFromIntent(data);
        // check condition
        if (signInAccountTask.isSuccessful()) {
            // When google sign in successful

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
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                                checkUserByGoogle();
                                            }
                                        });
                                    } else {
                                        // When task is unsuccessful
                                        // Display Toast
                                        Toast.makeText(Login.this, "Authentication Failed :" + task.getException()
                                                .getMessage(), Toast.LENGTH_SHORT).show();
                                        signOutGoogle();
                                    }
                                }
                            });

                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            signOutGoogle();
        }
    }



    public void checkUserByGoogle(){
        String a = googleSignInAccount.getId();
        if (!a.equalsIgnoreCase("")){
            for(int i=0; i<modelAkun.getTotalData(); i++){
                if (modelAkun.getData().get(i).getIdGoogle()!=null){
                    if (!modelAkun.getData().get(i).getIdGoogle().equalsIgnoreCase("")){
                        String id = modelAkun.getData().get(i).getIdGoogle();
                        if (id.equalsIgnoreCase(a)){
                            dataAkun = modelAkun.getData().get(i);
                            ok=10;
                            break;
                        }
                    }
                }
            }
            if (ok!=10){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setMessage("Akun Google belum pernah register")
                                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        signOutGoogle();
                                        goToDaftar();
                                    }
                                })
                                .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        signOutGoogle();
                                        dialog.cancel();
                                    }
                                })
                                .create()
                                .show();
                    }
                });

            } else if (ok==10){
                String s = "Google sign in successful";
                // Display Toast
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        signOutGoogle();
                        getDataProfilAkun();
                    }
                });

            }
        }
        //Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

    private void signOutGoogle(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        revokeGoogle();
                    }
                });
    }

    private void revokeGoogle(){
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // do nothing
                    }
                });
    }

    // ---------------------------------------------------------------------------------------------

    public void goToHome(){
        Intent a = new Intent(Login.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToDaftar(){
        Intent a = new Intent(Login.this, Register.class);
        startActivity(a);
        finish();
    }

    public void  goToSplashScreen(){
        Intent a = new Intent(Login.this, SplashScreen.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToSplashScreen();
    }

}