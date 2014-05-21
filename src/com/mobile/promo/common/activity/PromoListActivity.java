package com.mobile.promo.common.activity;

import static com.mobile.promo.plugin.manager.WidgetManager.SERVICE_STATUS_PARAM;
import static com.mobile.promo.plugin.manager.WidgetManager.USER_LOCATION_PARAM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mobile.promo.model.ExpandListChild;
import com.mobile.promo.model.ExpandListGroup;
import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.adapter.ExpandListAdapter;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.PhoneUtils;

public class PromoListActivity extends Activity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			String serviceStatus = getIntent().getStringExtra(SERVICE_STATUS_PARAM);
			if(serviceStatus.equalsIgnoreCase("Y")){
				double[] userLocation = getIntent().getDoubleArrayExtra(USER_LOCATION_PARAM);
				Toast.makeText(this, "Data is for your current location"+Arrays.toString(userLocation), Toast.LENGTH_LONG).show();
				JSONArray promosListAsJsonArray = DataStorage.getPromoListJSArrayForLocation(userLocation[0], userLocation[1], PhoneUtils.getUserTelephoneNumber(this));
				Log.w(LOG_TAG, "Selected Promo are "+promosListAsJsonArray.toString());
//				setContentView(R.layout.list_layout);
				setContentView(R.layout.expanding_list);
				ExpandableListView expandList = (ExpandableListView) findViewById(R.id.ExpList);
				ExpandListAdapter expAdapter = new ExpandListAdapter(PromoListActivity.this, getExpandListGroupList(promosListAsJsonArray));
				expandList.setAdapter(expAdapter);
//				setListAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1, movieNameList));
//				setListAdapter(new PromosListAdapter(this, getJsonObjectList(promosListAsJsonArray)));
//				setListAdapter(new ExpandListAdapter(this, getExpandListGroupList(promosListAsJsonArray)));
//				setListAdapter(new PromosListAdapter(this, promosListAsJsonArray, true));
				return;
			}
		}catch(JSONException e){
			
		}
		Toast.makeText(this, "Data is not for your current location ", Toast.LENGTH_LONG).show();
		
	}

	private ArrayList<ExpandListGroup> getExpandListGroupList(JSONArray promosListAsJsonArray) throws JSONException {
		Map<String, ExpandListGroup> expandListGroupByServiceType = new HashMap<String, ExpandListGroup>();
		for (int i = 0; i < promosListAsJsonArray.length(); i++) {
			JSONObject promo = promosListAsJsonArray.getJSONObject(i);
			String type = promo.getString(SERVICE_LIST_RESP_TYPE);
			String message = promo.getString(SERVICE_LIST_RESP_MESSAGE);
			String timing = promo.getString(SERVICE_LIST_RESP_TIME);
			ExpandListGroup serviceGroup;
			if(expandListGroupByServiceType.containsKey(type)){
				serviceGroup = expandListGroupByServiceType.get(type);
			}else{
				serviceGroup = new ExpandListGroup();
				serviceGroup.setName(type);
			}
			serviceGroup.addItem(new ExpandListChild(message, timing));
			expandListGroupByServiceType.put(type, serviceGroup);
		}
		return new ArrayList<ExpandListGroup>(expandListGroupByServiceType.values());
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
////       	DataStorage.setSelectedMovieIndex(position);
//        Intent movieInfoIntent = new Intent(PromoListActivity.this, PromoInfosActivity.class);
//        PromoListActivity.this.startActivity(movieInfoIntent);
//	}

}
