package com.rewangTani.rewangtani.bottombar.profilakun.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.databinding.BottombarPesananDibatalkanBinding;

public class PesananDibatalkan extends AppCompatActivity {

    BottombarPesananDibatalkanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesanan_dibatalkan);

        binding.btnDiproses.setOnClickListener(v->{
            goToDiproses();
        });

        binding.btnSelesai.setOnClickListener(v->{
            goToSelesai();
        });

    }

    public void goToDiproses(){
        Intent a = new Intent(PesananDibatalkan.this, PesananDiproses.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToSelesai(){
        Intent a = new Intent(PesananDibatalkan.this, PesananSelesai.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(PesananDibatalkan.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToProfilAkun();
    }

}