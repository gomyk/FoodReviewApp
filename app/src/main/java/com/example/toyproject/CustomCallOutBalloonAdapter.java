package com.example.toyproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.toyproject.utils.CommonContextHolder;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class CustomCallOutBalloonAdapter implements CalloutBalloonAdapter {

    private final View mCalloutBalloon;

    public CustomCallOutBalloonAdapter() {
        LayoutInflater layoutInflater = LayoutInflater.from(CommonContextHolder.getContext());
        mCalloutBalloon = layoutInflater.inflate(R.layout.callout_balloon, null);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}
