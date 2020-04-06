package com.cs4530cpjd.trekmix.MusicPlayer;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.util.Log;
import android.media.MediaPlayer;
import android.provider.MediaStore.Audio.Media;

import com.cs4530cpjd.trekmix.service.MonitorLocation;
import com.cs4530cpjd.trekmix.utility.observers.SongObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Destroyable;


public class MusicPlayer implements Destroyable {

    private static final String TAG="Main";
    private MediaPlayer mPlayer;
    private Context c;
    private long[] ids;
    private int nextIndex;
    private int numSongs;
    private MonitorLocation locMonster;

    private List<SongObserver> observers;

    // TODO: Figure out how to get playlist for current area if there is one.
    public MusicPlayer(Context context, MonitorLocation locationMonitor){
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        observers=new ArrayList<>();
        c = context;
        locMonster = locationMonitor;
    }

    public void addObserver(SongObserver observer){
        observers.add(observer);
    }

    /**
     * notify observers that the song has changed (this updates the notification
     * @param title: The track name
     * @param length: The track runtime
     * @param albumCover: The track album cover
     */
    private void passObservers(String title, float length, Bitmap albumCover){
        for(SongObserver o : observers)
            o.songChanged(new SongObserver.Packet(title,albumCover,length));
    }

    public void setPause(boolean pause){
        if(pause)
        {
            mPlayer.pause();
        }
        else
        {
            mPlayer.start();
        }
//        Log.d(TAG,"Set pause, unimplimented in music player");
    }

    public void playNext(){
//        Log.d(TAG,"play next, unimplimented in music player");
        mPlayer.stop();
        if(nextIndex < numSongs)
        {
            setNextFile(ids[nextIndex++]);
            try
            {
                mPlayer.prepare();
                mPlayer.start();
            }
            catch (IOException ioe)
            {
                Log.d(TAG, "setting datasource failed");
            }
        }
        passObservers("CHANGEME", mPlayer.getDuration(), null);
        // TODO: Need to figure out how to get title and album cover
    }

    private void setNextFile(long id)
    {
        try
        {
            mPlayer.setDataSource(c, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id));
        }
        catch (IOException ioe)
        {
            Log.d(TAG, "setting datasource failed");
        }
    }

    private void setPlaylist(long[] newIds)
    {
        ids = newIds;
        nextIndex = 0;
        numSongs = newIds.length;
    }

    @Override
    public void destroy(){

    }

    @Override
    public boolean isDestroyed(){

        return true;
    }
}
