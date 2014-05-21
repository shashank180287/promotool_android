package com.mobile.promo.plugin.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.mobile.promo.common.activity.PromoCommonListActivity;
import com.mobile.promo.plugin.alerts.InternetConnectionAlert;
import com.mobile.promo.plugin.alerts.LocationSyncAlert;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.manager.PluginNotificationManager;
import com.mobile.promo.plugin.manager.WidgetManager;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.HttpUrlHitUtils;
import com.mobile.promo.plugin.utils.LocationSyncUtils;
import com.mobile.promo.plugin.utils.NetworkUtils;
import com.mobile.promo.plugin.utils.PhoneUtils;
import com.mobile.promo.plugin.utils.ToolWidgetUtils;

public class PromosSearchProvider extends AppWidgetProvider implements Constants{
	
	private static LocationSyncUtils locationSyncUtils;
	
	@Override
	public void onEnabled(Context context) {
    	super.onEnabled(context);
    	locationSyncUtils = LocationSyncUtils.getInstance(context);
		Log.d(LOG_TAG, "Checking internet connection....");
        int status = NetworkUtils.getConnectivityStatus(context);
//      Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        if(status!= NetworkUtils.TYPE_NOT_CONNECTED){
        	Log.d(LOG_TAG, "Internet connection is present now checking gps connection....");
        	int gpsStatus = LocationSyncUtils.getLocationSyncStatus(context);
        	if(gpsStatus!=LocationSyncUtils.TYPE_NOT_AVAILABLE){
    			Log.d(LOG_TAG,"Location manager is enabled...Starting timer to update widget");
    			ToolWidgetUtils.startWidgetUpdateAlarmManager(context);
        	}else{
            	Log.d(LOG_TAG, "no location provider present..");
    			Intent locationSyncActivity = new Intent(context, LocationSyncAlert.class);
    			locationSyncActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			context.startActivity(locationSyncActivity);
        	}
        }else{
        	Log.d(LOG_TAG, "Internet connection is not present..");
			Intent internetConnectionActivity = new Intent(context, InternetConnectionAlert.class);
			internetConnectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(internetConnectionActivity);
        }
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.d(LOG_TAG, "Widget Provider disabled. Turning off timer");
		ToolWidgetUtils.stopWidgetUpdateAlarmManager(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.d(LOG_TAG, "Received intent for update" + intent.getAction());
		if(intent.getAction().equalsIgnoreCase("com.mobile.promo.plugin.PROMO_SEARCH_WIDGET_UPDATE")){
			Log.d(LOG_TAG, "Check for business");
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
			for (int appWidgetID : ids) {
				updateApplicationWidget(context, appWidgetManager, appWidgetID);
			}
		}else if(intent.getAction().equalsIgnoreCase("android.net.wifi.WIFI_STATE_CHANGED") || intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")){
			//android.net.conn.CONNECTIVITY_CHANGE
            //android.net.wifi.WIFI_STATE_CHANGED
			Log.d(LOG_TAG, "Received alerts for connectivity change");
			int isLocationProviderPresent = LocationSyncUtils.getLocationSyncStatus(context);
			int isInternetConnectionPresent = NetworkUtils.getConnectivityStatus(context);
			if(isLocationProviderPresent!=LocationSyncUtils.TYPE_NOT_AVAILABLE && isInternetConnectionPresent!= NetworkUtils.TYPE_NOT_CONNECTED){
				if(!ToolWidgetUtils.isWidgetUpdateAlarmManagerStarted(context))
					ToolWidgetUtils.startWidgetUpdateAlarmManager(context);
			}else{
				if(ToolWidgetUtils.isWidgetUpdateAlarmManagerStarted(context))
					ToolWidgetUtils.stopWidgetUpdateAlarmManager(context);
			}
		}else{
			// for android.appwidget.action.APPWIDGET_UPDATE
			Log.d(LOG_TAG, "Received alerts for app widget update");
		}
	}
	
	private void updateApplicationWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetID) {
		Log.w(LOG_TAG, "updateApplicationWidget method called");
		ComponentName thisWidget = new ComponentName(context,PromosSearchProvider.class);

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		Location userLocation=locationSyncUtils!=null? locationSyncUtils.getUserCurrentLocation(context): LocationSyncUtils.getInstance(context).getUserCurrentLocation(context);
		if (userLocation != null && DataStorage.isUserLocationChanged(userLocation)) {
			Log.w(LOG_TAG, "User location is ["+userLocation.getLatitude()+","+userLocation.getLongitude()+"]");
			DataStorage.setUserCurrentLocation(userLocation);
			for (int widgetId : allWidgetIds) {
				String statusTrackerUrl = SERVER_BASE_URL+"/"+ STATUS_TRACKER_URL+"?"+STATUS_TRACKER_REQ_USER_ID+"="+PhoneUtils.getUserTelephoneNumber(context)+
						"&"+STATUS_TRACKER_REQ_LATITUDE+"="+userLocation.getLatitude()+"&"+STATUS_TRACKER_REQ_LONGITUDE+"="+userLocation.getLongitude();
				String response = HttpUrlHitUtils.getResponseByHittingUrl(statusTrackerUrl);
				//DataStorage.getServiceInfoByLatAndLong(userLocation.getLatitude(), userLocation.getLongitude());
				Log.d(LOG_TAG, "Location request response "+response);
				String status = "N";
				JSONArray approvedRequests =null;
				try{
					if (response!=null){
						JSONObject responseObj =  new JSONObject(response);
						status = responseObj.getString(STATUS_TRACKER_RES_ISSERVICEAVAIL);
						if(responseObj.has(STATUS_TRACKER_RES_INVSEARCHRESP)){
							approvedRequests = responseObj.getJSONObject(STATUS_TRACKER_RES_INVSEARCHRESP).getJSONArray(STATUS_TRACKER_RES_INVSEARCHITEMS);
						}
					}

					WidgetManager.handleWidgetLayout(context, appWidgetManager, allWidgetIds, status, widgetId, new double[]{ userLocation.getLatitude(), userLocation.getLongitude()});
					if(approvedRequests!=null){
						String contentTitle = approvedRequests.getJSONObject(0).getString(STATUS_TRACKER_RES_REQ_BRAND);
						String contentText = "Was Rs."+approvedRequests.getJSONObject(0).getString(STATUS_TRACKER_RES_REQ_PRICE)+" But Now @ Rs."+approvedRequests.getJSONObject(0).getString(STATUS_TRACKER_RES_REQ_EFFECTIVE_PRICE);
						DataStorage.setItemsList(approvedRequests);
						Intent itemListIntent = new Intent(context, PromoCommonListActivity.class);
						PluginNotificationManager.createNotification(context, contentTitle, contentText, itemListIntent);
					}
				}catch(JSONException e){
					Log.e(LOG_TAG, "Error while getting error "+e.getMessage());
				}
			}
		}

	}
}
