package com.cs4530cpjd.trekmix.service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

/**
 * This class keeps turns location into meaningful
 * actions for the app
 */
public class MonitorLocation {
    private static final String TAG ="Main";

    Context context;
    public MonitorLocation(Context context){
        this.context=context;
    }
    public void updateLocation(Location loc){
        Log.d(TAG,"Monitored Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude());
        Toast.makeText(
                context,
                "Monitored Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
    }
}
