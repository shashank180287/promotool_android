package com.mobile.promo.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.list.PromosListAdapter;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.DataStorage;

public class PromoListActivity extends ListActivity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!DataStorage.isLiveData()){
		    AlertDialog.Builder alert = new AlertDialog.Builder(PromoListActivity.this);
	        alert.setMessage("This movie list is not live...");
	        final AlertDialog altDlg = alert.create();
	        
	        altDlg.show();
	        
	        new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					 altDlg.cancel();
				}
			}, 3000);
		}
		
		JSONArray promosListAsJsonArray = DataStorage.getPromoListJSArrayForCurrentLocation();
		setContentView(R.layout.list_layout);
//		setListAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1, movieNameList));
		setListAdapter(new PromosListAdapter(this, getJsonObjectList(promosListAsJsonArray)));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
       	DataStorage.setSelectedMovieIndex(position);
        Intent movieInfoIntent = new Intent(PromoListActivity.this, PromoInfosActivity.class);
        PromoListActivity.this.startActivity(movieInfoIntent);
	}
	
	
	private List<JSONObject> getJsonObjectList(JSONArray promosListAsJsonArray) {
		List<JSONObject> promosList = new ArrayList<JSONObject>();
		if(promosListAsJsonArray!=null){
			for (int i=0; i<promosListAsJsonArray.length();i++) {
			try{
				promosList.add(promosListAsJsonArray.getJSONObject(i));
			}catch (JSONException e) {
			}
		}
		}
		return promosList;
	}

}
