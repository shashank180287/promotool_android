package com.mobile.promo.plugin.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationSyncUtils implements Constants, LocationListener {

    public static int TYPE_GPS = 1;
    public static int TYPE_WIFI_NETWORK = 2;
    public static int TYPE_NOT_AVAILABLE = 0;
     
	private static Location userLocation;
	private Context context;
	private static LocationManager locManager;
	
	private LocationSyncUtils(){
		
	}
	
	public static LocationSyncUtils getInstance(Context context){
		locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		LocationSyncUtils locationSyncUtils =new LocationSyncUtils();
		locationSyncUtils.setContext(context);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationSyncUtils);
		return locationSyncUtils;
	}
 	/** Gets the current location and update the mobileLocation variable */
	public Location getUserCurrentLocation(Context context) {
		userLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return userLocation;
	}
	
    public static int getLocationSyncStatus(Context context) {
    	LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager!=null){
            if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) 
                return TYPE_GPS;
            if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                return TYPE_WIFI_NETWORK;
        } 
        return TYPE_NOT_AVAILABLE;
    }
     
    public static String getLocationSyncStatusString(Context context) {
        int conn = LocationSyncUtils.getLocationSyncStatus(context);
        String status = null;
        if (conn == LocationSyncUtils.TYPE_GPS) {
            status = "GPS enabled";
        } else if (conn == LocationSyncUtils.TYPE_WIFI_NETWORK) {
            status = "Mobile Network enabled";
        } else if (conn == LocationSyncUtils.TYPE_NOT_AVAILABLE) {
            status = "Location Sync not available";
        }
        return status;
    }
    
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(Constants.LOG_TAG, "onStatusChanged");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(Constants.LOG_TAG, "onProviderEnabled");
		Log.d(LOG_TAG, "Received alerts for location provider enabled");
		int isLocationProviderPresent = LocationSyncUtils.getLocationSyncStatus(context);
		int isInternetConnectionPresent = NetworkUtils.getConnectivityStatus(context);
		if(isLocationProviderPresent!=LocationSyncUtils.TYPE_NOT_AVAILABLE && isInternetConnectionPresent!= NetworkUtils.TYPE_NOT_CONNECTED){
			if(!ToolWidgetUtils.isWidgetUpdateAlarmManagerStarted(context))
				ToolWidgetUtils.startWidgetUpdateAlarmManager(context);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d(Constants.LOG_TAG, "onProviderDisabled");
		Log.d(LOG_TAG, "Received alerts for location provider disabled");
		if(ToolWidgetUtils.isWidgetUpdateAlarmManagerStarted(context))
			ToolWidgetUtils.stopWidgetUpdateAlarmManager(context);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(Constants.LOG_TAG, "onLocationChanged");
		userLocation = location;
	}
	
	private void setContext(Context context){
		this.context = context;
	}
}
