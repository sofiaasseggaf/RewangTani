package com.rewangTani.rewangtani.upperbar.rab;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRabDetailRabABinding;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.ModelSudahTanam;
import com.rewangTani.rewangtani.utility.StringDateComparator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRancanganAnggaranBiayaA extends AppCompatActivity {

    UpperbarRabDetailRabABinding binding;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    String estBiaya;
    DecimalFormat formatter;
    String idRencanaTanam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rab_detail_rab_a);

        Intent intent = getIntent();
        idRencanaTanam = intent.getStringExtra("idRencanaTanam");

        getData();

        binding.btnBiayaAktual.setOnClickListener(view -> {
            if (dataRencanaTanam!=null && !estBiaya.equalsIgnoreCase("")) {
                goToBiayaAktual();
            } else {
                Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Belum Ada Proses Sudah Tanam !", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnOngkosBuruh.setOnClickListener(v -> {
            if (binding.viewOngkosBuruh.getVisibility() == View.GONE) {
                binding.btnOngkosBuruh.setBackground(getResources().getDrawable(R.drawable.triangle_upside_down_green));
                binding.viewOngkosBuruh.setVisibility(View.VISIBLE);
            } else {
                binding.btnOngkosBuruh.setBackground(getResources().getDrawable(R.drawable.triangle_point_to_right_green));
                binding.viewOngkosBuruh.setVisibility(View.GONE);
            }
        });

        binding.btnSewaMesin.setOnClickListener(v -> {
            if (binding.viewSewaMesin.getVisibility() == View.GONE) {
                binding.btnSewaMesin.setBackground(getResources().getDrawable(R.drawable.triangle_upside_down_green));
                binding.viewSewaMesin.setVisibility(View.VISIBLE);
            } else {
                binding.btnSewaMesin.setBackground(getResources().getDrawable(R.drawable.triangle_point_to_right_green));
                binding.viewSewaMesin.setVisibility(View.GONE);
            }
        });

        binding.btnHargaBibit.setOnClickListener(v -> {
            if (binding.viewHargaBibit.getVisibility() == View.GONE) {
                binding.btnHargaBibit.setBackground(getResources().getDrawable(R.drawable.triangle_upside_down_green));
                binding.viewHargaBibit.setVisibility(View.VISIBLE);
            } else {
                binding.btnHargaBibit.setBackground(getResources().getDrawable(R.drawable.triangle_point_to_right_green));
                binding.viewHargaBibit.setVisibility(View.GONE);
            }
        });

        binding.btnHargaPupuk.setOnClickListener(v -> {
            if (binding.viewHargaPupuk.getVisibility() == View.GONE) {
                binding.btnHargaPupuk.setBackground(getResources().getDrawable(R.drawable.triangle_upside_down_green));
                binding.viewHargaPupuk.setVisibility(View.VISIBLE);
            } else {
                binding.btnHargaPupuk.setBackground(getResources().getDrawable(R.drawable.triangle_point_to_right_green));
                binding.viewHargaPupuk.setVisibility(View.GONE);
            }
        });

        binding.btnHargaObat.setOnClickListener(v -> {
            if (binding.viewHargaObat.getVisibility() == View.GONE) {
                binding.btnHargaObat.setBackground(getResources().getDrawable(R.drawable.triangle_upside_down_green));
                binding.viewHargaObat.setVisibility(View.VISIBLE);
            } else {
                binding.btnHargaObat.setBackground(getResources().getDrawable(R.drawable.triangle_point_to_right_green));
                binding.viewHargaObat.setVisibility(View.GONE);
            }
        });

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

    }

    public void getData(){
        binding.viewLoading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getRencanaTanam();
            }
        }).start();
    }

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                            String idrt = modelRencanaTanam.getData().get(i).getIdRencanaTanam();
                            if (idRencanaTanam.equalsIgnoreCase(idrt)) {
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                                if (dataRencanaTanam!=null){
                                    getOutputRencanaTanam();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.viewLoading.setVisibility(View.GONE);
                                            Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Data rencana tanam tidak ditemukan", Toast.LENGTH_LONG).show();
                                            call.cancel();
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getOutputRencanaTanam(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataV = apiInterface.getDataOutputRT();
        dataV.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                        try {
                            if (modelOutputRencanaTanam.getData().get(i).getIdRencanaTanam().equalsIgnoreCase(dataRencanaTanam.getIdRencanaTanam()))
                            {
                                estBiaya = modelOutputRencanaTanam.getData().get(i).getEstBiayaProduksi();
                            }
                        } catch (Exception e){ }
                    }
                    if (!estBiaya.equalsIgnoreCase("")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                setDataRABonly();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Data Output Rencana Tanam Tidak Ditemukan !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private String checkDesimal(String a){
        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public void setDataRABonly(){
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        binding.buruhTanam.setText(a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        binding.buruhBajak.setText(b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        binding.buruhSemprot.setText(c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        binding.buruhMenyiangiRumput.setText(d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        binding.buruhGalengan.setText(e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        binding.buruhPupuk.setText(f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        binding.buruhPanen.setText(g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        binding.mesinBajak.setText(h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        binding.mesinPanen.setText(i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        binding.mesinTanam.setText(j);

//        if (dataRencanaTanam.getIdSewamesinPompa()!=null){
//            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
//            txt_mesin_pompa1.setText(k);
//        } else if(dataRencanaTanam.getIdSewamesinPompa().equalsIgnoreCase("0")){
//            txt_mesin_pompa1.setText("-");
//        } else {
//            txt_mesin_pompa1.setText("-");
//        }
//        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
//            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
//            txt_mesin_pompa_bbm1.setText(l);
//        } else if(dataRencanaTanam.getIdSewamesinPompaBbm().equalsIgnoreCase("0")) {
//            txt_mesin_pompa_bbm1.setText("-");
//        } else {
//            txt_mesin_pompa_bbm1.setText("-");
//        }

        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        binding.bibitLokal.setText(m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        binding.bibitSubsidi.setText(n);
        String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        binding.pupukKimiaLokal.setText(o);
//        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
//        txt_pupuk_kimia_phonska1.setText(p);
//        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
//        txt_pupuk_kimia_urea1.setText(q);
//        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
//        txt_pupuk_kimia_fosfat1.setText(r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        binding.pupukOrganik.setText(s);

        String tgl = dataRencanaTanam.getCreatedDate().substring(8,10);
        String bln = dataRencanaTanam.getCreatedDate().substring(5,7);
        String thn = dataRencanaTanam.getCreatedDate().substring(0,4);
        binding.tglEstimasiAwal.setText(tgl+"-"+bln+"-"+thn);
        binding.totalBiaya.setText("Rp " + checkDesimal(estBiaya));
    }

    private void goToBiayaAktual() {
        Intent a = new Intent(DetailRancanganAnggaranBiayaA.this, DetailRancanganAnggaranBiayaB.class);
        a.putExtra("idRencanaTanam", idRencanaTanam);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToListRAB(){
        Intent a = new Intent(DetailRancanganAnggaranBiayaA.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListRAB();
    }
}