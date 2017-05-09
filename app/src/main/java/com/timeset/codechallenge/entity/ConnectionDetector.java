package com.timeset.codechallenge.entity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetector {
    private Context mContext;

    public ConnectionDetector(Context context){
        this.mContext = context;
    }

    /* Check if the phone is connected through mobile network or WiFi */
    public boolean isNetworkConnectedPP(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = false;
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null){
                switch (networkInfo.getType()){
                    case ConnectivityManager.TYPE_WIFI:
                        isConnected =  true;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        isConnected = true;
                        break;
                    default:
                        isConnected = false;
                        break;
                }
            }
        }

        return isConnected;
    }

}
