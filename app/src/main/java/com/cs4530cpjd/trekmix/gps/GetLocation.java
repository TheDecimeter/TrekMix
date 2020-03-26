package com.cs4530cpjd.trekmix.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.utility.PermissionResult;
import com.cs4530cpjd.trekmix.utility.Permissions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocation {
    private static final String TAG="Main";

    Activity activity;

    LocationManager locationManager;

    public GetLocation(Activity activity) {
        this.activity = activity;
        SetupListener(locationManager);
    }

    public boolean isRunning(){
        return locationManager!=null;
    }


    private void SetupListener(final LocationManager locationManager) {
        LocationListener locationListener = new MyLocationListener();
        LocationPermission p=new LocationPermission();
        p.locationListener=locationListener;
        p.locationManager=(LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Permissions.request(Permissions.C_ACCESS_FINE_LOCATION,p);

        this.locationManager=p.locationManager;
    }


    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {
//            editLocation.setText("");
//            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(
                    activity,
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.d(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.d(TAG, latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    Log.v(TAG,addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            Log.d(TAG,s);
//            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    private class LocationPermission implements PermissionResult{

        public LocationManager locationManager;
        public LocationListener locationListener;

        @Override
        public void result(boolean accepted) throws SecurityException {
            Log.d("Main","fired request itself "+accepted);
            if(accepted) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                Location l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Toast. makeText(activity,"got loc",Toast. LENGTH_SHORT).show();
                Toast. makeText(activity,"got loc "+l.getLatitude(),Toast. LENGTH_SHORT).show();
            }
            else{
                locationManager=null;
                Toast. makeText(activity,"Permissions for Location denied",Toast. LENGTH_SHORT).show();
            }
        }
    }
}
