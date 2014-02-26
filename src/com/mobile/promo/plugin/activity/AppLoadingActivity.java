package com.mobile.promo.plugin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mobile.promo.plugin.R;

public class AppLoadingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
       
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.app_loading);
        
       final Handler handler = new Handler();
       
       handler.postDelayed(new Runnable() {
           public void run() {
               Intent mInHome = new Intent(AppLoadingActivity.this, DealOfTheDayActivity.class);
               AppLoadingActivity.this.startActivity(mInHome);
               AppLoadingActivity.this.finish();
           }
       }, 5000);

    }
    
}
