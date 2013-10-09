package com.mobile.promo.plugin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.adapter.HomePageGridButtonAdapter;

public class PromoToolHome extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.shophere_home);
	
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new HomePageGridButtonAdapter(this));
	
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(PromoToolHome.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.promotool_home);
//		
//		Button appInfoButton = (Button) findViewById(R.id.info_button);
//		appInfoButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent appInfo = new Intent(PromoToolHome.this, ApplicationIntro.class);
//	            PromoToolHome.this.startActivity(appInfo);
//			}
//		});
//		
//		Button settingButton = (Button) findViewById(R.id.settings_button);
//		settingButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent settings = new Intent(PromoToolHome.this, SettingsPage.class);
//	            PromoToolHome.this.startActivity(settings);
//			}
//		});
//		
//		Button requestPlcaingButton = (Button) findViewById(R.id.requestplacing_button);
//		requestPlcaingButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent settings = new Intent(PromoToolHome.this, RequestRegisterInterface.class);
//	            PromoToolHome.this.startActivity(settings);
//			}
//		});
//		
//		Button inventoryButton = (Button) findViewById(R.id.inventory_button);
//		inventoryButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent settings = new Intent(PromoToolHome.this, InventorySystem.class);
//	            PromoToolHome.this.startActivity(settings);
//			}
//		});
//	}
}
