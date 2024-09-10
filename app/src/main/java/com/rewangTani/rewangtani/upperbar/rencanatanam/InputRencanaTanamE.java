package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamEBinding;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.DatumOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ResponseRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputRencanaTanamE extends AppCompatActivity {

    UpperbarRtInputRencanaTanamEBinding binding;
    DatumOutputRencanaTanam dataOutputRT;
    boolean isWithPompa;
    String luasLahan, potensiHasilVarietas, txtPompa, txtPompaBbm;
    double total, hasil, pendapatan;

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
                            if (!binding.pupukKimiaLokal.getText().toString().equalsIgnoreCase("") && !binding.pupukOrganik.getText().toString().equalsIgnoreCase("")) {
                                saveLocalData();
                            }
                        }
                    })

                    .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

    }

    private void saveLocalData() {
        luasLahan = ListRencanaTanam.getInstance().getDatumRencanaTanam().getLuasLahan();
        potensiHasilVarietas = ListRencanaTanam.getInstance().getDatumRencanaTanam().getPotensiHasilVarietas();
        if (ListRencanaTanam.getInstance().getDatumRencanaTanam().isWithPompa()) {
            isWithPompa = true;
        } else {
            isWithPompa = false;
        }

        DatumRencanaTanam datumRencanaTanam = new DatumRencanaTanam("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", "", binding.pupukKimiaLokal.getText().toString().replaceAll("[^0-9]", ""), "", binding.pupukOrganik.getText().toString().replaceAll("[^0-9]",
                ""), "", "", isWithPompa, luasLahan, potensiHasilVarietas);
        ListRencanaTanam.getInstance().setDetailRencanaTanam(datumRencanaTanam);
        startCountTotal();
//        moveToRAB();
    }

    private void startCountTotal() {
        DatumRencanaTanam datumRencanaTanam = ListRencanaTanam.getInstance().getDatumRencanaTanam();
        if (datumRencanaTanam != null) {
            findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
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
                    countTotal(datumRencanaTanam);
                }
            }).start();
        }
    }

    private void countTotal(DatumRencanaTanam datumRencanaTanam) {

        int a = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhTanam().replaceAll("[^0-9]", ""));
        int b = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhBajak().replaceAll("[^0-9]", ""));
        int c = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhSemprot().replaceAll("[^0-9]", ""));
        int d = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhMenyiangirumput().toString().replaceAll("[^0-9]", ""));
        int e = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhGalangan().replaceAll("[^0-9]", ""));
        int f = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhPupuk().replaceAll("[^0-9]", ""));
        int g = Integer.valueOf(datumRencanaTanam.getIdBiayaBuruhPanen().replaceAll("[^0-9]", ""));

        int h = Integer.valueOf(datumRencanaTanam.getIdSewaMesinBajak().replaceAll("[^0-9]", ""));
        int i = Integer.valueOf(datumRencanaTanam.getIdSewaMesinTanam().replaceAll("[^0-9]", ""));
        int j = Integer.valueOf(datumRencanaTanam.getIdSewaMesinPanen().replaceAll("[^0-9]", ""));
        int k = Integer.valueOf(datumRencanaTanam.getIdSewamesinPompa().replaceAll("[^0-9]", ""));
        int l = Integer.valueOf(datumRencanaTanam.getIdSewamesinPompaBbm().replaceAll("[^0-9]", ""));

        int m = Integer.valueOf(datumRencanaTanam.getIdBiayabibitLocalHet().replaceAll("[^0-9]", ""));
        int n = Integer.valueOf(datumRencanaTanam.getIdBiayabibitSubsidi().replaceAll("[^0-9]", ""));

        int o = Integer.valueOf(datumRencanaTanam.getIdBiayapupukKimiaLocalHet().replaceAll("[^0-9]", ""));
        int p = Integer.valueOf(datumRencanaTanam.getIdBiayapupukKimiaPhonska().replaceAll("[^0-9]", ""));
        int q = Integer.valueOf(datumRencanaTanam.getIdBiayapupukKimiaUrea().replaceAll("[^0-9]", ""));
        int r = Integer.valueOf(datumRencanaTanam.getIdBiayapupukKimiaFosfat().replaceAll("[^0-9]", ""));
        int s = Integer.valueOf(datumRencanaTanam.getIdBiayapupukOrganik().replaceAll("[^0-9]", ""));

        int obat = 1000000;
        int luaslahan = Integer.valueOf(luasLahan.replaceAll("[^0-9]", ""));
        double hektar = Double.valueOf(luasLahan) / 10000;

        Double estimasihasil = Double.valueOf(potensiHasilVarietas);
        hasil = estimasihasil * luaslahan / 10;
        pendapatan = 10000 * hasil;

        if (isWithPompa) {
            int durasi = Integer.valueOf(datumRencanaTanam.getIdSewamesinPompaBbm().replaceAll("[^0-9]", "")) * 2;
            double bbm = l * durasi * hektar;
            txtPompa = datumRencanaTanam.getIdSewamesinPompa().replaceAll("[^0-9]", "");
            txtPompaBbm = String.valueOf(bbm);
            total = a + b + c + d + e + f + g + h + i + j + k + bbm + m + n + o + p + q + r + s + obat;
        } else {
            total = a+b+c+d+e+f+g+h+i+j+m+n+p+q+r+s+obat;
            pendapatan = 10000 * hasil;
            txtPompa = "0";
            txtPompaBbm = "0";
        }

        sendDataRencanaTanam(datumRencanaTanam);
    }

    private void sendDataRencanaTanam(DatumRencanaTanam datumRencanaTanam){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idUser", datumRencanaTanam.getIdUser());
        jsonParams.put("idProfil", datumRencanaTanam.getIdProfil());
        jsonParams.put("idProfilTanah", datumRencanaTanam.getIdProfilTanah());
        jsonParams.put("idKomoditas", datumRencanaTanam.getIdKomoditas());
        jsonParams.put("idVarietas", datumRencanaTanam.getIdVarietas());
        jsonParams.put("idBiayaBuruhTanam", datumRencanaTanam.getIdBiayaBuruhTanam());
        jsonParams.put("idBiayaBuruhBajak", datumRencanaTanam.getIdBiayaBuruhBajak());
        jsonParams.put("idBiayaBuruhSemprot", datumRencanaTanam.getIdBiayaBuruhSemprot());
        jsonParams.put("idBiayaBuruhMenyiangirumput", datumRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        jsonParams.put("idBiayaBuruhGalangan", datumRencanaTanam.getIdBiayaBuruhGalangan());
        jsonParams.put("idBiayaBuruhPupuk", datumRencanaTanam.getIdBiayaBuruhPupuk());
        jsonParams.put("idBiayaBuruhPanen", datumRencanaTanam.getIdBiayaBuruhPanen());
        jsonParams.put("idSewaMesinBajak", datumRencanaTanam.getIdSewaMesinBajak());
        jsonParams.put("idSewaMesinTanam", datumRencanaTanam.getIdSewaMesinTanam());
        jsonParams.put("idSewaMesinPanen", datumRencanaTanam.getIdSewaMesinPanen());
        jsonParams.put("idBiayabibitLocalHet", datumRencanaTanam.getIdBiayabibitLocalHet());
        jsonParams.put("idBiayabibitSubsidi", datumRencanaTanam.getIdBiayabibitSubsidi());
        jsonParams.put("idBiayapupukKimiaLocalHet", datumRencanaTanam.getIdBiayabibitLocalHet());
        jsonParams.put("idBiayapupukKimiaPhonska", datumRencanaTanam.getIdBiayapupukKimiaPhonska());
        jsonParams.put("idBiayapupukOrganik", datumRencanaTanam.getIdBiayapupukOrganik());
        jsonParams.put("namaRencanaTanam", datumRencanaTanam.getNamaRencanaTanam());
        jsonParams.put("idBiayapupukKimiaUrea", datumRencanaTanam.getIdBiayapupukKimiaUrea());
        jsonParams.put("idBiayapupukKimiaFosfat", datumRencanaTanam.getIdBiayapupukKimiaFosfat());
        jsonParams.put("idSewamesinPompa", txtPompa);
        jsonParams.put("idSewamesinPompaBbm", txtPompaBbm);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseRencanaTanam> response = apiInterface.sendDataRencanaTanam(body);
        response.enqueue(new Callback<ResponseRencanaTanam>() {
            @Override
            public void onResponse(Call<ResponseRencanaTanam> call, retrofit2.Response<ResponseRencanaTanam> rawResponse) {
                try {
                    Log.d("SOFIA", String.valueOf(rawResponse.body()));
                    if (rawResponse.body() != null) {
                        ResponseRencanaTanam modelRencanaTanam = rawResponse.body();
                        try{
                            String idRT = modelRencanaTanam.getData().getIdRencanaTanam();
                            if (idRT!=null){
                                PreferenceUtils.saveIdRt(modelRencanaTanam.getData().getIdRencanaTanam(), getApplicationContext());
                                sendOutput();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                        Toast.makeText(InputRencanaTanamE.this, "Rencana tanam tidak ditemukan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e){ }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamE.this, "Gagal buat rencana tanam", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseRencanaTanam> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamE.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendOutput(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idRencanaTanam", PreferenceUtils.getIdRt(getApplicationContext()));
        jsonParams.put("estBiayaProduksi", String.valueOf(total));
        jsonParams.put("estHasil", hasil);
        jsonParams.put("estPendapatan", pendapatan);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataOutputRT(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getOutput();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputRencanaTanamE.this, "Gagal buat rencana tanam", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputRencanaTanamE.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getOutput() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.getDataOutputRT();
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                ModelOutputRencanaTanam modelOutputRencanaTanam = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                            if(modelOutputRencanaTanam.getData().get(i).getIdRencanaTanam()
                                    .equalsIgnoreCase(PreferenceUtils.getIdRt(getApplicationContext()))){
                                dataOutputRT = modelOutputRencanaTanam.getData().get(i);
                                PreferenceUtils.saveOIdRt(dataOutputRT.getIdOutputRencanaTanam(), getApplicationContext());
                                moveToRAB();
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputRencanaTanamE.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
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