package com.rewangTani.rewangtani.upperbar.kendalapertumbuhan;

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

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarKpInputKendalaPertumbuhanBinding;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputKendalaPertumbuhan extends AppCompatActivity {

    UpperbarKpInputKendalaPertumbuhanBinding binding;
    ModelRencanaTanam modelRencanaTanam;
    List<String> listRT = new ArrayList<String>();
    String namaRT, idRT, idPL;
    ArrayAdapter<String> adapterRT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_kp_input_kendala_pertumbuhan);

        //getData();

        idRT = "";

        binding.spRencanaTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaRT = binding.spRencanaTanam.getSelectedItem().toString();
                for (int a=0; a<modelRencanaTanam.getTotalData(); a++){
                    try {
                        if (modelRencanaTanam.getData().get(a).getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                            idRT = modelRencanaTanam.getData().get(a).getIdRencanaTanam();
                            idPL = modelRencanaTanam.getData().get(a).getIdProfilTanah();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InputKendalaPertumbuhan.this, "Simpan ", Toast.LENGTH_SHORT).show();
                goToListKendalaPertumbuhan();
//                if (!idRT.equalsIgnoreCase("")){
//                    sendData();
//                } else {
//                    Toast.makeText(InputKendalaPertumbuhan.this, "Pilih rencana tanam terlebih dahulu", Toast.LENGTH_SHORT).show();
//                }
            }
        });

//        btn_batal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

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
                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRT.add(modelRencanaTanam.getData().get(i).getNamaRencanaTanam());
                            }
                        } catch (Exception e){ }
                    }
                    if (listRT.size()!=0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setDataSpinner();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputKendalaPertumbuhan.this);
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
                                                goToListKendalaPertumbuhan();
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
                        Toast.makeText(InputKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataSpinner(){
        adapterRT = new ArrayAdapter<String>(InputKendalaPertumbuhan.this, R.layout.z_spinner_list, listRT);
        adapterRT.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spRencanaTanam.setAdapter(adapterRT);
    }

    public void sendData(){
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
                sendDataKendalaPertumbuhan();
            }
        }).start();
    }

    public void sendDataKendalaPertumbuhan(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idSudahTanam", idRT );
        jsonParams.put("idProfilTanah", idPL);
        jsonParams.put("kendalaHama", binding.kendalaHama.getText().toString());
        jsonParams.put("kendalaBencana", binding.kendalaBencana.getText().toString());
        jsonParams.put("kendalaLainnya", binding.kendalaLainnya.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendKendalaPertumbuhan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                goToListKendalaPertumbuhan();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(InputKendalaPertumbuhan.this, "Gagal input kendala pertumbuhan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void goToListKendalaPertumbuhan(){
        Intent a = new Intent(InputKendalaPertumbuhan.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }

    public  void goToRencanaTanam(){
        Intent a = new Intent(InputKendalaPertumbuhan.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal input kendala pertumbuhan ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListKendalaPertumbuhan();
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