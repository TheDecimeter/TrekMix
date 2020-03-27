package com.cs4530cpjd.trekmix.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cs4530cpjd.trekmix.utility.observers.NotificationBroadcastObserver;

import java.util.HashSet;

public class NotificationBroadcast extends BroadcastReceiver {

    private static final String TAG="Main";

    private static HashSet<NotificationBroadcastObserver> observers;


    @Override
    public void onReceive(Context context, Intent intent) {
//        if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_PLAY)){
//            Toast.makeText(context,"NOTIFY PLAY", Toast.LENGTH_SHORT).show();
//        }
//        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_PAUSE)){
//            Toast.makeText(context,"NOTIFY PAUSE", Toast.LENGTH_SHORT).show();
//        }
//        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_NEXT)){
//            Toast.makeText(context,"NOTIFY NEXT", Toast.LENGTH_SHORT).show();
//        }
//        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_EDIT)){
//            Toast.makeText(context,"NOTIFY EDIT", Toast.LENGTH_SHORT).show();
//        }
//        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_KILL)){
//            Toast.makeText(context,"NOTIFY KILL", Toast.LENGTH_SHORT).show();
//        }
        passMessage(intent.getAction());

    }

    private void passMessage(String msg){
        if(observers==null)
            return;
        for(NotificationBroadcastObserver o : observers)
            o.receivedBroadcast(msg);
    }


    public static void addObserver(NotificationBroadcastObserver observer){
        if(observers==null)
            observers=new HashSet<>();
        if(observers.contains(observer))
            return;
        observers.add(observer);
    }

    public static void removeObserver(NotificationBroadcastObserver observer){
        if(observers==null)
            return;
        if(observers.contains(observer))
            observers.remove(observer);
    }
}
