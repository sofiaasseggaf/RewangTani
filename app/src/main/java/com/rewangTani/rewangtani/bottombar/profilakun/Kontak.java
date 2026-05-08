package com.rewangTani.rewangtani.bottombar.profilakun;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPaKontakBinding;
import com.rewangTani.rewangtani.utility.DialogUtil;

public class Kontak extends AppCompatActivity
{

    BottombarPaKontakBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_kontak);

        initEvent();
    }

    private void initEvent()
    {

        binding.btnWhatsapp.setOnClickListener( v -> {
            DialogUtil.showCustomAlertDialog(
                    Kontak.this,
                    "Hubungi Whatsapp ?",
                    okButton -> { // hubungi whatsap
                    }
            );
        });

        binding.btnGmail.setOnClickListener( v -> {
            DialogUtil.showCustomAlertDialog(
                    Kontak.this,
                    "Hubungi Gmail ?",
                    okButton -> { // hubungi gmail
                    }
            );
        });

        binding.btnTelepon.setOnClickListener( v -> {
            DialogUtil.showCustomAlertDialog(
                    Kontak.this,
                    "Hubungi Telepon ?",
                    okButton -> { // hubungi tlp
                    }
            );
        });
    }

    public void goToBerandaProfil()
    {
        Intent a = new Intent(Kontak.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed()
    {
        goToBerandaProfil();
    }
}