package com.cs4530cpjd.trekmix.utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.LinkedList;


/**
 * helper class to streamline permission requesting
 */
public class Permissions {
    private static String TAG="PermReq";

    //to add permissions, add a constant below, a result object and link them with the 4 classes at the bottom
    public static final int C_READ_EXTERNAL_STORAGE =100, C_WRITE_EXTERNAL_STORAGE =101, C_ACCESS_FINE_LOCATION =102, C_ACCESS_COARSE_LOCATION =103;
    private static LinkedList<PermissionResult> ReadResult,WriteResult,FineLocationResult,CoarseLocationResult;
    private static Activity activity;

    public Permissions(Activity activity){
        Permissions.activity=activity;
        initResults();
    }

    /**
     * Check if permission is required on appropriate platforms, if so,
     * request it.
     * @param requestCode - The integer code for the request, Try constants built into this class,
     *                    e.g. C_READ_EXTERNAL_STORAGE
     * @param result - The result call back class which fires when permission has been
     *               granted or denied (access will be true if permission is granted)
     */
    public static void request(int requestCode, PermissionResult result){
        if(activity==null){
            Log.e(TAG,"Can not request permission without being linked" +
                    " to an activity");
            return;
        }
        addRequestHandler(requestCode,result);
        if(Build.VERSION.SDK_INT>=23) {
            String permission=codeToPermission(requestCode);
            if (ContextCompat.checkSelfPermission(activity, permission)
                    == PackageManager.PERMISSION_DENIED) {

                // Requesting the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
            }
            else {
                requestHandler(requestCode,new int [] {PackageManager.PERMISSION_GRANTED});
            }
        }
        else{
            requestHandler(requestCode,new int [] {PackageManager.PERMISSION_GRANTED});
        }
    }



    private static void addResultHandler(LinkedList<PermissionResult> results, PermissionResult result){
        Log.d(TAG,"Add REQUEST itself ");
        if(results==null){
            Log.e(TAG,"trying to add callback before initializing callback stack");
            return;
        }
        results.add(result);
    }


    private static void fireResults(LinkedList<PermissionResult> results,boolean allowed){
        try {

            Log.d(TAG,"fire request list size "+results.size());
            for (PermissionResult r : results)
                r.result(allowed);
        }
        catch(SecurityException e){
            Toast.makeText(activity,
                    "Security Exception thrown",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        results.clear();
    }



    /**
     * Handle requests, this should only be called in a request handler in the current activity
     * @param requestCode - The integer code for the request, e.g. C_READ_EXTERNAL_STORAGE
     * @param grantResults - Whether the permission was granted or not.
     */
    public static void requestHandler(int requestCode, @NonNull int[] grantResults){
        Log.d(TAG,"STATIC onRequest handler "+requestCode);
        switch(requestCode){
            case C_READ_EXTERNAL_STORAGE:
                fireResults(ReadResult,grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case C_WRITE_EXTERNAL_STORAGE:
                fireResults(WriteResult,grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case C_ACCESS_COARSE_LOCATION:
                fireResults(CoarseLocationResult,grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case C_ACCESS_FINE_LOCATION:
                fireResults(FineLocationResult,grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
        }
    }

    private static void addRequestHandler(int requestCode, PermissionResult result){
        Log.d(TAG,"Add REQUEST "+requestCode);
        switch(requestCode){
            case C_READ_EXTERNAL_STORAGE:
                addResultHandler(ReadResult, result);
                break;
            case C_WRITE_EXTERNAL_STORAGE:
                addResultHandler(WriteResult, result);
                break;
            case C_ACCESS_COARSE_LOCATION:
                addResultHandler(CoarseLocationResult, result);
                break;
            case C_ACCESS_FINE_LOCATION:
                addResultHandler(FineLocationResult, result);
                break;
        }
    }


    private static String codeToPermission(int requestCode){
        Log.d(TAG,"Get REQUEST name "+requestCode);
        switch(requestCode){
            case C_READ_EXTERNAL_STORAGE:
                return Manifest.permission.READ_EXTERNAL_STORAGE;
            case C_WRITE_EXTERNAL_STORAGE:
                return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case C_ACCESS_COARSE_LOCATION:
                return Manifest.permission.ACCESS_COARSE_LOCATION;
            case C_ACCESS_FINE_LOCATION:
                return Manifest.permission.ACCESS_FINE_LOCATION;
            default:
                return"";
        }
    }

    private static void initResults(){
        Log.d(TAG,"init results ");
        ReadResult=new LinkedList<>();
        WriteResult=new LinkedList<>();
        FineLocationResult=new LinkedList<>();
        CoarseLocationResult=new LinkedList<>();
    }


}
