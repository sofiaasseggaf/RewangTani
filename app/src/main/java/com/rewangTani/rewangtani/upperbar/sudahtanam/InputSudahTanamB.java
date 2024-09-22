package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamBBinding;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputSudahTanamB extends AppCompatActivity {

    UpperbarStInputSudahTanamBBinding binding;
    DatumRencanaTanam datumRencanaTanam;
    List<String> listToken = new ArrayList<String>();
    List<DatumAkun> listAkunwithToken = new ArrayList<>();
    ModelProfilAkun modelProfilAkun;
    List<String> listIDAkunwithAlamat = new ArrayList<>();
    List<String> listRT = new ArrayList<String>();
    List<String> listObatKimiaLocal = new ArrayList<>();
    List<String> listObatKimiaOrganik = new ArrayList<>();
    List<String> listObatSemua = new ArrayList<>();
    ArrayAdapter<String> adapterRT, adapterObatKimia, adapterObatOrganik;
    String namaRT, idRT, namaPL, idPL, namaK, idK, namaV, idV;

    String tipeSIa, tipeSIb, tipeSIc, idSistemIrigasi, txt_pompa, txt_pompabbm;
    Integer sewa, hargaBBM;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_b);

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

//        txt_obat_kimia.addTextChangedListener(new NumberTextWatcher(txt_obat_kimia));
//        txt_obat_organik.addTextChangedListener(new NumberTextWatcher(txt_obat_organik));

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocalData();

                /*if(sewa==1){
                    txt_pompa = "";
                    txt_pompabbm = "";
                    simpan();
                } else if(sewa==2){

                    if(!txt_mesin_pompa.getText().toString().equalsIgnoreCase("")){
                        txt_pompa = txt_mesin_pompa.getText().toString().replaceAll("[^0-9]", "");
                    } else {
                        txt_pompa = "";
                    }

                    if(!txt_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")){
                        if(!txt_durasi_mesin_pompa_bbm.getText().toString().equalsIgnoreCase("")){
                            int bbm = Integer.valueOf(txt_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""));
                            int durasi = Integer.valueOf(txt_durasi_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""))*2;
                            int totalbbm = bbm*durasi;
                            txt_pompabbm = String.valueOf(totalbbm);
                            simpan();
                        } else {
                            Toast.makeText(InputSudahTanamA.this, "Masukan durasi sewa mesin pompa", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        txt_pompabbm = "";
                        simpan();
                    }

                }*/
            }
        });

    }

    public void getData(){
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
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
                getIntentExtra();
            }
        }).start();
    }

    public void getIntentExtra(){
        // ambil data dari intent, ambil ID rencana tanam, trus set harga sebelumnya
        // datumRencanaTanam = get dari intent extra
        setHargaSebelumnya();
    }

    public void setHargaSebelumnya(){

        String a = checkDesimal(datumRencanaTanam.getIdBiayaBuruhTanam());
        binding.txtBuruhTanam.setText("Biaya Sebelumnya : Rp. "+a);
        String b = checkDesimal(datumRencanaTanam.getIdBiayaBuruhBajak());
        binding.txtBuruhBajak.setText("Biaya Sebelumnya : Rp. "+b);
        String c = checkDesimal(datumRencanaTanam.getIdBiayaBuruhSemprot());
        binding.txtBuruhSemprot.setText("Biaya Sebelumnya : Rp. "+c);
        String d = checkDesimal(datumRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        binding.txtBuruhMenyaingiRumput.setText("Biaya Sebelumnya : Rp. "+d);
        String e = checkDesimal(datumRencanaTanam.getIdBiayaBuruhGalangan());
        binding.txtBuruhGalengan.setText("Biaya Sebelumnya : Rp. "+e);
        String f = checkDesimal(datumRencanaTanam.getIdBiayaBuruhPupuk());
        binding.txtBuruhPupuk.setText("Biaya Sebelumnya : Rp. "+f);
        String g = checkDesimal(datumRencanaTanam.getIdBiayaBuruhPanen());
        binding.txtBuruhPanen.setText("Biaya Sebelumnya : Rp. "+g);



        /*String h = checkDesimal(datumRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak2.setText("Biaya Sebelumnya : Rp. "+h);
        String i = checkDesimal(datumRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen2.setText("Biaya Sebelumnya : Rp. "+i);
        String j = checkDesimal(datumRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam2.setText("Biaya Sebelumnya : Rp. "+j);

        if(datumRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(datumRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa2.setText("Biaya Sebelumnya : Rp. "+k);
        } else {
            txt_mesin_pompa2.setText("-");
        }

        if (datumRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(datumRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm2.setText("Biaya Sebelumnya : Rp. "+l);
        } else {
            txt_mesin_pompa_bbm2.setText("-");
        }

        String m = checkDesimal(datumRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local2.setText("Biaya Sebelumnya : Rp. "+m);
        String n = checkDesimal(datumRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi2.setText("Biaya Sebelumnya : Rp. "+n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local.setText(o);
        String p = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska2.setText("Biaya Sebelumnya : Rp. "+p);
        String q = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea2.setText("Biaya Sebelumnya : Rp. "+q);
        String r = checkDesimal(datumRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat2.setText("Biaya Sebelumnya : Rp. "+r);
        String s = checkDesimal(datumRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik2.setText("Biaya Sebelumnya : Rp. "+s);*/

    }

    /*

    private void sendNotificationObatKimia(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        String nama = PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext());

        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("namaSumber", nama);
        jsonParams.put("judulInfo", "HAMA DI DAERAH SEKITAR");
        jsonParams.put("ketInfo", "PEMAKAIAN OBAT "+ namaobatKimia);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendInfo(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        if (listToken.size()>0){
                            try {
                                for (int i=0; i<listToken.size(); i++){
                                    sendNotifObatKimia(listToken.get(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            checkObatOrganik();
                        } else {
                            checkObatOrganik();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal buat info obat kimia", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendNotifObatKimia(String a) throws JSONException {
        final APIInterfacesRest apiInterface = ApiClientNotification.getClient().create(APIInterfacesRest.class);
        String title = "HAMA DI DAERAH SEKITAR";

        JSONObject regis = new JSONObject();
        regis.put("to", a);
        JSONObject notif = new JSONObject();
        notif.put("title", title);
        notif.put("body", "PEMAKAIAN OBAT "+ namaobatKimia);
        regis.put("notification", notif);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(String.valueOf(regis))).toString());

        Call<ModelResultNotification> response = apiInterface.sendNotification(body);
        response.enqueue(new Callback<ModelResultNotification>() {
            @Override
            public void onResponse(Call<ModelResultNotification> call, retrofit2.Response<ModelResultNotification> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        if (rawResponse.body().getSuccess()==1){
                            // do nothing soalnya masih loop
                        } else {
                            // do nothing soalnya masih loop

                            *//*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(InputSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                                }
                            });*//*
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ModelResultNotification> call, Throwable throwable) {
                //Toast.makeText(InputSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void sendNotificationObatOrganik(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        String nama = PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext());

        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("namaSumber", nama);
        jsonParams.put("judulInfo", "HAMA DI DAERAH SEKITAR");
        jsonParams.put("ketInfo", "PEMAKAIAN OBAT "+ txt_nama_obat_organik.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendInfo(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        if (listToken.size()>0){
                            try {
                                for (int i=0; i<listToken.size(); i++){
                                    sendNotifObatOrganik(listToken.get(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sendDataSudahTanam();
                        } else {
                            sendDataSudahTanam();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal buat info obat organik", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendNotifObatOrganik(String a) throws JSONException {
        final APIInterfacesRest apiInterface = ApiClientNotification.getClient().create(APIInterfacesRest.class);
        String title = "HAMA DI DAERAH SEKITAR";

        JSONObject regis = new JSONObject();
        regis.put("to", a);
        JSONObject notif = new JSONObject();
        notif.put("title", title);
        notif.put("body", "PEMAKAIAN OBAT "+ txt_nama_obat_organik.getText().toString());
        regis.put("notification", notif);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(String.valueOf(regis))).toString());

        Call<ModelResultNotification> response = apiInterface.sendNotification(body);
        response.enqueue(new Callback<ModelResultNotification>() {
            @Override
            public void onResponse(Call<ModelResultNotification> call, retrofit2.Response<ModelResultNotification> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        if (rawResponse.body().getSuccess()==1){
                            // do nothing soalnya masih loop
                        } else {
                            // do nothing soalnya masih loop
*//*                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(InputSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                                }
                            });*//*
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ModelResultNotification> call, Throwable throwable) {
                //Toast.makeText(InputSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    */

    private String checkDesimal(String a){
        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    private void saveLocalData() {
        boolean isWithPompa;
        if(ListSudahTanam.getInstance().getDatumSudahTanam().isWithPompa()){
            isWithPompa = true;
        } else {
            isWithPompa = false;
        }

        DatumSudahTanam datumSudahTanam = new DatumSudahTanam( "", "", "", "", binding.buruhTanam.getText().toString().replaceAll("[^0-9]",""), binding.buruhBajak.getText().toString().replaceAll("[^0-9]",""), binding.buruhSemprot.getText().toString().replaceAll("[^0-9]",""), binding.buruhMenyiangiRumput.getText().toString().replaceAll("[^0-9]",""),
                binding.buruhGalengan.getText().toString().replaceAll("[^0-9]",""), binding.buruhPupuk.getText().toString().replaceAll("[^0-9]",""), binding.buruhPanen.getText().toString().replaceAll("[^0-9]",""), "", "", "", "","", "", "", "", "","",
                "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToC();
    }

    public void moveToC(){
        Intent a = new Intent(InputSudahTanamB.this, InputSudahTanamC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void moveToA(){
        Intent a = new Intent(InputSudahTanamB.this, InputSudahTanamA.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(InputSudahTanamB.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToA();
    }

}