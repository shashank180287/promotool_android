package com.mobile.promo.plugin.utils;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.activity.ApplicationIntro;
import com.mobile.promo.plugin.activity.InventorySystem;
import com.mobile.promo.plugin.activity.RequestRegisterInterface;
import com.mobile.promo.plugin.activity.SettingsPage;

public enum HomePageOptions {

	INVENTORY(R.drawable.search, InventorySystem.class, "SEARCH"), REQUEST_INTERFACE(R.drawable.place_request, RequestRegisterInterface.class,"PLACE REQUEST"), 
	SETTINGS(R.drawable.settings, SettingsPage.class,"SETTINGS"), HELP(R.drawable.about, ApplicationIntro.class, "HELP");
	
	private int imageId;
	private String imageText;
	private Class<?> activityClass;
	
	private HomePageOptions(int imageId, Class<?> activityClass, String imageText) {
		this.imageId = imageId;
		this.imageText = imageText;
		this.activityClass = activityClass;
	}

	public int getImageId() {
		return imageId;
	}

	public CharSequence getImageText() {
		return imageText;
	}

	public Class<?> getActivityClass() {
		return activityClass;
	}
	
}
