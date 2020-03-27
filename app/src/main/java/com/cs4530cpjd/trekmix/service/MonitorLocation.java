package com.cs4530cpjd.trekmix.service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.utility.observers.LocationObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps turns location into meaningful
 * actions for the app
 */
public class MonitorLocation {
    private static final String TAG ="Main";

    private List<LocationObserver> observers;

    Context context;
    public MonitorLocation(Context context){
        observers=new ArrayList<>();
        this.context=context;
    }

    public void addObserver(LocationObserver observer){
        observers.add(observer);
    }
    public void updateLocation(Location loc){
        Log.d(TAG,"Monitored Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude());

        passObservers(loc);

//        Toast.makeText(
//                context,
//                "Monitored Location changed: Lat: " + loc.getLatitude() + " Lng: "
//                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    private void passObservers(Location loc){
        String s=getLocationName(loc);
        for(LocationObserver o : observers)
            o.passLocation(loc,s);
    }

    /**
     * This can take a loc, see if it is near a pin, if so, return that loc's name
     * otherwise, return generic loc info
     * @param loc the loc
     * @return a human readable description of a location, or coordinates
     */
    private String getLocationName(Location loc){
        String r;
        //right now I just return coordinates
        r="Location: "+loc.getLatitude()+", "+loc.getLongitude();

        return r;
    }
}
