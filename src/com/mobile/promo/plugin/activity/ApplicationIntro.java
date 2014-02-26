package com.mobile.promo.plugin.activity;


import android.app.Activity;
import android.os.Bundle;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;

public class ApplicationIntro extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(ApplicationIntro.this);
		TabView tabView = tabProvider.getTabHost("About Us");
		tabView.setCurrentView(R.layout.main);
		setContentView(tabView.render(3));
        //lat: 12.916648  //long: 77.599976
    }
}