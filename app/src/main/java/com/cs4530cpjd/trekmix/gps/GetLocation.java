package com.cs4530cpjd.trekmix.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.service.MonitorLocation;
import com.cs4530cpjd.trekmix.utility.PermissionResult;
import com.cs4530cpjd.trekmix.utility.Permissions;



/**
 * This class is the nuts and bolts of getting the location and keeping the specific
 * coordinates up to date, for pin/region handling, use the MonitorLocation class
 */
public class GetLocation {
    private static final String TAG="Main";

//    Activity activity;

    Context context;
    LocationManager locationManager;
    MonitorLocation locationMonitor;

    public GetLocation(Context context,MonitorLocation locationMonitor) {
        this.context = context;
        this.locationMonitor=locationMonitor;
        SetupListener();
    }

    /**
     * See if the location service is currently running
     * @return true if the location service is updating
     */
    public boolean isRunning(){
        return locationManager!=null;
    }


    private void SetupListener() {
        LocationListener locationListener = new LocationListenerWithCallback();
        LocationPermission p=new LocationPermission();
        p.locationListener=locationListener;
        p.locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Permissions.request(Permissions.C_ACCESS_FINE_LOCATION,p);

        this.locationManager=p.locationManager;
    }




    private class LocationListenerWithCallback implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {

            GetLocation.this.locationMonitor.updateLocation(loc);
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
            Log.d(TAG,"fired request itself "+accepted);
            if(accepted) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                Location l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            else{
                locationManager=null;
                Toast. makeText(context,"Permissions for Location denied",Toast. LENGTH_SHORT).show();
            }
        }
    }
}
