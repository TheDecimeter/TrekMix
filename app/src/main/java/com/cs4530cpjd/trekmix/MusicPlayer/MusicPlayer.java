package com.cs4530cpjd.trekmix.MusicPlayer;

import android.content.Context;

import com.cs4530cpjd.trekmix.service.MonitorLocation;

import javax.security.auth.Destroyable;


public class MusicPlayer implements Destroyable {

    public MusicPlayer(Context context, MonitorLocation locationMonitor){

    }

    public void setPause(boolean pause){

    }

    public void playNext(){

    }

    @Override
    public void destroy(){

    }

    @Override
    public boolean isDestroyed(){

        return true;
    }
}
