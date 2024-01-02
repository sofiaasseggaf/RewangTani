package com.rewangTani.rewangtani.bottombar.profilakun.pesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.databinding.BottombarPesananSelesaiBinding;

public class PesananSelesai extends AppCompatActivity {

    BottombarPesananSelesaiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pesanan_selesai);

        binding.btnDiproses.setOnClickListener(v->{
            goToDiproses();
        });

        binding.btnDibatalkan.setOnClickListener(v->{
            goToDibatalkan();
        });

    }

    public void goToDiproses(){
        Intent a = new Intent(PesananSelesai.this, PesananDiproses.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToDibatalkan(){
        Intent a = new Intent(PesananSelesai.this, PesananDibatalkan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(PesananSelesai.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToProfilAkun();
    }
}