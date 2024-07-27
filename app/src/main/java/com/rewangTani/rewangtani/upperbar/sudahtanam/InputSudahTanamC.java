package com.rewangTani.rewangtani.upperbar.sudahtanam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamCBinding;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamB;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamC;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamD;

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

        binding.btnSelanjutnya.setOnClickListener(v->{
            moveToD();
        });

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