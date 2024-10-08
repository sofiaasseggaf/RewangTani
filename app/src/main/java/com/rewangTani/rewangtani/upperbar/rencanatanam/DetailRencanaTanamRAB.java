package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamFBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRencanaTanamRAB extends AppCompatActivity {

    UpperbarRtInputRencanaTanamFBinding binding;
    String namaPL, namaKomoditas, namaVarietas, est_biaya, est_hasil;
    ModelRencanaTanam modelRencanaTanam;
    DatumRencanaTanam dataRencanaTanam;
    ModelProfilLahan modelProfilLahan;
    ModelKomoditas modelKomoditas;
    ModelVarietas modelVarietas;
    ModelOutputRencanaTanam modelOutputRencanaTanam;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_f);

        getData();

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
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
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
                            String id = modelRencanaTanam.getData().get(i).getIdRencanaTanam();
                            if (modelRencanaTanam.getData().get(i).getIdRencanaTanam().equalsIgnoreCase(PreferenceUtils.getIdRt(getApplicationContext()))) {
                                dataRencanaTanam = modelRencanaTanam.getData().get(i);
                                if (dataRencanaTanam != null) {
                                    getDataProfilLahan();
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                if (response.body() != null) {
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        try {
                            if (modelProfilLahan.getData().get(i).getIdProfileTanah().equalsIgnoreCase(dataRencanaTanam.getIdProfilTanah())) {
                                namaPL = modelProfilLahan.getData().get(i).getNamaProfilTanah();
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (!namaPL.equalsIgnoreCase("")) {
                        getDataKomoditas();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
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
                if (response.body() != null) {
                    for (int i = 0; i < modelKomoditas.getTotalData(); i++) {
                        try {
                            if (modelKomoditas.getData().get(i).getIdKomoditas().equalsIgnoreCase(dataRencanaTanam.getIdKomoditas())) {
                                namaKomoditas = modelKomoditas.getData().get(i).getNamaKomoditas();
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (!namaKomoditas.equalsIgnoreCase("")) {
                        getDataVarietas();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelKomoditas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                if (response.body() != null) {
                    for (int i = 0; i < modelVarietas.getTotalData(); i++) {
                        try {
                            if (modelVarietas.getData().get(i).getIdVarietas().equalsIgnoreCase(dataRencanaTanam.getIdVarietas())) {
                                namaVarietas = modelVarietas.getData().get(i).getNamaVarietas().toString();
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (!namaVarietas.equalsIgnoreCase("")) {
                        getDataOutput();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVarietas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataOutput() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataV = apiInterface.getDataOutputRT();
        dataV.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body() != null) {
                    for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                        try {
                            if (modelOutputRencanaTanam.getData().get(i).getIdOutputRencanaTanam().equalsIgnoreCase(PreferenceUtils.getOIdRt(getApplicationContext()))) {
                                est_biaya = modelOutputRencanaTanam.getData().get(i).getEstBiayaProduksi();
                                est_hasil = modelOutputRencanaTanam.getData().get(i).getEstHasil().toString();
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (!est_biaya.equalsIgnoreCase("")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private String checkDesimal(String a) {
        if (a != null || !a.equalsIgnoreCase("")) {
            if (a.length() > 3) {
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public void setData() {
        //txt_nama.setText(dataRencanaTanam.getNamaRencanaTanam());
        //txt_profil_lahan.setText(namaPL);
        //txt_komoditas.setText(namaKomoditas);
        //txt_varietas.setText(namaVarietas);
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

        // INI TAMBAHIN OY !

        if (!dataRencanaTanam.getIdSewamesinPompa().equalsIgnoreCase("0")) {
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            binding.mesinPompa.setText(k);
        } else {
            binding.mesinPompa.setText("-");
        }
        if (!dataRencanaTanam.getIdSewamesinPompaBbm().equalsIgnoreCase("0")) {
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            binding.bbmMesinPompa.setText(l);
        } else {
            binding.bbmMesinPompa.setText("-");
        }

        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        binding.bibitLokal.setText(m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        binding.bibitSubsidi.setText(n);

        String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        binding.pupukKimiaLokal.setText(o);
//        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
//        binding.pupukKimiaPhonska.setText(p);
//        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
//        binding.pupukKimiaUrea.setText(q);
//        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
//        binding.pupukKimiaFosfat.setText(r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        binding.pupukOrganik.setText(s);

        est_biaya = formatter.format(Double.valueOf(est_biaya).longValue());
        binding.estimasiBiayaProduksi.setText(est_biaya);
        binding.estimasiHasilTanam.setText(est_hasil + " Kg");

        ListRencanaTanam.getInstance().initializeNewRencanaTanam();

    }

    public void deleteRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.deleteRencanaTanam(PreferenceUtils.getIdRt(getApplicationContext()));
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body() != null) {
                    PreferenceUtils.saveIdRt("", getApplicationContext());
                    deleteOutputRencanaTanam();
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void deleteOutputRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.deleteOutputRT(PreferenceUtils.getOIdRt(getApplicationContext()));
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body() != null) {
                    PreferenceUtils.saveOIdRt("", getApplicationContext());
                    goToInputRT();
                }
            }

            @Override
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(DetailRencanaTanamRAB.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }


    public void goToInputRT() {
        Intent a = new Intent(DetailRencanaTanamRAB.this, InputRencanaTanamA.class);
        startActivity(a);
    }

    public void goToListRT() {
        Intent a = new Intent(DetailRencanaTanamRAB.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToListRT();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Edit kembali rencana tanam ?")
//                .setCancelable(false)
//                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        deleteRencanaTanam();
//                    }
//                })
//
//                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alertDialog=builder.create();
//        alertDialog.show();
    }
}