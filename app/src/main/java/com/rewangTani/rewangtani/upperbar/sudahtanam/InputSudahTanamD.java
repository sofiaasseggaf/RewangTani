package com.rewangTani.rewangtani.upperbar.sudahtanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamDBinding;

public class InputSudahTanamD extends AppCompatActivity {

    UpperbarStInputSudahTanamDBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_d);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnSewaMesin.getLeft(), binding.btnSewaMesin.getTop());
            }
        });

        binding.btnSelanjutnya.setOnClickListener(v->{
            moveToE();
        });

    }

    public void moveToC(){
        Intent a = new Intent(InputSudahTanamD.this, InputSudahTanamC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void moveToE(){
        Intent a = new Intent(InputSudahTanamD.this, InputSudahTanamE.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToC();
    }
}