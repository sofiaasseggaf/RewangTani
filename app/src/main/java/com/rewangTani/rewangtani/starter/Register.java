package com.rewangTani.rewangtani.starter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.StarterRegisterBinding;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    StarterRegisterBinding binding;
    ModelAkun modelAkun, modelAkunAwal;
    DatumAkun dataAkun;
    ModelProfilAkun modelProfilAkun;
    DatumProfil dataProfil;
    PopupWindow popupWindow;
    View popupView;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    GoogleSignInAccount googleSignInAccount;
    AuthCredential authCredential;
    Task<GoogleSignInAccount> signInAccountTask;
    String token, googleid, googleusername, googleemail, googlename;
    int check, checkPanjangPasword, testUsername, testEmail;
    int pw = 0;
    int pw2 = 0;
    int tokenis = 0;


    private static final int RC_SIGN_IN = 101;
    private GoogleSignInClient googleSignInClient;
    private String selectedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.starter_register);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878909563548-6a6d4mj5uqmqm4hlea3sa73agsv10fhm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        showLoadingView();

        binding.txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = binding.txtPassword.length();
                if (length < 8) {
                    checkPanjangPasword = 0;
                    binding.panjangpassword.setText("Password kurang dari 8 karakter");
                } else {
                    checkPanjangPasword = 10;
                    binding.panjangpassword.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.btnPassword.setOnClickListener(v -> {
            if (pw == 0) {
                pw = 1;
                binding.btnPassword.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                binding.txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else if (pw == 1) {
                pw = 0;
                binding.btnPassword.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                binding.txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        binding.btnPassword2.setOnClickListener(v -> {
            if (pw2 == 0) {
                pw2 = 1;
                binding.btnPassword2.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                binding.txtRepeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else if (pw2 == 1) {
                pw2 = 0;
                binding.btnPassword2.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                binding.txtRepeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        binding.btnDaftar.setOnClickListener(v -> {
            checkUsername();
//            if (tokenis == 0)
//                Toast.makeText(Register.this, "Maaf register bermasalah", Toast.LENGTH_SHORT).show();
//            else
//                checkUsername();
        });

        binding.btnSignupWithGoogle.setOnClickListener(v -> {
            Toast.makeText(Register.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();
//            signOutGoogle();
//            Intent intent = mGoogleSignInClient.getSignInIntent();
//            startActivityForResult(intent, 100);
//            showAccountPicker();

        });

    }

    private void showAccountPicker() {
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

    private void showLoadingView() {
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
                    binding.textLoading.setText("TTunggu sebentar ya . . .");
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
                getDataAkun();
            }
        }).start();
    }

    public void getDataAkun() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> data = apiInterface.getDataAkun();
        data.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkunAwal = response.body();
                if (response.body() != null) {
//                    getToken();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(Register.this, "Data akun tidak ditemukan", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void getToken() {
        if (PreferenceUtils.getToken(getApplicationContext()).equalsIgnoreCase("")) {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                        tokenis = 0;
                                    }
                                });
                            } else {
                                // Get new FCM registration token
                                token = task.getResult();
                            }

                            if (token != null) {
                                tokenis = 1;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                    }
                                });
                            } else {
                                tokenis = 0;
                                getToken();
                            }
                        }
                    });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                }
            });
        }
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
                                                Toast.makeText(Register.this, "Authentication Failed :" + task.getException()
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
                    Toast.makeText(Register.this, "Authentication Failed ", Toast.LENGTH_SHORT).show();
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

    private void checkUsername() {
        if (!binding.txtUsername.getText().toString().equalsIgnoreCase("")) {
            String username = binding.txtUsername.getText().toString();
            for (int i = 0; i < modelAkunAwal.getTotalData(); i++) {
                if (modelAkunAwal.getData().get(i).getUserName().equalsIgnoreCase(username)) {
                    Toast.makeText(this, "Username sudah terpakai", Toast.LENGTH_SHORT).show();
                    testUsername = 1;
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Isi username terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }

        if (testUsername != 1) {
            testUsername = 0;
            checkEmail();
        } else {
            testUsername = 0;
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
                            Toast.makeText(Register.this, "Email sudah terpakai", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(Register.this, "Centang kebijakan privasi terlebih dahulu", Toast.LENGTH_SHORT).show();
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
                                handler.postDelayed(runnable, 1 * 1000);
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

    private void checkEmail() {
        if (!binding.txtEmail.getText().toString().equalsIgnoreCase("")) {
            String email = binding.txtEmail.getText().toString();
            for (int i = 0; i < modelAkunAwal.getTotalData(); i++) {
                if (modelAkunAwal.getData().get(i).getEmail().equalsIgnoreCase(email)) {
                    Toast.makeText(this, "Email sudah terpakai", Toast.LENGTH_SHORT).show();
                    testEmail = 1;
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Isi email terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }

        if (testEmail != 1) {
            testEmail = 0;
            checkPassword();
        } else {
            testEmail = 0;
        }
    }

    private void checkPassword() {
        if (!binding.txtPassword.getText().toString().equalsIgnoreCase("")) {
            if (binding.txtPassword.getText().toString().equalsIgnoreCase(binding.txtRepeatPassword.getText().toString())) {
                if (checkPanjangPasword == 10) {
                    boolean hitLetter = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        hitLetter = binding.txtPassword.getText().toString().codePoints().anyMatch(i -> Character.isLetter(i));
                    }
                    boolean hitDigit = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        hitDigit = binding.txtPassword.getText().toString().codePoints().anyMatch(i -> Character.isDigit(i));
                    }
                    boolean containsBoth = (hitLetter && hitDigit);
                    if (containsBoth) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        popupView = inflater.inflate(R.layout.starter_register_kebijakan_privacy, null);
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = LinearLayout.LayoutParams.MATCH_PARENT;
                        boolean focusable = true;
                        popupWindow = new PopupWindow(popupView, width, height, focusable);
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        CheckBox checkBox = popupView.findViewById(R.id.checkBox);
                        RelativeLayout btnDaftar = popupView.findViewById(R.id.btnDaftarPopup);

                        popupView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
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
                                    Toast.makeText(Register.this, "Centang kebijakan privasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                                } else if (check == 10) {
                                    popupWindow.dismiss();
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
                                    handler.postDelayed(runnable, 1 * 1000);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            sendDataAkun();
                                        }
                                    }).start();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(Register.this, "Password harus kombinasi angka dan huruf", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Password kurang dari 8 karakter", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(Register.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Isi password terlebih dahulu !", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Register.this, "Gagal register", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataAkun() {
        Log.i("SOFIA", "send data akun");
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userName", binding.txtUsername.getText().toString());
        jsonParams.put("email", binding.txtEmail.getText().toString());
        jsonParams.put("password", binding.txtPassword.getText().toString());
        jsonParams.put("namaAkun", binding.txtNama.getText().toString());
        jsonParams.put("token", token);
        jsonParams.put("idGoogle", "");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        Log.i("SOFIA", "come here ?");
                        getDataAkunAfterRegister();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Gagal register", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getDataAkunAfterRegister() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> data = apiInterface.getDataAkun();
        data.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelAkun.getTotalData(); i++) {
                            String email = modelAkun.getData().get(i).getEmail();
                            if (email.equalsIgnoreCase(binding.txtEmail.getText().toString())) {
                                dataAkun = modelAkun.getData().get(i);
                                break;
                            }
                        }
                        if (dataAkun != null) {
                            sendDataProfilAkun();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.viewLoading.setVisibility(View.GONE);
                                    Toast.makeText(Register.this, "Data akun tidak ditemukan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();

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
                                    Toast.makeText(Register.this, "Data akun tidak ditemukan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();

                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataProfilAkun() {
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", dataAkun.getIdAkun());
        jsonParams.put("namaDepan", dataAkun.getNamaAkun());
        jsonParams.put("namaBelakang", "");
        jsonParams.put("alamat", "");
        jsonParams.put("idAlamat", "");
        jsonParams.put("nik", "");
        jsonParams.put("tglLahir", "");
        jsonParams.put("gender", "");
        jsonParams.put("telepon", "");
        jsonParams.put("foto", "");
        jsonParams.put("idStatusPekerja", "");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataProfilAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getDataProfilAkun();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Gagal register", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getDataProfilAkun() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                            String idAkun = dataAkun.getIdAkun();
                            if (modelProfilAkun.getData().get(i).getIdAkun().equalsIgnoreCase(idAkun)) {
                                dataProfil = modelProfilAkun.getData().get(i);
                                if (dataProfil != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.viewLoading.setVisibility(View.GONE);
                                            Toast.makeText(Register.this, "Berhasil daftar", Toast.LENGTH_SHORT).show();
                                            saveData();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.viewLoading.setVisibility(View.GONE);
                                            Toast.makeText(Register.this, "Data profil tidak ditemukan", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void saveData() {
        PreferenceUtils.saveIdAkun(dataAkun.getIdAkun(), getApplicationContext());
        PreferenceUtils.saveToken(token, getApplicationContext());
        PreferenceUtils.saveIdProfil(dataProfil.getIdProfile(), getApplicationContext());
        PreferenceUtils.saveIdAlamat(dataProfil.getIdAlamat(), getApplicationContext());
        PreferenceUtils.savePassword(dataAkun.getPassword(), getApplicationContext());
        PreferenceUtils.saveUsername(dataAkun.getUserName(), getApplicationContext());
        PreferenceUtils.saveNamaDepan(dataProfil.getNamaDepan(), getApplicationContext());
        PreferenceUtils.saveNamaBelakang(dataProfil.getNamaBelakang(), getApplicationContext());
        PreferenceUtils.saveIDGoogle(dataAkun.getIdGoogle(), getApplicationContext());
        goToHome();
    }

    public void goToHome() {
        Intent a = new Intent(Register.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
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
            handler.postDelayed(runnable, 1 * 1000);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoogle(data);
                }
            }).start();

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda mau menutup aplikasi")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Register.super.onBackPressed();
                        finish();
                        finishAffinity();
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