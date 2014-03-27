package com.mobile.promo.plugin.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.common_menu , menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.menu_settings:{
	        	Intent intent = new Intent(getApplicationContext(), SettingsPage.class);
	        	startActivity(intent);
	            return true;
	        }
        }
        return super.onOptionsItemSelected(item);
	}
}