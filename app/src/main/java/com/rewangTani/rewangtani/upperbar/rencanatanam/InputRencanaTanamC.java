package com.rewangTani.rewangtani.upperbar.rencanatanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamCBinding;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

public class InputRencanaTanamC extends AppCompatActivity {

    UpperbarRtInputRencanaTanamCBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_c);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnOngkosBuruh.getLeft(), binding.btnOngkosBuruh.getTop());
            }
        });

        binding.mesinBajak.addTextChangedListener(new NumberTextWatcher(binding.mesinBajak));
        binding.mesinTanam.addTextChangedListener(new NumberTextWatcher(binding.mesinTanam));
        binding.mesinPanen.addTextChangedListener(new NumberTextWatcher(binding.mesinPanen));
        binding.mesinPompa.addTextChangedListener(new NumberTextWatcher(binding.mesinPompa));
        binding.bbmMesinPompa.addTextChangedListener(new NumberTextWatcher(binding.bbmMesinPompa));

        binding.btnSelanjutnya.setOnClickListener(v->{

            //  check trus ke D !
            moveToD();

        });
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