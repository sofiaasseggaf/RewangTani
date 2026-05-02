package com.rewangTani.rewangtani.starter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.ui.login.ActivityLogin;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_splash_screen);

        if (PreferenceUtils.getIdAkun(this) == null || PreferenceUtils.getIdAkun(this).equalsIgnoreCase("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(SplashScreen.this, ActivityLogin.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            goToHome();
        }
    }


    public void goToHome(){
        Intent a = new Intent(SplashScreen.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showConfirmDialog(this, () -> {
            SplashScreen.super.onBackPressed();
            finish();
            finishAffinity();
        });
    }
}