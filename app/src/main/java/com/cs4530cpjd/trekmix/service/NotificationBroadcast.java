package com.cs4530cpjd.trekmix.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_PLAY)){
            Toast.makeText(context,"NOTIFY PLAY", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_PAUSE)){
            Toast.makeText(context,"NOTIFY PAUSE", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_NEXT)){
            Toast.makeText(context,"NOTIFY NEXT", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_EDIT)){
            Toast.makeText(context,"NOTIFY EDIT", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(BackgroundMusicPlayer.NOTIFY_KILL)){
            Toast.makeText(context,"NOTIFY KILL", Toast.LENGTH_SHORT).show();
        }
    }
}
