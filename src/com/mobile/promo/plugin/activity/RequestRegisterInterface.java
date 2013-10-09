package com.mobile.promo.plugin.activity;

import java.util.ArrayList;
import java.util.List;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.alerts.InternetConnectionAlert;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.NetworkUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class RequestRegisterInterface extends Activity implements Constants{

	private Spinner reqCategoriesSpinner;
	private Spinner reqSubCategoriesSpinner;
	private Spinner reqTypeSpinner;
	private Spinner reqSubTypeSpinner;
	private EditText userInputText;
	private Button reqSubmitButton;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.requesting_interf);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "Checking internet connection....");
        int status = NetworkUtil.getConnectivityStatus(context);
        if(status!= NetworkUtil.TYPE_NOT_CONNECTED){
        	Log.d(LOG_TAG, "Internet connection is present....");
    		addItemsOnRequestCategories();
    		addItemsOnRequestType();
        }else{
        	Log.d(LOG_TAG, "Internet connection is not present..");
			Intent internetConnectionActivity = new Intent(context, InternetConnectionAlert.class);
			internetConnectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(internetConnectionActivity);
			this.finish();
        }
	};
	
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
		List<String> list = new ArrayList<String>();
		list.add("Appreal");
		list.add("Grossary");
		list.add("Other");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		reqCategoriesSpinner.setAdapter(dataAdapter);
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
				reqTypeSpinner.setEnabled(true);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

		});
	}
}
