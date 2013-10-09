package com.mobile.promo.plugin.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.mobile.promo.plugin.utils.Constants;

public class InternetConnectionAlert extends Activity implements Constants{

	final Context context = this;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(ALERT_TITLE_INTERNET_CONNECTION);
		alertDialogBuilder.setMessage(ALERT_MSG_INTERNET_CONNECTION);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				InternetConnectionAlert.this.finish();
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
