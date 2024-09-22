package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamEBinding;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

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

        binding.pupukKimiaLokal.addTextChangedListener(new NumberTextWatcher(binding.pupukKimiaLokal));
        //txt_pupuk_kimia_phonska.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_phonska));
        //txt_pupuk_kimia_urea.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_urea));
        //txt_pupuk_kimia_fosfat.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_fosfat));
        binding.pupukOrganik.addTextChangedListener(new NumberTextWatcher(binding.pupukOrganik));

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
                "", "", "", "", "", "", "","", "", "",
                binding.pupukKimiaLokal.getText().toString().replaceAll("[^0-9]",""), "","", "", binding.pupukOrganik.getText().toString().replaceAll("[^0-9]",""),
                "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToF();
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