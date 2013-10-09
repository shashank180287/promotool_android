package com.mobile.promo.plugin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.mobile.promo.plugin.utils.HomePageOptions;

public class HomePageGridButtonAdapter extends BaseAdapter {
    private Context mContext;

    public HomePageGridButtonAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return HomePageOptions.values().length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            button = new Button(mContext);
            button.setGravity(Gravity.BOTTOM);
            button.setTextColor(Color.MAGENTA);
            button.setEnabled(true);
            button.setLayoutParams(new GridView.LayoutParams(85, 85));
            button.setPadding(8, 8, 8, 8);
        } else {
            button = (Button) convertView;
        }

        button.setBackgroundResource(HomePageOptions.values()[position].getImageId());
        button.setText(HomePageOptions.values()[position].getImageText());
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent settings = new Intent(mContext, HomePageOptions.values()[position].getActivityClass());
	            mContext.startActivity(settings);
			}
        });
        return button;
    }
}
