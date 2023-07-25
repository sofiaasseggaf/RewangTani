package com.rewangTani.rewangtani.bottombar.profilakun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rewangTani.rewangtani.R;

public class Tentang extends AppCompatActivity {

    ImageButton btn_kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pa_tentang);

        btn_kembali = findViewById(R.id.btn_kembali);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBerandaProfil();
            }
        });

    }

    public void goToBerandaProfil(){
        Intent a = new Intent(Tentang.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBerandaProfil();
    }
}