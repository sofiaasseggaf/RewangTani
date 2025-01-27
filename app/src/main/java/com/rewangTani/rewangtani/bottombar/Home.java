package com.rewangTani.rewangtani.bottombar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterHomeImageCarousel;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarHomeBinding;
import com.rewangTani.rewangtani.middlebar.Blog;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.BerandaInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.TambahInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Home extends AppCompatActivity
{

    BottombarHomeBinding binding;
    int PERMISSION_CODE = 1;
    Double lat = 0.0;
    Double longt = 0.0;
    String translated;
    HttpResponse hr;

    private AdapterHomeImageCarousel adapterHomeImageCarousel;
    private int[] imageIds = {R.drawable.img_event, R.drawable.event1, R.drawable.event2};
    private ImageView[] dots;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_home);

        binding.txtNama.setText("Hai, " + PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext()));

        adapterHomeImageCarousel = new AdapterHomeImageCarousel(this, imageIds);
        binding.viewPager.setAdapter(adapterHomeImageCarousel);
        addDotsIndicator(0);

        binding.viewPager.setOnClickListener(v -> {
            goToBlog();
        });

        getLoc();

        binding.btnRencanaTanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRencanaTanam();
            }
        });

        binding.btnSudahTanam.setOnClickListener(new View.OnClickListener() {
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

        binding.btnHasilPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPanen();
            }
        });

        binding.btnRencanaBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRAB();
            }
        });

        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInfo();
            }
        });

        binding.btnWarungTenagaKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungTK();
            }
        });

        binding.btnWarungSewaMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungSM();
            }
        });

        binding.btnWarungPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungPupuk();
            }
        });

        binding.btnWarungBibit.setOnClickListener(v -> {
            goToWarungPupuk();
        });

        binding.btnWarungPestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungPestisida();
            }
        });

        binding.btnWarungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
            }
        });

        binding.btnLahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        binding.btnPesan.setOnClickListener(v -> {
            goToPesan();
        });

        binding.btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addDotsIndicator(position);
                //goToBlog();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        binding.btnCart.setOnClickListener(v -> {
            goToKeranjang();
        });

    }

    public void getLoc()
    {
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);   //default
        criteria.setCostAllowed(false);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location == null) {
            Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location2 != null) {
                lat = location.getLatitude();
                longt = location.getLongitude();
                if (lat != 0.0 && longt != 0.0 && lat != null && longt != null) {
                    setData();
                } else {
                    getLoc();
                }
            }
        } else if (location != null) {
            lat = location.getLatitude();
            longt = location.getLongitude();
            if (lat != 0.0 && longt != 0.0 && lat != null && longt != null) {
                setData();
            } else {
                getLoc();
            }
        } else {
            getLoc();

        }
    }

    public void setData() {
        getDataWeather(lat, longt);
    }

    private void addDotsIndicator(int position) {
        dots = new ImageView[imageIds.length];
        binding.layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int dotDrawable = (i == position) ? R.drawable.dot_selected : R.drawable.dot_unselected;
            dots[i].setImageResource(dotDrawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            binding.layoutDots.addView(dots[i], params);
        }
    }

    public void getDataWeather(Double a, Double b) {
        String url = "http://api.weatherapi.com/v1/current.json?key=14e35e2d6e264c6198c163938222104&q=" + a + "," + b;
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    SimpleDateFormat formatIncoming = new SimpleDateFormat("EEEE, dd MMMM y", new Locale("id"));
                    TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
                    formatIncoming.setTimeZone(tz);
                    binding.txtDate.setText(formatIncoming.format(new Date()));
                    String temp = response.getJSONObject("current").getString("temp_c");
                    binding.txtTemp.setText(temp + " C");
                    String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    translate(cond);
                    String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(icon)).into(binding.imgTemp);
                    String city = response.getJSONObject("location").getString("name");
                    binding.txtCity.setText(city);
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

    public void translate( String text )
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    String query = URLEncoder.encode(text, "UTF-8");
                    String url = "http://mymemory.translated.net/api/get?q=" + query + "&langpair=en%7Cid";
                    HttpClient hc = new DefaultHttpClient();
                    HttpGet hg = new HttpGet(url);
                    hr = hc.execute(hg);
                    if ( hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK )
                    {
                        JSONObject response = new JSONObject(EntityUtils.toString(hr.getEntity()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    translated = response.getJSONObject("responseData").getString("translatedText");
                                    binding.txtTempDesc.setText(translated);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void goToRencanaTanam() {
        Intent a = new Intent(Home.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToSudahTanam() {
        Intent a = new Intent(Home.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    public void gotoKendalaPertumbuhan() {
        Intent a = new Intent(Home.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }

    public void goToPanen() {
        Intent a = new Intent(Home.this, ListPanen.class);
        startActivity(a);
        finish();
    }

    public void goToRAB() {
        Intent a = new Intent(Home.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        finish();
    }

    public void goToInfo() {
        Intent a = new Intent(Home.this, BerandaInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToTambahInfo() {
        Intent a = new Intent(Home.this, TambahInfoPeringatanCuaca.class);
        startActivity(a);
        finish();
    }

    public void goToWarungTK() {
        Intent a = new Intent(Home.this, ListWarungTenagaKerja.class);
        startActivity(a);
        finish();
    }

    public void goToWarungSM() {
        Intent a = new Intent(Home.this, ListWarungSewaMesin.class);
        startActivity(a);
        finish();
    }

    public void goToWarungPupuk() {
        Intent a = new Intent(Home.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        finish();
    }

    public void goToWarungPestisida() {
        Intent a = new Intent(Home.this, ListWarungPestisida.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda() {
        Intent a = new Intent(Home.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToWarungku() {
        Intent a = new Intent(Home.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPesan() {
        Intent a = new Intent(Home.this, Inbox.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(Home.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun() {
        Intent a = new Intent(Home.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToBlog() {
        Intent a = new Intent(Home.this, Blog.class);
        startActivity(a);
        finish();
    }

    public void goToKeranjang() {
        Intent a = new Intent(Home.this, Keranjang.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}