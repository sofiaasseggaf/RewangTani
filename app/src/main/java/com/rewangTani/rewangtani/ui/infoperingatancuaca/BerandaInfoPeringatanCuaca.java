package com.rewangTani.rewangtani.ui.infoperingatancuaca;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.AdapterListInfo;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilById;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.databinding.UpperbarInfoBerandainfoBinding;
import com.rewangTani.rewangtani.model.modelinfo.DatumInfo;
import com.rewangTani.rewangtani.model.modelinfo.ModelInfo;
import com.rewangTani.rewangtani.ui.home.Home;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.StringDateComparator;
import com.rewangTani.rewangtani.utility.TextUtil;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;

public class BerandaInfoPeringatanCuaca extends AppCompatActivity {

    UpperbarInfoBerandainfoBinding binding;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat,longt;
    ModelInfo modelInfo;
    List<DatumInfo> listInfo = new ArrayList<DatumInfo>();
    List<DatumInfo> listInfoSorted = new ArrayList<DatumInfo>();
    AdapterListInfo itemList;
    ModelProfilById modelProfilById;
    int checkKelengkapan = 0;
    String translated;
    HttpResponse hr;
    List<String> info = new ArrayList<>();
    //EditText tokentxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_info_berandainfo);

        getLoc();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BerandaInfoPeringatanCuaca.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        lat = location.getLatitude();
        longt = location.getLongitude();

        //getData();

