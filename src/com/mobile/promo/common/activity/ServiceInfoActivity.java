package com.mobile.promo.common.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;

public class ServiceInfoActivity extends Activity implements OnClickListener{

	private JSONObject selectedMovieObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serviceinfo_layout);
		try{
			if(DataStorage.getMovieListAsJsonArray().length()>DataStorage.getSelectedMovieIndex()){
				selectedMovieObject = DataStorage.getMovieListAsJsonArray().getJSONObject(DataStorage.getSelectedMovieIndex());
		
				String movieName= selectedMovieObject.getString("name");
				String offer = selectedMovieObject.getString("offer");
			
				TextView movieNameTextView = (TextView) findViewById(R.id.movie_name);
				movieNameTextView.setText(movieName);
				movieNameTextView.setTypeface(null, Typeface.BOLD);
			
				TextView offerTextView = (TextView) findViewById(R.id.offer);
				offerTextView.setText(offer);
				offerTextView.setTypeface(null, Typeface.ITALIC);
			
				TextView timingTextView = (TextView) findViewById(R.id.timing_value);
				timingTextView.setText(selectedMovieObject.getString("starttime")+"-"+selectedMovieObject.getString("endtime"));
			
				TextView placeTextView = (TextView) findViewById(R.id.place_value);
				placeTextView.setText(selectedMovieObject.getString("place"));
			
				Button likeButton = (Button) findViewById(R.id.Button01);
				likeButton.setOnClickListener(this);
			}
		}catch (JSONException e) {
			
		}

	}

	@Override
	public void onClick(View view) {
//		DataStorage.storeUserTagging(selectedMovieObject);
		
	}
}
