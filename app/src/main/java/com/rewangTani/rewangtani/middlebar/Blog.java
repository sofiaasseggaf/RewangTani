package com.rewangTani.rewangtani.middlebar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.pesan.InboxPesan;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.MiddlebarBlogBinding;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;

public class Blog extends AppCompatActivity {

    MiddlebarBlogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_blog);

        binding.btnWarungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
            }
        });

        binding.btnLahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        binding.btnPesan.setOnClickListener(v->{
            goToPesan();
        });

        binding.btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

    }

    public void goToWarungku(){
        Intent a = new Intent(Blog.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPesan(){
        Intent a = new Intent(Blog.this, InboxPesan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(Blog.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(Blog.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }
}