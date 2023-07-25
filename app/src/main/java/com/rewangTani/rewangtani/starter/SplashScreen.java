package com.rewangTani.rewangtani.starter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

public class SplashScreen extends AppCompatActivity {

    ImageButton btn_masuk, btn_daftar;
    SplashScreen splashScreen;
    //private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_splash_screen);

        splashScreen = this;
        btn_masuk = findViewById(R.id.btn_masuk);
        btn_daftar = findViewById(R.id.btn_daftar);

        first();

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMasuk();
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDaftar();
            }
        });

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, Login.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }

    public void first(){
        if (PreferenceUtils.getIdAkun(this) == null || PreferenceUtils.getIdAkun(this).equalsIgnoreCase("")){
            //do nothing
        } else {

            goToHome();
        }
    }

    public void goToHome(){
        Intent a = new Intent(SplashScreen.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToDaftar(){
        Intent a = new Intent(SplashScreen.this, Register.class);
        startActivity(a);
        finish();
    }

    public void goToMasuk(){
        Intent a = new Intent(SplashScreen.this, Login.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda mau menutup aplikasi")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        SplashScreen.super.onBackPressed();
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
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }
}