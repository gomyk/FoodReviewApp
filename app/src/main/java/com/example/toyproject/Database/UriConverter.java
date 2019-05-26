package com.example.toyproject.Database;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class UriConverter {

    @TypeConverter
    public String fromList(List<String> uriList) {
        if (uriList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(uriList, type);
        return json;
    }

    @TypeConverter
    public List<String> toList(String input) {
        if (input == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> imageList = gson.fromJson(input, type);
        return imageList;
    }
}
