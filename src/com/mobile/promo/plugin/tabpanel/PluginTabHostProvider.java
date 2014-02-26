package com.mobile.promo.plugin.tabpanel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.activity.ApplicationIntro;
import com.mobile.promo.plugin.activity.DealOfTheDayActivity;
import com.mobile.promo.plugin.activity.InventorySearchSystem;
import com.mobile.promo.plugin.activity.RequestRegisterInterface;

public class PluginTabHostProvider extends TabHostProvider 
{
	private Tab dealOfTheDayTab;
	private Tab inventoryTab;
	private Tab registerRequestTab;
	private Tab appIntroTab;
	
	private TabView tabView;
	private GradientDrawable gradientDrawable, transGradientDrawable;

	public PluginTabHostProvider(Activity context) {
		super(context);
	}

	@Override
	public TabView getTabHost(String category) 
	{
		tabView = new TabView(context);
		tabView.setOrientation(TabView.Orientation.BOTTOM);
		tabView.setBackgroundID(R.drawable.tab_background_gradient);
		
		gradientDrawable = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xFFB2DA1D, 0xFF85A315});
	    gradientDrawable.setCornerRadius(0f);
	    gradientDrawable.setDither(true);
	    
	    transGradientDrawable = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0x00000000, 0x00000000});
	    transGradientDrawable.setCornerRadius(0f);
	    transGradientDrawable.setDither(true);

		dealOfTheDayTab = new Tab(context, category);
		dealOfTheDayTab.setIcon(R.drawable.best_deal);
		dealOfTheDayTab.setIconSelected(R.drawable.best_deal);
		dealOfTheDayTab.setBtnText("Best Deal");
		dealOfTheDayTab.setBtnTextColor(Color.WHITE);
		dealOfTheDayTab.setSelectedBtnTextColor(Color.BLACK);
//		homeTab.setBtnColor(Color.parseColor("#00000000"));
//		homeTab.setSelectedBtnColor(Color.parseColor("#0000FF"));
		dealOfTheDayTab.setBtnGradient(transGradientDrawable);
		dealOfTheDayTab.setSelectedBtnGradient(gradientDrawable);
		dealOfTheDayTab.setIntent(new Intent(context, DealOfTheDayActivity.class));

		inventoryTab = new Tab(context, category);
		inventoryTab.setIcon(R.drawable.search);
		inventoryTab.setIconSelected(R.drawable.search);
		inventoryTab.setBtnText("Search");
		inventoryTab.setBtnTextColor(Color.WHITE);
		inventoryTab.setSelectedBtnTextColor(Color.BLACK);
//		contactTab.setBtnColor(Color.parseColor("#00000000"));
//		contactTab.setSelectedBtnColor(Color.parseColor("#0000FF"));
		inventoryTab.setBtnGradient(transGradientDrawable);
		inventoryTab.setSelectedBtnGradient(gradientDrawable);
		inventoryTab.setIntent(new Intent(context, InventorySearchSystem.class));

		registerRequestTab = new Tab(context, category);
		registerRequestTab.setIcon(R.drawable.place_request);
		registerRequestTab.setIconSelected(R.drawable.place_request);
		registerRequestTab.setBtnText("Request");
		registerRequestTab.setBtnTextColor(Color.WHITE);
		registerRequestTab.setSelectedBtnTextColor(Color.BLACK);
//		shareTab.setBtnColor(Color.parseColor("#00000000"));
//		shareTab.setSelectedBtnColor(Color.parseColor("#0000FF"));
		registerRequestTab.setBtnGradient(transGradientDrawable);
		registerRequestTab.setSelectedBtnGradient(gradientDrawable);
		registerRequestTab.setIntent(new Intent(context, RequestRegisterInterface.class));
		
		appIntroTab = new Tab(context, category);
		appIntroTab.setIcon(R.drawable.about);
		appIntroTab.setIconSelected(R.drawable.about);
		appIntroTab.setBtnText("About Us");
		appIntroTab.setBtnTextColor(Color.WHITE);
		appIntroTab.setSelectedBtnTextColor(Color.BLACK);
//		moreTab.setBtnColor(Color.parseColor("#00000000"));
//		moreTab.setSelectedBtnColor(Color.parseColor("#0000FF"));
		appIntroTab.setBtnGradient(transGradientDrawable);
		appIntroTab.setSelectedBtnGradient(gradientDrawable);
		appIntroTab.setIntent(new Intent(context, ApplicationIntro.class));

		tabView.addTab(dealOfTheDayTab);
		tabView.addTab(inventoryTab);
		tabView.addTab(registerRequestTab);
		tabView.addTab(appIntroTab);

		return tabView;
	}
}