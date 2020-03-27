package com.cs4530cpjd.trekmix.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;

import com.cs4530cpjd.trekmix.MainActivity;
import com.cs4530cpjd.trekmix.R;
import com.cs4530cpjd.trekmix.gps.GetLocation;

public class ServiceStarter implements Runnable {
    private static final String TAG ="Main";
//    private static final String ChannelID="TREK_MIX_CHANNEL";
//    private static final String ChannelDes="Location based music player channel";
//    private static final String ChannelName="Location based music";
//
//    private static final int NOTIFICATION_ID=88;


//    public static final String NOTIFY_NEXT="com.cs4530cpjd.trekmix.gps.GetLocation.next";
//    public static final String NOTIFY_PLAY="com.cs4530cpjd.trekmix.gps.GetLocation.play";
//    public static final String NOTIFY_PAUSE="com.cs4530cpjd.trekmix.gps.GetLocation.pause";
//    public static final String NOTIFY_EDIT="com.cs4530cpjd.trekmix.gps.GetLocation.edit";
//    public static final String NOTIFY_KILL="com.cs4530cpjd.trekmix.gps.GetLocation.kill";


    Activity activity;

    public ServiceStarter(Activity activity){
        this.activity=activity;
    }

    @Override
    public void run() {

        Log.d(TAG,"Start Clicked");
        Toast. makeText(activity,"Start Clicked",Toast. LENGTH_SHORT).show();


        Intent intent = new Intent(activity, BackgroundMusicPlayer.class);
        if(!BackgroundMusicPlayer.isActive()) {
            Toast. makeText(activity,"Starting Player",Toast. LENGTH_SHORT).show();
            activity.startService(intent);
        }
        else {
            Toast. makeText(activity,"Ending Player",Toast. LENGTH_SHORT).show();
            activity.stopService(intent);
        }

//        TutorialNotificationCreator();
    }

    /**
     * sample code from class, mostly here for reference
     */
//    private void createNotification() {
//        final Intent intent = new Intent(activity, MainActivity.class);
//        final PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
//        Notification.Builder nb;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            nb = new Notification.Builder(activity, ChannelID);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(ChannelID, ChannelID, importance);
//// Register the channel with the system; you can't change the importance
//// or other notification behaviors after this
//            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            Log.d(TAG, "Channel " + ChannelID + " registered");
//        } else {
//            nb = new Notification.Builder(activity);
//        }
////        final Notification notification = nb.setSmallIcon(android.R.drawable.ic_media_play)
////                .setOngoing(true)
////                .setContentTitle("Song Playing")
////                .setContentText("Click to go to the Music Player");
////                .setContentIntent(pendingIntent).build();
//// Make this a foreground service
//
//
////        activity.startService(notification);
//    }


    /**
     * sample code from youtube tutorial (Moved to service/BackgroundMusicPlayer
     */
//    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
//    private void TutorialNotificationCreator(){
//        RemoteViews expandedView=new RemoteViews(activity.getPackageName(), R.layout.music_notification);
//
//        NotificationManager nm=(NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
//        createNotificationChannel(nm);
//
//        NotificationCompat.Builder nc=new NotificationCompat.Builder(activity,ChannelID);
//        Intent notifyIntent=new Intent(activity, MainActivity.class);
//
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent
//                = PendingIntent.getActivity(
//                activity,
//                0,notifyIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        nc.setContentIntent(pendingIntent);
//        nc.setSmallIcon(R.drawable.ic_notification_empty);
//        nc.setAutoCancel(true);
//        nc.setCustomBigContentView(expandedView);
//        nc.setContentTitle("Trek Mix");
//        nc.setContentText("Control Audio");
//
//        nc.getBigContentView().setTextViewText(R.id.textSongName,"Poo song"); //possible error here, suppressed
//        nc.getBigContentView().setTextViewText(R.id.textLocName,"unknown loc");
//
//        setListeners(expandedView,activity);
//
//        nm.notify(5,nc.build());
//
//    }

//    private void setListeners(RemoteViews view,Context context){
//        Intent next = new Intent(NOTIFY_NEXT);
//        Intent play = new Intent(NOTIFY_PLAY);
//        Intent pause = new Intent(NOTIFY_PAUSE);
//        Intent edit = new Intent(NOTIFY_EDIT);
//        Intent kill = new Intent(NOTIFY_KILL);
//
//        PendingIntent pNext=PendingIntent.getBroadcast(context,0,next,PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnNext,pNext);
//
//        PendingIntent pPlay=PendingIntent.getBroadcast(context,0,play,PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnPlay,pPlay);
//
//        PendingIntent pPause=PendingIntent.getBroadcast(context,0,pause,PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnPause,pPause);
//
//        PendingIntent pEdit=PendingIntent.getBroadcast(context,0,edit,PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnEdit,pEdit);
//
//        PendingIntent pKill=PendingIntent.getBroadcast(context,0,kill,PendingIntent.FLAG_UPDATE_CURRENT);
//        view.setOnClickPendingIntent(R.id.btnKill,pKill);
//
//    }

//    private void createNotificationChannel(NotificationManager nm) {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    ChannelID,
//                    ChannelName,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(ChannelDes);
//            nm.createNotificationChannel(channel);
//        }
//    }


    @Override
    public void finalize(){

    }


}
