package com.cs4530cpjd.trekmix.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cs4530cpjd.trekmix.MainActivity;
import com.cs4530cpjd.trekmix.MusicPlayer.MusicPlayer;
import com.cs4530cpjd.trekmix.R;
import com.cs4530cpjd.trekmix.gps.GetLocation;

public class BackgroundMusicPlayer extends Service {
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
        musicPlayer=new MusicPlayer(this,locationMonitor);

        TutorialNotificationCreator(this);
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


    /**
     * sample code from youtube tutorial
     */
    @SuppressLint("RestrictedApi") //getBigContentView gives errors which might be a bug in the system
    private void TutorialNotificationCreator(Context context){
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
        nc.setAutoCancel(true);
        nc.setCustomBigContentView(expandedView);
        nc.setContentTitle("Trek Mix");
        nc.setContentText("Control Audio");

        nc.getBigContentView().setTextViewText(R.id.textSongName,"Poo song"); //possible error here, suppressed
        nc.getBigContentView().setTextViewText(R.id.textLocName,"unknown loc");

        setListeners(expandedView,context);

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

}
