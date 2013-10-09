package com.mobile.promo.plugin.alerts;

import com.mobile.promo.plugin.utils.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class LocationSyncAlert extends Activity implements Constants{

	final Context context = this;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(ALERT_TITLE_LOCATION_SYNC);
		alertDialogBuilder.setMessage(ALERT_MSG_LOCATION_SYNC);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
			Intent callGPSSettingIntent = new Intent(
			android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(callGPSSettingIntent);
			}
			});
		alertDialogBuilder.setNegativeButton("No Thanks",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				LocationSyncAlert.this.finish();
				Intent homeIntent= new Intent(Intent.ACTION_MAIN);
				homeIntent.addCategory(Intent.CATEGORY_HOME);
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(homeIntent);
			}
		});	
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
}
