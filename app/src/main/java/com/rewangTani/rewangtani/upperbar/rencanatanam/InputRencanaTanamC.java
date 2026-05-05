package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamCBinding;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.TextUtil;

public class InputRencanaTanamC extends AppCompatActivity
{

    UpperbarRtInputRencanaTanamCBinding binding;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_c);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnOngkosBuruh.getLeft(), binding.btnOngkosBuruh.getTop());
            }
        });

        viewModel.getDraftRencanaTanamLiveData().observe(this, draft ->
        {
            if (draft == null) return;

            binding.mesinBajak.setText(draft.idSewaMesinBajak);
            binding.mesinTanam.setText(draft.idSewaMesinTanam);
            binding.mesinPanen.setText(draft.idSewaMesinPanen);
            binding.mesinPompa.setText(draft.idSewamesinPompa);
            binding.bbmMesinPompa.setText(draft.idSewamesinPompaBbm);

            if (draft.withPompa) {
                binding.viewMesinPompa.setVisibility(View.VISIBLE);
            } else {
                binding.viewMesinPompa.setVisibility(View.GONE);
            }
        });

//        if (ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa()) {
//            binding.viewMesinPompa.setVisibility(View.VISIBLE);
//        } else {
//            binding.viewMesinPompa.setVisibility(View.GONE);
//        }

        binding.mesinBajak.addTextChangedListener(new NumberTextWatcher(binding.mesinBajak));
        binding.mesinTanam.addTextChangedListener(new NumberTextWatcher(binding.mesinTanam));
        binding.mesinPanen.addTextChangedListener(new NumberTextWatcher(binding.mesinPanen));
        binding.mesinPompa.addTextChangedListener(new NumberTextWatcher(binding.mesinPompa));
        binding.bbmMesinPompa.addTextChangedListener(new NumberTextWatcher(binding.bbmMesinPompa));

        binding.btnSelanjutnya.setOnClickListener(v ->
        {
            saveLocalData();
//            if (ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa()) {
//                if (!binding.mesinBajak.getText().toString().equalsIgnoreCase("") && !binding.mesinTanam.getText().toString().equalsIgnoreCase("") &&
//                        !binding.mesinPanen.getText().toString().equalsIgnoreCase("") && !binding.mesinPompa.getText().toString().equalsIgnoreCase("") &&
//                        !binding.bbmMesinPompa.getText().toString().equalsIgnoreCase("")) {
//                    saveLocalData();
//                }
//            } else {
//                if (!binding.mesinBajak.getText().toString().equalsIgnoreCase("") && !binding.mesinTanam.getText().toString().equalsIgnoreCase("") &&
//                        !binding.mesinPanen.getText().toString().equalsIgnoreCase("")) {
//                    saveLocalData();
//                }
//            }

        });
    }

    private void saveLocalData()
    {
//        boolean isWithPompa;
//        String luasLahan = ListRencanaTanam.getInstance().getDatumRencanaTanam().getLuasLahan();
//        String potensiHasilVarietas = ListRencanaTanam.getInstance().getDatumRencanaTanam().getPotensiHasilVarietas();
//        isWithPompa = ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa();

        viewModel.updateDraftRencanaTanam(draft -> {
            if (draft.withPompa)
            {
                draft.idSewamesinPompa = TextUtil.cleanNumber(binding.mesinPompa);
                draft.idSewamesinPompaBbm = TextUtil.cleanNumber(binding.bbmMesinPompa);
            }
            draft.idSewaMesinBajak = TextUtil.cleanNumber(binding.mesinBajak);
            draft.idSewaMesinTanam = TextUtil.cleanNumber(binding.mesinTanam);
            draft.idSewaMesinPanen = TextUtil.cleanNumber(binding.mesinPanen);
        });

//        DatumRencanaTanam datumRencanaTanam = new DatumRencanaTanam("", "", "", "", "", "", "", "",
//                "", "", "", binding.mesinBajak.getText().toString().replaceAll("[^0-9]", ""), binding.mesinTanam.getText().toString().replaceAll("[^0-9]", ""),
//                binding.mesinPanen.getText().toString().replaceAll("[^0-9]", ""), binding.mesinPompa.getText().toString().replaceAll("[^0-9]", ""),binding.bbmMesinPompa.getText().toString().replaceAll("[^0-9]", ""),
//                "", "", "", "","", "", "", isWithPompa, luasLahan, potensiHasilVarietas);
//        ListRencanaTanam.getInstance().setDetailRencanaTanam(getApplicationContext(), datumRencanaTanam);

        moveToD();
    }

    public void moveToD(){
        Intent a = new Intent(InputRencanaTanamC.this, InputRencanaTanamD.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void moveToB(){
        Intent a = new Intent(InputRencanaTanamC.this, InputRencanaTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToB();
    }

}