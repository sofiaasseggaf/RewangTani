package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamFBinding;

public class InputSudahTanamF extends AppCompatActivity {

    UpperbarStInputSudahTanamFBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_f);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnHargaPupuk.getLeft(), binding.btnHargaPupuk.getTop());
            }
        });

        binding.btnSimpan.setOnClickListener(v->{
            Toast.makeText(this, "Simpan !", Toast.LENGTH_SHORT).show();
            goToListST();
        });

    }

    public void moveToE(){
        Intent a = new Intent(InputSudahTanamF.this, InputSudahTanamE.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(InputSudahTanamF.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToE();
    }
}