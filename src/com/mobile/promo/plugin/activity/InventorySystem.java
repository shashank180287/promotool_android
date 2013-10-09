package com.mobile.promo.plugin.activity;

import static com.mobile.promo.plugin.utils.Constants.*;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mobile.promo.plugin.PromoExpandableListActivity;
import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.alerts.InternetConnectionAlert;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.utils.HttpUrlHitter;
import com.mobile.promo.plugin.utils.NetworkUtil;

public class InventorySystem extends Activity {

	private Spinner categoriesSpinner;
	private Spinner subCategoriesSpinner;
	private Button searchButton;
	private Context context;
	
	private final String SERVICE_TYPES_RESP_KEY_SERV_TYPE = "serviceTypes";
	private final String SERVICE_TYPES_RESP_KEY_NAME = "name";
	private final String SERVICE_TYPES_RESP_KEY_CODE = "code";
	private final String SUB_CATEGORY_RESP_KEY_SUBCAT ="subCategories";
	private final String SUB_CATEGORY_RESP_KEY_SUBCAT_NAME = "name";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.inventory_system);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "Checking internet connection....");
        int status = NetworkUtil.getConnectivityStatus(context);
        if(status!= NetworkUtil.TYPE_NOT_CONNECTED){
        	Log.d(LOG_TAG, "Internet connection is present....");
    		addItemsOnCategories();
        }else{
        	Log.d(LOG_TAG, "Internet connection is not present..");
			Intent internetConnectionActivity = new Intent(context, InternetConnectionAlert.class);
			internetConnectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(internetConnectionActivity);
			this.finish();
        }
	};
	
	// add items into spinner dynamically
	public void addItemsOnCategories() {
		categoriesSpinner = (Spinner) findViewById(R.id.category);
		subCategoriesSpinner = (Spinner) findViewById(R.id.sub_category);
		searchButton = (Button) findViewById(R.id.btnSubmit);
		
		String serviceTypesFetchUrl = SERVER_BASE_URL+"/"+SERVICE_TYPE_URL_EXT+"/";
		String response = HttpUrlHitter.getResponseByHittingUrl(serviceTypesFetchUrl);
		if(response!=null){
			try{
				JSONObject serviceTypeResObject = new JSONObject(response);
				if(serviceTypeResObject.has(SERVICE_TYPES_RESP_KEY_SERV_TYPE)){
					final JSONArray serviceTypesArray = serviceTypeResObject.getJSONArray(SERVICE_TYPES_RESP_KEY_SERV_TYPE);
					List<String> serviceTypeNameList = new ArrayList<String>();
					for (int i = 0; i < serviceTypesArray.length(); i++) {
						if(i==0)
							serviceTypeNameList.add("Select One...");
						serviceTypeNameList.add(serviceTypesArray.getJSONObject(i).getString(SERVICE_TYPES_RESP_KEY_NAME));
					}
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceTypeNameList);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					categoriesSpinner.setAdapter(dataAdapter);
					addListenerOnSpinnerItemSelection(serviceTypesArray);
				}
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
						ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new ArrayList<String>());
						subCategoriesSpinner.setAdapter(dataAdapter);
						subCategoriesSpinner.setEnabled(false);
						return;
					}
					selectedServiceType = serviceTypeArray.getJSONObject(position-1);
					Log.d(LOG_TAG, "Selected item is:"+selectedServiceType);
					String subCategoryFetchUrl = SERVER_BASE_URL+"/"+SUB_CATEGORY_URL_EXT+"/"+selectedServiceType.getString(SERVICE_TYPES_RESP_KEY_CODE)+"/";
					String response = HttpUrlHitter.getResponseByHittingUrl(subCategoryFetchUrl);
					if(response!=null){
						JSONObject subCategoryResObject = new JSONObject(response);
						if(subCategoryResObject.has(SUB_CATEGORY_RESP_KEY_SUBCAT)){
							final JSONArray subCategoryArray = subCategoryResObject.getJSONArray(SUB_CATEGORY_RESP_KEY_SUBCAT);
							List<String> subCategoryNameList = new ArrayList<String>();
							for (int i = 0; i < subCategoryArray.length(); i++) {
								if(i==0)
									subCategoryNameList.add("Select One...");
								subCategoryNameList.add(subCategoryArray.getJSONObject(i).getString(SUB_CATEGORY_RESP_KEY_SUBCAT_NAME));
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
					String response = HttpUrlHitter.getResponseByHittingUrl(subCategoryFetchUrl);
					if(response!=null){
						JSONObject itemsObject = new JSONObject(response);
						Log.d(LOG_TAG, itemsObject.toString());
						Intent appInfo = new Intent(InventorySystem.this, PromoExpandableListActivity.class);
			            InventorySystem.this.startActivity(appInfo);
					}
				}catch (Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}
		}); 
	}
}
