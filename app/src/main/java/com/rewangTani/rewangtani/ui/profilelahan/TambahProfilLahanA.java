package com.rewangTani.rewangtani.ui.profilelahan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanABinding;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProfilLahanA extends FragmentActivity {

    BottombarPlTambahProfilLahanABinding binding;
    ModelProfilLahan modelProfilLahan;
    List<String> listProfilLahan = new ArrayList<>();
    int checkNama, checkLatLong;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat, longt;
    String centerLat, centerLong, luasGarapan;
    StringBuilder latitudeBuilder, longitudeBuilder;
    private IMapController osmMapController;
    ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;
    ArrayList<OverlayItem> markerItems = new ArrayList<>();
    private ArrayList<GeoPoint> landPoints = new ArrayList<>();
    private Polygon landPolygon;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_a);

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(TambahProfilLahanA.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TambahProfilLahanA.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TambahProfilLahanA.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }
        if (ActivityCompat.checkSelfPermission(TambahProfilLahanA.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TambahProfilLahanA.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }

        binding.osmMapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.osmMapView.setBuiltInZoomControls(true);
        binding.osmMapView.setMultiTouchControls(true);
        osmMapController = binding.osmMapView.getController();
        binding.osmMapView.setBuiltInZoomControls(true);
        binding.osmMapView.setMultiTouchControls(true);

        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if ( location != null )
        {
            lat = location.getLatitude();
            longt = location.getLongitude();
            centerLat = String.valueOf(lat).substring(0, 8);
            centerLong = String.valueOf(longt).substring(0, 7);
            binding.koordinatLahan.setText(centerLat + ", " + centerLong);
            GeoPoint startPoint = new GeoPoint(lat, longt);
            osmMapController.setCenter(startPoint);
            binding.osmMapView.setZoomLevel(18);
        }
        else
        {
            binding.koordinatLahan.setText("0.00, 0.00");
        }

        Overlay touchOverlay = new Overlay(this){
            @Override
            public void draw(Canvas arg0, MapView arg1, boolean arg2) {

            }
            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
                binding.osmMapView.invalidate();

                final Drawable marker = getApplicationContext().getResources().getDrawable(R.drawable.ic_osm_marker);
                int markerWidth = marker.getIntrinsicWidth();
                int markerHeight = marker.getIntrinsicHeight();
                marker.setBounds(0, markerHeight, markerWidth, 0);

                Projection proj = mapView.getProjection();
                GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());

                landPoints.add(loc);

                if ( landPoints.size() >= 3 )
                {
                    createLandPolygon();
                }

                OverlayItem mapItem = new OverlayItem(
                        "",
                        "",
                        loc
                );

                mapItem.setMarker(marker);
                markerItems.add(mapItem);

                if (anotherItemizedIconOverlay != null) {
                    mapView.getOverlays().remove(anotherItemizedIconOverlay);
                }

                anotherItemizedIconOverlay =
                        new ItemizedIconOverlay<>(
                                getApplicationContext(),
                                markerItems,
                                null
                        );

                mapView.getOverlays().add(anotherItemizedIconOverlay);
                mapView.invalidate();
                return true;
            }
        };
        binding.osmMapView.getOverlays().add(touchOverlay);

        getData();

        binding.btnSelanjutnya.setOnClickListener( v -> checkNama() );

        binding.btnResetMarker.setOnClickListener( v -> resetLandDrawing() );
    }

    public void getData()
    {
        binding.viewLoading.setVisibility(View.VISIBLE);
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
        handler.postDelayed(runnable, 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfilLahan();
            }
        }).start();
    }

    public void getDataProfilLahan()
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body() != null) {
                    for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelProfilLahan.getData().get(i).getIdUser())) {
                                listProfilLahan.add(modelProfilLahan.getData().get(i).getNamaProfilTanah());
                            }
                        } catch (Exception e) {
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            checkLocalData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(TambahProfilLahanA.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    private void checkLocalData()
    {
        if ( !PreferenceUtils.getPLnamaProfilLahan(getApplicationContext()).equalsIgnoreCase("") )
        {
            binding.namaProfilLahan.setText(PreferenceUtils.getPLnamaProfilLahan(getApplicationContext()));
        }
    }

    private void checkNama()
    {
        if (!binding.namaProfilLahan.getText().toString().equalsIgnoreCase("")){
            if (listProfilLahan.size() == 0) {
                checkLatLong();
            } else if (listProfilLahan.size() > 0) {
                for (int i = 0; i < listProfilLahan.size(); i++) {
                    if (binding.namaProfilLahan.getText().toString().equalsIgnoreCase(listProfilLahan.get(i))) {
                        Toast.makeText(this, "Nama profil lahan sudah dipakai", Toast.LENGTH_SHORT).show();
                        checkNama = 1;
                        break;
                    }
                }
                if (checkNama != 1) {
                    checkNama = 0;
                    checkLatLong();
                } else {
                    checkNama = 0;
                }
            }
        }
        else
        {
            Toast.makeText(this, "Isi nama profil lahan terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLatLong()
    {
        if ( !centerLat.equalsIgnoreCase("") && !centerLong.equalsIgnoreCase("") )
        {
            for ( int i = 0; i < modelProfilLahan.getTotalData(); i++ )
            {
                if ( modelProfilLahan.getData().get(i).getLatitude().equalsIgnoreCase(centerLat) &&
                        modelProfilLahan.getData().get(i).getLongitude().equalsIgnoreCase(centerLong) )
                {
                    Toast.makeText(this, "Lokasi lahan sudah terpakai", Toast.LENGTH_SHORT).show();
//                    if ( )
                    checkLatLong = 1;
                    break;
                }
            }

            if ( checkLatLong != 1 )
            {
                checkLatLong = 0;
                saveLocalData();
            }
            else
            {
                checkLatLong = 0;
            }
        }
        else
        {
            Toast.makeText(this, "Tambah koordinat lahan terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }
    }

    private void createLandPolygon()
    {
        if ( landPolygon != null )
        {
            binding.osmMapView.getOverlays().remove(landPolygon);
        }

        GeoPoint center = getPolygonCenter();

        osmMapController.setCenter(center);
        binding.osmMapView.setZoomLevel(18);

        String centerLatitude = String.valueOf(center.getLatitude());
        String centerLongitude = String.valueOf(center.getLongitude());

        centerLat = centerLatitude.substring(0, 8);
        centerLong = centerLongitude.substring(0, 7);
        String latLong = centerLat + ", " + centerLong;
        binding.koordinatLahan.setText(latLong);

        luasGarapan = String.valueOf(Math.round(calculateLandArea()));

        ArrayList<GeoPoint> sortedPoints = sortPolygonPoints();

        latitudeBuilder = new StringBuilder();
        longitudeBuilder = new StringBuilder();

        for ( GeoPoint point : sortedPoints )
        {
            if ( latitudeBuilder.length() > 0 )
            {
                latitudeBuilder.append(";");
                longitudeBuilder.append(";");
            }

            latitudeBuilder.append(point.getLatitude());
            longitudeBuilder.append(point.getLongitude());
        }

        landPolygon = new Polygon();
        landPolygon.setPoints(sortedPoints);
        landPolygon.getFillPaint().setColor(Color.argb(80, 0, 128, 0));
        landPolygon.getOutlinePaint().setColor(Color.GREEN);
        landPolygon.getOutlinePaint().setStrokeWidth(3f);
        binding.osmMapView.getOverlays().add(landPolygon);

        binding.osmMapView.invalidate();
    }

    private double calculateLandArea()
    {
        GeoPoint center = getPolygonCenter();

        double centerLat = Math.toRadians(center.getLatitude());
        double metersPerDegreeLat = 111320.0;
        double metersPerDegreeLon = 111320.0 * Math.cos(centerLat);

        ArrayList<GeoPoint> points = sortPolygonPoints();

        double area = 0.0;

        for ( int i = 0; i < points.size(); i++ )
        {
            GeoPoint current = points.get(i);
            GeoPoint next = points.get((i + 1) % points.size());

            double x1 = (current.getLongitude() - center.getLongitude())
                    * metersPerDegreeLon;

            double y1 = (current.getLatitude() - center.getLatitude())
                    * metersPerDegreeLat;

            double x2 = (next.getLongitude() - center.getLongitude())
                    * metersPerDegreeLon;

            double y2 = (next.getLatitude() - center.getLatitude())
                    * metersPerDegreeLat;

            area += (x1 * y2) - (x2 * y1);
        }

        return Math.abs(area) / 2.0;
    }

    private GeoPoint getPolygonCenter()
    {
        double latitude = 0;
        double longitude = 0;

        for ( GeoPoint point : landPoints )
        {
            latitude += point.getLatitude();
            longitude += point.getLongitude();
        }

        latitude /= landPoints.size();
        longitude /= landPoints.size();

        return new GeoPoint(latitude, longitude);
    }

    private ArrayList<GeoPoint> sortPolygonPoints()
    {
        GeoPoint center = getPolygonCenter();
        ArrayList<GeoPoint> sortedPoints = new ArrayList<>(landPoints);

        Collections.sort(sortedPoints, new Comparator<GeoPoint>()
        {
            @Override
            public int compare(GeoPoint p1, GeoPoint p2)
            {
                double angle1 = Math.atan2(p1.getLatitude() - center.getLatitude(), p1.getLongitude() - center.getLongitude());
                double angle2 = Math.atan2(p2.getLatitude() - center.getLatitude(), p2.getLongitude() - center.getLongitude());

                return Double.compare(angle1, angle2);
            }
        });

        return sortedPoints;
    }

    private void resetLandDrawing()
    {
        if ( landPolygon != null )
        {
            binding.osmMapView.getOverlays().remove(landPolygon);
            landPolygon = null;
        }

        if ( anotherItemizedIconOverlay != null )
        {
            binding.osmMapView.getOverlays().remove(anotherItemizedIconOverlay);
            anotherItemizedIconOverlay = null;
        }

        landPoints.clear();
        markerItems.clear();
        latitudeBuilder = new StringBuilder();
        longitudeBuilder = new StringBuilder();
        centerLat = Global.STRING_DEFAULT_VALUE;
        centerLong = Global.STRING_DEFAULT_VALUE;

        binding.koordinatLahan.setText(Global.STRING_DEFAULT_VALUE);
        binding.osmMapView.invalidate();
    }

    private void saveLocalData()
    {
        if ( latitudeBuilder == null && longitudeBuilder == null )
        {
            latitudeBuilder = new StringBuilder();
            longitudeBuilder = new StringBuilder();
            latitudeBuilder.append(centerLat).append(";");
            longitudeBuilder.append(centerLong).append(";");
        }

        PreferenceUtils.savePLnamaProfilLahan(binding.namaProfilLahan.getText().toString(), getApplicationContext());
        PreferenceUtils.savePLlatitude(latitudeBuilder.toString(), getApplicationContext());
        PreferenceUtils.savePLlongitude(longitudeBuilder.toString(), getApplicationContext());
        PreferenceUtils.savePLLuasGarapanProfilLahan(luasGarapan, getApplicationContext());
        goToTambahProfilLahanB();
    }

    public void goToTambahProfilLahanB() {
        Intent a = new Intent(TambahProfilLahanA.this, TambahProfilLahanB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    public void goToListProfilLahan() {
        Intent a = new Intent(TambahProfilLahanA.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCustomAlertDialog(
                TambahProfilLahanA.this,
                getString(R.string.confirm_batal_tambah_pl),
                okButton -> {
                    PreferenceUtils.savePLnamaProfilLahan("", getApplicationContext());
                    PreferenceUtils.savePLlatitude("", getApplicationContext());
                    PreferenceUtils.savePLlongitude("", getApplicationContext());
                    goToListProfilLahan();
                } );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.osmMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.osmMapView.onPause();
    }

}