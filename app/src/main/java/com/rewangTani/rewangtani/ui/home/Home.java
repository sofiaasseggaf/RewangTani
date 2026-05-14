package com.rewangTani.rewangtani.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterHomeImageCarousel;
import com.rewangTani.rewangtani.bottombar.pesan.Inbox;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.BottombarHomeBinding;
import com.rewangTani.rewangtani.middlebar.Blog;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.service.ChatService;
import com.rewangTani.rewangtani.ui.keranjang.ActivityKeranjang;
import com.rewangTani.rewangtani.ui.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.BerandaInfoPeringatanCuaca;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.LocationHelper;
import com.rewangTani.rewangtani.utility.NavigationManager;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.TextUtil;
import com.rewangTani.rewangtani.utility.WebSocketManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Home extends AppCompatActivity
{

    BottombarHomeBinding binding;
    private HomeViewModel viewModel;
    private AdapterHomeImageCarousel adapterHomeImageCarousel;
    private final int[] imageIds = {R.drawable.img_event, R.drawable.event1, R.drawable.event2};
    private ImageView[] dots;
    private WebSocketManager webSocketManager;
    LocationListener locationListener;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_home);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initLayout();
        initEvent();
        initObserver();
        initChatService();
        initListener();
        checkLocationPermission();
    }

    private void initLayout()
    {

        String user = "Hai, " + PreferenceUtils.getNamaDepan(getApplicationContext()) + " " + PreferenceUtils.getNamaBelakang(getApplicationContext());
        binding.txtNama.setText(user);

        adapterHomeImageCarousel = new AdapterHomeImageCarousel(this, imageIds);
        binding.viewPager.setAdapter(adapterHomeImageCarousel);
        addDotsIndicator(0);
    }

    private void initEvent()
    {

        binding.viewPager.setOnClickListener(v -> NavigationManager.startActivity(this, Blog.class) );
        binding.btnCart.setOnClickListener(v -> NavigationManager.startActivity(this, ActivityKeranjang.class) );
        binding.btnRencanaTanam.setOnClickListener( v -> NavigationManager.startActivity(this, ListRencanaTanam.class) );
        binding.btnHasilPanen.setOnClickListener( v -> NavigationManager.startActivity(this, ListPanen.class) );
        binding.btnRencanaBiaya.setOnClickListener( v -> NavigationManager.startActivity(this, ListRancanganAnggaranBiaya.class) );

        binding.btnInfo.setOnClickListener( v -> NavigationManager.startActivity(this, BerandaInfoPeringatanCuaca.class) );
        binding.btnWarungSewaMesin.setOnClickListener( v -> NavigationManager.startActivity(this, ListWarungSewaMesin.class) );
        binding.btnWarungPupuk.setOnClickListener( v -> NavigationManager.startActivity(this, ListWarungBibitdanPupuk.class) );
        binding.btnWarungBibit.setOnClickListener( v -> NavigationManager.startActivity(this, ListWarungBibitdanPupuk.class) );
        binding.btnWarungPestisida.setOnClickListener( v -> NavigationManager.startActivity(this, ListWarungPestisida.class) );
        binding.btnWarungTenagaKerja.setOnClickListener( v -> NavigationManager.startActivity(this, ListWarungTenagaKerja.class) );

        binding.btnPesan.setOnClickListener( v -> NavigationManager.startActivity(this, Inbox.class) );
        binding.btnAkun.setOnClickListener( v -> NavigationManager.startActivity(this, BerandaProfile.class) );
        binding.btnLahan.setOnClickListener( v -> NavigationManager.startActivity(this, ListProfileLahan.class) );
        binding.btnWarungku.setOnClickListener( v -> NavigationManager.startActivity(this, PesananWarungku.class) );
        binding.btnSudahTanam.setOnClickListener( v -> {
            DialogUtil.showCustomAlertDialogTwoCustomTextButtons(
                    Home.this,
                    getString(R.string.confirm_page_st),
                    okButton -> NavigationManager.startActivity(Home.this, ListSudahTanam.class),
                    cancelButton -> NavigationManager.startActivity(Home.this, ListKendalaPertumbuhan.class),
                    getString(R.string.page_pt),
                    getString(R.string.page_kp));
        });
    }

    private void initObserver()
    {

        viewModel.getUnreadInboxes().observe(this, count -> {
            if (count != null) {
                binding.notificationTick.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                binding.txtInbox.setText(String.valueOf(count));
            } else {
                binding.notificationTick.setVisibility(View.GONE);
            }
        });
    }

    private void initChatService()
    {

        Intent serviceIntent = new Intent(this, ChatService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void initListener()
    {

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addDotsIndicator(position);
//                NavigationManager.startActivity(Home.this, Blog.class);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void checkLocationPermission()
    {

        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)
        {
            startLocationListener();
        }
        else
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Global.LOCATION_PERMISSION_CODE);
        }
    }

    private void startLocationListener()
    {

        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    getDataWeather(location.getLatitude(), location.getLongitude());
                }
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {}
            @Override
            public void onProviderDisabled(@NonNull String provider) {}
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

        LocationHelper.getLastKnownLocation(Home.this, locationListener, new LocationHelper.LocationResultCallback() {
            @Override
            public void onLocationFound(double lat, double lon)
            {
                getDataWeather(lat, lon);
            }

            @Override
            public void onLocationFailed()
            {
                Toast.makeText(Home.this, "Could not find location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDataWeather(Double a, Double b)
    {
        String url = Global.WEATHER_API_URL + a + "," + b;
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
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
                        Toast.makeText(Home.this, cond + " - Translation Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(Home.this, "Weather Error", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);
    }

    private void addDotsIndicator(int position)
    {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Global.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationListener();
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed()
    {
        DialogUtil.showExitConfirmation(this, v -> {
            Home.super.onBackPressed();
            finish();
            finishAffinity();
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        String myId = PreferenceUtils.getIdProfil(this);
        viewModel.triggerSync(myId);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        LocationHelper.stopLocationUpdates(this, locationListener);
    }
}