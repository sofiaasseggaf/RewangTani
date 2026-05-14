package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListRencanaTanam;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.databinding.UpperbarRtListRencanaTanamBinding;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.DialogUtil;

import java.util.List;

public class ListRencanaTanam extends AppCompatActivity
{

    UpperbarRtListRencanaTanamBinding binding;
    private HomeViewModel viewModel;
    AdapterListRencanaTanam itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_list_rencana_tanam);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initEvent();
        initObserver();
    }

    private void initEvent()
    {

        binding.btnTambah.setOnClickListener( v -> goToInputRencanaTanam() );
        binding.btnPanen.setOnClickListener( v -> goToPanen() );
        binding.btnRab.setOnClickListener( v -> goToRAB() );
        binding.btnSt.setOnClickListener( v -> {
            DialogUtil.showCustomAlertDialogTwoCustomTextButtons(
                    ListRencanaTanam.this,
                    getString(R.string.confirm_page_st),
                    okButton -> goToST(),
                    cancelButton -> goToKP(),
                    getString(R.string.page_pt),
                    getString(R.string.page_kp));
        });
    }

    private void initObserver()
    {

        viewModel.getAllRencanaTanamById().observe(this, items ->
        {
            if (items == null || items.isEmpty()) {
                binding.scrollView.setVisibility(View.GONE);
                binding.frameDataNotFound.setVisibility(View.VISIBLE);
            } else {
                binding.frameDataNotFound.setVisibility(View.GONE);
                binding.scrollView.setVisibility(View.VISIBLE);
                setData(items);
            }
        });
    }

    public void setData(List<DatumRencanaTanam> items)
    {
        itemList = new AdapterListRencanaTanam(items);
        binding.rvRencanaTanam.setLayoutManager(new LinearLayoutManager(ListRencanaTanam.this));
        binding.rvRencanaTanam.setAdapter(itemList);
        binding.rvRencanaTanam.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvRencanaTanam,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListRencanaTanam.this, DetailRencanaTanamNonEditable.class);
                        a.putExtra("idRencanaTanam", items.get(position).getIdRencanaTanam());
                        startActivity(a);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }
                }));
    }

    public void goToInputRencanaTanam(){
        Intent a = new Intent(ListRencanaTanam.this, InputRencanaTanamA.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListRencanaTanam.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToST(){
        Intent a = new Intent(ListRencanaTanam.this, ListSudahTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToKP(){
        Intent a = new Intent(ListRencanaTanam.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToPanen(){
        Intent a = new Intent(ListRencanaTanam.this, ListPanen.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(ListRencanaTanam.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}