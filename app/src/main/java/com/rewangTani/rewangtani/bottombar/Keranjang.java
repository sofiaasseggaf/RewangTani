package com.rewangTani.rewangtani.bottombar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.R;

public class Keranjang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_keranjang);
    }

    public void goToBeranda(){
        Intent a = new Intent(Keranjang.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}