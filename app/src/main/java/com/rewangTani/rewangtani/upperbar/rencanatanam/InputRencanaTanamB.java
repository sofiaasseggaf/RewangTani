package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamBBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.DatumOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ResponseRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class InputRencanaTanamB extends AppCompatActivity {

    UpperbarRtInputRencanaTanamBBinding binding;
    ResponseRencanaTanam modelRencanaTanam;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    DatumOutputRencanaTanam dataOutputRT;
    int pompa, hargaBBM;
    double total, hasil, pendapatan, mdpl;
    String numberOnly, txt_pompa, txt_pompabbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_b);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnNama.getLeft(), binding.btnNama.getTop());
            }
        });

        binding.buruhTanam.addTextChangedListener(new NumberTextWatcher(binding.buruhTanam));
        binding.buruhBajak.addTextChangedListener(new NumberTextWatcher(binding.buruhBajak));
        binding.buruhSemprot.addTextChangedListener(new NumberTextWatcher(binding.buruhSemprot));
        binding.buruhMenyiangiRumput.addTextChangedListener(new NumberTextWatcher(binding.buruhMenyiangiRumput));
        binding.buruhGalengan.addTextChangedListener(new NumberTextWatcher(binding.buruhGalengan));
        binding.buruhPupuk.addTextChangedListener(new NumberTextWatcher(binding.buruhPupuk));
        binding.buruhPanen.addTextChangedListener(new NumberTextWatcher(binding.buruhPanen));


        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocalData();

                /*
                if(pompa==1){
                    if(!txt_buruh_tanam.getText().toString().equalsIgnoreCase("")&&!txt_buruh_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_semprot.getText().toString().equalsIgnoreCase("")&&!txt_buruh_menyiangi.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_galengan.getText().toString().equalsIgnoreCase("")&&!txt_buruh_pupuk.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_panen.getText().toString().equalsIgnoreCase("")&&!txt_mesin_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_tanam.getText().toString().equalsIgnoreCase("")&&!txt_mesin_panen.getText().toString().equalsIgnoreCase("")&&
                            !txt_bibit_local.getText().toString().equalsIgnoreCase("")&&!txt_bibit_subsidi.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_phonska.getText().toString().equalsIgnoreCase("")&& !txt_pupuk_kimia_urea.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_fosfat.getText().toString().equalsIgnoreCase("")&& !txt_pupuk_organik.getText().toString().equalsIgnoreCase("")) {
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        final Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            int count = 0;
                            @Override
                            public void run() {
                                count++;
                                if (count == 1) {
                                    txtload.setText("Tunggu sebentar ya ."); }
                                else if (count == 2) {
                                    txtload.setText("Tunggu sebentar ya . ."); }
                                else if (count == 3) {
                                    txtload.setText("Tunggu sebentar ya . . ."); }
                                if (count == 3)
                                    count = 0;
                                handler.postDelayed(this, 1500);
                            }
                        };
                        handler.postDelayed(runnable, 1 * 1000);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                hitungTanpaPompa();
                            }
                        }).start();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Isi semua field terlebih dahulu", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else if(pompa==2){
                    if(!txt_buruh_tanam.getText().toString().equalsIgnoreCase("")&&!txt_buruh_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_semprot.getText().toString().equalsIgnoreCase("")&&!txt_buruh_menyiangi.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_galengan.getText().toString().equalsIgnoreCase("")&&!txt_buruh_pupuk.getText().toString().equalsIgnoreCase("")&&
                            !txt_buruh_panen.getText().toString().equalsIgnoreCase("")&&!txt_mesin_bajak.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_tanam.getText().toString().equalsIgnoreCase("")&&!txt_mesin_panen.getText().toString().equalsIgnoreCase("")&&
                            !txt_mesin_pompa.getText().toString().equalsIgnoreCase("")&&!txt_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")&&
                            !txt_bibit_local.getText().toString().equalsIgnoreCase("")&&!txt_bibit_subsidi.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_phonska.getText().toString().equalsIgnoreCase("")&&!txt_pupuk_kimia_urea.getText().toString().equalsIgnoreCase("")&&
                            !txt_pupuk_kimia_fosfat.getText().toString().equalsIgnoreCase("")&&!txt_pupuk_organik.getText().toString().equalsIgnoreCase("")&&
                            !et_nama_rt.getText().toString().equalsIgnoreCase("")&&!txt_durasi_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")){
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        final Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            int count = 0;
                            @Override
                            public void run() {
                                count++;
                                if (count == 1) {
                                    txtload.setText("Tunggu sebentar ya ."); }
                                else if (count == 2) {
                                    txtload.setText("Tunggu sebentar ya . ."); }
                                else if (count == 3) {
                                    txtload.setText("Tunggu sebentar ya . . ."); }
                                if (count == 3)
                                    count = 0;
                                handler.postDelayed(this, 1500);
                            }
                        };
                        handler.postDelayed(runnable, 1 * 1000);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                hitung();
                            }
                        }).start();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamA.this, "Isi semua field terlebih dahulu", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(InputRencanaTanamA.this, "Pilih profil lahan terlebih dahulu !", Toast.LENGTH_LONG).show();
                        }
                    });
                }

*/
            }
        });
    }

    private void saveLocalData() {
        boolean isWithPompa;
        String luasLahan = ListRencanaTanam.getInstance().getDatumRencanaTanam().getLuasLahan();
        String potensiHasilVarietas = ListRencanaTanam.getInstance().getDatumRencanaTanam().getPotensiHasilVarietas();
        if(ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa()){
            isWithPompa = true;
        } else {
            isWithPompa = false;
        }

        DatumRencanaTanam datumRencanaTanam = new DatumRencanaTanam("", "", "", "", binding.buruhTanam.getText().toString().replaceAll("[^0-9]", ""),
                binding.buruhBajak.getText().toString().replaceAll("[^0-9]", ""), binding.buruhSemprot.getText().toString().replaceAll("[^0-9]", ""), binding.buruhMenyiangiRumput.getText().toString().replaceAll("[^0-9]", ""),
                binding.buruhGalengan.getText().toString().replaceAll("[^0-9]", ""), binding.buruhPupuk.getText().toString().replaceAll("[^0-9]", ""), binding.buruhPanen.getText().toString().replaceAll("[^0-9]", ""),
                "", "", "", "","", "", "", "", "","", "", "", isWithPompa, luasLahan, potensiHasilVarietas);
        ListRencanaTanam.getInstance().setDetailRencanaTanam(getApplicationContext(), datumRencanaTanam);
        moveToC();
    }

    public void moveToA(){
        Intent a = new Intent(InputRencanaTanamB.this, InputRencanaTanamA.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void moveToC(){
        Intent a = new Intent(InputRencanaTanamB.this, InputRencanaTanamC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(InputRencanaTanamB.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToA();
    }

}