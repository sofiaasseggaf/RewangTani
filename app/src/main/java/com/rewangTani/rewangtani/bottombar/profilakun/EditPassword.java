package com.rewangTani.rewangtani.bottombar.profilakun;

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
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPaEditpasswordBinding;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditPassword extends AppCompatActivity {

    BottombarPaEditpasswordBinding binding;
    String pw_lama;
    int check, checkPanjangPasword;
    int pw = 0;
    int pw2 = 0;
    int pw3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_editpassword);

        binding.inputPasswordBaru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = binding.inputPasswordBaru.length();
                if (length < 8) {
                    checkPanjangPasword = 0;
                    binding.textErrorInputPassword.setVisibility(View.VISIBLE);
                    binding.textErrorInputPassword.setText("Password kurang dari 8 karakter");
                } else {
                    checkPanjangPasword = 10;
                    binding.textErrorInputPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.btnHideViewPasswordLama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw == 0) {
                    pw = 1;
                    binding.btnHideViewPasswordLama.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    binding.inputPasswordLama.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw == 1) {
                    pw = 0;
                    binding.btnHideViewPasswordLama.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    binding.inputPasswordLama.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        binding.btnHideViewPasswordBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw2 == 0) {
                    pw2 = 1;
                    binding.btnHideViewPasswordBaru.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    binding.inputPasswordBaru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw2 == 1) {
                    pw2 = 0;
                    binding.btnHideViewPasswordBaru.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    binding.inputPasswordBaru.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        binding.btnHideViewPasswordBaruKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw3 == 0) {
                    pw3 = 1;
                    binding.btnHideViewPasswordBaruKonfirmasi.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    binding.inputPasswordBaruKonfirmasi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw3 == 1) {
                    pw3 = 0;
                    binding.btnHideViewPasswordBaruKonfirmasi.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    binding.inputPasswordBaruKonfirmasi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        binding.btnGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputPasswordLama.getText().toString().equalsIgnoreCase(PreferenceUtils.getPassword(getApplicationContext()))) {
                    check = 10;
                } else {
                    Toast.makeText(EditPassword.this, "Sandi lama salah", Toast.LENGTH_SHORT).show();
                    check = 0;
                }

                if (check == 10) {
                    if (binding.inputPasswordBaru.getText().toString().equalsIgnoreCase(binding.inputPasswordBaruKonfirmasi.getText().toString())) {
                        if (checkPanjangPasword == 10) {
                            boolean hitLetter = false;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                hitLetter = binding.inputPasswordBaru.getText().toString().codePoints().anyMatch(i -> Character.isLetter(i));
                            }
                            boolean hitDigit = false;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                hitDigit = binding.inputPasswordBaru.getText().toString().codePoints().anyMatch(i -> Character.isDigit(i));
                            }
                            boolean containsBoth = (hitLetter && hitDigit);
                            if (containsBoth) {
                                simpanEdit();
                            } else {
                                Toast.makeText(EditPassword.this, "Password harus kombinasi angka dan huruf", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditPassword.this, "Password kurang dari 8 karakter", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditPassword.this, "Sandi baru tidak sama", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void simpanEdit() {
        if (PreferenceUtils.getIDGoogle(getApplicationContext()).equalsIgnoreCase("")) {
            pw_lama = PreferenceUtils.getPassword(getApplicationContext());
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
                    updateDataAkun();
                }
            }).start();
        } else {
            //berarti login w google, jadiin create akun, pw lamanya gamasalah apapun itu
        }
    }

    public void updateDataAkun() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("password", binding.inputPasswordBaru.getText().toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        saveData();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditPassword.this, "Gagal ubah password", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(EditPassword.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void saveData() {
        PreferenceUtils.savePassword(binding.inputPasswordBaru.getText().toString(), getApplicationContext());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(EditPassword.this, "Berhasil ubah password", Toast.LENGTH_LONG).show();
                goToBerandaProfil();
            }
        });
    }

    public void goToBerandaProfil() {
        Intent a = new Intent(EditPassword.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal ganti password ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToBerandaProfil();
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