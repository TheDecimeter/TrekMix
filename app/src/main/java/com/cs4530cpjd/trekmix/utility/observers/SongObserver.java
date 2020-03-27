package com.cs4530cpjd.trekmix.utility.observers;

import android.graphics.Bitmap;

public interface SongObserver {
    void songChanged(Packet song);

    class Packet{
        public final String title;
        public final Bitmap albumCover;
        public final float length;

        public Packet(String title,Bitmap albumCover, float length){
            this.title=title;
            this.albumCover=albumCover;
            this.length=length;
        }
    }
}
