package com.rewangTani.rewangtani.bottombar.profilakun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditPassword extends AppCompatActivity {

    EditText et_sandi_lama, et_sandi_baru, et_ulangi_sandi_baru;
    ImageButton btn_simpan, btn_batal, btn_password_lama, btn_password_baru, btn_password_baru_ulangi;
    String pw_lama;
    int check, checkPanjangPasword;
    TextView txtload, panjangpassword;
    int pw = 0;
    int pw2 = 0;
    int pw3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pa_editpassword);

        et_sandi_lama = findViewById(R.id.et_sandi_lama);
        et_sandi_baru = findViewById(R.id.et_sandi_baru);
        et_ulangi_sandi_baru = findViewById(R.id.et_ulangi_sandi_baru);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);
        txtload = findViewById(R.id.textloading);
        panjangpassword = findViewById(R.id.panjangpassword);
        btn_password_lama = findViewById(R.id.btn_password_lama);
        btn_password_baru = findViewById(R.id.btn_password_baru);
        btn_password_baru_ulangi = findViewById(R.id.btn_password_baru_ulangi);


        et_sandi_baru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = et_sandi_baru.length();
                if (length<8){
                    checkPanjangPasword = 0;
                    panjangpassword.setText("Password kurang dari 8 karakter");
                } else {
                    checkPanjangPasword = 10;
                    panjangpassword.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        btn_password_lama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw==0){
                    pw = 1;
                    btn_password_lama.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    et_sandi_lama.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw==1){
                    pw = 0;
                    btn_password_lama.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    et_sandi_lama.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btn_password_baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw2==0){
                    pw2 = 1;
                    btn_password_baru.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    et_sandi_baru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw2==1){
                    pw2 = 0;
                    btn_password_baru.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    et_sandi_baru.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btn_password_baru_ulangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw3==0){
                    pw3 = 1;
                    btn_password_baru_ulangi.setImageDrawable(getDrawable(R.drawable.icon_password_off));
                    et_ulangi_sandi_baru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (pw3==1){
                    pw3 = 0;
                    btn_password_baru_ulangi.setImageDrawable(getDrawable(R.drawable.icon_password_on));
                    et_ulangi_sandi_baru.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_sandi_lama.getText().toString().equalsIgnoreCase(PreferenceUtils.getPassword(getApplicationContext()))){
                    check = 10;
                } else {
                    Toast.makeText(EditPassword.this, "Sandi lama salah", Toast.LENGTH_SHORT).show();
                    check = 0;
                }

                if(check==10){
                    if (et_sandi_baru.getText().toString().equalsIgnoreCase(et_ulangi_sandi_baru.getText().toString())) {
                        if(checkPanjangPasword==10){
                            boolean hitLetter = false;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                hitLetter = et_sandi_baru.getText().toString().codePoints().anyMatch( i -> Character.isLetter( i ) );
                            }
                            boolean hitDigit = false;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                hitDigit = et_sandi_baru.getText().toString().codePoints().anyMatch( i -> Character.isDigit( i ) );
                            }
                            boolean containsBoth = ( hitLetter && hitDigit ) ;
                            if (containsBoth) {
                                simpanEdit();
                            } else {
                                Toast.makeText(EditPassword.this, "Password harus kombinasi angka dan huruf", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditPassword.this, "Password kurang dari 8 karakter", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(EditPassword.this, "Sandi baru tidak sama", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public  void simpanEdit(){
        if (PreferenceUtils.getIDGoogle(getApplicationContext()).equalsIgnoreCase("")){
            pw_lama = PreferenceUtils.getPassword(getApplicationContext());
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
                    updateDataAkun();
                }
            }).start();
        } else {
            //berarti login w google, jadiin create akun, pw lamanya gamasalah apapun itu
        }
    }

    public void updateDataAkun(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("password", et_sandi_baru.getText().toString() );
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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(EditPassword.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void saveData(){
        PreferenceUtils.savePassword(et_sandi_baru.getText().toString(), getApplicationContext());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(EditPassword.this, "Berhasil ubah password", Toast.LENGTH_LONG).show();
                goToBerandaProfil();
            }
        });
    }

    public void goToBerandaProfil(){
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
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

}