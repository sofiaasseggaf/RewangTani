package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamEBinding;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

public class InputRencanaTanamE extends AppCompatActivity {

    UpperbarRtInputRencanaTanamEBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_e);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnHargaBibit.getLeft(), binding.btnHargaBibit.getTop());
            }
        });

        binding.pupukKimiaLokal.addTextChangedListener(new NumberTextWatcher(binding.pupukOrganik));
        //txt_pupuk_kimia_phonska.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_phonska));
        //txt_pupuk_kimia_urea.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_urea));
        //txt_pupuk_kimia_fosfat.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_fosfat));
        binding.pupukOrganik.addTextChangedListener(new NumberTextWatcher(binding.pupukOrganik));

        binding.btnSimpan.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Simpan Rencana Tanam ?")
                    .setCancelable(false)
                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            //  check semua trus save dan ke RAB !

                            moveToRAB();

                        }
                    })

                    .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog =builder.create();
            alertDialog.show();

        });

    }

    public void moveToRAB() {
        Intent a = new Intent(InputRencanaTanamE.this, DetailRencanaTanamRAB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void moveToD() {
        Intent a = new Intent(InputRencanaTanamE.this, InputRencanaTanamD.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToD();
    }

}