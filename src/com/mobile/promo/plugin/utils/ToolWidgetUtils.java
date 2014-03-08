package com.mobile.promo.plugin.utils;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ToolWidgetUtils implements Constants{

	public static void startWidgetUpdateAlarmManager(Context context) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 1);
		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), WIDGET_REFRESH_TIME_MILISEC, provideWidgetSearchIntent(context));
	}
	
	public static void stopWidgetUpdateAlarmManager(Context context) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(provideWidgetSearchIntent(context));
	}
	
	public static boolean isWidgetUpdateAlarmManagerStarted(Context context) {
		return (PendingIntent.getBroadcast(context, 0, new Intent(PROMO_TOOL_WIDGET_RECEIVER), PendingIntent.FLAG_NO_CREATE) !=null);
	}

	private static PendingIntent provideWidgetSearchIntent(Context context) {
		Intent intent = new Intent(PROMO_TOOL_WIDGET_RECEIVER);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}
}
