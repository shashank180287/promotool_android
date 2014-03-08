package com.mobile.promo.plugin.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
	
}
