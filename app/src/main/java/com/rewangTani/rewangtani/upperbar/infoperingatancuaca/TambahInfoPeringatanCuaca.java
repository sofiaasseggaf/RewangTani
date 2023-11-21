package com.rewangTani.rewangtani.upperbar.infoperingatancuaca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahInfoPeringatanCuaca extends AppCompatActivity {

    EditText txt_judul, txt_ket;
    ImageButton btn_tambahkan;
    TextView txtload;
    List<String> listToken = new ArrayList<String>();
    List<DatumAkun> listAkunwithToken = new ArrayList<>();
    ModelAkun modelAkun;
    ModelProfilAkun modelProfilAkun;
    List<String> listIDAkunwithAlamat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_info_tambahinfo);

        txt_judul = findViewById(R.id.txt_judul);
        txt_ket = findViewById(R.id.txt_ket);
        btn_tambahkan = findViewById(R.id.btn_tambahkan);
        txtload = findViewById(R.id.textloading);

        start();

        //listToken.add("fsj3WpP_RO6pvtqLeV5ph8:APA91bGHNCcogHYYlA6Oi8NL1jIOSe9yEgK8loNR_6Uu9PfGwPnKlsU_goxGIgpNRhI1p99rhtrOo4xusOKDo-Z3UWXTPUKzhoVsn-YM5gKXfNtWfejT_XBqAxofyfobOuxotdVI_biT");
        //listToken.add("cMMac1hvSI-xC6pKpvdOKL:APA91bHZOY8HHqneDhxcFGAnN2kgeKdX3fvgTkuQySH5X72kSrLOtQnCQ0FP1DOtlowIdfQ0ICagjBqs_aVsWu40fG-9mfQa_duJqlscCMGK70GYbYI989v3JrIYGP3HqivqUTRD0Sjt");

        btn_tambahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_judul.getText().toString().equalsIgnoreCase("") && !txt_ket.getText().toString().equalsIgnoreCase("")) {
                    if (listToken.size()>0){
                        send();
                    } else {
                        sendDataInfotanpaNotification();
                    }
                    //startService();
                } else {
                    Toast.makeText(TambahInfoPeringatanCuaca.this, "Lengkapi Field Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void start(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu Sebentar Ya ."); }
                else if (count == 2) {
                    txtload.setText("Tunggu Sebentar Ya . ."); }
                else if (count == 3) {
                    txtload.setText("Tunggu Sebentar Ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataAkun();
            }
        }).start();
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
                        }
                    }
                    if (listAkunwithToken!=null){
                        getDataAlamat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahInfoPeringatanCuaca.this, "Data Token Tidak Ditemukan", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(TambahInfoPeringatanCuaca.this, "Data Token Tidak Ditemukan", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                Toast.makeText(TambahInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                            }
                        });

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
                        Toast.makeText(TambahInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    private void send(){

        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu Sebentar Ya ."); }
                else if (count == 2) {
                    txtload.setText("Tunggu Sebentar Ya . ."); }
                else if (count == 3) {
                    txtload.setText("Tunggu Sebentar Ya . . ."); }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendDataInfo();
            }
        }).start();
    }

    private void sendDataInfo(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            jsonParams = new ArrayMap<>();
        }
        String nama = PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext());

        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("namaSumber", nama);
        jsonParams.put("judulInfo", txt_judul.getText().toString());
        jsonParams.put("ketInfo", txt_ket.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendInfo(body);
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
                                //BerandaInfoPeringatanCuaca.dataInfo.getInfo();
                                // set notifikasi ke yg daerah sekitarnya
                                try {
                                    for (int i=0; i<listToken.size(); i++){
                                        sendNotification(listToken.get(i));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                goToBerandaInfo();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahInfoPeringatanCuaca.this, "Gagal Buat Info", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendDataInfotanpaNotification(){

        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        String nama = PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext());

        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("namaSumber", nama);
        jsonParams.put("judulInfo", txt_judul.getText().toString());
        jsonParams.put("ketInfo", txt_ket.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.sendInfo(body);
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
                                //BerandaInfoPeringatanCuaca.dataInfo.getInfo();
                                // set notifikasi ke yg daerah sekitarnya
                                goToBerandaInfo();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(TambahInfoPeringatanCuaca.this, "Gagal Buat Info", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TambahInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void sendNotification(String a) throws JSONException {
        final APIInterfacesRest apiInterface = ApiClientNotification.getClient().create(APIInterfacesRest.class);

        PreferenceUtils.saveTitle(txt_judul.getText().toString(), getApplicationContext());

        //String token = PreferenceUtils.getToken(getApplicationContext());

        /*List<String> regs = new ArrayList<String>();
        regs.add(token);
        Gson objGson = new Gson();
        String registration = objGson.toJson(regs);*/

        JSONObject regis = new JSONObject();
        regis.put("to", a);

        JSONObject notif = new JSONObject();
        notif.put("title", txt_judul.getText().toString());
        notif.put("body", txt_ket.getText().toString());

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
                            //Toast.makeText(TambahInfoPeringatanCuaca.this, "Berhasil Kirim Info", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(TambahInfoPeringatanCuaca.this, "Gagal Kirim Info", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ModelResultNotification> call, Throwable throwable) {
                Toast.makeText(TambahInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi Kirim Notifikasi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void startService(){
        Intent serviceIntent = new Intent(TambahInfoPeringatanCuaca.this, Services.class);
        serviceIntent.putExtra("judul", txt_judul.getText().toString());
        serviceIntent.putExtra("ket", txt_ket.getText().toString());
        startService(serviceIntent);
    }

    public void stopService(){
        Intent serviceIntent = new Intent(TambahInfoPeringatanCuaca.this, Services.class);
        stopService(serviceIntent);
    }

    public void goToBerandaInfo(){
        //stopService();
        Intent a = new Intent(TambahInfoPeringatanCuaca.this, BerandaInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal tambah info terbaru ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToBerandaInfo();
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