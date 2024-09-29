package com.rewangTani.rewangtani.bottombar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.starter.Login;

public class Keranjang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_keranjang);

        Toast.makeText(Keranjang.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();

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