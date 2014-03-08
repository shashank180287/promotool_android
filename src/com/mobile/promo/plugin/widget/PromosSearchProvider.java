package com.mobile.promo.plugin.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.RemoteViews;

import com.mobile.promo.common.activity.PromoListActivity;
import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.alerts.InternetConnectionAlert;
import com.mobile.promo.plugin.alerts.LocationSyncAlert;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.LocationSyncUtils;
import com.mobile.promo.plugin.utils.NetworkUtils;
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
		Location userLocation= locationSyncUtils.getUserCurrentLocation(context);

		if (userLocation != null && DataStorage.isUserLocationChanged(userLocation)) {
			DataStorage.setUserCurrentLocation(userLocation);
			for (int widgetId : allWidgetIds) {
				DataStorage.getServiceInfoByLatAndLong(userLocation.getLatitude(), userLocation.getLongitude());
				if (DataStorage.isLiveData()) {
					RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
					remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.old_on);
					Intent activityIntent = new Intent(context, PromoListActivity.class);
					activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activityIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
					
					PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					remoteViews.setOnClickPendingIntent(R.id.backgroundImage, pendingIntent);
					appWidgetManager.updateAppWidget(widgetId, remoteViews);

				} else {
					 Log.w(LOG_TAG, "Movie list is empty...");
					 
					RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
					remoteViews.setImageViewResource(R.id.backgroundImage, R.drawable.old_off);

					// Register an onClickListener
					Intent clickIntent = new Intent(context, PromoListActivity.class);
					clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
					clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
					PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					remoteViews.setOnClickPendingIntent(R.id.backgroundImage,pendingIntent);
					appWidgetManager.updateAppWidget(widgetId, remoteViews);
				}
			}
		}

	}
}
