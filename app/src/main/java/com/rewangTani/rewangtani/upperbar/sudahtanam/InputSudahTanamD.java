package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamDBinding;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

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

        binding.bibitLokal.addTextChangedListener(new NumberTextWatcher(binding.bibitLokal));
        binding.bibitSubsidi.addTextChangedListener(new NumberTextWatcher(binding.bibitSubsidi));

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
                "", "", "", "", "", "", "","", binding.bibitLokal.getText().toString().replaceAll("[^0-9]",""), binding.bibitSubsidi.getText().toString().replaceAll("[^0-9]",""),
                "", "","", "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToE();
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