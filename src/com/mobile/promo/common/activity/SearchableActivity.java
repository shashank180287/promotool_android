package com.mobile.promo.common.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.adapter.PromosListAdapter;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.utils.Constants;


public class SearchableActivity extends ListActivity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent intent = getIntent();
//	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//	    	String query = intent.getStringExtra(SearchManager.QUERY);
//	    	Log.d(LOG_TAG, "Query is: "+query);
//	    	JSONArray movieListAsJsonArray = DataStorage.getMovieListAsJsonArray();
//	    	List<JSONObject> movieNameList=new ArrayList<JSONObject>();
//	    	for (int i=0; i<movieListAsJsonArray.length();i++) {
//	    		try{
//	    			JSONObject movieObj = movieListAsJsonArray.getJSONObject(i);
//	    			if(movieObj!=null && movieObj.getString("name").toLowerCase().contains(query.toLowerCase()))
//	    				movieNameList.add(movieObj);
//				}catch (JSONException e) {
//				}
//	    	}
	    	setContentView(R.layout.list_layout);
//	    	setListAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1, movieNameList));
//	    	setListAdapter(new PromosListAdapter(this,movieNameList));
//	    	setListAdapter(new PromosListAdapter(this, movieListAsJsonArray, true));
//	    }
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
//      	DataStorage.setSelectedMovieIndex(position);
        Intent movieInfoIntent = new Intent(SearchableActivity.this, PromoInfosActivity.class);
        SearchableActivity.this.startActivity(movieInfoIntent);
	}
}
