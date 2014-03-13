package com.mobile.promo.plugin.activity;

import static com.mobile.promo.plugin.utils.WordUtils.capatalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.tabpanel.PluginTabHostProvider;
import com.mobile.promo.plugin.tabpanel.TabView;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.HttpUrlHitUtils;
import com.mobile.promo.plugin.utils.PhoneUtils;
import com.mobile.promo.plugin.view.resolver.ViewResolver;
import com.mobile.promo.plugin.view.resolver.ViewResolverFactory;

public class RequestRegisterInterface extends Activity implements Constants {

	private Spinner reqCategoriesSpinner;
	private Spinner reqSubCategoriesSpinner;
	private Spinner reqTypeSpinner;
	private Context context;
	private List<View> requestTypeBasedViews = new ArrayList<View>();
	private List<ViewResolver> requestTypeBasedViewResolvers = new ArrayList<ViewResolver>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PluginTabHostProvider tabProvider = new PluginTabHostProvider(
				RequestRegisterInterface.this);
		TabView tabView = tabProvider.getTabHost("Request");
		tabView.setCurrentView(R.layout.requesting_interf);
		setContentView(tabView.render(2));
		context = this;
		addItemsOnRequestCategories();
	}

	// add items into spinner dynamically
	public void addItemsOnRequestCategories() {
		reqCategoriesSpinner = (Spinner) findViewById(R.id.reqcategory);
		reqSubCategoriesSpinner = (Spinner) findViewById(R.id.reqsubcategory);
		reqTypeSpinner = (Spinner) findViewById(R.id.reqtype);

		JSONArray serviceTypesArray = DataStorage.getServiceTypes();
		if (serviceTypesArray != null) {
			try {
				List<String> serviceTypeNameList = new ArrayList<String>();
				for (int i = 0; i < serviceTypesArray.length(); i++) {
					if (i == 0)
						serviceTypeNameList.add("Select One...");
					serviceTypeNameList.add(capatalize(serviceTypesArray
							.getJSONObject(i).getString(
									SERVICE_TYPES_RESP_KEY_NAME)));
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						this, android.R.layout.simple_spinner_item,
						serviceTypeNameList);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				reqCategoriesSpinner.setAdapter(dataAdapter);
				addListenerOnSpinnerItemSelection(serviceTypesArray);
			} catch (JSONException e) {
				Log.e(LOG_TAG,
						"exception occur while parsing object "
								+ e.getMessage());
			}
		}
		reqSubCategoriesSpinner.setEnabled(false);
		reqTypeSpinner.setEnabled(false);
	}

	public void addListenerOnSpinnerItemSelection(
			final JSONArray serviceTypeArray) {
		reqCategoriesSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(LOG_TAG, "Selected item at index :" + position);
						JSONObject selectedServiceType;
						try {
							if (position == 0) {
								reqSubCategoriesSpinner.setEnabled(false);
								return;
							}
							selectedServiceType = serviceTypeArray
									.getJSONObject(position - 1);
							Log.d(LOG_TAG, "Selected item is:"
									+ selectedServiceType);
							String subCategoryFetchUrl = SERVER_BASE_URL
									+ "/"
									+ SUB_CATEGORY_URL_EXT
									+ "/"
									+ selectedServiceType
											.getString(SERVICE_TYPES_RESP_KEY_CODE)
									+ "/";
							String response = HttpUrlHitUtils
									.getResponseByHittingUrl(subCategoryFetchUrl);
							if (response != null) {
								JSONObject subCategoryResObject = new JSONObject(
										response);
								if (subCategoryResObject
										.has(SUB_CATEGORY_RESP_KEY_SUBCAT)) {
									final JSONArray subCategoryArray = subCategoryResObject
											.getJSONArray(SUB_CATEGORY_RESP_KEY_SUBCAT);
									List<String> subCategoryNameList = new ArrayList<String>();
									for (int i = 0; i < subCategoryArray
											.length(); i++) {
										if (i == 0)
											subCategoryNameList
													.add("Select One...");
										subCategoryNameList
												.add(capatalize(subCategoryArray
														.getJSONObject(i)
														.getString(
																SUB_CATEGORY_RESP_KEY_SUBCAT_NAME)));
									}
									ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
											context,
											android.R.layout.simple_spinner_item,
											subCategoryNameList);
									dataAdapter
											.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									reqSubCategoriesSpinner
											.setAdapter(dataAdapter);
									reqSubCategoriesSpinner.setEnabled(true);
									addListenerOnSubCategSpinnerItemSelection(
											selectedServiceType,
											subCategoryArray);
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

	public void addListenerOnSubCategSpinnerItemSelection(
			final JSONObject selectedServiceType,
			final JSONArray subCategoryArray) {
		reqSubCategoriesSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							if (position == 0) {
								reqTypeSpinner.setEnabled(false);
								return;
							}
							JSONObject selectedSubCate = subCategoryArray
									.getJSONObject(position);
							String selectedSubCategory = parent
									.getItemAtPosition(position).toString();
							Log.d(LOG_TAG, "Selected sub category as :"
									+ selectedSubCategory);
							String requestTypeUrl = SERVER_BASE_URL + "/"
									+ REQUEST_TYPES_URL_EXT;
							String response = HttpUrlHitUtils
									.getResponseByHittingUrl(requestTypeUrl);
							if (response != null && response.contains("[")
									&& response.contains("]")) {
								String requestTypeWithComma = response
										.substring(1, response.length() - 1);
								List<String> list = new ArrayList<String>();
								List<String> requestTypeList = Arrays
										.asList(requestTypeWithComma.split(","));
								for (int i = 0; i < requestTypeList.size(); i++) {
									if (i == 0)
										list.add("Select One...");
									list.add(capatalize(requestTypeList.get(i).trim()));
								}
								ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
										context,
										android.R.layout.simple_spinner_item,
										list);
								dataAdapter
										.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								reqTypeSpinner.setAdapter(dataAdapter);
								reqTypeSpinner.setEnabled(true);
								addListenerOnRequestTypeSelection(selectedServiceType, selectedSubCate,
										requestTypeList);
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

	private void addListenerOnRequestTypeSelection(
			final JSONObject selectedServiceType,
			final JSONObject selectedSubCategory,
			final List<String> requestTypeList) {
		reqTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					LinearLayout layout = (LinearLayout) findViewById(R.id.requestInrt);
					if(requestTypeBasedViews.size()>0){
						for (View reqView : requestTypeBasedViews) {
							layout.removeView(reqView);
						}
					}
					if (position == 0) {
						return;
					}
					final String selectedRequestType = parent.getItemAtPosition(
							position).toString();
					Log.d(LOG_TAG, "Selected request type as :"
							+ selectedRequestType);
					String requestTypeSelectionUrl = SERVER_BASE_URL + "/"
							+ REQUEST_TYPES_DETAILS_URL_EXT + "/"
							+ selectedRequestType;
					String response = HttpUrlHitUtils
							.getResponseByHittingUrl(requestTypeSelectionUrl);
					if (response != null) {
						JSONObject selectionObjects = new JSONObject( response.substring(response.indexOf(REQUEST_DETAIL_RESP_KEY_SELECTION)+14, response.lastIndexOf("'")));
						int i=1;
						while(selectionObjects.has("selection"+i)) {
							JSONObject selectionObject = selectionObjects.getJSONObject("selection"+i);
							ViewResolver viewResolver = ViewResolverFactory.getViewResolver(selectionObject.getString(REQUEST_TYPE_DETAIL_RESP_KEY_TYPE), context);
							if(viewResolver!=null){
								requestTypeBasedViewResolvers.add(viewResolver);
								View viewObject;
								if(selectionObject.has(REQUEST_TYPE_DETAIL_RESP_KEY_VALUE))
									viewObject = viewResolver.resolveView(selectionObject.getString(REQUEST_TYPE_DETAIL_RESP_KEY_VALUE));
								else
									viewObject = viewResolver.resolveView();
								layout.addView(viewObject);
								requestTypeBasedViews.add(viewObject);
							}
							i++;
						}
						Button submitButton = new Button(context);
						submitButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						submitButton.setText("Submit Request");
						submitButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								int i = 0;
								String ctx = null;
								boolean isCtxSelected=false;
								String subCtx = null;
								boolean isSubCtxSelected=false;
								for (View requestView : requestTypeBasedViews) {
									i++;
									if(requestView instanceof Button)
										continue;
									ViewResolver viewResolver = requestTypeBasedViewResolvers.get(i-1);
									try{
										String selectedData = viewResolver.showSelectedData();
										if(!isCtxSelected){
											ctx = selectedData;
											isCtxSelected = true;
										}else if(!isSubCtxSelected){
											subCtx = selectedData;
											isSubCtxSelected = true;
										}
									}catch(IllegalArgumentException e){
										Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
										return;
									}
								}
								JSONObject requestSubmitObject = new JSONObject();
								try {
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_ID, PhoneUtils.getUserTelephoneNumber(context));
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_CODE, selectedServiceType.getString(SERVICE_TYPES_RESP_KEY_CODE));
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_SUBCAT, selectedSubCategory.getString(SUB_CATEGORY_RESP_KEY_SUBCAT_NAME));
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_REQTYPE, selectedRequestType);
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_CTX, ctx);
									requestSubmitObject.put(REQUEST_TYPE_SUBMIT_REQ_KEY_SUBCTX, subCtx);

									String response = HttpUrlHitUtils.postResponseByHittingUrlWithRequestBody(SERVER_BASE_URL+"/" + REQUEST_SUBMIT_URL_EXT + "/", requestSubmitObject.toString());
									if(response!=null){
										JSONObject resObj = new JSONObject(response);
										if(resObj.has("status") && resObj.getString("status").equalsIgnoreCase("TRUE")){
											Toast.makeText(context,"Request Submitted Successfully.", Toast.LENGTH_LONG).show();
											finish();
											startActivity(getIntent());
											return;
										}
									}
									
									Toast.makeText(context,"Request Submittion failed.Try after some time.", Toast.LENGTH_LONG).show();
									finish();
									startActivity(getIntent());
									return;
								} catch (JSONException e) {
									Log.e(LOG_TAG, e.getMessage());
								}
							}
							
						});
						layout.addView(submitButton);
						requestTypeBasedViews.add(submitButton);
					}
				} catch (Exception e) {
					Log.e(LOG_TAG, e.getMessage());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
	}

}
