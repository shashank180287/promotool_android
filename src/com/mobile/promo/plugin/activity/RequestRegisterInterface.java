package com.mobile.promo.plugin.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;
import com.mobile.promo.plugin.utils.Constants;

public class RequestRegisterInterface extends Activity implements Constants{

	private Spinner reqCategoriesSpinner;
	private Spinner reqSubCategoriesSpinner;
	private Spinner reqTypeSpinner;
	private Spinner reqSubTypeSpinner;
	private EditText userInputText;
	private Button reqSubmitButton;
	private Context context;
	
	private final String SERVICE_TYPES_RESP_KEY_NAME = "name";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(RequestRegisterInterface.this);
		TabView tabView = tabProvider.getTabHost("Request");
		tabView.setCurrentView(R.layout.requesting_interf);
		setContentView(tabView.render(2));
		context = this;
		addItemsOnRequestCategories();
	}
	
	private void addItemsOnRequestType() {
		List<String> list = new ArrayList<String>();
		list.add("Rate");
		list.add("Brand");
		list.add("Other");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		reqTypeSpinner.setAdapter(dataAdapter);
		addListenerOnRequestTypeSelection();
	}

	private void addListenerOnRequestTypeSelection() {
		reqTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedCategory =parent.getItemAtPosition(position).toString();
				Log.d(LOG_TAG, "Selected request type as :"+selectedCategory);
				List<String> list = new ArrayList<String>();
				if("Rate".equalsIgnoreCase(selectedCategory)){
					list.add("Less Than");
					list.add("More Than");
					list.add("Equal");
					reqSubTypeSpinner.setVisibility(View.VISIBLE);
					userInputText.setVisibility(View.VISIBLE);
				}else if("Brand".equalsIgnoreCase(selectedCategory)){
					list.add("XYZ");
					list.add("ABC");
					list.add("Other");
					reqSubTypeSpinner.setVisibility(View.VISIBLE);
					userInputText.setVisibility(View.GONE);
				}else{
					// don't do anything
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				reqSubTypeSpinner.setAdapter(dataAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
		addListenerOnSubRequestTypeSelection();
	}

	private void addListenerOnSubRequestTypeSelection() {
		reqSubTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedSubCategory =parent.getItemAtPosition(position).toString();
				Log.d(LOG_TAG, "Selected sub request type as :"+selectedSubCategory);
				reqSubmitButton.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}

	// add items into spinner dynamically
	public void addItemsOnRequestCategories() {
		reqCategoriesSpinner = (Spinner) findViewById(R.id.reqcategory);
		reqSubCategoriesSpinner = (Spinner) findViewById(R.id.reqsubcategory);
		reqTypeSpinner = (Spinner) findViewById(R.id.reqtype);
		reqSubTypeSpinner = (Spinner) findViewById(R.id.subreqtype);
		userInputText = (EditText) findViewById(R.id.userinput);
		reqSubmitButton = (Button) findViewById(R.id.btnReqSubmit);
		JSONArray serviceTypesArray = DataStorage.getServiceTypes();
		if(serviceTypesArray!=null){
			try{
				List<String> serviceTypeNameList = new ArrayList<String>();
				for (int i = 0; i < serviceTypesArray.length(); i++) {
					if(i==0)
						serviceTypeNameList.add("Select One...");
					serviceTypeNameList.add(serviceTypesArray.getJSONObject(i).getString(SERVICE_TYPES_RESP_KEY_NAME));
				}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceTypeNameList);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			reqCategoriesSpinner.setAdapter(dataAdapter);
			}catch (JSONException e) {
				Log.e(LOG_TAG, "exception occur while parsing object "+e.getMessage());
			}
		}
		addListenerOnSpinnerItemSelection();
		reqSubCategoriesSpinner.setEnabled(false);
		reqTypeSpinner.setEnabled(false);
		reqSubTypeSpinner.setVisibility(View.GONE);
		userInputText.setVisibility(View.GONE);
		reqSubmitButton.setEnabled(false);
	}
	  
	public void addListenerOnSpinnerItemSelection() {
		reqCategoriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedCategory =parent.getItemAtPosition(position).toString();
				Log.d(LOG_TAG, "Selected item as :"+selectedCategory);
				reqSubCategoriesSpinner.setEnabled(true);
				List<String> list = new ArrayList<String>();
				if("Appreal".equalsIgnoreCase(selectedCategory)){
					list.add("Tees");
					list.add("Shoes");
					list.add("Other");
				}else if("Grossary".equalsIgnoreCase(selectedCategory)){
					list.add("Rice");
					list.add("Wheat");
					list.add("Other");
				}else{
					list.add("Home decore");
					list.add("Electrical");
					list.add("Other");
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				reqSubCategoriesSpinner.setAdapter(dataAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
		addListenerOnSubCategSpinnerItemSelection();
	}
	
	public void addListenerOnSubCategSpinnerItemSelection() {
		reqSubCategoriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedSubCategory =parent.getItemAtPosition(position).toString();
				Log.d(LOG_TAG, "Selected sub category as :"+selectedSubCategory);
				addItemsOnRequestType() ;
				reqTypeSpinner.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}
}
