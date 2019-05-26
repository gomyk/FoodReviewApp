package com.example.toyproject;

import android.util.Log;
import android.view.ViewGroup;

import com.example.toyproject.utils.CommonContextHolder;
import com.example.toyproject.utils.LocationInfoProvider;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapViewManager implements MapView.POIItemEventListener , MapView.MapViewEventListener {
    private static final String TAG = "MapViewManager";
    private MapView mMapView;
    final LocationInfoProvider locationInfoProvider;
    private MapViewCustomListener mListener;
    public MapViewManager(ViewGroup container) {
        mMapView = new MapView(CommonContextHolder.getContext());
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setCalloutBalloonAdapter(new CustomCallOutBalloonAdapter());
        container.addView(mMapView);
        locationInfoProvider = new LocationInfoProvider(CommonContextHolder.getContext());
    }
    public void setCustomMapViewListener(MapViewCustomListener listener){
        mListener = listener;
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
    public void  addLocationMarker(double lon, double lat) {
        mMapView.removeAllPOIItems();
        mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lon), 1, true);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setShowCalloutBalloonOnTouch(true);
        mMapView.addPOIItem(marker);
    }

    public void updateCurrentLocation() {
        locationInfoProvider.getLocation();
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onPOIItemSelected");
        mListener.onPoiTouched();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onCalloutBalloonOfPOIItemTouched");
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.d(TAG, "onCalloutBalloonOfPOIItemTouched_more_parameters");
        mListener.onCalloutBallounTouched(mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude, mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude);
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.d(TAG, "onDraggablePOIItemMoved");
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.d(TAG,"onMapViewInitialized");
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewCenterPointMoved");
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        Log.d(TAG,"onMapViewZoomLevelChanged");
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewSingleTapped");
        mListener.onFingerOnMap();
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewDoubleTapped");
        mListener.onFingerOnMap();
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewLongPressed");
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewDragStarted");
        mListener.onFingerOnMap();
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewDragEnded");
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Log.d(TAG,"onMapViewMoveFinished");
    }

    public interface MapViewCustomListener {
        public void onPoiTouched();
        public void onCalloutBallounTouched(double lon, double lat);
        public void onFingerOnMap();
    }
}
