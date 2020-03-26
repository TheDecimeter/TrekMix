package com.cs4530cpjd.trekmix.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cs4530cpjd.trekmix.gps.GetLocation;

public class BackgroundMusicPlayer extends Service {
    private static final String TAG="Main";

    private MonitorLocation locationMonitor;
    private GetLocation locationGetter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Created Service");

        //create a location monitor to keep updating us if the location changes
        locationMonitor=new MonitorLocation(this);
        locationGetter = new GetLocation(this,locationMonitor);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG,"Start Command Service");
        Toast.makeText(getApplicationContext(), "Commanded to start",    Toast.LENGTH_SHORT).show();
        return startId;
    }
    public void onStart(Intent intent, int startId) {

        Log.d(TAG,"Start Service");
    }
    @Override
    public void onDestroy() {
        Log.d(TAG,"Destroy Service");
        //tell the music player to stop playing?
    }
    @Override
    public void onLowMemory() {
    }


}
