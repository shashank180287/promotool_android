package com.mobile.promo.plugin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;

public class PromosListAdapter extends BaseAdapter {

	private JSONArray data;
	private static LayoutInflater inflater = null;
	private boolean listenItemSelect;

	public PromosListAdapter(Activity activity, JSONArray data,
			boolean listenItemSelect) {
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.length();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) { 
	        View vi=convertView; 
	        if(convertView==null) 
	            vi = inflater.inflate(R.layout.list_row, null); 
	  
	        TextView mainHeading = (TextView)vi.findViewById(R.id.main_head);
	        TextView subHeading = (TextView)vi.findViewById(R.id.sub_head); 
	        TextView otherInfo = (TextView)vi.findViewById(R.id.other_info);
	        try {
	        	JSONObject promo = data.getJSONObject(position); 
				mainHeading.setText(promo.getString("itemName"));
				subHeading.setText(promo.getString("brand")); 
				otherInfo.setText(promo.getString("effectivePrice"));
			} catch (JSONException e) {
				mainHeading.setText("");
				subHeading.setText(""); 
				otherInfo.setText(""); 
			} 
	        
	        if(!listenItemSelect){
	        	ImageView arrow = (ImageView) vi.findViewById(R.id.next_nav);
	        	arrow.setVisibility(View.INVISIBLE);
	        }
	       return vi; 
	    }}
