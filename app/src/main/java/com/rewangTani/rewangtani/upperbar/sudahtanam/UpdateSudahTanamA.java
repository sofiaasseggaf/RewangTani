package com.rewangTani.rewangtani.upperbar.sudahtanam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.rewangTani.rewangtani.model.modelakun.DatumAkun;
import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelinfo.ModelResultNotification;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ResponseSendObat;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
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

public class UpdateSudahTanamA extends AppCompatActivity {

    EditText txt_buruh_tanam, txt_buruh_bajak, txt_buruh_semprot, txt_buruh_menyiangi, txt_buruh_galengan, txt_buruh_pupuk, txt_buruh_panen;
    EditText txt_mesin_bajak, txt_mesin_tanam, txt_mesin_panen, txt_mesin_pompa, txt_mesin_pompa_bbm, txt_durasi_mesin_pompa_bbm;
    EditText txt_bibit_local, txt_bibit_subsidi;
    EditText txt_pupuk_kimia_local, txt_pupuk_kimia_phonska, txt_pupuk_kimia_urea, txt_pupuk_kimia_fosfat, txt_pupuk_organik;
    EditText txt_obat_kimia, txt_obat_organik, txt_nama_obat_organik;
    TextView txt_buruh_tanam2, txt_buruh_bajak2, txt_buruh_semprot2, txt_buruh_menyiangi2, txt_buruh_galengan2, txt_buruh_pupuk2, txt_buruh_panen2;
    TextView txt_mesin_bajak2, txt_mesin_tanam2, txt_mesin_panen2, txt_mesin_pompa2, txt_mesin_pompa_bbm2;
    TextView txt_bibit_local2, txt_bibit_subsidi2;
    TextView txt_pupuk_kimia_phonska2, txt_pupuk_kimia_urea2, txt_pupuk_kimia_fosfat2, txt_pupuk_organik2;
    Spinner sp_obat_organik, sp_obat_kimia;
    ImageButton btn_simpan, btn_batal;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    ModelObat modelObat;
    ModelAkun modelAkun;
    List<DatumAkun> listAkunwithToken = new ArrayList<>();
    ModelProfilAkun modelProfilAkun;
    List<String> listIDAkunwithAlamat = new ArrayList<>();
    List<String> listToken = new ArrayList<String>();
    List<String> listObatKimiaLocal = new ArrayList<>();
    List<String> listObatKimiaOrganik = new ArrayList<>();
    List<String> listObatSemua = new ArrayList<>();
    ArrayAdapter<String> adapterObatKimia, adapterObatOrganik;
    TextView txtload, txt_nama_rt, txt_profil_lahan, txt_komoditas, txt_varietas, txt_estimasi_rab, txt_estimasi_hasil;
    String namaRT, namaPL ,namaKomoditas, namaVarietas, id;
    String idObatKimia=null;
    String idObatOrganik=null;
    String tipeSIa, tipeSIb, tipeSIc, idSistemIrigasi, txt_pompa, txt_pompabbm;
    LinearLayout ll_pompa;
    Integer sewa;
    DecimalFormat formatter;
    Integer hargaBBM;
    String namaobatKimia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_st_update_sudah_tanam_a);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_nama_rt = findViewById(R.id.txt_nama_rt);
        txt_profil_lahan = findViewById(R.id.txt_profil_lahan);
        txt_komoditas = findViewById(R.id.txt_komoditas);
        txt_varietas = findViewById(R.id.txt_varietas);
        txt_estimasi_rab = findViewById(R.id.txt_estimasi_rab);
        txt_estimasi_hasil = findViewById(R.id.txt_estimasi_hasil);
        txt_buruh_tanam = findViewById(R.id.txt_buruh_tanam);
        txt_buruh_bajak = findViewById(R.id.txt_buruh_bajak);
        txt_buruh_semprot = findViewById(R.id.txt_buruh_semprot);
        txt_buruh_menyiangi = findViewById(R.id.txt_buruh_menyiangi);
        txt_buruh_galengan = findViewById(R.id.txt_buruh_galengan);
        txt_buruh_pupuk = findViewById(R.id.txt_buruh_pupuk);
        txt_buruh_panen = findViewById(R.id.txt_buruh_panen);
        txt_mesin_bajak = findViewById(R.id.txt_mesin_bajak);
        txt_mesin_panen = findViewById(R.id.txt_mesin_panen);
        txt_mesin_tanam = findViewById(R.id.txt_mesin_tanam);
        txt_mesin_pompa = findViewById(R.id.txt_mesin_pompa);
        txt_mesin_pompa_bbm = findViewById(R.id.txt_mesin_pompa_bbm);
        txt_durasi_mesin_pompa_bbm = findViewById(R.id.txt_durasi_mesin_pompa_bbm);
        txt_bibit_local = findViewById(R.id.txt_bibit_local);
        txt_bibit_subsidi = findViewById(R.id.txt_bibit_subsidi);
        //txt_pupuk_kimia_local = findViewById(R.id.txt_pupuk_kimia_local);
        txt_pupuk_kimia_phonska = findViewById(R.id.txt_pupuk_kimia_phonska);
        txt_pupuk_kimia_urea = findViewById(R.id.txt_pupuk_kimia_urea);
        txt_pupuk_kimia_fosfat = findViewById(R.id.txt_pupuk_kimia_fosfat);
        txt_pupuk_organik = findViewById(R.id.txt_pupuk_organik);
        txt_nama_obat_organik = findViewById(R.id.txt_nama_obat_organik);
        sp_obat_kimia = findViewById(R.id.sp_obat_kimia);
        txt_obat_kimia = findViewById(R.id.txt_obat_kimia);
        txt_obat_organik = findViewById(R.id.txt_obat_organik);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);
        txtload = findViewById(R.id.textloading);
        ll_pompa = findViewById(R.id.ll_pompa);

        txt_buruh_tanam2 = findViewById(R.id.txt_buruh_tanam2);
        txt_buruh_bajak2 = findViewById(R.id.txt_buruh_bajak2);
        txt_buruh_semprot2 = findViewById(R.id.txt_buruh_semprot2);
        txt_buruh_menyiangi2 = findViewById(R.id.txt_buruh_menyiangi2);
        txt_buruh_galengan2 = findViewById(R.id.txt_buruh_galengan2);
        txt_buruh_pupuk2 = findViewById(R.id.txt_buruh_pupuk2);
        txt_buruh_panen2 = findViewById(R.id.txt_buruh_panen2);
        txt_mesin_bajak2 = findViewById(R.id.txt_mesin_bajak2);
        txt_mesin_panen2 = findViewById(R.id.txt_mesin_panen2);
        txt_mesin_tanam2 = findViewById(R.id.txt_mesin_tanam2);
        txt_mesin_pompa2 = findViewById(R.id.txt_mesin_pompa2);
        txt_mesin_pompa_bbm2 = findViewById(R.id.txt_mesin_pompa_bbm2);
        txt_bibit_local2 = findViewById(R.id.txt_bibit_local2);
        txt_bibit_subsidi2 = findViewById(R.id.txt_bibit_subsidi2);
        //txt_pupuk_kimia_local2 = findViewById(R.id.txt_pupuk_kimia_local2);
        txt_pupuk_kimia_phonska2 = findViewById(R.id.txt_pupuk_kimia_phonska2);
        txt_pupuk_kimia_urea2 = findViewById(R.id.txt_pupuk_kimia_urea2);
        txt_pupuk_kimia_fosfat2 = findViewById(R.id.txt_pupuk_kimia_fosfat2);
        txt_pupuk_organik2 = findViewById(R.id.txt_pupuk_organik2);

        getData();

        txt_buruh_tanam.addTextChangedListener(new NumberTextWatcher(txt_buruh_tanam));
        txt_buruh_bajak.addTextChangedListener(new NumberTextWatcher(txt_buruh_bajak));
        txt_buruh_semprot.addTextChangedListener(new NumberTextWatcher(txt_buruh_semprot));
        txt_buruh_menyiangi.addTextChangedListener(new NumberTextWatcher(txt_buruh_menyiangi));
        txt_buruh_galengan.addTextChangedListener(new NumberTextWatcher(txt_buruh_galengan));
        txt_buruh_pupuk.addTextChangedListener(new NumberTextWatcher(txt_buruh_pupuk));
        txt_buruh_panen.addTextChangedListener(new NumberTextWatcher(txt_buruh_panen));
        txt_mesin_bajak.addTextChangedListener(new NumberTextWatcher(txt_mesin_bajak));
        txt_mesin_tanam.addTextChangedListener(new NumberTextWatcher(txt_mesin_tanam));
        txt_mesin_panen.addTextChangedListener(new NumberTextWatcher(txt_mesin_panen));
        txt_mesin_pompa.addTextChangedListener(new NumberTextWatcher(txt_mesin_pompa));
        txt_mesin_pompa_bbm.addTextChangedListener(new NumberTextWatcher(txt_mesin_pompa_bbm));
        txt_bibit_local.addTextChangedListener(new NumberTextWatcher(txt_bibit_local));
        txt_bibit_subsidi.addTextChangedListener(new NumberTextWatcher(txt_bibit_subsidi));
        //txt_pupuk_kimia_local.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_local));
        txt_pupuk_kimia_phonska.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_phonska));
        txt_pupuk_kimia_urea.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_urea));
        txt_pupuk_kimia_fosfat.addTextChangedListener(new NumberTextWatcher(txt_pupuk_kimia_fosfat));
        txt_pupuk_organik.addTextChangedListener(new NumberTextWatcher(txt_pupuk_organik));
        txt_obat_kimia.addTextChangedListener(new NumberTextWatcher(txt_obat_kimia));
        txt_obat_organik.addTextChangedListener(new NumberTextWatcher(txt_obat_organik));

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

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
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sewa==1){
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
                            Toast.makeText(UpdateSudahTanamA.this, "Masukan durasi sewa mesin pompa", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        txt_pompabbm = "";
                        simpan();
                    }

                }
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                                namaRT = modelRencanaTanam.getData().get(i).getNamaRencanaTanam();
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                            }
                        }
                        if (dataRencanaTanam!=null){
                            getDataProfilLahan();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(UpdateSudahTanamA.this, "Data rencana tanam tidak ada", Toast.LENGTH_LONG).show();
                                    call.cancel();
                                }
                            });
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
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataProfilLahan() {

        // sumur bor
        tipeSIa = "10a9631e-6add-459e-b7e2-aed3a0c907df";
        // permukaan
        tipeSIb = "26b145d3-632b-4f59-8571-b85a993169b3";
        // tadah hujan
        tipeSIc = "570ca522-6cca-4c9a-9b83-adc7d3cc0389";


        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        try {
                            if (modelProfilLahan.getData().get(i).getIdProfileTanah().equalsIgnoreCase(dataRencanaTanam.getIdProfilTanah())) {
                                namaPL = modelProfilLahan.getData().get(i).getNamaProfilTanah();
                                idSistemIrigasi = modelProfilLahan.getData().get(i).getIdSistemIrigasi().toString();
                            }
                        } catch (Exception e){ }
                    }
                    if (!namaPL.equalsIgnoreCase("")){
                        getDataAkun();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Data profil lahan tidak ada", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
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
//                            listToken.add(modelAkun.getData().get(i).getToken());
                        }
                    }
                    if (listAkunwithToken!=null){
                        getDataAlamat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Data token tidak ditemukan", Toast.LENGTH_SHORT).show();
                                setFieldSistemIrigasi();

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                                        setFieldSistemIrigasi();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    setFieldSistemIrigasi();
                                    //Toast.makeText(UpdateSudahTanam.this, "Data Token Tidak Ditemukan", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    } catch (Exception e){ }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    public void setFieldSistemIrigasi(){

        if(idSistemIrigasi.equalsIgnoreCase(tipeSIb)){
            ll_pompa.setVisibility(View.GONE);
            sewa = 1;
            getDataKomoditas();
        } else {
            ll_pompa.setVisibility(View.VISIBLE);
            sewa = 2;
            getDataKomoditas();
        }
    }

    public void getDataKomoditas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKomoditas> dataK = apiInterface.getDataKomoditas();
        dataK.enqueue(new Callback<ModelKomoditas>() {
            @Override
            public void onResponse(Call<ModelKomoditas> call, Response<ModelKomoditas> response) {
                modelKomoditas = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelKomoditas.getTotalData(); i++) {
                        try {
                            if (modelKomoditas.getData().get(i).getIdKomoditas().equalsIgnoreCase(dataRencanaTanam.getIdKomoditas())) {
                                namaKomoditas = modelKomoditas.getData().get(i).getNamaKomoditas(); }
                        } catch (Exception e){ }
                    }
                    if (!namaKomoditas.equalsIgnoreCase("")){
                        getDataVarietas();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Data komoditas tidak ada", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < modelVarietas.getTotalData(); i++) {
                        try {
                            if (modelVarietas.getData().get(i).getIdVarietas().equalsIgnoreCase(dataRencanaTanam.getIdVarietas())) {
                                namaVarietas = modelVarietas.getData().get(i).getNamaVarietas().toString(); }
                        } catch (Exception e){ }
                    }
                    if (!namaVarietas.equalsIgnoreCase("")){
                        getDataObat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Data varietas tidak ada", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataSpinnerObat();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelObat> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataSpinnerObat(){
        if (listObatKimiaLocal.size()>0){
            Collections.sort(listObatKimiaLocal);
            adapterObatKimia = new ArrayAdapter<String>(UpdateSudahTanamA.this, R.layout.z_spinner_list, listObatKimiaLocal);
            adapterObatKimia.setDropDownViewResource(R.layout.z_spinner_list);
            sp_obat_kimia.setAdapter(adapterObatKimia);
        }

       /* if (listObatKimiaOrganik.size()>0){
            //Collections.sort(listObatKimiaOrganik);
            adapterObatOrganik = new ArrayAdapter<String>(UpdateSudahTanam.this, R.layout.z_spinner_list, listObatKimiaOrganik);
            adapterObatOrganik.setDropDownViewResource(R.layout.z_spinner_list);
            sp_obat_organik.setAdapter(adapterObatOrganik);
        }*/

        setData();
    }

    public void setData() {
        txt_nama_rt.setText(namaRT);
        txt_profil_lahan.setText(namaPL);
        txt_komoditas.setText(namaKomoditas);
        txt_varietas.setText(namaVarietas);

        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam2.setText("Biaya Sebelumnya : Rp. "+a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak2.setText("Biaya Sebelumnya : Rp. "+b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot2.setText("Biaya Sebelumnya : Rp. "+c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi2.setText("Biaya Sebelumnya : Rp. "+d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan2.setText("Biaya Sebelumnya : Rp. "+e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk2.setText("Biaya Sebelumnya : Rp. "+f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen2.setText("Biaya Sebelumnya : Rp. "+g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak2.setText("Biaya Sebelumnya : Rp. "+h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen2.setText("Biaya Sebelumnya : Rp. "+i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam2.setText("Biaya Sebelumnya : Rp. "+j);

        if(dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa2.setText("Biaya Sebelumnya : Rp. "+k);
        } else {
            txt_mesin_pompa2.setText("-");
        }

        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm2.setText("Biaya Sebelumnya : Rp. "+l);
        } else {
            txt_mesin_pompa_bbm2.setText("-");
        }

        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local2.setText("Biaya Sebelumnya : Rp. "+m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi2.setText("Biaya Sebelumnya : Rp. "+n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local.setText(o);
        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska2.setText("Biaya Sebelumnya : Rp. "+p);
        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea2.setText("Biaya Sebelumnya : Rp. "+q);
        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat2.setText("Biaya Sebelumnya : Rp. "+r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik2.setText("Biaya Sebelumnya : Rp. "+s);

    }

    public void simpan(){
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
                checkObatKimia();
            }
        }).start();
    }

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
        jsonParams.put("idProfilTanah", dataRencanaTanam.getIdProfilTanah());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendHargaObat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        sendKendalaPertumbuhanKimia();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal input obat kimia", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void sendKendalaPertumbuhanKimia(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        // MAXIMAL ISIAN FIELD 25 KARAKTER!
        jsonParams.put("idSudahTanam", dataRencanaTanam.getIdRencanaTanam() );
        jsonParams.put("idProfilTanah", dataRencanaTanam.getIdProfilTanah());
        jsonParams.put("kendalaHama", namaobatKimia);
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        sendNotificationObatKimia();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal input kendala pertumbuhan kimia", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        try {
                            if (listToken.size()>0){
                                for (int i=0; i<listToken.size(); i++){
                                    sendNotifObatKimia(listToken.get(i));
                                }
                                checkObatOrganik();
                            } else {
                                checkObatOrganik();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal buat info obat kimia", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(UpdateSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                                }
                            });*/
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ModelResultNotification> call, Throwable throwable) {
              //  Toast.makeText(UpdateSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void checkObatOrganik(){
        if (idObatOrganik!=null){
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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
        jsonParams.put("idProfilTanah", dataRencanaTanam.getIdProfilTanah());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendHargaObat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        sendKendalaPertumbuhanOrganik();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
        jsonParams.put("idSudahTanam", dataRencanaTanam.getIdRencanaTanam() );
        jsonParams.put("idProfilTanah", dataRencanaTanam.getIdProfilTanah());
        jsonParams.put("kendalaHama", txt_nama_obat_organik.getText().toString());
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat");

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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal input kendala pertumbuhan organik", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        try {
                            if (listToken.size()>0){
                                for (int i=0; i<listToken.size(); i++){
                                    sendNotifObatOrganik(listToken.get(i));
                                }
                                sendDataSudahTanam();
                            } else {
                                sendDataSudahTanam();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal buat info obat organik", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
/*                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(InputSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                                }
                            });*/
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ModelResultNotification> call, Throwable throwable) {
               // Toast.makeText(UpdateSudahTanam.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void sendDataSudahTanam(){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idRencanaTanam", dataRencanaTanam.getIdRencanaTanam());
        jsonParams.put("idPertumbuhanNormal", " ");
        jsonParams.put("idKendalaPertumbuhan", " ");
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
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                goToListST();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(UpdateSudahTanamA.this, "Gagal update sudah tanam", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(UpdateSudahTanamA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
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

    public  void goToListST(){
        Intent a = new Intent(UpdateSudahTanamA.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal update sudah tanam ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListST();
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
    }
}