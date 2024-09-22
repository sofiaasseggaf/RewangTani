package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamFBinding;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ResponseSendObat;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputSudahTanamF extends AppCompatActivity {

    UpperbarStInputSudahTanamFBinding binding;
    ModelObat modelObat;
    List<String> listObatKimiaLocal = new ArrayList<>();
    List<String> listObatKimiaOrganik = new ArrayList<>();
    List<String> listObatSemua = new ArrayList<>();
    ArrayAdapter<String> adapterObatKimia;
    String namaobatKimia;
    String idObatKimia = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_f);

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnHargaPupuk.getLeft(), binding.btnHargaPupuk.getTop());
            }
        });

        binding.obatKimia.addTextChangedListener(new NumberTextWatcher(binding.obatKimia));
        binding.obatOrganik.addTextChangedListener(new NumberTextWatcher(binding.obatOrganik));

        binding.spObatKimia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namaobatKimia = binding.spObatKimia.getSelectedItem().toString();
                for (int a = 0; a < modelObat.getTotalData(); a++) {
                    try {
                        if (modelObat.getData().get(a).getNamaObat().equalsIgnoreCase(namaobatKimia)) {
                            idObatKimia = modelObat.getData().get(a).getIdObat();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnSimpan.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Simpan Sudah Tanam ?")
                    .setCancelable(false)
                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            saveLocalData(false, "");
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
                getDataObat();
            }
        }).start();
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
                if (response.body() != null) {
                    for (int i = 0; i < modelObat.getTotalData(); i++) {
                        listObatSemua.add(modelObat.getData().get(i).getNamaObat());
                        try {
                            if (modelObat.getData().get(i).getIdSubkategori().equalsIgnoreCase("4b81bee1-6988-43c8-8c44-350539054f21")) {
                                listObatKimiaLocal.add(modelObat.getData().get(i).getNamaObat());
                            } else if (modelObat.getData().get(i).getIdSubkategori().equalsIgnoreCase("21bd7381-07ab-4097-94dc-7670cd6f6971")) {
                                listObatKimiaOrganik.add(modelObat.getData().get(i).getNamaObat());
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        setDataSpinnerObat();
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelObat> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataSpinnerObat() {
        if (listObatKimiaLocal.size() > 0) {
            Collections.sort(listObatKimiaLocal);
            adapterObatKimia = new ArrayAdapter<String>(InputSudahTanamF.this, R.layout.z_spinner_list, listObatKimiaLocal);
            adapterObatKimia.setDropDownViewResource(R.layout.z_spinner_list);
            binding.spObatKimia.setAdapter(adapterObatKimia);
        }
    }

    private void saveLocalData(boolean isSaveIdObatOrganik, String idObatOrganik) {
        boolean isWithPompa;
        String biayaObatKimia = "";
        String namaObatOrganik = "";
        String biayaObatOrganik = "";

        if (ListSudahTanam.getInstance().getDatumSudahTanam().isWithPompa()) {
            isWithPompa = true;
        } else {
            isWithPompa = false;
        }

        if (isSaveIdObatOrganik) {
            DatumSudahTanam datumSudahTanam = new DatumSudahTanam("", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", idObatOrganik, "", "", "", isWithPompa, "");
            ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        } else {

            if (binding.obatKimia.getText().toString().equalsIgnoreCase("")) {
                idObatKimia = "";
                biayaObatKimia = "";
            } else {
                biayaObatKimia = binding.obatKimia.getText().toString().replaceAll("[^0-9]", "");
            }

            if (!binding.obatOrganik.getText().toString().equalsIgnoreCase("") && !binding.namaObatOrganik.getText().toString().equalsIgnoreCase("")) {
                namaObatOrganik = binding.namaObatOrganik.getText().toString();
                biayaObatOrganik = binding.obatOrganik.getText().toString().replaceAll("[^0-9]", "");
            }

            DatumSudahTanam datumSudahTanam = new DatumSudahTanam("", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", idObatKimia, "", "", biayaObatKimia, "", biayaObatOrganik, isWithPompa, namaObatOrganik);
            ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
            startSendData();

        }

    }

    private void startSendData() {
        DatumSudahTanam datumSudahTanam = ListSudahTanam.getInstance().getDatumSudahTanam();
        if (datumSudahTanam != null) {
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
                    checkObatKimia(datumSudahTanam);
                }
            }).start();
        }
    }

    private void checkObatKimia(DatumSudahTanam datumSudahTanam) {
        if (!datumSudahTanam.getIdObatKimiaLocal().equalsIgnoreCase("")) {
            sendDataHargaObatKimia(datumSudahTanam);
        } else {
            checkObatOrganik(datumSudahTanam);
        }
    }

    private void sendDataHargaObatKimia(DatumSudahTanam datumSudahTanam) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idObat", datumSudahTanam.getIdObatKimiaLocal());
        jsonParams.put("hargaObat", datumSudahTanam.getIdBiayaobatKimiaLocalHet());
        jsonParams.put("ketObat", "");
        jsonParams.put("idProfilTanah", datumSudahTanam.getIdProfilLahan());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseBody> response = apiInterface.sendHargaObat(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        sendKendalaPertumbuhanKimia(datumSudahTanam);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamF.this, "Gagal input data obat kimia", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendKendalaPertumbuhanKimia(DatumSudahTanam datumSudahTanam) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSudahTanam", datumSudahTanam.getIdRencanaTanam());
        jsonParams.put("idProfilTanah", datumSudahTanam.getIdProfilLahan());
        jsonParams.put("kendalaHama", "-");
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat Kimia = " + namaobatKimia);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.body() != null) {
//                    sendNotificationObatKimia();
                    checkObatOrganik(datumSudahTanam);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputSudahTanamF.this, "Gagal input kendala pertumbuhan kimia", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void checkObatOrganik(DatumSudahTanam datumSudahTanam) {
        if (!datumSudahTanam.getNamaObatOrganik().equalsIgnoreCase("") && !datumSudahTanam.getIdBiayaobatOrganik().equalsIgnoreCase("")) {
            sendDataObatOrganik(datumSudahTanam);
        } else {
            sendDataSudahTanam(datumSudahTanam);
        }
    }

    private void sendDataObatOrganik(DatumSudahTanam datumSudahTanam) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSubkategori", "21bd7381-07ab-4097-94dc-7670cd6f6971");
        jsonParams.put("namaObat", datumSudahTanam.getNamaObatOrganik());
        jsonParams.put("kandunganObat", "-");
        jsonParams.put("kegunaanObat", "-");
        jsonParams.put("produsenObat", "-");
        jsonParams.put("ketObat", "-");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        Call<ResponseSendObat> response = apiInterface.sendObat(body);
        response.enqueue(new Callback<ResponseSendObat>() {
            @Override
            public void onResponse(Call<ResponseSendObat> call, retrofit2.Response<ResponseSendObat> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        if (rawResponse.body().getData().getIdObat() != null) {
                            String idObatOrganik = rawResponse.body().getData().getIdObat();
                            saveLocalData(true, idObatOrganik);
                            sendDataHargaObatOrganik(datumSudahTanam);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    Toast.makeText(InputSudahTanamF.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataHargaObatOrganik(DatumSudahTanam datumSudahTanam) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idObat", datumSudahTanam.getIdObatOrganik());
        jsonParams.put("hargaObat", datumSudahTanam.getIdBiayaobatOrganik());
        jsonParams.put("ketObat", "");
        jsonParams.put("idProfilTanah", datumSudahTanam.getIdProfilLahan());
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
                        sendKendalaPertumbuhanOrganik(datumSudahTanam);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputSudahTanamF.this, "Gagal input obat organik", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendKendalaPertumbuhanOrganik(DatumSudahTanam datumSudahTanam) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSudahTanam", datumSudahTanam.getIdRencanaTanam());
        jsonParams.put("idProfilTanah", datumSudahTanam.getIdProfilLahan());
        jsonParams.put("kendalaHama", "-");
        jsonParams.put("kendalaBencana", "-");
        jsonParams.put("kendalaLainnya", "Penggunaan Obat Organik = " + datumSudahTanam.getNamaObatOrganik());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.body() != null) {
//                    sendNotificationObatKimia();
                    sendDataSudahTanam(datumSudahTanam);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            Toast.makeText(InputSudahTanamF.this, "Gagal input kendala pertumbuhan kimia", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataSudahTanam(DatumSudahTanam datumSudahTanam){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idRencanaTanam", datumSudahTanam.getIdRencanaTanam() );
        jsonParams.put("idPertumbuhanNormal", "");
        jsonParams.put("idKendalaPertumbuhan", "");
        jsonParams.put("idBiayaburuhTanam", datumSudahTanam.getIdBiayaburuhTanam());
        jsonParams.put("idBiayaburuhBajak", datumSudahTanam.getIdBiayaburuhBajak());
        jsonParams.put("idBiayaburuhSemprot", datumSudahTanam.getIdBiayaburuhSemprot());
        jsonParams.put("idBiayaburuhMenyiangirumput", datumSudahTanam.getIdBiayaburuhMenyiangirumput());
        jsonParams.put("idBiayaburuhGalangan", datumSudahTanam.getIdBiayaburuhGalangan());
        jsonParams.put("idBiayaburuhPupuk", datumSudahTanam.getIdBiayaburuhPupuk());
        jsonParams.put("idBiayaburuhPanen", datumSudahTanam.getIdBiayaburuhPanen());
        jsonParams.put("idSewamesinBajak", datumSudahTanam.getIdSewamesinBajak());
        jsonParams.put("idSewamesinTanam", datumSudahTanam.getIdSewamesinTanam());
        jsonParams.put("idSewamesinPanen", datumSudahTanam.getIdSewamesinPanen());
        jsonParams.put("idSewamesinPompa", datumSudahTanam.getIdSewamesinPompa());
        jsonParams.put("idSewamesinPompaBbm", datumSudahTanam.getIdSewamesinPompaBbm());
        jsonParams.put("idBiayabibitLocalHet", datumSudahTanam.getIdBiayabibitLocalHet());
        jsonParams.put("idBiayabibitSubsidi", datumSudahTanam.getIdBiayabibitSubsidi());
        jsonParams.put("idBiayapupukKimiaLocalHet", datumSudahTanam.getIdBiayapupukKimiaLocalHet());
        jsonParams.put("idBiayapupukKimiaPhonska", datumSudahTanam.getIdBiayapupukKimiaPhonska());
        jsonParams.put("idBiayapupukKimiaUrea", datumSudahTanam.getIdBiayapupukKimiaUrea());
        jsonParams.put("idBiayapupukKimiaFosfat", datumSudahTanam.getIdBiayapupukKimiaFosfat());
        jsonParams.put("idBiayapupukOrganik", datumSudahTanam.getIdBiayapupukOrganik());
        jsonParams.put("idObatKimiaLocal", datumSudahTanam.getIdObatKimiaLocal());
        jsonParams.put("idObatKimiaSubsidi", datumSudahTanam.getIdObatKimiaSubsidi());
        jsonParams.put("idObatOrganik", datumSudahTanam.getIdObatOrganik());
        jsonParams.put("idBiayaobatKimiaLocalHet", datumSudahTanam.getIdBiayaobatKimiaLocalHet());
        jsonParams.put("idBiayaobatKimiaSubsidi", datumSudahTanam.getIdBiayaobatKimiaSubsidi());
        jsonParams.put("idBiayaobatOrganik", datumSudahTanam.getIdBiayaobatOrganik());

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
                                Toast.makeText(InputSudahTanamF.this, "Gagal input sudah tanam", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputSudahTanamF.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void moveToE() {
        Intent a = new Intent(InputSudahTanamF.this, InputSudahTanamE.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToListST() {
        Intent a = new Intent(InputSudahTanamF.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveToE();
    }
}