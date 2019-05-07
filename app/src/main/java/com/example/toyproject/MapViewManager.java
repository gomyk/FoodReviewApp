package com.example.toyproject;

import android.util.Log;
import android.view.ViewGroup;

import com.example.toyproject.utils.CommonContextHolder;
import com.example.toyproject.utils.LocationInfoProvider;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapViewManager implements MapView.POIItemEventListener {
    private static final String TAG = "MapViewManager";
    private MapView mMapView;
    final LocationInfoProvider locationInfoProvider;

    public MapViewManager(ViewGroup container) {
        mMapView = new MapView(CommonContextHolder.getContext());
        mMapView.setPOIItemEventListener(this);
        mMapView.setCalloutBalloonAdapter(new CustomCallOutBalloonAdapter());
        container.addView(mMapView);
        locationInfoProvider = new LocationInfoProvider(CommonContextHolder.getContext());
    }

    public void addCurrentLocationMarker() {
        mMapView.removeAllPOIItems();
        mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(locationInfoProvider.getLatitude(), locationInfoProvider.getLongitude()), 1, true);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(locationInfoProvider.getLatitude(), locationInfoProvider.getLongitude()));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setShowCalloutBalloonOnTouch(true);
        mMapView.addPOIItem(marker);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onPOIItemSelected");
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onCalloutBalloonOfPOIItemTouched");
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.d(TAG, "onCalloutBalloonOfPOIItemTouched_more_parameters");
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.d(TAG, "onDraggablePOIItemMoved");
    }
}
