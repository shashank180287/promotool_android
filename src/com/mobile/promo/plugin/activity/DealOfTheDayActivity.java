package com.mobile.promo.plugin.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;

public class DealOfTheDayActivity extends Activity {

	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(DealOfTheDayActivity.this);
		TabView tabView = tabProvider.getTabHost("Deal Of The Day");
		tabView.setCurrentView(R.layout.deal_of_day);
		setContentView(tabView.render(0));
		byte[] byteArray = DataStorage.getDealOfTheDayBytes();
		if(byteArray!=null){
			Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			image = (ImageView) findViewById(R.id.bestDeal);
			image.setImageBitmap(bmp);
		}
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
