package com.mobile.promo.common.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;

public class PromoInfosActivity extends Activity implements OnClickListener {

	private JSONObject selectedMovieObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movieinfo_layout);
		
		TableLayout movieInfoTable = (TableLayout) findViewById(R.id.movieinfo_table);
		
		try{
			if(DataStorage.getMovieListAsJsonArray().length()>DataStorage.getSelectedMovieIndex()){
				selectedMovieObject = DataStorage.getMovieListAsJsonArray().getJSONObject(DataStorage.getSelectedMovieIndex());
		
				String movieName= selectedMovieObject.getString("name");
				
				TableRow movieNameRow = new TableRow(this);
				movieNameRow.setId(10);
				movieNameRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				TextView movieNameView = new TextView(this);
				movieNameView.setId(1);
		        movieNameView.setText(movieName);
		        movieNameView.setTextColor(Color.WHITE);
		        movieNameView.setTextSize(12);
//		        movieNameView.setPadding(5, 5, 5, 5);
		        movieNameRow.addView(movieNameView);// add the column to the table row here
		        movieInfoTable.addView(movieNameRow, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		        
		        
		        String[] movieTimings = selectedMovieObject.getString("movietimimngs").split(",");
		        String[] movieOffers = selectedMovieObject.getString("movieoffers").split(",");
		        
		        int count=0;
		        for (int i=0;i<movieOffers.length;i++) {
		        	String movieOffer = movieOffers[i];
		        	TableRow offerRow = new TableRow(this);
					offerRow.setId(count++);
					offerRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
					TextView offerTextView = new TextView(this);
					offerTextView.setId(20);
			        offerTextView.setText("Offer");
			        offerTextView.setTextColor(Color.WHITE);
//			        movieNameView.setPadding(5, 5, 5, 5);
			        offerRow.addView(offerTextView);// add the column to the table row here
		        
			        TextView offerText = new TextView(this);
			        offerText.setId(count+2);// define id that must be unique
			        offerText.setText(movieOffer); // set the text for the header 
			        offerText.setTextColor(Color.WHITE); // set the color
			        offerText.setPadding(5, 5, 5, 5); // set the padding (if required)
			        offerRow.addView(offerText); // add the column to the table row here
			        movieInfoTable.addView(offerRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		        	TableRow timingRow = new TableRow(this);
					timingRow.setId(count++);
					timingRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
					TextView timingView = new TextView(this);
					timingView.setId(count++);
			        timingView.setText("Time");
			        timingView.setTextColor(Color.WHITE);
//			        movieNameView.setPadding(5, 5, 5, 5);
			        timingRow.addView(timingView);// add the column to the table row here
		        
			        TextView timeText = new TextView(this);
			        timeText.setId(count+2);// define id that must be unique
			        timeText.setText(movieTimings[i]); // set the text for the header 
			        timeText.setTextColor(Color.WHITE); // set the color
			        timeText.setPadding(5, 5, 5, 5); // set the padding (if required)
			        timingRow.addView(timeText); // add the column to the table row here
			        movieInfoTable.addView(timingRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        }
		        TableRow buttonRow = new TableRow(this);
		        Button likeButton = new Button(this);
		        likeButton.setText("Call..");
				likeButton.setOnClickListener(this);
				buttonRow.addView(likeButton);
				movieInfoTable.addView(buttonRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				
			}
		}catch (JSONException e) {
			
		}
	}

	@Override
	public void onClick(View v) {
		
		
	}
}
