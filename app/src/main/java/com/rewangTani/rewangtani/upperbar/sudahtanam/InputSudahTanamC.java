package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamCBinding;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

public class InputSudahTanamC extends AppCompatActivity {

    UpperbarStInputSudahTanamCBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_c);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnOngkosBuruh.getLeft(), binding.btnOngkosBuruh.getTop());
            }
        });

        if (ListSudahTanam.getInstance().getDatumSudahTanam().isWithPompa()) {
            binding.viewMesinPompa.setVisibility(View.VISIBLE);
        } else {
            binding.viewMesinPompa.setVisibility(View.GONE);
        }

        binding.mesinBajak.addTextChangedListener(new NumberTextWatcher(binding.mesinBajak));
        binding.mesinTanam.addTextChangedListener(new NumberTextWatcher(binding.mesinTanam));
        binding.mesinPanen.addTextChangedListener(new NumberTextWatcher(binding.mesinPanen));
        binding.mesinPompa.addTextChangedListener(new NumberTextWatcher(binding.mesinPompa));
        binding.bbmMesinPompa.addTextChangedListener(new NumberTextWatcher(binding.bbmMesinPompa));

        binding.btnSelanjutnya.setOnClickListener(v->{
            saveLocalData();
        });

    }

    private void saveLocalData() {
        boolean isWithPompa;
        if(ListSudahTanam.getInstance().getDatumSudahTanam().isWithPompa()){
            isWithPompa = true;
        } else {
            isWithPompa = false;
        }

        DatumSudahTanam datumSudahTanam = new DatumSudahTanam( "", "", "", "", "", "", "", "",
                "", "", "", binding.mesinBajak.getText().toString().replaceAll("[^0-9]",""), binding.mesinTanam.getText().toString().replaceAll("[^0-9]",""), binding.mesinPanen.getText().toString().replaceAll("[^0-9]",""),
                binding.mesinPompa.getText().toString().replaceAll("[^0-9]",""),binding.bbmMesinPompa.getText().toString().replaceAll("[^0-9]",""), "", "",
                "", "","", "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToD();
    }

    public void moveToB(){
        Intent a = new Intent(InputSudahTanamC.this, InputSudahTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void moveToD(){
        Intent a = new Intent(InputSudahTanamC.this, InputSudahTanamD.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToB();
    }
}