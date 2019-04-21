package com.example.toyproject.utils;

import android.content.Context;

public class CommonContextHolder {
    private static Context mContext;
    public CommonContextHolder(){

    }
    public static Context getContext(){
        return mContext;
    }
    public static void setContext(Context context){
        if(context != null){
            CommonContextHolder.mContext = context;
        }
    }
}
