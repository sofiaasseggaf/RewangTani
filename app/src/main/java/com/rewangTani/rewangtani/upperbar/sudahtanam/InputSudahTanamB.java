package com.rewangTani.rewangtani.upperbar.sudahtanam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.APIService.ApiClientNotification;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamABinding;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamBBinding;
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelinfo.ModelResultNotification;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ResponseSendObat;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamA;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamB;
import com.rewangTani.rewangtani.upperbar.rencanatanam.InputRencanaTanamC;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputSudahTanamB extends AppCompatActivity {

    UpperbarStInputSudahTanamBBinding binding;
    ModelRencanaTanam modelRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    ModelObat modelObat;
    DatumRencanaTanam datumRencanaTanam;
    ModelAkun modelAkun;
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
    String idObatKimia=null;
    String idObatOrganik=null;
    String tipeSIa, tipeSIb, tipeSIc, idSistemIrigasi, txt_pompa, txt_pompabbm;
    Integer sewa, hargaBBM;
    DecimalFormat formatter;
    String namaobatKimia;

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

        //getData();

        binding.buruhTanam.addTextChangedListener(new NumberTextWatcher(binding.buruhTanam));
        binding.buruhBajak.addTextChangedListener(new NumberTextWatcher(binding.buruhBajak));
        binding.buruhSemprot.addTextChangedListener(new NumberTextWatcher(binding.buruhSemprot));
        binding.buruhMenyiangiRumput.addTextChangedListener(new NumberTextWatcher(binding.buruhMenyiangiRumput));
        binding.buruhGalengan.addTextChangedListener(new NumberTextWatcher(binding.buruhGalengan));
        binding.buruhPupuk.addTextChangedListener(new NumberTextWatcher(binding.buruhPupuk));
        binding.buruhPanen.addTextChangedListener(new NumberTextWatcher(binding.buruhPanen));
//        txt_mesin_bajak.addTextChangedListener(new NumberTextWatcher(txt_mesin_bajak));
//        txt_mesin_tanam.addTextChangedListener(new NumberTextWatcher(txt_mesin_tanam));
//        txt_mesin_panen.addTextChangedListener(new NumberTextWatcher(txt_mesin_panen));
//        txt_mesin_pompa.addTextChangedListener(new NumberTextWatcher(txt_mesin_pompa));
//        txt_mesin_pompa_bbm.addTextChangedListener(new NumberTextWatcher(txt_mesin_pompa_bbm));
//        txt_bibit_local.addTextChangedListener(new NumberTextWatcher(txt_bibit_local));
//        txt_bibit_subsidi.addTextChangedListener(new NumberTextWatcher(txt_bibit_subsidi));
//        txt_pupuk_kimia_phonska.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_phonska));
//        txt_pupuk_kimia_urea.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_urea));
//        txt_pupuk_kimia_fosfat.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_fosfat));
//        txt_pupuk_organik.addTextChangedListener(new NumberTextWatcher(txt_pupuk_organik));
//        txt_obat_kimia.addTextChangedListener(new NumberTextWatcher(txt_obat_kimia));
//        txt_obat_organik.addTextChangedListener(new NumberTextWatcher(txt_obat_organik));

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

      /*  sp_rt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaRT = sp_rt.getSelectedItem().toString();
                for (int a=0; a<modelRencanaTanam.getTotalData(); a++){
                    try {
                        if (modelRencanaTanam.getData().get(a).getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                            idRT = modelRencanaTanam.getData().get(a).getIdRencanaTanam();
                            idPL = modelRencanaTanam.getData().get(a).getIdProfilTanah();
                            idK = modelRencanaTanam.getData().get(a).getIdKomoditas();
                            idV = modelRencanaTanam.getData().get(a).getIdVarietas();
                            datumRencanaTanam = modelRencanaTanam.getData().get(a);
                            setDataPL();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        sp_obat_kimia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namaobatKimia = sp_obat_kimia.getSelectedItem().toString();
                for (int a=0; a<modelObat.getTotalData(); a++){
                    try {
                        if (modelObat.getData().get(a).getNamaObat().equalsIgnoreCase(namaobatKimia)){
                            idObatKimia = modelObat.getData().get(a).getIdObat();
                        }
                    } catch (Exception e){}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check trus ke C !

                //simpan();
                moveToC();
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

    /*

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRT.add(modelRencanaTanam.getData().get(i).getNamaRencanaTanam());
                                //listProfilLahan.add(modelRencanaTanam.getData().get(i).getIdProfilTanah());
                            }
                        } catch (Exception e){ }
                    }
                    if (listRT.size()!=0){
                        getDataProfilLahan();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputSudahTanamA.this);
                                builder.setMessage("Buat rencana tanam terlebih dahulu")
                                        .setPositiveButton("Buat Rencana Tanam", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                goToRencanaTanam();
                                            }
                                        })
                                        .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                goToListST();
                                            }
                                        })
                                        .create()
                                        .show();
                            }

                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProfilLahan() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    getDataKomoditas();
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    public void getDataKomoditas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKomoditas> dataK = apiInterface.getDataKomoditas();
        dataK.enqueue(new Callback<ModelKomoditas>() {
            @Override
            public void onResponse(Call<ModelKomoditas> call, Response<ModelKomoditas> response) {
                modelKomoditas = response.body();
                if (response.body()!=null){
                    getDataVarietas();
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataVarietas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelVarietas> dataV = apiInterface.getDataVarietas();
        dataV.enqueue(new Callback<ModelVarietas>() {
            @Override
            public void onResponse(Call<ModelVarietas> call, Response<ModelVarietas> response) {
                modelVarietas = response.body();
                if (response.body()!=null){
                    getDataObat();
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataObat() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelObat> dataO = apiInterface.getDataObat();
        dataO.enqueue(new Callback<ModelObat>() {
            @Override
            public void onResponse(Call<ModelObat> call, Response<ModelObat> response) {
                modelObat = response.body();
                // 4b81bee1-6988-43c8-8c44-350539054f21 = obat kimia local
                // 21bd7381-07ab-4097-94dc-7670cd6f6971 = obat organik
                if (response.body()!=null){
                    for (int i = 0; i < modelObat.getTotalData(); i++) {
                        listObatSemua.add(modelObat.getData().get(i).getNamaObat());
                        try {
                            if (modelObat.getData().get(i).getIdSubkategori().equalsIgnoreCase("4b81bee1-6988-43c8-8c44-350539054f21")) {
                                listObatKimiaLocal.add(modelObat.getData().get(i).getNamaObat());
                            } else if(modelObat.getData().get(i).getIdSubkategori().equalsIgnoreCase("21bd7381-07ab-4097-94dc-7670cd6f6971")){
                                listObatKimiaOrganik.add(modelObat.getData().get(i).getNamaObat());
                            }
                        } catch (Exception e){ }
                    }
                    if (listObatSemua.size()>0){
                        getDataAkun();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelObat> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataAkun(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAkun> data = apiInterface.getDataAkun();
        data.enqueue(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                modelAkun = response.body();
                if (response.body() != null) {
                    for (int i=0; i<modelAkun.getTotalData(); i++){
                        if (modelAkun.getData().get(i).getToken()!=null){
                            listAkunwithToken.add(modelAkun.getData().get(i));
                            //listToken.add(modelAkun.getData().get(i).getToken());
                        }
                    }
                    if (listAkunwithToken!=null){
                        getDataAlamat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Data token tidak ditemukan", Toast.LENGTH_SHORT).show();
                                setDataSpinnerRT();

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void getDataAlamat(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body()!=null){
                    String idAlamat = PreferenceUtils.getIdAlamat(getApplicationContext());
                    try{
                        for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                            if(modelProfilAkun.getData().get(i).getIdAlamat()!=null){
                                if(modelProfilAkun.getData().get(i).getIdAlamat().equalsIgnoreCase(idAlamat)){
                                    listIDAkunwithAlamat.add(modelProfilAkun.getData().get(i).getIdAkun());
                                }
                            }
                        }
                        if (listIDAkunwithAlamat.size()>0){
                            for (int a=0; a<listAkunwithToken.size(); a++){
                                for (int j=0; j<listIDAkunwithAlamat.size(); j++){
                                    if(listAkunwithToken.get(a).getIdAkun().equalsIgnoreCase(listIDAkunwithAlamat.get(j))){
                                        listToken.add(listAkunwithToken.get(a).getToken());
                                    }
                                }
                            }
                        }

                        if (listToken.size()>0){
                            for (int a=0; a<modelAkun.getTotalData(); a++){
                                for (int b=0; b>listToken.size(); b++){
                                    if (modelAkun.getData().get(a).getIdAkun().equalsIgnoreCase(PreferenceUtils.getIdAkun(getApplicationContext()))){
                                        String thistoken = modelAkun.getData().get(a).getToken();
                                        if (listToken.get(b).equalsIgnoreCase(thistoken)){
                                            listToken.remove(listToken.get(b));
                                        }
                                    }
                                }
                            }
                            if (listToken.size()>0){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                        setDataSpinnerRT();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    setDataSpinnerRT();
                                    //Toast.makeText(InputSudahTanam.this, "Data Token Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (Exception e){ }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            //Toast.makeText(TambahInfoPeringatanCuaca.this, "Data Profil Tidak Ditemukan", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    public void setDataSpinnerRT(){

        // sumur bor
        tipeSIa = "10a9631e-6add-459e-b7e2-aed3a0c907df";
        // permukaan
        tipeSIb = "26b145d3-632b-4f59-8571-b85a993169b3";
        // tadah hujan
        tipeSIc = "570ca522-6cca-4c9a-9b83-adc7d3cc0389";

        adapterRT = new ArrayAdapter<String>(InputSudahTanamA.this, R.layout.z_spinner_list, listRT);
        adapterRT.setDropDownViewResource(R.layout.z_spinner_list);
        sp_rt.setAdapter(adapterRT);

        setDataSpinnerObat();
    }

    public void setDataSpinnerObat(){
        if (listObatKimiaLocal.size()>0){
            Collections.sort(listObatKimiaLocal);
            adapterObatKimia = new ArrayAdapter<String>(InputSudahTanamA.this, R.layout.z_spinner_list, listObatKimiaLocal);
            adapterObatKimia.setDropDownViewResource(R.layout.z_spinner_list);
            sp_obat_kimia.setAdapter(adapterObatKimia);
        }
//        if (listObatKimiaOrganik.size()>0){
//            //Collections.sort(listObatKimiaOrganik);
//            adapterObatOrganik = new ArrayAdapter<String>(InputSudahTanam.this, R.layout.z_spinner_list, listObatKimiaOrganik);
//            adapterObatOrganik.setDropDownViewResource(R.layout.z_spinner_list);
//            sp_obat_organik.setAdapter(adapterObatOrganik);
//        }
    }

    public void setFieldSistemIrigasi(){

        if(idSistemIrigasi.equalsIgnoreCase(tipeSIb)){
            ll_pompa.setVisibility(View.GONE);
            sewa = 1;
            setDataK();
        } else {
            ll_pompa.setVisibility(View.VISIBLE);
            sewa = 2;
            setDataK();
        }
    }

    private void setDataPL(){
        for (int a=0; a<=modelProfilLahan.getTotalData(); a++){
            if(modelProfilLahan.getData().get(a).getIdProfileTanah().equalsIgnoreCase(idPL)){
                namaPL = modelProfilLahan.getData().get(a).getNamaProfilTanah();
                idSistemIrigasi = modelProfilLahan.getData().get(a).getIdSistemIrigasi().toString();
                txt_profil_lahan.setText(namaPL);
                setFieldSistemIrigasi();
            }
        }
    }

    private void setDataK(){
        for (int a=0; a<=modelKomoditas.getTotalData(); a++){
            if(modelKomoditas.getData().get(a).getIdKomoditas().equalsIgnoreCase(idK)){
                namaK = modelKomoditas.getData().get(a).getNamaKomoditas();
                txt_komoditas.setText(namaK);
                setDataV();
            }
        }
    }

    private void setDataV(){
        for (int a=0; a<=modelVarietas.getTotalData(); a++){
            if(modelVarietas.getData().get(a).getIdVarietas().equalsIgnoreCase(idV)){
                namaV = modelVarietas.getData().get(a).getNamaVarietas().toString();
                txt_varietas.setText(namaV);
                setHargaSebelumnya();
            }
        }
    }

    */

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

    public void simpan(){
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
                check();
                // checkObatKimia();
                //sendDataSudahTanam();
            }
        }).start();
    }

    private void check(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                moveToC();
            }
        });
    }

    /*

    private void checkObatKimia(){
        if (idObatKimia!=null){
            sendDataObatKimia();
        } else {
            checkObatOrganik();
        }
    }

    private void sendDataObatKimia(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idObat", idObatKimia );
        jsonParams.put("hargaObat", txt_obat_kimia.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idProfilTanah", idPL);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendHargaObat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        //sendNotificationObatKimia();
                        sendKendalaPertumbuhanKimia();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal input obat kimia", Toast.LENGTH_LONG).show();
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

    public void sendKendalaPertumbuhanKimia(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        //String kendala = "Penggunaan Obat "+namaobatKimia;

        jsonParams.put("idSudahTanam", idRT );
        jsonParams.put("idProfilTanah", idPL);
        jsonParams.put("kendalaHama", namaobatKimia);
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat Kimia");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.body() != null) {
                    sendNotificationObatKimia();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputSudahTanamA.this, "Gagal input kendala pertumbuhan kimia", Toast.LENGTH_LONG).show();
                        }
                    });
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

    private void checkObatOrganik(){
        if (!txt_nama_obat_organik.getText().toString().equalsIgnoreCase("")){
            sendDataObatOrganik();
        } else {
            sendDataSudahTanam();
        }
    }

    private void sendDataObatOrganik(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSubkategori", "21bd7381-07ab-4097-94dc-7670cd6f6971" );
        jsonParams.put("namaObat", txt_nama_obat_organik.getText().toString() );
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseSendObat> response = apiInterface.sendObat(body);
        response.enqueue(new Callback<ResponseSendObat>() {
            @Override
            public void onResponse(Call<ResponseSendObat> call, retrofit2.Response<ResponseSendObat> rawResponse) {
                try {
                    if (rawResponse.body().getData().getIdObat() != null) {
                        idObatOrganik = rawResponse.body().getData().getIdObat();
                        sendDataHargaObatOrganik();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseSendObat> call, Throwable throwable) {
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

    private void sendDataHargaObatOrganik(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idObat", idObatOrganik );
        jsonParams.put("hargaObat", txt_obat_organik.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idProfilTanah", idPL);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendHargaObat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        //sendNotificationObatOrganik();
                        sendKendalaPertumbuhanOrganik();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
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

    public void sendKendalaPertumbuhanOrganik(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        // MAXIMAL ISIAN FIELD 25 KARAKTER!
        jsonParams.put("idSudahTanam", idRT );
        jsonParams.put("idProfilTanah", idPL);
        jsonParams.put("kendalaHama", txt_nama_obat_organik.getText().toString());
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat Organik");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        sendNotificationObatOrganik();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal input kendala pertumbuhan organik", Toast.LENGTH_LONG).show();
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

    private void sendDataSudahTanam(){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();


        jsonParams.put("idRencanaTanam", idRT );
        jsonParams.put("idPertumbuhanNormal", "");
        jsonParams.put("idKendalaPertumbuhan", "");
        jsonParams.put("idBiayaburuhTanam", txt_buruh_tanam.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhBajak", txt_buruh_bajak.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhSemprot", txt_buruh_semprot.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhMenyiangirumput", txt_buruh_menyiangi.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhGalangan", txt_buruh_galengan.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhPupuk", txt_buruh_pupuk.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaburuhPanen", txt_buruh_panen.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinBajak", txt_mesin_bajak.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinTanam", txt_mesin_tanam.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinPanen", txt_mesin_panen.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idSewamesinPompa", txt_pompa);
        jsonParams.put("idSewamesinPompaBbm", txt_pompabbm);
        jsonParams.put("idBiayabibitLocalHet", txt_bibit_local.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayabibitSubsidi", txt_bibit_subsidi.getText().toString().replaceAll("[^0-9]", ""));
        //jsonParams.put("idBiayapupukKimiaLocalHet", txt_pupuk_kimia_local.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukKimiaLocalHet", "");
        jsonParams.put("idBiayapupukKimiaPhonska", txt_pupuk_kimia_phonska.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukKimiaUrea", txt_pupuk_kimia_urea.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukKimiaFosfat", txt_pupuk_kimia_fosfat.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayapupukOrganik", txt_pupuk_organik.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idObatKimiaLocal", idObatKimia);
        jsonParams.put("idObatKimiaSubsidi", null);
        jsonParams.put("idObatOrganik", idObatOrganik);
        jsonParams.put("idBiayaobatKimiaLocalHet", txt_obat_kimia.getText().toString().replaceAll("[^0-9]", ""));
        jsonParams.put("idBiayaobatKimiaSubsidi", "");
        jsonParams.put("idBiayaobatOrganik", txt_obat_organik.getText().toString().replaceAll("[^0-9]", ""));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataSudahTanam(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                goToListST();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamA.this, "Gagal input sudah tanam", Toast.LENGTH_LONG).show();
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

    */

    private String checkDesimal(String a){
        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
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