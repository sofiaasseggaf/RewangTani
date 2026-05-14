package com.rewangTani.rewangtani.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.rewangTani.rewangtani.ui.home.Home;

public class LocationHelper
{

    public interface LocationResultCallback
    {
        void onLocationFound(double lat, double lon);
        void onLocationFailed();
    }

    public static void getLastKnownLocation(Context context, LocationListener listener, LocationResultCallback callback)
    {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if ( location == null )
        {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if ( location == null )
        {
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }

        if ( location != null && location.getLatitude() != 0.0 )
        {
            callback.onLocationFound(location.getLatitude(), location.getLongitude());
        } else {
            callback.onLocationFailed();
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000, 300, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 600000, 300, listener);
    }

    public static void stopLocationUpdates(Context context, LocationListener listener)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm != null && listener != null) {
            lm.removeUpdates(listener);
        }
    }

    //    public static void startMovingLocationUpdates(Context context, LocationListener listener)
    //    {
    //
    //        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    //        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
    //                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    //        {
    //            // Request updates every 1 minutes (600000ms) or if user move 300 meters
    //            locationManager.requestLocationUpdates(
    //                    LocationManager.NETWORK_PROVIDER,
    //                    600000,
    //                    300,
    //                    listener
    //            );
    //
    //            locationManager.requestLocationUpdates(
    //                    LocationManager.GPS_PROVIDER,
    //                    600000,
    //                    300,
    //                    listener
    //            );
    //        }
    //    }
}