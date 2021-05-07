package com.example.weatherchecker;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue requestQueue;


    public VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized VolleySingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue(){
        return  requestQueue;
    }


}
