package com.rewangTani.rewangtani.bottombar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.photoutil.MainActivity;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.BerandaWarungku;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.BerandaInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.TambahInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.DeleteAll;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    TextView homeNama, txt_temp, txt_condition, txtload;
    ImageView img_temp;
    ImageButton btn_rencana_tanam, btn_sudah_tanam, btn_panen, btn_rab;
    ImageButton btn_w_tenaga_kerja, btn_w_sewa_mesin, btn_w_pupuk, btn_w_pestisida;
    ImageButton btn_beranda, btn_warungku, btn_profil_lahan, btn_profil_akun, btn_plus_info;
    RelativeLayout btn_info;
    int PERMISSION_CODE = 1;
    Double lat = 0.0;
    Double longt = 0.0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_home);

        homeNama = findViewById(R.id.homeNama);
        txt_temp = findViewById(R.id.txt_temp);
        txt_condition = findViewById(R.id.txt_condition);
        img_temp = findViewById(R.id.img_temp);
        btn_rencana_tanam = findViewById(R.id.btn_rencana_tanam);
        btn_sudah_tanam = findViewById(R.id.btn_sudah_tanam);
        btn_panen = findViewById(R.id.btn_panen);
        btn_rab = findViewById(R.id.btn_rab);
        btn_info = findViewById(R.id.btn_info);
        btn_w_tenaga_kerja = findViewById(R.id.btn_w_tenaga_kerja);
        btn_w_sewa_mesin = findViewById(R.id.btn_w_sewa_mesin);
        btn_w_pupuk = findViewById(R.id.btn_w_pupuk);
        btn_w_pestisida = findViewById(R.id.btn_w_pestisida);
        btn_beranda = findViewById(R.id.btn_beranda);
        btn_warungku = findViewById(R.id.btn_warungku);
        btn_profil_lahan = findViewById(R.id.btn_profil_lahan);
        btn_profil_akun = findViewById(R.id.btn_profil_akun);
        btn_plus_info = findViewById(R.id.btn_plus_info);
        txtload = findViewById(R.id.textloading);


        homeNama.setText(PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext()));
        start();

        btn_rencana_tanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRencanaTanam();
            }
        });

        btn_sudah_tanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setMessage("Apa yang ingin anda perbarui ?")
                        .setCancelable(true)
                        .setPositiveButton("Proses Tanam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                goToSudahTanam();
                            }
                        })

                        .setNegativeButton("Kendala Pertumbuhan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                gotoKendalaPertumbuhan();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_panen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPanen();
            }
        });

        btn_rab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRAB();
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInfo();
            }
        });

        btn_plus_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTambahInfo();
            }
        });

        btn_w_tenaga_kerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent a = new Intent(Home.this, DeleteAll.class);
                startActivity(a);
                finish();*/
                goToWarungTK();
            }
        });

        btn_w_sewa_mesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungSM();
            }
        });

        btn_w_pupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungPupuk();
            }
        });

        btn_w_pestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungPestisida();
            }
        });

        btn_warungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
            }
        });

        btn_profil_lahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        btn_profil_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

    }

    // ------------------------------------------------------------------------------

    public void start() {
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    txtload.setText("Tunggu sebentar ya . .");
                } else if (count == 3) {
                    txtload.setText("Tunggu sebentar ya . . .");
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
                getLoc();
            }
        }).start();
    }

    public void getLoc() {

        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
                }
            });
        }

        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    @SuppressLint("MissingPermission")
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if(location2!=null){
                                lat = location.getLatitude();
                                longt = location.getLongitude();
                                if (lat!=0.0 && longt!=0.0 && lat!=null && longt!=null){
                                    //homeNama.setText(PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext()));
                                    setData();
                                } else {
                                    start();
                                }
                            }
                        }
                    } else if (location!=null){
                        lat = location.getLatitude();
                        longt = location.getLongitude();
                        if (lat!=0.0 && longt!=0.0 && lat!=null && longt!=null){
                            //homeNama.setText(PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext()));
                            setData();
                        } else {
                            start();
                        }
                    }
                    else {
                        start();
                    }
                }
            });
        }
    }

    public void setData(){
        getDataWeather(lat,longt);
    }

    public void getDataWeather(Double a, Double b){
        String url = "http://api.weatherapi.com/v1/current.json?key=14e35e2d6e264c6198c163938222104&q=" + a + "," + b;
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
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
                Toast.makeText(Home.this, "Weather Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void goToRencanaTanam(){
        Intent a = new Intent(Home.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToSudahTanam(){
        Intent a = new Intent(Home.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    public void gotoKendalaPertumbuhan(){
        Intent a = new Intent(Home.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }

    public void goToPanen(){
        Intent a = new Intent(Home.this, ListPanen.class);
        startActivity(a);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(Home.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        finish();
    }

    public void goToInfo(){
        Intent a = new Intent(Home.this, BerandaInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToTambahInfo(){
        Intent a = new Intent(Home.this, TambahInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToWarungTK(){
        Intent a = new Intent(Home.this, ListWarungTenagaKerja.class);
        startActivity(a);
        finish();
    }

    public void goToWarungSM(){
        Intent a = new Intent(Home.this, ListWarungSewaMesin.class);
        startActivity(a);
        finish();
    }

    public void goToWarungPupuk(){
        Intent a = new Intent(Home.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        finish();
    }

    public void goToWarungPestisida(){
        Intent a = new Intent(Home.this, ListWarungPestisida.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(Home.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(Home.this, BerandaWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(Home.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(Home.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda mau menutup aplikasi")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Home.super.onBackPressed();
                        finish();
                        finishAffinity();
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
    }

    }