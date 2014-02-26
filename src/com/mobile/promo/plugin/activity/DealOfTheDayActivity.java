package com.mobile.promo.plugin.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;

public class DealOfTheDayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(DealOfTheDayActivity.this);
		TabView tabView = tabProvider.getTabHost("Deal Of The Day");
		tabView.setCurrentView(R.layout.deal_of_day);
		setContentView(tabView.render(0));
	}
	
}
