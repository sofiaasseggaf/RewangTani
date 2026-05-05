package com.rewangTani.rewangtani.ui.profilelahan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListProfilLahan;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarPlListProfileLahanBinding;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.Utils;

import java.util.List;

public class ListProfileLahan extends AppCompatActivity
{

    BottombarPlListProfileLahanBinding binding;
    private HomeViewModel viewModel;
    AdapterListProfilLahan itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_list_profile_lahan);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initEvent();
        initObserver();
    }

    private void initEvent()
    {

        binding.btnTambah.setOnClickListener( v -> viewModel.cekKelengkapanProfile() );
        binding.btnPesan.setOnClickListener(v-> goToPesan() );
        binding.btnHome.setOnClickListener( v -> goToBeranda() );
        binding.btnAkun.setOnClickListener( v -> goToProfilAkun() );
        binding.btnWarungku.setOnClickListener( v -> goToWarungku() );
    }

    private void initObserver()
    {

        viewModel.profileLengkap.observe(this, isLengkap ->
        {
            Toast.makeText(this, "profile lengkap ? = " + isLengkap, Toast.LENGTH_SHORT).show();
            if (isLengkap) {
                goToTambahPL();
            } else {
                binding.frameDataNotFound.setVisibility(View.GONE);
                Utils.showCustomAlertDialogTwoCustomTextButtons(
                        ListProfileLahan.this,
                        getString(R.string.txt_dialog_msg_lengkapi_data_profile),
                        okButton -> goToEditProfil(),
                        cancelButton -> goToBeranda(),
                        getString(R.string.confirm_lengkapi_data_profile),
                        getString(R.string.confirm_back));
            }

        });

        viewModel.getAllProfileLahanById().observe(this, items ->
        {
            if (items == null || items.isEmpty()) {
                binding.viewLoading.setVisibility(View.GONE);
                binding.frameDataNotFound.setVisibility(View.VISIBLE);
            } else {
                binding.viewLoading.setVisibility(View.GONE);
                binding.scrollview.setVisibility(View.VISIBLE);
                setData(items);
            }
        });
    }

    public void setData(List<DatumProfilLahan> items)
    {
        itemList = new AdapterListProfilLahan(items);
        binding.rvProfilLahan.setLayoutManager(new LinearLayoutManager(ListProfileLahan.this));
        binding.rvProfilLahan.setAdapter(itemList);
        binding.rvProfilLahan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvProfilLahan,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListProfileLahan.this, DetailProfilLahan.class);
                        a.putExtra("idProfilLahan", items.get(position).getIdProfileTanah());
                        startActivity(a);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }
                }));
    }

    public void goToTambahPL(){
        Intent a = new Intent(ListProfileLahan.this, TambahProfilLahanA.class);
        startActivity(a);
        finish();
    }

    public void goToEditProfil(){
        Intent a = new Intent(ListProfileLahan.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListProfileLahan.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(ListProfileLahan.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPesan(){
        Intent a = new Intent(ListProfileLahan.this, Inbox.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(ListProfileLahan.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}