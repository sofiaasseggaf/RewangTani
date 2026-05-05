package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamDBinding;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.utility.DraftRencanaTanamManager;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.TextUtil;

public class InputRencanaTanamD extends AppCompatActivity
{

    UpperbarRtInputRencanaTanamDBinding binding;
    private HomeViewModel viewModel;
    private DraftRencanaTanamManager draftRencanaTanamManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_d);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        draftRencanaTanamManager = viewModel.getDraftRencanaTanamManager();

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnSewaMesin.getLeft(), binding.btnSewaMesin.getTop());
            }
        });

        viewModel.getDraftRencanaTanamLiveData().observe(this, draft ->
        {
            if (draft == null) return;

            binding.bibitLokal.setText(draft.idBiayabibitLocalHet);
            binding.bibitSubsidi.setText(draft.idBiayabibitSubsidi);
        });

        binding.bibitLokal.addTextChangedListener(new NumberTextWatcher(binding.bibitLokal));
        binding.bibitSubsidi.addTextChangedListener(new NumberTextWatcher(binding.bibitSubsidi));

        binding.btnSelanjutnya.setOnClickListener(v -> {
            if (!binding.bibitLokal.getText().toString().equalsIgnoreCase("") && !binding.bibitSubsidi.getText().toString().equalsIgnoreCase("")) {
                saveLocalData();
            }
        });
    }

    private void saveLocalData()
    {
//        boolean isWithPompa;
//        String luasLahan = ListRencanaTanam.getInstance().getDatumRencanaTanam().getLuasLahan();
//        String potensiHasilVarietas = ListRencanaTanam.getInstance().getDatumRencanaTanam().getPotensiHasilVarietas();
//        isWithPompa = ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa();

        viewModel.updateDraftRencanaTanam(draft -> {
            draft.idBiayabibitLocalHet = TextUtil.cleanNumber(binding.bibitLokal);
            draft.idBiayabibitSubsidi = TextUtil.cleanNumber(binding.bibitSubsidi);
        });

//        DatumRencanaTanam datumRencanaTanam = new DatumRencanaTanam("", "", "", "", "", "", "", "",
//                "", "", "", "", "", "", "", "",
//                binding.bibitLokal.getText().toString().replaceAll("[^0-9]", ""), binding.bibitSubsidi.getText().toString().replaceAll("[^0-9]", ""),
//                "", "","", "", "", isWithPompa, luasLahan, potensiHasilVarietas);
//        ListRencanaTanam.getInstance().setDetailRencanaTanam(getApplicationContext(), datumRencanaTanam);

        moveToE();
    }

    public void moveToE() {
        Intent a = new Intent(InputRencanaTanamD.this, InputRencanaTanamE.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void moveToC() {
        Intent a = new Intent(InputRencanaTanamD.this, InputRencanaTanamC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToC();
    }

}