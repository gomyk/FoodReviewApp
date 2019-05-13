package com.example.toyproject.AccountManager;

import java.util.Map;

public class UserAccountDataHolder {
    public static String sNickName = null;
    public static String sProfileImagePath = null;
    public static String sThumbnailPath = null;
    public static Map<String, String> sUserProperty;

    public static void setNickName(String nickName) {
        UserAccountDataHolder.sNickName = nickName;
    }

    public static void setProfileImagePath(String profileImagePath) {
        UserAccountDataHolder.sProfileImagePath = profileImagePath;
    }

    public static void setThumbnailPath(String thumbnailPath) {
        UserAccountDataHolder.sThumbnailPath = thumbnailPath;
    }

    public static void setUserProperty(Map<String, String> property) {
        UserAccountDataHolder.sUserProperty = property;
    }
}
