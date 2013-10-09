package com.mobile.promo.plugin.list;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;

public class PromosListAdapter extends BaseAdapter {
	 
	    private List<JSONObject> data; 
	    private static LayoutInflater inflater=null; 
	  
	    public PromosListAdapter(Activity activity, List<JSONObject> data) { 
	        this.data=data; 
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	    } 
	  
	    public int getCount() { 
	        return data.size(); 
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
	  
	        TextView movieName = (TextView)vi.findViewById(R.id.movie_name);
	        TextView theaterName = (TextView)vi.findViewById(R.id.theater_name); 
	        TextView timings = (TextView)vi.findViewById(R.id.movie_timings);
	  
	        JSONObject promo = data.get(position); 
	  
	        try {
				movieName.setText(promo.getString("name"));
				theaterName.setText(promo.getString("message")); 
//				timings.setText(movie.getString("starttime")+"-"+movie.getString("endtime"));
				timings.setText(promo.getString("time"));
			} catch (JSONException e) {
				movieName.setText("");
				theaterName.setText(""); 
				timings.setText(""); 
			} 
	       return vi; 
	    } 
}
