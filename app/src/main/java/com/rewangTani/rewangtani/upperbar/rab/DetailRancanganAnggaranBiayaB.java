package com.rewangTani.rewangtani.upperbar.rab;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRabDetailRabBBinding;
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

public class DetailRancanganAnggaranBiayaB extends AppCompatActivity {

    UpperbarRabDetailRabBBinding binding;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelSudahTanam modelSudahTanam;
    List<DatumSudahTanam> listSudahTanam = new ArrayList<>();
    List<DatumSudahTanam> listSudahTanamSorted = new ArrayList<>();
    List<String> date = new ArrayList<>();
    DecimalFormat formatter;
    String idRencanaTanam;
    int ab, ac, ad, ae, af, ag, ah, ai, aj, ak, al, am, an, ao, ap, aq, ar, as, at, au;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rab_detail_rab_b);

        Intent intent = getIntent();
        idRencanaTanam = intent.getStringExtra("idRencanaTanam");

        getData();

        binding.btnRAB.setOnClickListener(view -> {
            goToRAB();
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

    public void getData() {
        binding.viewLoading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . .");
                } else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . .");
                }
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
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                            String idrt = modelRencanaTanam.getData().get(i).getIdRencanaTanam();
                            if (idRencanaTanam.equalsIgnoreCase(idrt)) {
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                                if (dataRencanaTanam != null) {
                                    getSudahTanam();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.viewLoading.setVisibility(View.GONE);
                                            Toast.makeText(DetailRancanganAnggaranBiayaB.this, "Data Rencana Tanam Tidak Ditemukan", Toast.LENGTH_LONG).show();
                                            call.cancel();
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRancanganAnggaranBiayaB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getSudahTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSudahTanam> dataRT = apiInterface.getDataSudahTanam();
        dataRT.enqueue(new Callback<ModelSudahTanam>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                modelSudahTanam = response.body();
                if (response.body() != null) {
                    for (int i = 0; i < modelSudahTanam.getTotalData(); i++) {
                        try {
                            if (modelSudahTanam.getData().get(i).getIdRencanaTanam().equalsIgnoreCase(dataRencanaTanam.getIdRencanaTanam())) {
                                listSudahTanam.add(modelSudahTanam.getData().get(i));
                            }
                        } catch (Exception e) {
                        }
                    }
                    date.clear();
                    if (listSudahTanam.size() > 0) {
                        for (int a = 0; a < listSudahTanam.size(); a++) {
                            String b = listSudahTanam.get(a).getCreatedDate();
                            b.substring(0, b.length() - 6);
                            date.add(b);
                        }
                        Collections.sort(date, new StringDateComparator());

                        for (int z = date.size() - 1; z >= 0; z--) {
                            // i=2
                            String dt = date.get(z);
                            for (int x = 0; x < listSudahTanam.size(); x++) {
                                if (listSudahTanam.get(x).getCreatedDate().equalsIgnoreCase(dt)) {
                                    listSudahTanamSorted.add(listSudahTanam.get(x));
                                }
                            }
                        }


                        if (listSudahTanamSorted.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.viewLoading.setVisibility(View.GONE);
                                    setData();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(DetailRancanganAnggaranBiayaB.this, "Belum Ada Proses Sudah Tanam !", Toast.LENGTH_SHORT).show();
                                goToRAB();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelSudahTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailRancanganAnggaranBiayaB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData() {

        for (int aa = listSudahTanamSorted.size() - 1; aa >= 0; aa--) {
            if (!listSudahTanamSorted.get(aa).getIdBiayaburuhTanam().equalsIgnoreCase("")) {
                String aaa = checkDesimal(listSudahTanamSorted.get(aa).getIdBiayaburuhTanam());
                binding.buruhTanam.setText(aaa);
                ab = Integer.valueOf(listSudahTanamSorted.get(aa).getIdBiayaburuhTanam());
            }
        }
        for (int bb = listSudahTanamSorted.size() - 1; bb >= 0; bb--) {
            if (!listSudahTanamSorted.get(bb).getIdBiayaburuhBajak().equalsIgnoreCase("")) {
                String bbb = checkDesimal(listSudahTanamSorted.get(bb).getIdBiayaburuhBajak());
                binding.buruhBajak.setText(bbb);
                ac = Integer.valueOf(listSudahTanamSorted.get(bb).getIdBiayaburuhBajak());
            }
        }
        for (int cc = listSudahTanamSorted.size() - 1; cc >= 0; cc--) {
            if (!listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot().equalsIgnoreCase("")) {
                String ccc = checkDesimal(listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot());
                binding.buruhSemprot.setText(ccc);
                ad = Integer.valueOf(listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot());
            }
        }
        for (int dd = listSudahTanamSorted.size() - 1; dd >= 0; dd--) {
            if (!listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput().equalsIgnoreCase("")) {
                String ddd = checkDesimal(listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput());
                binding.buruhMenyiangiRumput.setText(ddd);
                ae = Integer.valueOf(listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput());
            }
        }
        for (int ee = listSudahTanamSorted.size() - 1; ee >= 0; ee--) {
            if (!listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan().equalsIgnoreCase("")) {
                String eee = checkDesimal(listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan());
                binding.buruhGalengan.setText(eee);
                af = Integer.valueOf(listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan());
            }
        }
        for (int ff = listSudahTanamSorted.size() - 1; ff >= 0; ff--) {
            if (!listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk().equalsIgnoreCase("")) {
                String fff = checkDesimal(listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk());
                binding.buruhPupuk.setText(fff);
                ag = Integer.valueOf(listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk());
            }
        }
        for (int gg = listSudahTanamSorted.size() - 1; gg >= 0; gg--) {
            if (!listSudahTanamSorted.get(gg).getIdBiayaburuhPanen().equalsIgnoreCase("")) {
                String ggg = checkDesimal(listSudahTanamSorted.get(gg).getIdBiayaburuhPanen());
                binding.buruhPanen.setText(ggg);
                ah = Integer.valueOf(listSudahTanamSorted.get(gg).getIdBiayaburuhPanen());
            }
        }
        for (int hh = listSudahTanamSorted.size() - 1; hh >= 0; hh--) {
            if (!listSudahTanamSorted.get(hh).getIdSewamesinBajak().equalsIgnoreCase("")) {
                String hhh = checkDesimal(listSudahTanamSorted.get(hh).getIdSewamesinBajak());
                binding.mesinBajak.setText(hhh);
                ai = Integer.valueOf(listSudahTanamSorted.get(hh).getIdSewamesinBajak());
            }
        }
        for (int ii = listSudahTanamSorted.size() - 1; ii >= 0; ii--) {
            if (!listSudahTanamSorted.get(ii).getIdSewamesinPanen().equalsIgnoreCase("")) {
                String iii = checkDesimal(listSudahTanamSorted.get(ii).getIdSewamesinPanen());
                binding.mesinPanen.setText(iii);
                aj = Integer.valueOf(listSudahTanamSorted.get(ii).getIdSewamesinPanen());
            }
        }
        for (int jj = listSudahTanamSorted.size() - 1; jj >= 0; jj--) {
            if (!listSudahTanamSorted.get(jj).getIdSewamesinTanam().equalsIgnoreCase("")) {
                String jjj = checkDesimal(listSudahTanamSorted.get(jj).getIdSewamesinTanam());
                binding.mesinTanam.setText(jjj);
                ak = Integer.valueOf(listSudahTanamSorted.get(jj).getIdSewamesinTanam());
            }
        }
//        for(int kk=listSudahTanamSorted.size()-1; kk>=0; kk--){
//            if(!listSudahTanamSorted.get(kk).getIdSewamesinPompa().equalsIgnoreCase("")){
//                String kkk = checkDesimal(listSudahTanamSorted.get(kk).getIdSewamesinPompa());
//                txt_mesin_pompa2.setText(kkk);
//                al = Integer.valueOf(listSudahTanamSorted.get(kk).getIdSewamesinPompa());
//            }
//        }
//        for(int ll=listSudahTanamSorted.size()-1; ll>=0; ll--) {
//            if (!listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm().equalsIgnoreCase("")) {
//                String lll = checkDesimal(listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm());
//                txt_mesin_pompa_bbm2.setText(lll);
//                am = Integer.valueOf(listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm());
//            }
//        }
        for (int mm = listSudahTanamSorted.size() - 1; mm >= 0; mm--) {
            if (!listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet().equalsIgnoreCase("")) {
                String mmm = checkDesimal(listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet());
                binding.bibitLokal.setText(mmm);
                an = Integer.valueOf(listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet());
            }
        }
        for (int nn = listSudahTanamSorted.size() - 1; nn >= 0; nn--) {
            if (!listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi().equalsIgnoreCase("")) {
                String nnn = checkDesimal(listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi());
                binding.bibitSubsidi.setText(nnn);
                ao = Integer.valueOf(listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi());
            }
        }
        for (int oo = listSudahTanamSorted.size() - 1; oo >= 0; oo--) {
            if (!listSudahTanamSorted.get(oo).getIdBiayapupukKimiaLocalHet().equalsIgnoreCase("")) {
                String ooo = checkDesimal(listSudahTanamSorted.get(oo).getIdBiayapupukKimiaLocalHet());
                binding.pupukKimiaLokal.setText(ooo);
            }
        }
//        for (int pp = listSudahTanamSorted.size() - 1; pp >= 0; pp--) {
//            if (!listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska().equalsIgnoreCase("")) {
//                String ppp = checkDesimal(listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska());
//                txt_pupuk_kimia_phonska2.setText(ppp);
//                ap = Integer.valueOf(listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska());
//            }
//        }
//        for (int qq = listSudahTanamSorted.size() - 1; qq >= 0; qq--) {
//            if (!listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea().equalsIgnoreCase("")) {
//                String qqq = checkDesimal(listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea());
//                txt_pupuk_kimia_urea2.setText(qqq);
//                aq = Integer.valueOf(listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea());
//            }
//        }
//        for (int rr = listSudahTanamSorted.size() - 1; rr >= 0; rr--) {
//            if (!listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat().equalsIgnoreCase("")) {
//                String rrr = checkDesimal(listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat());
//                txt_pupuk_kimia_fosfat2.setText(rrr);
//                ar = Integer.valueOf(listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat());
//            }
//        }
        for (int ss = listSudahTanamSorted.size() - 1; ss >= 0; ss--) {
            if (!listSudahTanamSorted.get(ss).getIdBiayapupukOrganik().equalsIgnoreCase("")) {
                String sss = checkDesimal(listSudahTanamSorted.get(ss).getIdBiayapupukOrganik());
                binding.pupukOrganik.setText(sss);
                as = Integer.valueOf(listSudahTanamSorted.get(ss).getIdBiayapupukOrganik());
            }
        }
//        for (int tt = listSudahTanamSorted.size() - 1; tt >= 0; tt--) {
//            if (!listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet().equalsIgnoreCase("")) {
//                String ttt = checkDesimal(listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet());
//                txt_obat_kimia_local2.setText(ttt);
//                at = Integer.valueOf(listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet());
//            }
//        }
//        for (int uu = listSudahTanamSorted.size() - 1; uu >= 0; uu--) {
//            if (!listSudahTanamSorted.get(uu).getIdBiayaobatKimiaSubsidi().equalsIgnoreCase("")) {
//                String uuu = checkDesimal(listSudahTanamSorted.get(uu).getIdBiayaobatKimiaSubsidi());
//                txt_obat_kimia_subsidi2.setText(uuu);
//            }
//        }
//        for (int vv = listSudahTanamSorted.size() - 1; vv >= 0; vv--) {
//            if (!listSudahTanamSorted.get(vv).getIdBiayaobatOrganik().equalsIgnoreCase("")) {
//                String vvv = checkDesimal(listSudahTanamSorted.get(vv).getIdBiayaobatOrganik());
//                txt_obat_organik2.setText(vvv);
//                au = Integer.valueOf(listSudahTanamSorted.get(vv).getIdBiayaobatOrganik());
//            }
//        }

        String tgl = listSudahTanamSorted.get(0).getCreatedDate().substring(8,10);
        String bln = listSudahTanamSorted.get(0).getCreatedDate().substring(5,7);
        String thn = listSudahTanamSorted.get(0).getCreatedDate().substring(0,4);
        binding.tglTerakhirUpdate.setText(tgl + "-" + bln + "-" + thn);
        int totalBiaya = ab + ac + ad + ae + af + ag + ah + ai + aj + ak + al + am + an + ao + ap + aq + ar + as + at + au;
        binding.totalBiaya.setText("Rp " + checkDesimal(String.valueOf(totalBiaya)));

    }

    private String checkDesimal(String a) {
        if (a != null || !a.equalsIgnoreCase("")) {
            if (a.length() > 3) {
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    private void goToRAB() {
        Intent a = new Intent(DetailRancanganAnggaranBiayaB.this, DetailRancanganAnggaranBiayaA.class);
        a.putExtra("idRencanaTanam", idRencanaTanam);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToListRAB(){
        Intent a = new Intent(DetailRancanganAnggaranBiayaB.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListRAB();
    }

}