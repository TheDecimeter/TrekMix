package com.cs4530cpjd.trekmix.service;

import android.content.Context;
import android.widget.Toast;

public class ServiceStarter implements Runnable {

    Context context;
    public ServiceStarter(Context context){
        this.context=context;
    }

    @Override
    public void run() {
        Toast. makeText(context,"Start Clicked",Toast. LENGTH_SHORT).show();
    }
}
