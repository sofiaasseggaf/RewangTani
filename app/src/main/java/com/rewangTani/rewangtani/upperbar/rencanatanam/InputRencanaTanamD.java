package com.rewangTani.rewangtani.upperbar.rencanatanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamDBinding;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

public class InputRencanaTanamD extends AppCompatActivity {

    UpperbarRtInputRencanaTanamDBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_d);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnSewaMesin.getLeft(), binding.btnSewaMesin.getTop());
            }
        });

        binding.bibitLokal.addTextChangedListener(new NumberTextWatcher(binding.bibitLokal));
        binding.bibitSubsidi.addTextChangedListener(new NumberTextWatcher(binding.bibitSubsidi));

        binding.btnSelanjutnya.setOnClickListener(v -> {

            //  check trus ke E !
            moveToE();

        });
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