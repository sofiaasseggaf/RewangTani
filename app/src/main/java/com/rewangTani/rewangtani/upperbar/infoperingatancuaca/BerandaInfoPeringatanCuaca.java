package com.rewangTani.rewangtani.upperbar.infoperingatancuaca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.AdapterListInfo;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelinfo.DatumInfo;
import com.rewangTani.rewangtani.model.modelinfo.ModelInfo;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.StringDateComparator;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class BerandaInfoPeringatanCuaca extends AppCompatActivity {

    TextView txt_temp, txt_condition, txtload;
    ImageView img_temp;
    ImageButton btn_tambah;
    RecyclerView rv_info;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat,longt;
    ModelInfo modelInfo;
    List<DatumInfo> listInfo = new ArrayList<DatumInfo>();
    List<DatumInfo> listInfoSorted = new ArrayList<DatumInfo>();
    AdapterListInfo itemList;
    public static BerandaInfoPeringatanCuaca dataInfo;
    DataProfilById dataProfilById;
    int checkKelengkapan = 0;
    List<String> info = new ArrayList<>();
    //EditText tokentxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_info_berandainfo);

        txt_temp = findViewById(R.id.txt_temp);
        txt_condition = findViewById(R.id.txt_condition);
        img_temp = findViewById(R.id.img_temp);
        btn_tambah = findViewById(R.id.btn_tambah);
        rv_info = findViewById(R.id.rv_info);
        txtload = findViewById(R.id.textloading);

        //tokentxt = findViewById(R.id.tokentxt);

        dataInfo = this;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BerandaInfoPeringatanCuaca.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        lat = location.getLatitude();
        longt = location.getLongitude();

        getData();

        //tokentxt.setText(PreferenceUtils.getToken(getApplicationContext()));

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkKelengkapan==1){
                    goToTambahInfo();
                } else if (checkKelengkapan==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BerandaInfoPeringatanCuaca.this);
                    builder.setMessage("Lengkapi data profil terlebih dahulu")
                            .setPositiveButton("Lengkapi Data Profil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    goToProfilLahan();
                                }
                            })
                            .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToBerandaInfo();
                                }
                            })
                            .create()
                            .show();
                }
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
                getDataProfil();
            }
        }).start();
    }

    public void getDataProfil() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, retrofit2.Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (response.body()!=null){
                    if (dataProfilById.getData().getTelepon()!=null && dataProfilById.getData().getNik()!=null &&
                            dataProfilById.getData().getIdAlamat()!=null && dataProfilById.getData().getAlamat()!=null &&
                            dataProfilById.getData().getGender()!=null && dataProfilById.getData().getTglLahir()!=null){
                        checkKelengkapan = 1;
                        getInfo();
                    } else {
                        checkKelengkapan = 0;
                        getInfo();

                    }
                }
            }
            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getInfo() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelInfo> dataRT = apiInterface.getDataInfo();
        dataRT.enqueue(new Callback<ModelInfo>() {
            @Override
            public void onResponse(Call<ModelInfo> call, retrofit2.Response<ModelInfo> response) {
                modelInfo = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelInfo.getTotalData(); i++) {
                        try {
                            listInfo.add(modelInfo.getData().get(i));
                        } catch (Exception e){ }
                    }


                   /* if (listInfo.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataInfo();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                getDataWeather(lat,longt);
                            }
                        });
                    }*/



                    if (listInfo.size()>0){
                        for(int a=0; a<listInfo.size(); a++){
                            String b = listInfo.get(a).getCreatedDate();
                            b.substring(0, b.length() - 6);
                            info.add(b);
                        }
                        Collections.sort(info, new StringDateComparator());

                        for(int z=info.size()-1; z>=0; z--){
                            // i=2
                            String dt = info.get(z);
                            for (int x=0; x<listInfo.size(); x++){
                                if(listInfo.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                                    listInfoSorted.add(listInfo.get(x));
                                }
                            }
                        }


                        if (listInfoSorted.size()>0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    setDataInfo();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                getDataWeather(lat,longt);
                                Toast.makeText(BerandaInfoPeringatanCuaca.this, "Data info belum ada", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }





                }
            }
            @Override
            public void onFailure(Call<ModelInfo> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(BerandaInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setDataInfo(){
        itemList = new AdapterListInfo(listInfoSorted);
        rv_info.setLayoutManager(new LinearLayoutManager(BerandaInfoPeringatanCuaca.this));
        rv_info.setAdapter(itemList);
        getDataWeather(lat, longt);
    }


    public void getDataWeather(Double a, Double b){
        String url = "http://api.weatherapi.com/v1/current.json?key=14e35e2d6e264c6198c163938222104&q=" + a + "," + b;
        RequestQueue requestQueue = Volley.newRequestQueue(BerandaInfoPeringatanCuaca.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp = response.getJSONObject("current").getString("temp_c");
                    txt_temp.setText(temp + " C");
                    String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    txt_condition.setText(cond);
                    String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(icon)).into(img_temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerandaInfoPeringatanCuaca.this, "Weather Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void goToTambahInfo(){
        Intent a = new Intent(BerandaInfoPeringatanCuaca.this, TambahInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToBerandaInfo(){
        Intent a = new Intent(BerandaInfoPeringatanCuaca.this, BerandaInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(BerandaInfoPeringatanCuaca.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(BerandaInfoPeringatanCuaca.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}