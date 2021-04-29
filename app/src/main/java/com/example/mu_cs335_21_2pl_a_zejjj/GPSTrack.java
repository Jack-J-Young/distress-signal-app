package com.example.mu_cs335_21_2pl_a_zejjj;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GPSTrack extends Service implements LocationListener {
    private Context mContext;

    // GPS status
    boolean isGPSEnabled = false;

    // network status
    boolean isNetworkEnabled = false;

    // GPS status
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static long minUpdateDistance = 1;

    // The minimum time between updates in milliseconds
    private static long minUpdateTime = 1000 * 60 * 1; // 1 minute

    // Location Manager
    protected LocationManager locationManager;


    public GPSTrack(Context context)
    {
        this.mContext = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public void getLocation()
    {
        location=null;
        try
        {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled)
            {
                // no network provider is enabled
            }
            else
            {


                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                {
                    canGetLocation=true;

                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                minUpdateTime,
                                minUpdateDistance, this);

                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                // First get location from Network Provider
                else  if (isNetworkEnabled)
                {


                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            minUpdateTime,
                            minUpdateDistance, this);

                    Log.d("Network", "Network");
                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    // return latitude
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }


        return latitude;
    }


    //Return Longitude
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }




    //Check if wifi/gps enabled
    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
