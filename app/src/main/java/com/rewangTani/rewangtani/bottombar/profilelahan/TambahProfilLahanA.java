package com.rewangTani.rewangtani.bottombar.profilelahan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.SupportMapFragment;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanABinding;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class TambahProfilLahanA extends FragmentActivity implements OnMapReadyCallback, OnMapsSdkInitializedCallback {
public class TambahProfilLahanA extends FragmentActivity {

    BottombarPlTambahProfilLahanABinding binding;
    private GoogleMap mMap;
    ModelProfilLahan modelProfilLahan;
    List<String> listProfilLahan = new ArrayList<>();
    int checkNama, checkLatLong;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat, longt;
    String lat2, longt2;
    private IMapController osmMapController;
    private static final int PERMISSION_REQUEST_CODE = 1;
    ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_a);
//        MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LEGACY, this);
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

        if (location != null) {
            lat = location.getLatitude();
            longt = location.getLongitude();
            lat2 = String.valueOf(lat).substring(0, 8);
            longt2 = String.valueOf(longt).substring(0, 7);
            binding.koordinatLahan.setText(lat2 + ", " + longt2);
            GeoPoint startPoint = new GeoPoint(lat, longt);
            osmMapController.setCenter(startPoint);
            binding.osmMapView.setZoomLevel(18);
        } else {
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
                String latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
                String longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
                lat2 = latitude.substring(0, 8);
                longt2 = longitude.substring(0, 7);
                binding.koordinatLahan.setText(lat2 + ", " + longt2);
                osmMapController.setCenter(loc);
                binding.osmMapView.setZoomLevel(18);
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double)loc.getLatitudeE6())/1000000), (((double)loc.getLongitudeE6())/1000000)));
                mapItem.setMarker(marker);
                overlayArray.add(mapItem);
                if(anotherItemizedIconOverlay==null){
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                    mapView.invalidate();
                }else{
                    mapView.getOverlays().remove(anotherItemizedIconOverlay);
                    mapView.invalidate();
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                }
                return true;
            }
        };
        binding.osmMapView.getOverlays().add(touchOverlay);

        getData();

        binding.btnSelanjutnya.setOnClickListener(v -> {
            checkNama();
        });
    }

    public void getData() {
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
        handler.postDelayed(runnable, 1 * 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfilLahan();
            }
        }).start();
    }

    public void getDataProfilLahan() {
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
                                listProfilLahan.add(modelProfilLahan.getData().get(i).getNamaProfilTanah().toString());
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

    private void checkLocalData(){
        if (!PreferenceUtils.getPLnamaProfilLahan(getApplicationContext()).equalsIgnoreCase("")){
            binding.namaProfilLahan.setText(PreferenceUtils.getPLnamaProfilLahan(getApplicationContext()));
        }

//        if (!PreferenceUtils.getPLlatitude(getApplicationContext()).equalsIgnoreCase("") && !PreferenceUtils.getPLlongitude(getApplicationContext()).equalsIgnoreCase("")) {
//            binding.koordinatLahan.setText(PreferenceUtils.getPLlatitude(getApplicationContext()) + ", " + PreferenceUtils.getPLlongitude(getApplicationContext()));
//            Overlay touchOverlay = new Overlay(this){
//                @Override
//                public void draw(Canvas arg0, MapView mapView, boolean arg2) {
//                    binding.osmMapView.invalidate();
//                    final Drawable marker = getApplicationContext().getResources().getDrawable(R.drawable.ic_osm_marker);
//                    int markerWidth = marker.getIntrinsicWidth();
//                    int markerHeight = marker.getIntrinsicHeight();
//                    marker.setBounds(0, markerHeight, markerWidth, 0);
//                    GeoPoint lastPoint = new GeoPoint(Double.valueOf(PreferenceUtils.getPLlatitude(getApplicationContext())), Double.valueOf(PreferenceUtils.getPLlongitude(getApplicationContext())));
//                    osmMapController.setCenter(lastPoint);
//                    binding.osmMapView.setZoomLevel(18);
//
//                    ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
//                    OverlayItem mapItem = new OverlayItem("", "", lastPoint);
//                    mapItem.setMarker(marker);
//                    overlayArray.add(mapItem);
//                    if(anotherItemizedIconOverlay==null){
//                        anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
//                        mapView.getOverlays().add(anotherItemizedIconOverlay);
//                        mapView.invalidate();
//                    }else{
//                        mapView.getOverlays().remove(anotherItemizedIconOverlay);
//                        mapView.invalidate();
//                        anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
//                        mapView.getOverlays().add(anotherItemizedIconOverlay);
//                    }
//                    super.draw(arg0, mapView, arg2);
//                }
//                @Override
//                public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
//                    return true;
//                }
//            };
//            binding.osmMapView.getOverlays().add(touchOverlay);
//        }
    }

    private void checkNama() {
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
        } else  {
            Toast.makeText(this, "Isi nama profil lahan terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLatLong() {
        if (!lat2.equalsIgnoreCase("") && !longt2.equalsIgnoreCase("")) {
            for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                if (modelProfilLahan.getData().get(i).getLatitude().equalsIgnoreCase(lat2) &&
                        modelProfilLahan.getData().get(i).getLongitude().equalsIgnoreCase(longt2)) {
                    Toast.makeText(this, "Lokasi lahan sudah terpakai", Toast.LENGTH_SHORT).show();
                    checkLatLong = 1;
                    break;
                }
            }
            if (checkLatLong != 1) {
                checkLatLong = 0;
                saveLocalData();
            } else {
                checkLatLong = 0;
            }
        }
    }

    private void saveLocalData() {
        PreferenceUtils.savePLnamaProfilLahan(binding.namaProfilLahan.getText().toString(), getApplicationContext());
        PreferenceUtils.savePLlatitude(lat2, getApplicationContext());
        PreferenceUtils.savePLlongitude(longt2, getApplicationContext());
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Tambah Profil Lahan ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        PreferenceUtils.savePLnamaProfilLahan("", getApplicationContext());
                        PreferenceUtils.savePLlatitude("", getApplicationContext());
                        PreferenceUtils.savePLlongitude("", getApplicationContext());
                        goToListProfilLahan();
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