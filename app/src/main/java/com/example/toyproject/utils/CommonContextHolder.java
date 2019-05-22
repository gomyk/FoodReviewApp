package com.example.toyproject.utils;

import android.content.Context;
import android.text.TextUtils;

public class CommonContextHolder {
    private static Context sContext;
    private static String sLoginMethod;
    private static boolean sRecyclerViewVisible;

    public CommonContextHolder() {

    }

    public static Context getContext() {
        return sContext;
    }

    public static String getLoginMethod() {
        return sLoginMethod;
    }
    public static boolean getRecyclerViewVisible(){
        return sRecyclerViewVisible;
    }
    public static void setContext(Context context) {
        if (context != null) {
            CommonContextHolder.sContext = context;
        }
    }

    public static void setLoginMethod(String loginMethod) {
        if (!TextUtils.isEmpty(loginMethod)) {
            CommonContextHolder.sLoginMethod = loginMethod;
        }
    }

    public static void setRecyclerViewVisible(boolean visible){
        sRecyclerViewVisible = visible;
    }
}
