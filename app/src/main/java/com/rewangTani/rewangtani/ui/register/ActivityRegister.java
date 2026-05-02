package com.rewangTani.rewangtani.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.databinding.StarterRegisterBinding;
import com.rewangTani.rewangtani.utility.DialogUtil;

public class ActivityRegister extends AppCompatActivity {

    private RegisterViewModel viewModel;
    StarterRegisterBinding binding;
    PopupWindow popupWindow;
    View popupView;
    GoogleSignInClient mGoogleSignInClient;
    boolean isVisible = false;
    boolean isVisible2 = false;

//    FirebaseAuth firebaseAuth;
//    GoogleSignInAccount googleSignInAccount;
//    AuthCredential authCredential;
//    Task<GoogleSignInAccount> signInAccountTask;
//    String token, googleid, googleusername, googleemail, googlename;
//    int tokenis = 0;
//    private static final int RC_SIGN_IN = 101;
//    private GoogleSignInClient googleSignInClient;
//    private String selectedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.starter_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        initListener();
        initGoogleSign();
        observeViewModel();

        viewModel.getAccounts().observe(this, acc -> {
            Log.i("SOFIA", "size = " + acc.size());
        });
    }

    private void initListener()
    {
        binding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = binding.txtPassword.length();
                if (length < 8) {
                    binding.panjangpassword.setText("Password kurang dari 8 karakter");
                } else {
                    binding.panjangpassword.setText("");
                }
            }
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

        binding.btnPassword2.setOnClickListener(v -> {
            if (isVisible2) {
                binding.txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnPassword2.setImageResource(R.drawable.ic_password_show);
            } else {
                binding.txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnPassword2.setImageResource(R.drawable.ic_password_hide);
            }
            isVisible2 = !isVisible2;
        });

        binding.btnDaftar.setOnClickListener(v -> {
            submit();
//            if (tokenis == 0)
//                Toast.makeText(Register.this, "Maaf register bermasalah", Toast.LENGTH_SHORT).show();
//            else
//                checkUsername();
        });

        binding.btnSignupWithGoogle.setOnClickListener(v -> {
            Toast.makeText(ActivityRegister.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//            signOutGoogle();
//            Intent intent = mGoogleSignInClient.getSignInIntent();
//            startActivityForResult(intent, 100);
//            showAccountPicker();

        });
    }

    private void initGoogleSign()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878909563548-6a6d4mj5uqmqm4hlea3sa73agsv10fhm.apps.googleusercontent.com")
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

        viewModel.registerSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show();
                goToHome();
            }
        });

    }

    private void submit()
    {
        String username = binding.txtUsername.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();
        String repeatPassword = binding.txtRepeatPassword.getText().toString();
        String nama = binding.txtNama.getText().toString();

        boolean valid = viewModel.validate(username, email, password, repeatPassword);

        if (valid) {
            showPrivacyPopup(username, email, password, nama);
        }
    }

    private void showPrivacyPopup(String username, String email, String password, String nama)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.starter_register_kebijakan_privacy, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        CheckBox checkBox = popupView.findViewById(R.id.checkBox);
        RelativeLayout btnDaftar = popupView.findViewById(R.id.btnDaftarPopup);

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });

        btnDaftar.setOnClickListener(view -> {
            if (!checkBox.isChecked()) {
                Toast.makeText(ActivityRegister.this, "Centang kebijakan privasi terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                popupWindow.dismiss();
                viewModel.register(username, email, password, nama);
            }
        });
    }

