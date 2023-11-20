package com.rewangTani.rewangtani.upperbar.rab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
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

    TextView txt_buruh_tanam1, txt_buruh_bajak1, txt_buruh_semprot1, txt_buruh_menyiangi1, txt_buruh_galengan1, txt_buruh_pupuk1, txt_buruh_panen1;
    TextView txt_buruh_tanam2, txt_buruh_bajak2, txt_buruh_semprot2, txt_buruh_menyiangi2, txt_buruh_galengan2, txt_buruh_pupuk2, txt_buruh_panen2;
    TextView txt_mesin_bajak1, txt_mesin_tanam1,  txt_mesin_panen1, txt_mesin_pompa1, txt_mesin_pompa_bbm1;
    TextView txt_mesin_bajak2, txt_mesin_tanam2, txt_mesin_panen2, txt_mesin_pompa2, txt_mesin_pompa_bbm2;
    TextView txt_bibit_local1, txt_bibit_local2, txt_bibit_subsidi1, txt_bibit_subsidi2;
    TextView txt_pupuk_kimia_phonska1, txt_pupuk_kimia_urea1, txt_pupuk_kimia_fosfat1, txt_pupuk_organik1;
    TextView txt_pupuk_kimia_phonska2, txt_pupuk_kimia_urea2, txt_pupuk_kimia_fosfat2, txt_pupuk_organik2;
    TextView txt_obat_kimia_local2, txt_obat_organik2;
    TextView txt_date1, txt_date2, txt_total1, txt_total2;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    ModelSudahTanam modelSudahTanam;
    List<DatumSudahTanam> listSudahTanam = new ArrayList<>();
    List<DatumSudahTanam> listSudahTanamSorted = new ArrayList<>();
    List<String> date = new ArrayList<>();
    TextView txtload;
    String id, estBiaya;
    DecimalFormat formatter;
    int ab, ac, ad, ae, af, ag, ah, ai, aj, ak, al, am, an, ao, ap, aq, ar, as, at, au;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_rab_detail_rab);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_buruh_tanam1 = findViewById(R.id.txt_buruh_tanam1); txt_buruh_tanam2 = findViewById(R.id.txt_buruh_tanam2);
        txt_buruh_bajak1 = findViewById(R.id.txt_buruh_bajak1); txt_buruh_bajak2 = findViewById(R.id.txt_buruh_bajak2);
        txt_buruh_semprot1 = findViewById(R.id.txt_buruh_semprot1); txt_buruh_semprot2 = findViewById(R.id.txt_buruh_semprot2);
        txt_buruh_menyiangi1 = findViewById(R.id.txt_buruh_menyiangi1); txt_buruh_menyiangi2 = findViewById(R.id.txt_buruh_menyiangi2);
        txt_buruh_galengan1 = findViewById(R.id.txt_buruh_galengan1); txt_buruh_galengan2 = findViewById(R.id.txt_buruh_galengan2);
        txt_buruh_pupuk1 = findViewById(R.id.txt_buruh_pupuk1); txt_buruh_pupuk2 = findViewById(R.id.txt_buruh_pupuk2);
        txt_buruh_panen1 = findViewById(R.id.txt_buruh_panen1); txt_buruh_panen2 = findViewById(R.id.txt_buruh_panen2);

        txt_mesin_bajak1 = findViewById(R.id.txt_mesin_bajak1); txt_mesin_bajak2 = findViewById(R.id.txt_mesin_bajak2);
        txt_mesin_tanam1 = findViewById(R.id.txt_mesin_tanam1); txt_mesin_tanam2 = findViewById(R.id.txt_mesin_tanam2);
        txt_mesin_panen1 = findViewById(R.id.txt_mesin_panen1); txt_mesin_panen2 = findViewById(R.id.txt_mesin_panen2);
        txt_mesin_pompa1 = findViewById(R.id.txt_mesin_pompa1); txt_mesin_pompa2 = findViewById(R.id.txt_mesin_pompa2);
        txt_mesin_pompa_bbm1 = findViewById(R.id.txt_mesin_pompa_bbm1); txt_mesin_pompa_bbm2 = findViewById(R.id.txt_mesin_pompa_bbm2);

        txt_bibit_local1 = findViewById(R.id.txt_bibit_local1); txt_bibit_local2 = findViewById(R.id.txt_bibit_local2);
        txt_bibit_subsidi1 = findViewById(R.id.txt_bibit_subsidi1); txt_bibit_subsidi2 = findViewById(R.id.txt_bibit_subsidi2);

        //txt_pupuk_kimia_local1 = findViewById(R.id.txt_pupuk_kimia_local1); txt_pupuk_kimia_local2 = findViewById(R.id.txt_pupuk_kimia_local2);
        txt_pupuk_kimia_phonska1 = findViewById(R.id.txt_pupuk_kimia_phonska1); txt_pupuk_kimia_phonska2 = findViewById(R.id.txt_pupuk_kimia_phonska2);
        txt_pupuk_kimia_urea1 = findViewById(R.id.txt_pupuk_kimia_urea1); txt_pupuk_kimia_urea2 = findViewById(R.id.txt_pupuk_kimia_urea2);
        txt_pupuk_kimia_fosfat1 = findViewById(R.id.txt_pupuk_kimia_fosfat1); txt_pupuk_kimia_fosfat2 = findViewById(R.id.txt_pupuk_kimia_fosfat2);
        txt_pupuk_organik1 = findViewById(R.id.txt_pupuk_organik1); txt_pupuk_organik2 = findViewById(R.id.txt_pupuk_organik2);

        txt_obat_kimia_local2 = findViewById(R.id.txt_obat_kimia_local2);
        //txt_obat_kimia_subsidi2 = findViewById(R.id.txt_obat_kimia_subsidi2);
        txt_obat_organik2 = findViewById(R.id.txt_obat_organik2);

        txt_date1 = findViewById(R.id.txt_date1); txt_date2 = findViewById(R.id.txt_date2);
        txt_total1 = findViewById(R.id.txt_total1); txt_total2 = findViewById(R.id.txt_total2);

        txtload = findViewById(R.id.textloading);

        getData();

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

    }

    public void getData(){
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
                            if (id.equalsIgnoreCase(idrt)) {
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                                if (dataRencanaTanam!=null){
                                    getOutputRencanaTanam();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        getSudahTanam();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                if (response.body()!=null){
                    for (int i = 0; i < modelSudahTanam.getTotalData(); i++) {
                            try {
                                if (modelSudahTanam.getData().get(i).getIdRencanaTanam().equalsIgnoreCase(dataRencanaTanam.getIdRencanaTanam()))
                                {
                                    listSudahTanam.add(modelSudahTanam.getData().get(i));
                                }
                            } catch (Exception e){ }
                    }
                    date.clear();
                    if (listSudahTanam.size()>0){
                        for(int a=0; a<listSudahTanam.size(); a++){
                            String b = listSudahTanam.get(a).getCreatedDate();
                            b.substring(0, b.length() - 6);
                            date.add(b);
                        }
                        Collections.sort(date, new StringDateComparator());

                        for(int z=date.size()-1; z>=0; z--){
                            // i=2
                            String dt = date.get(z);
                            for (int x=0; x<listSudahTanam.size(); x++){
                                if(listSudahTanam.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                                    listSudahTanamSorted.add(listSudahTanam.get(x));
                                }
                            }
                        }


                        if (listSudahTanamSorted.size()>0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    setData();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(DetailRancanganAnggaranBiayaA.this, "Data sudah tanam belum ada", Toast.LENGTH_SHORT).show();
                                setDataRABonly();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
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

    public void setData(){
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam1.setText(a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak1.setText(b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot1.setText(c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi1.setText(d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan1.setText(e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk1.setText(f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen1.setText(g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak1.setText(h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen1.setText(i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam1.setText(j);
        if (dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa1.setText(k);
        } else if(dataRencanaTanam.getIdSewamesinPompa().equalsIgnoreCase("0")){
            txt_mesin_pompa1.setText("-");
        } else {
            txt_mesin_pompa1.setText("-");
        }
        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm1.setText(l);
        } else if(dataRencanaTanam.getIdSewamesinPompaBbm().equalsIgnoreCase("0")) {
            txt_mesin_pompa_bbm1.setText("-");
        } else {
            txt_mesin_pompa_bbm1.setText("-");
        }
        /*String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
        txt_mesin_pompa1.setText(k);
        String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
        txt_mesin_pompa_bbm1.setText(l);*/
        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local1.setText(m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi1.setText(n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local1.setText(o);
        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska1.setText(p);
        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea1.setText(q);
        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat1.setText(r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik1.setText(s);

        for(int aa=listSudahTanamSorted.size()-1; aa>=0; aa--){
            if(!listSudahTanamSorted.get(aa).getIdBiayaburuhTanam().equalsIgnoreCase("")){
                String aaa = checkDesimal(listSudahTanamSorted.get(aa).getIdBiayaburuhTanam());
                txt_buruh_tanam2.setText(aaa);
                ab = Integer.valueOf(listSudahTanamSorted.get(aa).getIdBiayaburuhTanam());
            }
        }
        for(int bb=listSudahTanamSorted.size()-1; bb>=0; bb--){
            if(!listSudahTanamSorted.get(bb).getIdBiayaburuhBajak().equalsIgnoreCase("")){
                String bbb = checkDesimal(listSudahTanamSorted.get(bb).getIdBiayaburuhBajak());
                txt_buruh_bajak2.setText(bbb);
                ac = Integer.valueOf(listSudahTanamSorted.get(bb).getIdBiayaburuhBajak());
            }
        }
        for(int cc=listSudahTanamSorted.size()-1; cc>=0; cc--){
            if(!listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot().equalsIgnoreCase("")){
                String ccc = checkDesimal(listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot());
                txt_buruh_semprot2.setText(ccc);
                ad = Integer.valueOf(listSudahTanamSorted.get(cc).getIdBiayaburuhSemprot());
            }
        }
        for(int dd=listSudahTanamSorted.size()-1; dd>=0; dd--){
            if(!listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput().equalsIgnoreCase("")){
                String ddd = checkDesimal(listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput());
                txt_buruh_menyiangi2.setText(ddd);
                ae = Integer.valueOf(listSudahTanamSorted.get(dd).getIdBiayaburuhMenyiangirumput());
            }
        }
        for(int ee=listSudahTanamSorted.size()-1; ee>=0; ee--){
            if(!listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan().equalsIgnoreCase("")){
                String eee = checkDesimal(listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan());
                txt_buruh_galengan2.setText(eee);
                af = Integer.valueOf(listSudahTanamSorted.get(ee).getIdBiayaburuhGalangan());
            }
        }
        for(int ff=listSudahTanamSorted.size()-1; ff>=0; ff--){
            if(!listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk().equalsIgnoreCase("")){
                String fff = checkDesimal(listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk());
                txt_buruh_pupuk2.setText(fff);
                ag = Integer.valueOf(listSudahTanamSorted.get(ff).getIdBiayaburuhPupuk());
            }
        }
        for(int gg=listSudahTanamSorted.size()-1; gg>=0; gg--){
            if(!listSudahTanamSorted.get(gg).getIdBiayaburuhPanen().equalsIgnoreCase("")){
                String ggg = checkDesimal(listSudahTanamSorted.get(gg).getIdBiayaburuhPanen());
                txt_buruh_panen2.setText(ggg);
                ah = Integer.valueOf(listSudahTanamSorted.get(gg).getIdBiayaburuhPanen());
            }
        }
        for(int hh=listSudahTanamSorted.size()-1; hh>=0; hh--){
            if(!listSudahTanamSorted.get(hh).getIdSewamesinBajak().equalsIgnoreCase("")){
                String hhh = checkDesimal(listSudahTanamSorted.get(hh).getIdSewamesinBajak());
                txt_mesin_bajak2.setText(hhh);
                ai = Integer.valueOf(listSudahTanamSorted.get(hh).getIdSewamesinBajak());
            }
        }
        for(int ii=listSudahTanamSorted.size()-1; ii>=0; ii--){
            if(!listSudahTanamSorted.get(ii).getIdSewamesinPanen().equalsIgnoreCase("")){
                String iii = checkDesimal(listSudahTanamSorted.get(ii).getIdSewamesinPanen());
                txt_mesin_panen2.setText(iii);
                aj = Integer.valueOf(listSudahTanamSorted.get(ii).getIdSewamesinPanen());
            }
        }
        for(int jj=listSudahTanamSorted.size()-1; jj>=0; jj--){
            if(!listSudahTanamSorted.get(jj).getIdSewamesinTanam().equalsIgnoreCase("")){
                String jjj = checkDesimal(listSudahTanamSorted.get(jj).getIdSewamesinTanam());
                txt_mesin_tanam2.setText(jjj);
                ak = Integer.valueOf(listSudahTanamSorted.get(jj).getIdSewamesinTanam());
            }
        }

        for(int kk=listSudahTanamSorted.size()-1; kk>=0; kk--){
            if(!listSudahTanamSorted.get(kk).getIdSewamesinPompa().equalsIgnoreCase("")){
                String kkk = checkDesimal(listSudahTanamSorted.get(kk).getIdSewamesinPompa());
                txt_mesin_pompa2.setText(kkk);
                al = Integer.valueOf(listSudahTanamSorted.get(kk).getIdSewamesinPompa());
            }
        }
        for(int ll=listSudahTanamSorted.size()-1; ll>=0; ll--){
            if(!listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm().equalsIgnoreCase("")){
                String lll = checkDesimal(listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm());
                txt_mesin_pompa_bbm2.setText(lll);
                am = Integer.valueOf(listSudahTanamSorted.get(ll).getIdSewamesinPompaBbm());
            }
        }
        for(int mm=listSudahTanamSorted.size()-1; mm>=0; mm--){
            if(!listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet().equalsIgnoreCase("")){
                String mmm = checkDesimal(listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet());
                txt_bibit_local2.setText(mmm);
                an = Integer.valueOf(listSudahTanamSorted.get(mm).getIdBiayabibitLocalHet());
            }
        }
        for(int nn=listSudahTanamSorted.size()-1; nn>=0; nn--){
            if(!listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi().equalsIgnoreCase("")){
                String nnn = checkDesimal(listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi());
                txt_bibit_subsidi2.setText(nnn);
                ao = Integer.valueOf(listSudahTanamSorted.get(nn).getIdBiayabibitSubsidi());
            }
        }
        /*for(int oo=listSudahTanamSorted.size()-1; oo>=0; oo--){
            if(!listSudahTanamSorted.get(oo).getIdBiayapupukKimiaLocalHet().equalsIgnoreCase("")){
                String ooo = checkDesimal(listSudahTanamSorted.get(oo).getIdBiayapupukKimiaLocalHet());
                txt_pupuk_kimia_local2.setText(ooo);
            }
        }*/
        for(int pp=listSudahTanamSorted.size()-1; pp>=0; pp--){
            if(!listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska().equalsIgnoreCase("")){
                String ppp = checkDesimal(listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska());
                txt_pupuk_kimia_phonska2.setText(ppp);
                ap = Integer.valueOf(listSudahTanamSorted.get(pp).getIdBiayapupukKimiaPhonska());
            }
        }
        for(int qq=listSudahTanamSorted.size()-1; qq>=0; qq--){
            if(!listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea().equalsIgnoreCase("")){
                String qqq = checkDesimal(listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea());
                txt_pupuk_kimia_urea2.setText(qqq);
                aq = Integer.valueOf(listSudahTanamSorted.get(qq).getIdBiayapupukKimiaUrea());
            }
        }
        for(int rr=listSudahTanamSorted.size()-1; rr>=0; rr--){
            if(!listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat().equalsIgnoreCase("")){
                String rrr = checkDesimal(listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat());
                txt_pupuk_kimia_fosfat2.setText(rrr);
                ar = Integer.valueOf(listSudahTanamSorted.get(rr).getIdBiayapupukKimiaFosfat());
            }
        }
        for(int ss=listSudahTanamSorted.size()-1; ss>=0; ss--){
            if(!listSudahTanamSorted.get(ss).getIdBiayapupukOrganik().equalsIgnoreCase("")){
                String sss = checkDesimal(listSudahTanamSorted.get(ss).getIdBiayapupukOrganik());
                txt_pupuk_organik2.setText(sss);
                as = Integer.valueOf(listSudahTanamSorted.get(ss).getIdBiayapupukOrganik());
            }
        }
        for(int tt=listSudahTanamSorted.size()-1; tt>=0; tt--){
            if(!listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet().equalsIgnoreCase("")){
                String ttt = checkDesimal(listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet());
                txt_obat_kimia_local2.setText(ttt);
                at = Integer.valueOf(listSudahTanamSorted.get(tt).getIdBiayaobatKimiaLocalHet());
            }
        }
        /*for(int uu=listSudahTanamSorted.size()-1; uu>=0; uu--){
            if(!listSudahTanamSorted.get(uu).getIdBiayaobatKimiaSubsidi().equalsIgnoreCase("")){
                String uuu = checkDesimal(listSudahTanamSorted.get(uu).getIdBiayaobatKimiaSubsidi());
                txt_obat_kimia_subsidi2.setText(uuu);
            }
        }*/
        for(int vv=listSudahTanamSorted.size()-1; vv>=0; vv--){
            if(!listSudahTanamSorted.get(vv).getIdBiayaobatOrganik().equalsIgnoreCase("")){
                String vvv = checkDesimal(listSudahTanamSorted.get(vv).getIdBiayaobatOrganik());
                txt_obat_organik2.setText(vvv);
                au = Integer.valueOf(listSudahTanamSorted.get(vv).getIdBiayaobatOrganik());
            }
        }

        String tgl = dataRencanaTanam.getCreatedDate().substring(8,10);
        String bln = dataRencanaTanam.getCreatedDate().substring(5,7);
        String thn = dataRencanaTanam.getCreatedDate().substring(0,4);
        txt_date1.setText(tgl+"-"+bln+"-"+thn);
        txt_total1.setText("Rp " + checkDesimal(estBiaya));
        //txt_date1.setText(dataRencanaTanam.getCreatedDate().substring(0,10));
        String tgl2 = listSudahTanamSorted.get(0).getCreatedDate().substring(8,10);
        String bln2 = listSudahTanamSorted.get(0).getCreatedDate().substring(5,7);
        String thn2 = listSudahTanamSorted.get(0).getCreatedDate().substring(0,4);
        //txt_date2.setText(listSudahTanamSorted.get(0).getCreatedDate().substring(0,10));
        txt_date2.setText(tgl2+"-"+bln2+"-"+thn2);
        int total2 = ab+ac+ad+ae+af+ag+ah+ai+aj+ak+al+am+an+ao+ap+aq+ar+as+at+au;
        txt_total2.setText("Rp " + checkDesimal(String.valueOf(total2)));
    }

    public void setDataRABonly(){
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam1.setText(a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak1.setText(b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot1.setText(c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi1.setText(d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan1.setText(e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk1.setText(f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen1.setText(g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak1.setText(h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen1.setText(i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam1.setText(j);
        if (dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa1.setText(k);
        } else if(dataRencanaTanam.getIdSewamesinPompa().equalsIgnoreCase("0")){
            txt_mesin_pompa1.setText("-");
        } else {
            txt_mesin_pompa1.setText("-");
        }
        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm1.setText(l);
        } else if(dataRencanaTanam.getIdSewamesinPompaBbm().equalsIgnoreCase("0")) {
            txt_mesin_pompa_bbm1.setText("-");
        } else {
            txt_mesin_pompa_bbm1.setText("-");
        }
        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local1.setText(m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi1.setText(n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local1.setText(o);
        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska1.setText(p);
        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea1.setText(q);
        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat1.setText(r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik1.setText(s);

        String tgl = dataRencanaTanam.getCreatedDate().substring(8,10);
        String bln = dataRencanaTanam.getCreatedDate().substring(5,7);
        String thn = dataRencanaTanam.getCreatedDate().substring(0,4);
        txt_date1.setText(tgl+"-"+bln+"-"+thn);
        txt_total1.setText("Rp " + checkDesimal(estBiaya));
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