package com.rewangTani.rewangtani.upperbar.panen;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.NumberTextWatcher;
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

public class InputPanen extends AppCompatActivity {

    Spinner sp_tujuan_jual, sp_hasil_panen, sp_rencana_tanam;
    EditText et_jumlah_hasil_panen, et_harga_jual;
    ImageButton btn_simpan, btn_batal;
    ModelRencanaTanam modelRencanaTanam;
    List<String> listRencanaTanam = new ArrayList<>();
    TextView txtload;
    ArrayAdapter<String> adapterRT;
    String namaRT, idRT;
    String[] tujuanjual, hasilpanen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_p_input_panen);

        sp_tujuan_jual = findViewById(R.id.sp_tujuan_jual);
        sp_hasil_panen = findViewById(R.id.sp_hasil_panen);
        sp_rencana_tanam = findViewById(R.id.sp_rencana_tanam);
        et_jumlah_hasil_panen = findViewById(R.id.et_jumlah_hasil_panen);
        et_harga_jual = findViewById(R.id.et_harga_jual);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);
        txtload = findViewById(R.id.textloading);

        getData();

        et_harga_jual.addTextChangedListener(new NumberTextWatcher(et_harga_jual));
        //et_jumlah_hasil_panen.addTextChangedListener(new NumberTextWatcher(et_jumlah_hasil_panen));

        sp_rencana_tanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaRT = sp_rencana_tanam.getSelectedItem().toString();
                for (int a=0; a<modelRencanaTanam.getTotalData(); a++){
                    try {
                        if (modelRencanaTanam.getData().get(a).getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                            idRT = modelRencanaTanam.getData().get(a).getIdRencanaTanam();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_jumlah_hasil_panen.getText().toString().equalsIgnoreCase("") && !et_harga_jual.getText().toString().equalsIgnoreCase("")){
                    getIDRT();
                } else {
                    Toast.makeText(InputPanen.this, "Lengkapi field terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batal();
            }
        });

    }

    private void getData(){
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

    public void getRencanaTanam(){
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
                                listRencanaTanam.add(modelRencanaTanam.getData().get(i).getNamaRencanaTanam());
                            }
                        } catch (Exception e){ }
                    }
                    if (listRencanaTanam.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataSpinner();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputPanen.this);
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
                                                goToListPanen();
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(InputPanen.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setDataSpinner(){
        adapterRT = new ArrayAdapter<String>(InputPanen.this, R.layout.z_spinner_list, listRencanaTanam);
        adapterRT.setDropDownViewResource(R.layout.z_spinner_list);
        sp_rencana_tanam.setAdapter(adapterRT);
        setSpinnerTujuanJual();
    }

    public void setSpinnerTujuanJual(){
        tujuanjual = getResources().getStringArray(R.array.tujuanjual);
        ArrayAdapter<String> adapterTJ = new ArrayAdapter<>(InputPanen.this, R.layout.z_spinner_list, tujuanjual);
        adapterTJ.setDropDownViewResource(R.layout.z_spinner_list);
        sp_tujuan_jual.setAdapter(adapterTJ);
        setSpinnerHasilPanen();
    }

    public void setSpinnerHasilPanen(){
        hasilpanen = getResources().getStringArray(R.array.jenishasilpanen);
        ArrayAdapter<String> adapterHP = new ArrayAdapter<>(InputPanen.this, R.layout.z_spinner_list, hasilpanen);
        adapterHP.setDropDownViewResource(R.layout.z_spinner_list);
        sp_hasil_panen.setAdapter(adapterHP);
    }

    public void getIDRT(){
        namaRT = sp_rencana_tanam.getSelectedItem().toString();
        for (int a=0; a<modelRencanaTanam.getTotalData(); a++){
            try {
                if (modelRencanaTanam.getData().get(a).getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                    idRT = modelRencanaTanam.getData().get(a).getIdRencanaTanam();
                    savePanen();
                }
            } catch (Exception e){}
        }
    }

    public void savePanen(){
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
                sendDataPanen();
            }
        }).start();
    }

    private void sendDataPanen(){
        Double a = Double.parseDouble(et_jumlah_hasil_panen.getText().toString());
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        //String now = formatter.format(new Date());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idRencanaTanam", idRT );
        jsonParams.put("tujuanJual", sp_tujuan_jual.getSelectedItem().toString());
        jsonParams.put("jenisHasilPanen", sp_hasil_panen.getSelectedItem().toString());
        jsonParams.put("hasilPanen", a);
        jsonParams.put("hargaAktual", et_harga_jual.getText().toString().replaceAll("[^0-9]", ""));

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendDataPanen(body);
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
                                goToListPanen();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputPanen.this, "Gagal update sudah tanam", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputPanen.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void batal(){
        onBackPressed();
    }

    public void goToListPanen(){
        Intent a = new Intent(InputPanen.this, ListPanen.class);
        startActivity(a);
        finish();
    }

    public  void goToRencanaTanam(){
        Intent a = new Intent(InputPanen.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal tambah panen ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToListPanen();
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