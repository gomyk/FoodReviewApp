package com.example.toyproject.utils;

import android.content.Context;
import android.text.TextUtils;

public class CommonContextHolder {
    private static Context sContext;
    private static String sLoginMethod;

    public CommonContextHolder() {

    }

    public static Context getContext() {
        return sContext;
    }

    public static String getLoginMethod() {
        return sLoginMethod;
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
}
