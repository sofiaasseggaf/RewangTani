package com.rewangTani.rewangtani.bottombar.profilakun;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPaTentangBinding;

public class Tentang extends AppCompatActivity {

    BottombarPaTentangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_tentang);

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