package com.rewangTani.rewangtani.adapter.adapterbottombar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rewangTani.rewangtani.bottombar.profilelahan.FragmentTambahProfilLahanA;
import com.rewangTani.rewangtani.bottombar.profilelahan.FragmentTambahProfilLahanB;
import com.rewangTani.rewangtani.bottombar.profilelahan.FragmentTambahProfilLahanC;
import com.rewangTani.rewangtani.bottombar.profilelahan.FragmentTambahProfilLahanD;

public class AdapterViewPagerTambahProfilLahan extends FragmentStatePagerAdapter {


    public AdapterViewPagerTambahProfilLahan(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTambahProfilLahanA();
            case 1:
                return new FragmentTambahProfilLahanB();
            case 2:
                return new FragmentTambahProfilLahanC();
            case 3:
                return new FragmentTambahProfilLahanD();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Nama";
            case 1:
                return "Lokasi";
            case 2:
                return "Ukuran";
            case 3:
                return "Status Pekerja";
            default:
                return null;
        }
    }

}
