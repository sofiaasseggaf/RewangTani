package com.rewangTani.rewangtani.ui.keranjang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterKeranjang;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.BottombarKeranjangBinding;

public class ActivityKeranjang extends AppCompatActivity
{

    private KeranjangViewModel viewModel;
    private AdapterKeranjang adapter;
    BottombarKeranjangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_keranjang);
        viewModel = new ViewModelProvider(this).get(KeranjangViewModel.class);

        initLayout();
        initEvent();
        observeViewModel();

        Toast.makeText(ActivityKeranjang.this, "Fitur sedang dalam perbaikan", Toast.LENGTH_SHORT).show();

    }

    private void initLayout()
    {

    }

    private void initEvent()
    {
        adapter = new AdapterKeranjang(viewModel);
        binding.rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKeranjang.setAdapter(adapter);
    }

    private void observeViewModel()
    {
        viewModel.loadProducts();

        viewModel.isLoading.observe(this, isLoading -> {
            binding.viewLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.errorMessage.observe(this, msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.cartItems.observe(this, list -> {
            adapter.setData(list);
        });

        viewModel.getTotalPrice().observe(this, total -> {
            binding.txtTotal.setText("Rp " + total);
            binding.txtTotalHarga.setText("Rp " + total);
        });


    }



//    public void addToCart(int productId) {
//        executor.execute(() -> {
//            if (cartDao.isExist(productId)) {
//                cartDao.increase(productId);
//            } else {
//                cartDao.insert(new CartEntity(productId, 1));
//            }
//        });
//    }

    public void goToBeranda(){
        Intent a = new Intent(ActivityKeranjang.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}