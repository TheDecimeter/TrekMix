package com.cs4530cpjd.trekmix.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.gps.GetLocation;

public class ServiceStarter implements Runnable {
    private static final String TAG ="Main";

    Activity activity;
    GetLocation locationGetter;

    public ServiceStarter(Activity activity){
        this.activity=activity;
    }

    @Override
    public void run() {

        Log.d(TAG,"Started Service");
        Toast. makeText(activity,"Start Clicked",Toast. LENGTH_SHORT).show();

        if(locationGetter==null)
            locationGetter=new GetLocation(activity);
        else {
            Toast. makeText(activity,"Already started",Toast. LENGTH_SHORT).show();
            Log.d(TAG, "already started");
        }

    }

    @Override
    public void finalize(){

    }
}
