package com.rewangTani.rewangtani.bottombar.profilakun.pesanan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.databinding.BottombarPesananDiprosesBinding;

public class PesananDiproses extends AppCompatActivity {

    BottombarPesananDiprosesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesanan_diproses);

        binding.btnSelesai.setOnClickListener(v->{
            goToSelesai();
        });

        binding.btnDibatalkan.setOnClickListener(v->{
            goToDibatalkan();
        });
    }

    public void goToSelesai(){
        Intent a = new Intent(PesananDiproses.this, PesananSelesai.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToDibatalkan(){
        Intent a = new Intent(PesananDiproses.this, PesananDibatalkan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(PesananDiproses.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToProfilAkun();
    }
}