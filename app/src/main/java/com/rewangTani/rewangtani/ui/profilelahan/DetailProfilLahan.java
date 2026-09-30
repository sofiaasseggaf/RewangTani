package com.rewangTani.rewangtani.ui.profilelahan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.databinding.BottombarPlDetailProfilLahanBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelprofillahan.DataProfilLahanById;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;

import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfilLahan extends AppCompatActivity {

    BottombarPlDetailProfilLahanBinding binding;
    String namaSistemIrigasi;
    ModelProfilLahan modelProfilLahan;
    DatumProfilLahan dataProfilLahan;
    ModelSistemIrigasi modelSistemIrigasi;
    DataProfilLahanById dataProfilLahanById;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_detail_profil_lahan);

        Intent intent = getIntent();
        String idProfilLahan = intent.getStringExtra("idProfilLahan");

        binding.osmMapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.osmMapView.setBuiltInZoomControls(true);
        binding.osmMapView.setMultiTouchControls(true);

        getData(idProfilLahan);

        binding.btnSimpan.setOnClickListener( v -> updateProfilLahan() );
    }

    public void getData( String idProfilLahan )
    {
        binding.viewLoading.setVisibility(View.VISIBLE);
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
                getProfilLahan(idProfilLahan);
            }
        }).start();
    }

    public void getProfilLahan( String idProfilLahan )
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilLahan> dataPL = apiInterface.getDataProfilLahan();
        dataPL.enqueue(new Callback<ModelProfilLahan>() {
            @Override
            public void onResponse(Call<ModelProfilLahan> call, Response<ModelProfilLahan> response) {
                modelProfilLahan = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                            String idpl = modelProfilLahan.getData().get(i).getIdProfileTanah();
                            if (idProfilLahan.equalsIgnoreCase(idpl)) {
                                dataProfilLahan = modelProfilLahan.getData().get(i);
                                if (dataProfilLahan!=null){
                                    getSistemIrigasi();
                                }
                            }
                        }
                    } catch (Exception e){ }
                }
            }
            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getSistemIrigasi()
    {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSistemIrigasi> data = apiInterface.getDataSistemIrigasi();
        data.enqueue(new Callback<ModelSistemIrigasi>() {
            @Override
            public void onResponse(Call<ModelSistemIrigasi> call, Response<ModelSistemIrigasi> response) {
                modelSistemIrigasi = response.body();
                if (response.body()!=null){
                    try{
                        for (int i = 0; i < modelSistemIrigasi.getTotalData(); i++) {
                            if(modelSistemIrigasi.getData().get(i).getIdSistemIrigasi().equalsIgnoreCase(dataProfilLahan.getIdSistemIrigasi().toString())){
                                namaSistemIrigasi = modelSistemIrigasi.getData().get(i).getNamaSistemIrigasi();
                            }
                        }
                    } catch (Exception e){}
                    if (!namaSistemIrigasi.equalsIgnoreCase("")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                setData();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSistemIrigasi> call, Throwable t) {
                binding.viewLoading.setVisibility(View.GONE);
                Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData()
    {
        String latitude = dataProfilLahan.getLatitude();
        String longitude = dataProfilLahan.getLongitude();

        ArrayList<GeoPoint> points = new ArrayList<>();
        String[] latitudes = latitude.split(";");
        String[] longitudes = longitude.split(";");

        for ( int i = 0; i < latitudes.length; i++ )
        {
            double lat = Double.parseDouble(latitudes[i]);
            double lon = Double.parseDouble(longitudes[i]);
            points.add(new GeoPoint(lat, lon));
        }

        drawLandDetail(points);

        binding.namaProfilLahan.setText(dataProfilLahan.getNamaProfilTanah());
        binding.luasGarapan.setText(dataProfilLahan.getLuasGarapan().toString() + " m2");

        Integer ph2 = dataProfilLahan.getPhTanah();
        double ph = ph2 / 10.0;
        binding.phTanah.setText(String.valueOf(ph));

        Integer kt2 = dataProfilLahan.getKemiringanTanah();
        double kt = kt2 / 10.0;
        binding.kemiringanTanah.setText(String.valueOf(kt));

        binding.sistemIrigasi.setText(namaSistemIrigasi);
    }

    private void drawLandDetail( ArrayList<GeoPoint> points )
    {
        if ( points == null || points.size() == 0 ) {
            return;
        }

        double latitude = 0;
        double longitude = 0;

        for ( GeoPoint point : points )
        {
            latitude += point.getLatitude();
            longitude += point.getLongitude();
        }

        latitude /= points.size();
        longitude /= points.size();

        GeoPoint center = new GeoPoint(latitude, longitude);

        String centerLatitude = String.valueOf(center.getLatitude());
        String centerLongitude = String.valueOf(center.getLongitude());

        String lat = centerLatitude.substring(0, Math.min(8, centerLatitude.length()));
        String lon = centerLongitude.substring(0, Math.min(7, centerLongitude.length()));

        binding.koordinatLahan.setText(lat + ", " + lon);

        binding.osmMapView.post(() -> {
            IMapController mapController = binding.osmMapView.getController();
            mapController.setZoom(18);
            mapController.setCenter(center);
            binding.osmMapView.invalidate();
        });

        ArrayList<OverlayItem> markerItems = new ArrayList<>();

        Drawable marker = getApplicationContext().getResources().getDrawable(R.drawable.ic_osm_marker);

        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();

        marker.setBounds(0, markerHeight, markerWidth, 0);

        for ( GeoPoint point : points )
        {
            OverlayItem mapItem = new OverlayItem(
                    "",
                    "",
                    point
            );

            mapItem.setMarker(marker);
            markerItems.add(mapItem);
        }

        ItemizedIconOverlay<OverlayItem> markerOverlay = new ItemizedIconOverlay<>(getApplicationContext(), markerItems,null);

        binding.osmMapView.getOverlays().add(markerOverlay);

        Polygon polygon = new Polygon();
        polygon.setPoints(points);
        polygon.getFillPaint().setColor(Color.argb(80, 0, 128, 0));

        polygon.getOutlinePaint().setColor(Color.GREEN);
        polygon.getOutlinePaint().setStrokeWidth(3f);

        binding.osmMapView.getOverlays().add(polygon);

        binding.osmMapView.invalidate();
    }

    public void updateProfilLahan()
    {
        binding.viewLoading.setVisibility(View.VISIBLE);
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
                sendDataProfilTanah();
            }
        }).start();
    }

    private void sendDataProfilTanah(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("idProfileTanah", dataProfilLahan.getIdProfileTanah());
        jsonParams.put("namaProfilTanah", binding.namaProfilLahan.getText().toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProfilLahan(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    if (rawResponse.body() != null) {
                        getProfilLahanTerbaru(dataProfilLahan.getIdProfileTanah());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(DetailProfilLahan.this, "Gagal ubah nama profil lahan", Toast.LENGTH_LONG).show();
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
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getProfilLahanTerbaru(String id){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilLahanById> dataRT = apiInterface.getDatumProfilLahan(id);
        dataRT.enqueue(new Callback<DataProfilLahanById>() {
            @Override
            public void onResponse(Call<DataProfilLahanById> call, Response<DataProfilLahanById> response) {
                dataProfilLahanById = response.body();
                if (response.body()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(DetailProfilLahan.this, "Berhasil ubah nama profil lahan", Toast.LENGTH_SHORT).show();
                            binding.namaProfilLahan.setText(dataProfilLahanById.getData().getNamaProfilTanah());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DataProfilLahanById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(DetailProfilLahan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void goToListProfilLahan(){
        Intent a = new Intent(DetailProfilLahan.this, ListProfileLahan.class);
        startActivity(a);
        finish();
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

    @Override
    public void onBackPressed() {
        goToListProfilLahan();
    }
}