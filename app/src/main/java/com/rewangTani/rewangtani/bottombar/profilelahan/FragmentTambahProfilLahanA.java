package com.rewangTani.rewangtani.bottombar.profilelahan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPlFragmentTambahProfilLahanABinding;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTambahProfilLahanA extends Fragment implements OnMapReadyCallback {

    BottombarPlFragmentTambahProfilLahanABinding binding;
    private GoogleMap mMap;
    ModelProfilLahan modelProfilLahan;
    List<String> listProfilLahan = new ArrayList<>();
    int checkNama, checkLatLong;
    LocationManager locationManager;
    int PERMISSION_CODE = 1;
    Double lat, longt;
    String lat2, longt2;

    public FragmentTambahProfilLahanA() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottombar_pl_fragment_tambah_profil_lahan_a , container , false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.bottombar_pl_fragment_tambah_profil_lahan_a);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        lat = location.getLatitude();
        longt = location.getLongitude();
        lat2 = String.valueOf(lat).substring(0, 8);
        longt2 = String.valueOf(longt).substring(0, 7);

//        int status = GooglePlayServicesUtil
//                .isGooglePlayServicesAvailable(getBaseContext());
//
//        // Showing status
//        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
//            // not available
//            int requestCode = 10;
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
//                    requestCode);
//            dialog.show();
//        } else {
//            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }

        //getData();

        binding.btnSelanjutnya.setOnClickListener(v -> {
            goToTambahProfilLahanB();
            //checkNama();
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
                            if (PreferenceUtils.getIdAkun(getContext())
                                    .equalsIgnoreCase(modelProfilLahan.getData().get(i).getIdUser())) {
                                listProfilLahan.add(modelProfilLahan.getData().get(i).getNamaProfilTanah().toString());
                            }
                        } catch (Exception e) {
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            binding.koordinatLahan.setText(lat + ", " + longt);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelProfilLahan> call, Throwable t) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                                call.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enabling MyLocation Layer of Google Map
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });

    }

    private void checkNama() {
        if (!binding.namaProfilLahan.getText().toString().equalsIgnoreCase("")){
            if (listProfilLahan.size() == 0) {
                checkLatLong();
            } else if (listProfilLahan.size() > 0) {
                for (int i = 0; i < listProfilLahan.size(); i++) {
                    if (binding.namaProfilLahan.getText().toString().equalsIgnoreCase(listProfilLahan.get(i))) {
                        Toast.makeText(getContext(), "Nama profil lahan sudah dipakai", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Isi nama profil lahan terlebih dahulu !", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkLatLong() {
        if (!lat2.equalsIgnoreCase("") && !longt2.equalsIgnoreCase("")) {
            for (int i = 0; i < modelProfilLahan.getTotalData(); i++) {
                if (modelProfilLahan.getData().get(i).getLatitude().equalsIgnoreCase(lat2) &&
                        modelProfilLahan.getData().get(i).getLongitude().equalsIgnoreCase(longt2)) {
                    Toast.makeText(getContext(), "Lokasi lahan sudah terpakai", Toast.LENGTH_SHORT).show();
                    checkLatLong = 1;
                    break;
                }
            }
            if (checkLatLong != 1) {
                checkLatLong = 0;
                tambahDataProfilLahan();
            } else {
                checkLatLong = 0;
            }
        }
    }

    private void tambahDataProfilLahan() {
        PreferenceUtils.savePLnamaProfilLahan(binding.namaProfilLahan.getText().toString(), getContext());
        PreferenceUtils.savePLlatitude(lat2, getContext());
        PreferenceUtils.savePLlongitude(longt2, getContext());
        goToTambahProfilLahanB();
    }

    public void goToTambahProfilLahanB() {
        Intent a = new Intent(getActivity(), FragmentTambahProfilLahanB.class);
        startActivity(a);
//        getActivity().overridePendingTransition(R.anim.slide_in_right,
//                R.anim.slide_out_left);
    }


/*    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }*/

}