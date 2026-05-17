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
import com.rewangTani.rewangtani.databinding.BottombarKeranjangBinding;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.FakePaymentActivity;
import com.rewangTani.rewangtani.utility.TextUtil;

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

        adapter = new AdapterKeranjang(viewModel);
        binding.rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKeranjang.setAdapter(adapter);

        initEvent();
        initObserver();
    }

    private void initEvent()
    {

        binding.btnMetodePengiriman.setOnClickListener( v -> {
            String total = binding.txtTotal.getText().toString();
            if ( !total.equalsIgnoreCase("Rp 0") )
            {

                for (String id : viewModel.getCheckedItemId().getValue())
                {
                    viewModel.confirmDelete(id);
                }

                Intent intent = new Intent(this, FakePaymentActivity.class);
                intent.putExtra("Harga", binding.txtTotal.getText().toString());
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Tidak ada transaksi untuk di proses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initObserver()
    {

        viewModel.loadProducts();

        viewModel.isLoading.observe(this, isLoading -> {
            binding.viewLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.errorMessage.observe(this, msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.cartUI.observe(this, list -> {
            adapter.setData(list);
        });

        viewModel.getTotalPrice().observe(this, total -> {
            binding.txtTotal.setText("Rp " + TextUtil.checkDesimal(String.valueOf(total)));
            binding.txtTotalHarga.setText("Rp " + TextUtil.checkDesimal(String.valueOf(total)));
        });

        viewModel.showDeleteDialogTrigger.observe(this, productId -> {
            if (productId != null) {
                DialogUtil.showCustomAlertDialog(
                        ActivityKeranjang.this,
                        getString(R.string.confirm_delete_item),
                        okButton -> { viewModel.confirmDelete(productId); }
                );

                viewModel.showDeleteDialogTrigger.setValue(null);
            }
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