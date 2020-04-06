package com.cs4530cpjd.trekmix.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cs4530cpjd.trekmix.MainActivity;
import com.cs4530cpjd.trekmix.MusicPlayer.MusicPlayer;
import com.cs4530cpjd.trekmix.R;
import com.cs4530cpjd.trekmix.SettingsActivity;
import com.cs4530cpjd.trekmix.gps.GetLocation;
import com.cs4530cpjd.trekmix.utility.observers.LocationObserver;
import com.cs4530cpjd.trekmix.utility.observers.NotificationBroadcastObserver;
import com.cs4530cpjd.trekmix.utility.observers.SongObserver;

public class BackgroundMusicPlayer extends Service implements LocationObserver, NotificationBroadcastObserver, SongObserver {
    private static final String TAG="Main";


    private static final String ChannelID="TREK_MIX_CHANNEL";
    private static final String ChannelDes="Location based music player channel";
    private static final String ChannelName="Location based music";

    private static final int NOTIFICATION_ID=88;


    public static final String NOTIFY_NEXT="com.cs4530cpjd.trekmix.service.next";
    public static final String NOTIFY_PLAY="com.cs4530cpjd.trekmix.service.play";
    public static final String NOTIFY_PAUSE="com.cs4530cpjd.trekmix.service.pause";
    public static final String NOTIFY_EDIT="com.cs4530cpjd.trekmix.service.edit";
    public static final String NOTIFY_KILL="com.cs4530cpjd.trekmix.service.kill";


    private MonitorLocation locationMonitor;
    private GetLocation locationGetter;
    private MusicPlayer musicPlayer;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private static boolean active=false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"Created Service");


        //get events from the notification's broadcast receiver
        NotificationBroadcast.addObserver(this);

        //create the notification
        TutorialNotificationCreator(this, "unknown location");

        //create a location monitor to keep updating us if the location changes
        locationMonitor=new MonitorLocation(this);
        locationMonitor.addObserver(this);

        //set up the gps to actually get location and pass it to the location monitor
        locationGetter = new GetLocation(this,locationMonitor);

        //set up the music player, this doesn't do anything yet
        musicPlayer=new MusicPlayer(this,locationMonitor);
        musicPlayer.addObserver(this);


        active=true;
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
        active=false;
        NotificationBroadcast.removeObserver(this);
        if(notificationManager!=null)
            notificationManager.cancel(NOTIFICATION_ID);

        if(!musicPlayer.isDestroyed())
            musicPlayer.destroy();

        Log.d(TAG,"Destroy Service");
        //tell the music player to stop playing?
    }
    @Override
    public void onLowMemory() {
    }

    public static boolean isActive(){
        return active;
    }

    /**
     * sample code from youtube tutorial
     */
    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
    private void TutorialNotificationCreator(Context context, String loc){
        RemoteViews expandedView=new RemoteViews(context.getPackageName(), R.layout.music_notification);

        NotificationManager nm=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(nm);

        NotificationCompat.Builder nc=new NotificationCompat.Builder(context,ChannelID);
        Intent notifyIntent=new Intent(context, MainActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                context,
                0,notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        nc.setContentIntent(pendingIntent);
        nc.setSmallIcon(R.drawable.ic_notification_empty);
//        nc.setAutoCancel(true);

//        nc.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        nc.setCustomContentView(expandedView);

        nc.setCustomBigContentView(expandedView);
        nc.setContentTitle("Trek Mix");
        nc.setContentText("Control Audio");

        nc.getBigContentView().setTextViewText(R.id.textSongName,"Poo song"); //possible error here, suppressed
        nc.getBigContentView().setTextViewText(R.id.textLocName,loc);

        nc.getContentView().setTextViewText(R.id.textSongName,"Poo song"); //possible error here, suppressed
        nc.getContentView().setTextViewText(R.id.textLocName,loc);

        nc.setOngoing(true);


//        if(Build.VERSION.SDK_INT>=21)
//            nc.setStyle(new Notification.MediaStyle().setMediaSession(/*token*/)));

        setListeners(expandedView,context);

        this.notificationManager=nm;
        this.notificationBuilder=nc;

        nm.notify(NOTIFICATION_ID,nc.build());

    }



    private void createNotificationChannel(NotificationManager nm) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ChannelID,
                    ChannelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(ChannelDes);
            nm.createNotificationChannel(channel);
        }
    }

    private void setListeners(RemoteViews view,Context context){
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent edit = new Intent(NOTIFY_EDIT);
        Intent kill = new Intent(NOTIFY_KILL);

        PendingIntent pNext=PendingIntent.getBroadcast(context,0,next,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnNext,pNext);

        PendingIntent pPlay=PendingIntent.getBroadcast(context,0,play,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPlay,pPlay);

        PendingIntent pPause=PendingIntent.getBroadcast(context,0,pause,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause,pPause);

        PendingIntent pEdit=PendingIntent.getBroadcast(context,0,edit,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnEdit,pEdit);

        PendingIntent pKill=PendingIntent.getBroadcast(context,0,kill,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnKill,pKill);

    }

    @Override
    public void passLocation(Location location,String readableLoc) {
//        TutorialNotificationCreator(this,readableLoc);

        resetLocation(readableLoc);
    }

    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
    private void resetLocation(String readableLoc){
        Log.d(TAG,"update notification "+readableLoc);
        notificationBuilder.getBigContentView().setTextViewText(R.id.textLocName,readableLoc);
        notificationBuilder.getContentView().setTextViewText(R.id.textLocName,readableLoc);

        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
    }

    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
    private void resetSong(String songTitle, Bitmap newIage){
        notificationBuilder.getBigContentView().setTextViewText(R.id.textSongName,songTitle);
        notificationBuilder.getContentView().setTextViewText(R.id.textSongName,songTitle);
        if(newIage!=null) {
            notificationBuilder.getBigContentView().setImageViewBitmap(R.id.albumImage, newIage);
            notificationBuilder.getContentView().setImageViewBitmap(R.id.albumImage, newIage);
        }

        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
    }

    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
    private void setPause(boolean paused){
        if(paused) {
            Log.d(TAG,"set paused true");
            notificationBuilder.getBigContentView().setViewVisibility(R.id.btnPause, View.VISIBLE);
            notificationBuilder.getBigContentView().setViewVisibility(R.id.btnPlay, View.GONE);
        }
        else{
            notificationBuilder.getBigContentView().setViewVisibility(R.id.btnPause, View.GONE);
            notificationBuilder.getBigContentView().setViewVisibility(R.id.btnPlay, View.VISIBLE);
        }

        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());

        musicPlayer.setPause(paused);
    }

    @Override
    public void receivedBroadcast(String action) {
        Log.d(TAG,"notify play");
        if(action.equals(NOTIFY_PLAY)){
            setPause(true);
        }
        else if(action.equals(NOTIFY_PAUSE)){
            Log.d(TAG,"notify pause");
            setPause(false);
        }
        else if(action.equals(NOTIFY_NEXT)){
            Log.d(TAG,"notify next");
            musicPlayer.playNext();
        }
        else if(action.equals(NOTIFY_EDIT)){
            Log.d(TAG,"notify edti");

            Intent intent=new Intent(this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
        else if(action.equals(NOTIFY_KILL)){
            Log.d(TAG,"notify kill");
            this.stopSelf();
        }
    }

    @Override
    public void songChanged(Packet song) {
        resetSong(song.title,song.albumCover);
    }
}
