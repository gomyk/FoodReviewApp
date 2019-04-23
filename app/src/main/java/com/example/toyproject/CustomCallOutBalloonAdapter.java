package com.example.toyproject;

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
        ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.ic_launcher_background);
        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
        ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("Custom CalloutBalloon");
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}
