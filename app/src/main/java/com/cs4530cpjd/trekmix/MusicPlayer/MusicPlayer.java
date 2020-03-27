package com.cs4530cpjd.trekmix.MusicPlayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.cs4530cpjd.trekmix.service.MonitorLocation;
import com.cs4530cpjd.trekmix.utility.observers.LocationObserver;
import com.cs4530cpjd.trekmix.utility.observers.SongObserver;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Destroyable;


public class MusicPlayer implements Destroyable {

    private static final String TAG="Main";

    private List<SongObserver> observers;

    public MusicPlayer(Context context, MonitorLocation locationMonitor){

            observers=new ArrayList<>();
    }

    public void addObserver(SongObserver observer){

    }

    /**
     * notify observers that the song has changed (this updates the notification
     * @param title
     * @param length
     * @param albumCover
     */
    private void passObservers(String title, float length, Bitmap albumCover){
        for(SongObserver o : observers)
            o.songChanged(new SongObserver.Packet(title,albumCover,length));
    }

    public void setPause(boolean pause){
        Log.d(TAG,"Set pause, unimplimented in music player");
    }

    public void playNext(){
        Log.d(TAG,"play next, unimplimented in music player");

    }

    @Override
    public void destroy(){

    }

    @Override
    public boolean isDestroyed(){

        return true;
    }
}
