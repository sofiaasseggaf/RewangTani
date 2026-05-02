package com.rewangTani.rewangtani.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.StarterLoginBinding;
import com.rewangTani.rewangtani.ui.register.ActivityRegister;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.ViewUtil;

public class ActivityLogin extends AppCompatActivity
{

    private LoginViewModel viewModel;
    StarterLoginBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    boolean isVisible = false;

//    GoogleSignInAccount googleSignInAccount;
//    Task<GoogleSignInAccount> signInAccountTask;
//    int tokenis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.starter_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initEvent();
        initGoogleSign();
        observeViewModel();
    }

    private void initEvent()
    {
        binding.btnDaftar.setOnClickListener(v -> {
            goToDaftar();
        });

        binding.btnPassword.setOnClickListener(v -> {
            ViewUtil.togglePassword(binding.txtPassword, binding.btnPassword);
        });

        binding.btnPassword.setOnClickListener(v -> {
            if (isVisible) {
                binding.txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnPassword.setImageResource(R.drawable.ic_password_show);
            } else {
                binding.txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnPassword.setImageResource(R.drawable.ic_password_hide);
            }
            isVisible = !isVisible;
        });

        binding.btnMasuk.setOnClickListener(v -> {
            viewModel.validateLogin(binding.txtUsername.getText().toString(), binding.txtPassword.getText().toString());
        });

        binding.btnLoginWithGoogle.setOnClickListener(v -> {
            Toast.makeText(ActivityLogin.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//            Intent intent = mGoogleSignInClient.getSignInIntent();
//            startActivityForResult(intent, 100);
        });
    }

    private void initGoogleSign()
    {
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("871147289264-at1ci259siqgsjao8akj50cdgkend9ma.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void observeViewModel()
    {
        viewModel.loadAccounts();

        viewModel.isLoading.observe(this, isLoading -> {
            binding.viewLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.errorMessage.observe(this, msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.loginSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        });
    }

/*    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.viewLoading.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                    tokenis = 0;
                                }
                            });
                        } else {
                            // Get new FCM registration token
                            token = task.getResult();
                        }
                        if (token != null) {
                            tokenis = 1;
                            updateDataAkunToken();
                        } else {
                            tokenis = 0;
                            getToken();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {}
                });
    }

    public void updateDataAkunToken() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", dataAkun.getIdAkun());
        jsonParams.put("token", token);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        saveDataProfileToPreference();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
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
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void getGoogle(Intent data) {
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
                                                binding.viewLoading.setVisibility(View.GONE);
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

    public void checkUserByGoogle() {
        String a = googleSignInAccount.getId();
        if (!a.equalsIgnoreCase("")) {
            for (int i = 0; i < modelAkun.getTotalData(); i++) {
                if (modelAkun.getData().get(i).getIdGoogle() != null) {
                    if (!modelAkun.getData().get(i).getIdGoogle().equalsIgnoreCase("")) {
                        String id = modelAkun.getData().get(i).getIdGoogle();
                        if (id.equalsIgnoreCase(a)) {
                            dataAkun = modelAkun.getData().get(i);
                            ok = 10;
                            break;
                        }
                    }
                }
            }
            if (ok != 10) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
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

            } else if (ok == 10) {
                String s = "Google sign in successful";
                // Display Toast
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
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

    private void signOutGoogle() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        revokeGoogle();
                    }
                });
    }

    private void revokeGoogle() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { }
                });
    }*/

    public void goToHome() {
        Intent a = new Intent(ActivityLogin.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToDaftar() {
        Intent a = new Intent(ActivityLogin.this, ActivityRegister.class);
        startActivity(a);
        finish();
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            binding.viewLoading.setVisibility(View.VISIBLE);
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
            handler.postDelayed(runnable, 1000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoogle(data);
                }
            }).start();

        }
    }*/

    @Override
    public void onBackPressed()
    {
        DialogUtil.showConfirmDialog(this, () -> {
            ActivityLogin.super.onBackPressed();
            finish();
            finishAffinity();
        });
    }
}