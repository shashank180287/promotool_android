package com.mobile.promo.plugin.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mobile.promo.plugin.R;

public class PluginNotificationManager {

	private static int SIMPLE_NOTFICATION_ID;

	public static void createNotification(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE );
		final Notification notifyDetails = new Notification(R.drawable.icon,
				"Hurry!!!", System.currentTimeMillis());
		CharSequence contentTitle = "Notification Details...";
		CharSequence contentText = "Browse Android Official Site by clicking me";
		Intent notifyIntent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse("http://www.android.com"));
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notifyIntent, android.content.Intent.FLAG_ACTIVITY_NEW_TASK);

		notifyDetails.setLatestEventInfo(context, contentTitle, contentText,
				intent);
		notificationManager.notify(SIMPLE_NOTFICATION_ID, notifyDetails);
	}

	public static void closeNotification(NotificationManager notificationManager) {
		notificationManager.cancel(SIMPLE_NOTFICATION_ID);

	}
}