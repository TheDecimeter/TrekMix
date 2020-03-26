package com.cs4530cpjd.trekmix.service;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.gps.GetLocation;

public class ServiceStarter implements Runnable {
    private static final String TAG ="Main";

    Activity activity;

    public ServiceStarter(Activity activity){
        this.activity=activity;
    }

    @Override
    public void run() {

        Log.d(TAG,"Start Clicked");
        Toast. makeText(activity,"Start Clicked",Toast. LENGTH_SHORT).show();

        Intent intent = new Intent(activity, BackgroundMusicPlayer.class);
        activity.startService(intent);


    }


    @Override
    public void finalize(){

    }


}
