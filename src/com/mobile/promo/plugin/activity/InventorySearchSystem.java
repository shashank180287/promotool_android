package com.mobile.promo.plugin.activity;

import static com.mobile.promo.plugin.utils.Constants.INVENTORY_SEARCH_REQ_PARAM_CAT;
import static com.mobile.promo.plugin.utils.Constants.INVENTORY_SEARCH_REQ_PARAM_SUBCAT;
import static com.mobile.promo.plugin.utils.Constants.SUB_CATEGORY_RESP_KEY_SUBCAT;
import static com.mobile.promo.plugin.utils.Constants.SUB_CATEGORY_RESP_KEY_SUBCAT_NAME;
import static com.mobile.promo.plugin.utils.Constants.INVENTORY_URL_EXT;
import static com.mobile.promo.plugin.utils.Constants.LOG_TAG;
import static com.mobile.promo.plugin.utils.Constants.SEARCH_ITEM_CONTEXT;
import static com.mobile.promo.plugin.utils.Constants.SERVER_BASE_URL;
import static com.mobile.promo.plugin.utils.Constants.SUB_CATEGORY_URL_EXT;
import static com.mobile.promo.plugin.utils.Constants.SERVICE_TYPES_RESP_KEY_NAME;
import static com.mobile.promo.plugin.utils.Constants.SERVICE_TYPES_RESP_KEY_CODE;
import static com.mobile.promo.plugin.utils.WordUtils.capatalize;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mobile.promo.common.activity.PromoCommonListActivity;
import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;
import com.mobile.promo.plugin.utils.HttpUrlHitUtils;

public class InventorySearchSystem extends Activity {

	private Spinner categoriesSpinner;
	private Spinner subCategoriesSpinner;
	private Button searchButton;
	private Context context;
	
	private final String ITEM_SEARCH_RESP_KEY_ITEMS = "inventorySearchItems";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(InventorySearchSystem.this);
		TabView tabView = tabProvider.getTabHost("Search");
		tabView.setCurrentView(R.layout.inventory_system);
		setContentView(tabView.render(1));
		
		context = this;
		addItemsOnCategories();
	}
	
	// add items into spinner dynamically
	public void addItemsOnCategories() {
		categoriesSpinner = (Spinner) findViewById(R.id.category);
		subCategoriesSpinner = (Spinner) findViewById(R.id.sub_category);
		searchButton = (Button) findViewById(R.id.btnSubmit);
		
		JSONArray serviceTypesArray = DataStorage.getServiceTypes();
		if(serviceTypesArray!=null){
			try{
				List<String> serviceTypeNameList = new ArrayList<String>();
				for (int i = 0; i < serviceTypesArray.length(); i++) {
					if(i==0)
						serviceTypeNameList.add("Select One...");
					serviceTypeNameList.add(capatalize(serviceTypesArray.getJSONObject(i).getString(SERVICE_TYPES_RESP_KEY_NAME)));
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceTypeNameList);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categoriesSpinner.setAdapter(dataAdapter);
				addListenerOnSpinnerItemSelection(serviceTypesArray);
			}catch (JSONException e) {
				Log.e(LOG_TAG, "exception occur while parsing object "+e.getMessage());
			}
		}
		subCategoriesSpinner.setEnabled(false);
		searchButton.setEnabled(false);
	}
	  
	public void addListenerOnSpinnerItemSelection(final JSONArray serviceTypeArray) {
		categoriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(LOG_TAG, "Selected item at index :"+position);
				JSONObject selectedServiceType;
				try {
					if(position==0){
						subCategoriesSpinner.setEnabled(false);
						return;
					}
					selectedServiceType = serviceTypeArray.getJSONObject(position-1);
					Log.d(LOG_TAG, "Selected item is:"+selectedServiceType);
					String subCategoryFetchUrl = SERVER_BASE_URL+"/"+SUB_CATEGORY_URL_EXT+"/"+selectedServiceType.getString(SERVICE_TYPES_RESP_KEY_CODE)+"/";
					String response = HttpUrlHitUtils.getResponseByHittingUrl(subCategoryFetchUrl);
					if(response!=null){
						JSONObject subCategoryResObject = new JSONObject(response);
						if(subCategoryResObject.has(SUB_CATEGORY_RESP_KEY_SUBCAT)){
							final JSONArray subCategoryArray = subCategoryResObject.getJSONArray(SUB_CATEGORY_RESP_KEY_SUBCAT);
							List<String> subCategoryNameList = new ArrayList<String>();
							for (int i = 0; i < subCategoryArray.length(); i++) {
								if(i==0)
									subCategoryNameList.add("Select One...");
								subCategoryNameList.add(capatalize(subCategoryArray.getJSONObject(i).getString(SUB_CATEGORY_RESP_KEY_SUBCAT_NAME)));
							}
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, subCategoryNameList);
							dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							subCategoriesSpinner.setAdapter(dataAdapter);
							subCategoriesSpinner.setEnabled(true);
							addListenerOnSubCategSpinnerItemSelection(selectedServiceType, subCategoryArray);
						}
					}
				} catch (JSONException e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}
	
	public void addListenerOnSubCategSpinnerItemSelection(final JSONObject selectedServiceType, final JSONArray subCategoryArray) {
		subCategoriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					searchButton.setEnabled(false);
					return;
				}
				try{
					JSONObject selectedSubCategory =subCategoryArray.getJSONObject(position-1);
					Log.d(LOG_TAG, "Selected sub category as :"+selectedSubCategory);
					searchButton.setEnabled(true);
					addListenerOnButtonSelection(selectedServiceType.getString(SERVICE_TYPES_RESP_KEY_NAME), selectedSubCategory.getString(SUB_CATEGORY_RESP_KEY_SUBCAT_NAME));
				}catch (JSONException e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}
	
	public void addListenerOnButtonSelection(final String serviceTypeName, final String subCategoryName) {
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					String subCategoryFetchUrl = SERVER_BASE_URL+"/"+INVENTORY_URL_EXT+"/"+SEARCH_ITEM_CONTEXT+"?"+INVENTORY_SEARCH_REQ_PARAM_CAT+"="+serviceTypeName+"&"+INVENTORY_SEARCH_REQ_PARAM_SUBCAT+"="+subCategoryName;
					String response = HttpUrlHitUtils.getResponseByHittingUrl(subCategoryFetchUrl);
					if(response!=null){
						JSONObject itemsObject = new JSONObject(response);
						Log.d(LOG_TAG, itemsObject.toString());
						JSONArray items = itemsObject.getJSONArray(ITEM_SEARCH_RESP_KEY_ITEMS);
						DataStorage.setItemsList(items);
						Intent appInfo = new Intent(InventorySearchSystem.this, PromoCommonListActivity.class);
			            InventorySearchSystem.this.startActivity(appInfo);
					}
				}catch (Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}
		}); 
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