/*    private void showAccountPicker() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("com.google");

        if (accounts.length == 0) {
            Toast.makeText(this, "No Google accounts found on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] accountNames = new String[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            accountNames[i] = accounts[i].name;
        }

        new AlertDialog.Builder(this)
                .setTitle("Select a Google Account")
                .setItems(accountNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedAccount = accountNames[which];
                        initiateGoogleSignIn(selectedAccount);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void initiateGoogleSignIn(String accountName) {
        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("878909563548") // Replace with your client ID
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Sign out from previous accounts for testing (optional)
        googleSignInClient.signOut();

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void getGoogle(Intent data) {
        signInAccountTask = GoogleSignIn
                .getSignedInAccountFromIntent(data);
        if (signInAccountTask.isSuccessful()) {
            try {
                googleSignInAccount = signInAccountTask
                        .getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    authCredential = GoogleAuthProvider
                            .getCredential(googleSignInAccount.getIdToken()
                                    , null);
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
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                binding.viewLoading.setVisibility(View.GONE);
                                                Toast.makeText(NewRegisterActivity.this, "Authentication Failed :" + task.getException()
                                                        .getMessage(), Toast.LENGTH_SHORT).show();
                                                signOutGoogle();

                                            }
                                        });
                                    }
                                }
                            });
                }

            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    Toast.makeText(NewRegisterActivity.this, "Authentication Failed ", Toast.LENGTH_SHORT).show();
                    signOutGoogle();

                }
            });
        }
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
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    public void checkUserByGoogle() {
        googleid = googleSignInAccount.getId();
        googleemail = googleSignInAccount.getEmail();
        googleusername = googleSignInAccount.getGivenName();
        googlename = googleSignInAccount.getGivenName();
        if (!googleid.equalsIgnoreCase("") && !googleusername.equalsIgnoreCase("")
                && !googleemail.equalsIgnoreCase("") && !googlename.equalsIgnoreCase("")) {

            checkEmailGoogle();
        }

    }

    private void checkEmailGoogle() {
        if (!googleemail.equalsIgnoreCase("")) {
            for (int i = 0; i < modelAkunAwal.getTotalData(); i++) {
                if (modelAkunAwal.getData().get(i).getEmail().equalsIgnoreCase(googleemail)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(NewRegisterActivity.this, "Email sudah terpakai", Toast.LENGTH_LONG).show();
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.signOut();
                            testEmail = 1;
                            signOutGoogle();
                        }
                    });
                    break;
                }
            }
        }
        if (testEmail != 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    testEmail = 0;

                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = inflater.inflate(R.layout.starter_register_kebijakan_privacy, null);
                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    CheckBox checkBox = popupView.findViewById(R.id.checkBox);
                    RelativeLayout btnDaftar = popupView.findViewById(R.id.btnDaftarPopup);

                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            mGoogleSignInClient.signOut();
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                    btnDaftar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkBox.isChecked()) {
                                check = 10;
                            }
                            if (check != 10) {
                                Toast.makeText(NewRegisterActivity.this, "Centang kebijakan privasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                            } else if (check == 10) {
                                mGoogleSignInClient.signOut();
                                popupWindow.dismiss();

                                binding.viewLoading.setVisibility(View.VISIBLE);
                                final Handler handler = new Handler();
                                Runnable runnable = new Runnable() {
                                    int count = 0;
                                    @Override
                                    public void run() {
                                        count++;
                                        if (count == 1) {
                                            binding.textLoading.setText("Loading .");
                                        } else if (count == 2) {
                                            binding.textLoading.setText("Loading . .");
                                        } else if (count == 3) {
                                            binding.textLoading.setText("Loading . . .");
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
                                        sendDataAkunGoogle();
                                    }
                                }).start();
                            }
                        }
                    });
                }
            });
        } else {
            testEmail = 0;
        }
    }

    private void sendDataAkunGoogle() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userName", googleusername);
        jsonParams.put("email", googleemail);
        jsonParams.put("password", "");
        jsonParams.put("namaAkun", googlename);
        jsonParams.put("token", token);
        jsonParams.put("idGoogle", googleid);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getDataAkunwithGoogle();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(NewRegisterActivity.this, "Gagal register", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(NewRegisterActivity.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getDataAkunwithGoogle() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> data = apiInterface.getDataAkun();
        data.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelAkun.getTotalData(); i++) {
                            if (!modelAkun.getData().get(i).getIdGoogle().equalsIgnoreCase("")) {
                                if (modelAkun.getData().get(i).getIdGoogle() != null) {
                                    String id = modelAkun.getData().get(i).getIdGoogle();
                                    if (id.equalsIgnoreCase(googleid)) {
                                        dataAkun = modelAkun.getData().get(i);
                                        break;
                                    }
                                }
                            }
                        }
                        if (dataAkun != null) {
                            firebaseAuth.signOut();
                            sendDataProfilAkun();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.viewLoading.setVisibility(View.GONE);
                                    Toast.makeText(NewRegisterActivity.this, "Data akun tidak ditemukan", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(NewRegisterActivity.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();

                    }
                });
                call.cancel();
            }
        });
    }*/

    public void goToHome() {
        Intent a = new Intent(ActivityRegister.this, Home.class);
        startActivity(a);
        finish();
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
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
            ActivityRegister.super.onBackPressed();
            finish();
            finishAffinity();
        });
    }
}