        //tokentxt.setText(PreferenceUtils.getToken(getApplicationContext()));

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (checkKelengkapan==1){
//                    goToTambahInfo();
//                } else if (checkKelengkapan==0){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(BerandaInfoPeringatanCuaca.this);
//                    builder.setMessage("Lengkapi data profil terlebih dahulu")
//                            .setPositiveButton("Lengkapi Data Profil", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int i) {
//                                    goToProfilLahan();
//                                }
//                            })
//                            .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    goToBerandaInfo();
//                                }
//                            })
//                            .create()
//                            .show();
//                }
                goToTambahInfo();
            }
        });
    }

    public void getLoc() {

        if (ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(BerandaInfoPeringatanCuaca.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BerandaInfoPeringatanCuaca.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);

            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);   //default
        criteria.setCostAllowed(false);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location == null) {
            location =
                    locationManager.getLastKnownLocation(
                            LocationManager.NETWORK_PROVIDER
                    );
        }

        if (location != null) {
            lat = location.getLatitude();
            longt = location.getLongitude();
            Log.d(
                    "LOCATION",
                    "lat = " + lat
                            + ", long = " + longt
            );

            getData();
        } else {
            Toast.makeText(this, "Lokasi tidak terdeteksi", Toast.LENGTH_LONG).show();
        }
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
        handler.postDelayed(runnable, 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfil();
            }
        }).start();
    }

    public void getDataProfil() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<ModelProfilById>() {
            @Override
            public void onResponse(Call<ModelProfilById> call, retrofit2.Response<ModelProfilById> response) {
                modelProfilById = response.body();
                if (response.body()!=null){
                    if (modelProfilById.getData().getTelepon()!=null && modelProfilById.getData().getNik()!=null &&
                            modelProfilById.getData().getIdAlamat()!=null && modelProfilById.getData().getAlamat()!=null &&
                            modelProfilById.getData().getGender()!=null && modelProfilById.getData().getTglLahir()!=null){
                        checkKelengkapan = 1;
                        getInfo();
                    } else {
                        checkKelengkapan = 0;
                        getInfo();

                    }
                }
            }
            @Override
            public void onFailure(Call<ModelProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                    setDataInfo();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                getDataWeather(lat,longt);
//                                getWeather(lat, longt);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(BerandaInfoPeringatanCuaca.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void setDataInfo(){
        itemList = new AdapterListInfo(listInfoSorted);
        binding.rvInfo.setLayoutManager(new LinearLayoutManager(BerandaInfoPeringatanCuaca.this));
        binding.rvInfo.setAdapter(itemList);
        getDataWeather(lat, longt);
//        getWeather(lat, longt);
    }

    public void getDataWeather(Double a, Double b)
    {
        String url = Global.WEATHER_API_URL + a + "," + b;
        String urlThreeDays = Global.WEATHER_FORECAST_API_URL + a + "," + b + "&days=7";

        RequestQueue requestQueue = Volley.newRequestQueue(BerandaInfoPeringatanCuaca.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                SimpleDateFormat formatIncoming = new SimpleDateFormat("EEEE, dd MMMM y", new Locale("id"));
                TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
                formatIncoming.setTimeZone(tz);
                binding.txtDate.setText(formatIncoming.format(new Date()));
                String temp = response.getJSONObject("current").getString("temp_c");
                String txtTemperature = temp + " C";
                binding.txtTemp.setText(txtTemperature);
                String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                Picasso.get().load("http:".concat(icon)).into(binding.imgTemp);
                String city = response.getJSONObject("location").getString("name");
                binding.txtCity.setText(city);

                TextUtil.translateText(this, cond, new TextUtil.TranslationCallback() {
                    @Override
                    public void onTranslationSuccess(String translatedText) {
                        binding.txtTempDesc.setText(translatedText);
                    }
                    @Override
                    public void onTranslationError(Exception e) {
                        Toast.makeText(BerandaInfoPeringatanCuaca.this, cond + " - Translation Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(BerandaInfoPeringatanCuaca.this, "Weather Error", Toast.LENGTH_SHORT).show());

        JsonObjectRequest jsonObjectRequestDays = new JsonObjectRequest(Request.Method.GET, urlThreeDays, null, response -> {
            try {
                SimpleDateFormat formatIncoming = new SimpleDateFormat("EEEE, dd MMMM y", new Locale("id"));
                TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
                formatIncoming.setTimeZone(tz);
                binding.txtDate.setText(formatIncoming.format(new Date()));
                String temp = response.getJSONObject("current").getString("temp_c");
                String txtTemperature = temp + " C";
                binding.txtTemp.setText(txtTemperature);
                String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                Picasso.get().load("http:".concat(icon)).into(binding.imgTemp);
                String city = response.getJSONObject("location").getString("name");
                binding.txtCity.setText(city);

                TextUtil.translateText(this, cond, new TextUtil.TranslationCallback() {
                    @Override
                    public void onTranslationSuccess(String translatedText) {
                        binding.txtTempDesc.setText(translatedText);
                    }
                    @Override
                    public void onTranslationError(Exception e) {
                        Toast.makeText(BerandaInfoPeringatanCuaca.this, cond + " - Translation Error", Toast.LENGTH_LONG).show();
                    }
                });

                // =========================
                // 3-DAY FORECAST
                // =========================

                JSONArray forecastDays = response.getJSONObject("forecast").getJSONArray("forecastday");

                ImageView[] weatherImages = {
                        binding.imgTemp1,
                        binding.imgTemp2,
                        binding.imgTemp3
                };

                TextView[] weatherDates = {
                        binding.txtDate1,
                        binding.txtDate2,
                        binding.txtDate3
                };

                TextView[] weatherTexts = {
                        binding.txtTemp1,
                        binding.txtTemp2,
                        binding.txtTemp3
                };

                for (int i = 0; i < 7 && i <   forecastDays.length(); i++)
                {

                    JSONObject dayObject = forecastDays.getJSONObject(i);
                    JSONObject day = dayObject.getJSONObject("day");
                    String date = dayObject.getString("date");
                    JSONObject condition = day.getJSONObject("condition");
                    String conditionText = condition.getString("text");
                    String icon2 = condition.getString("icon");

                    try {
                        SimpleDateFormat input =
                                new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                        SimpleDateFormat output =
                                new SimpleDateFormat("EEE, dd MMM", new Locale("id"));

                        Date parsedDate = input.parse(date);

                        if (parsedDate != null) {
                            weatherDates[i].setText(output.format(parsedDate));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Picasso.get()
                            .load("http:".concat(icon2))
                            .into(weatherImages[i]);

                    final int index = i;
                    final String conditionToTranslate = conditionText;

                    TextUtil.translateText(
                            this,
                            conditionToTranslate,
                            new TextUtil.TranslationCallback() {

                                @Override
                                public void onTranslationSuccess(
                                        String translatedText
                                ) {

                                    weatherTexts[index]
                                            .setText(translatedText);
                                }

                                @Override
                                public void onTranslationError(
                                        Exception e
                                ) {

                                    weatherTexts[index]
                                            .setText(conditionToTranslate);

                                    Log.e(
                                            "WEATHER_TRANSLATION",
                                            "Translation failed for: "
                                                    + conditionToTranslate,
                                            e
                                    );
                                }
                            }
                    );
                }


            } catch (JSONException e) {

                e.printStackTrace();
            }
        },

                error -> {

                    Toast.makeText(
                            BerandaInfoPeringatanCuaca.this,
                            "Weather Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        requestQueue.add(jsonObjectRequestDays);
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