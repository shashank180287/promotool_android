package com.mobile.promo.plugin.manager;

import static com.mobile.promo.plugin.utils.Constants.LOG_TAG;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.mobile.promo.common.activity.PromoListActivity;
import com.mobile.promo.plugin.R;

public class WidgetManager {

	public static final String SERVICE_STATUS_PARAM = "serviceStatus";	
	public static final String USER_LOCATION_PARAM = "userLocation";	
	
	public static void handleWidgetLayout(Context context, AppWidgetManager appWidgetManager, int[] allWidgetIds, String serviceStatus, int widgetId, double[] userLocation) {
		if ("Y".equalsIgnoreCase(serviceStatus)) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			remoteViews.setImageViewResource(R.id.backgroundImage,
					R.drawable.new_on);
			Intent activityIntent = new Intent(context, PromoListActivity.class);
			activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activityIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);
			activityIntent.putExtra(WidgetManager.SERVICE_STATUS_PARAM,
					serviceStatus);
			activityIntent.putExtra(WidgetManager.USER_LOCATION_PARAM,
					userLocation);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.backgroundImage,
					pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
			return;
		}
		Log.w(LOG_TAG, "Movie list is empty...");

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		remoteViews.setImageViewResource(R.id.backgroundImage,
				R.drawable.new_off);

		// Register an onClickListener
		Intent clickIntent = new Intent(context, PromoListActivity.class);
		clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		clickIntent
				.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
		clickIntent.putExtra(WidgetManager.SERVICE_STATUS_PARAM,
				serviceStatus);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews
				.setOnClickPendingIntent(R.id.backgroundImage, pendingIntent);
		appWidgetManager.updateAppWidget(widgetId, remoteViews);
	}
}
