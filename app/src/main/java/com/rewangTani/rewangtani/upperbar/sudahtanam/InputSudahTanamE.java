package com.rewangTani.rewangtani.upperbar.sudahtanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamEBinding;

public class InputSudahTanamE extends AppCompatActivity {

    UpperbarStInputSudahTanamEBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_e);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnHargaBibit.getLeft(), binding.btnHargaBibit.getTop());
            }
        });

        binding.btnSelanjutnya.setOnClickListener(v->{
            moveToF();
        });

    }

    public void moveToD(){
        Intent a = new Intent(InputSudahTanamE.this, InputSudahTanamD.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void moveToF(){
        Intent a = new Intent(InputSudahTanamE.this, InputSudahTanamF.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToD();
    }
}