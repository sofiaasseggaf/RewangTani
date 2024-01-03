package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterViewPagerTambahProfilLahan;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanBinding;

public class TambahProfilLahanActivity extends AppCompatActivity {

    BottombarPlTambahProfilLahanBinding binding;
    AdapterViewPagerTambahProfilLahan adapterViewPagerTambahProfilLahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan);

        adapterViewPagerTambahProfilLahan = new AdapterViewPagerTambahProfilLahan(getSupportFragmentManager());
        binding.viewPagerTambahLahan.setAdapter(adapterViewPagerTambahProfilLahan);
        binding.tabLayoutTambahLahan.setupWithViewPager(binding.viewPagerTambahLahan);

    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        binding.viewPagerTambahLahan.setCurrentItem(item, smoothScroll);
    }

    public void goToListProfilLahan() {
        Intent a = new Intent(TambahProfilLahanActivity.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Tambah Profil Lahan ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListProfilLahan();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}