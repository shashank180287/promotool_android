package com.mobile.promo.common.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.adapter.PromosListAdapter;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONArray;

public class PromoCommonListActivity extends ListActivity {

    private PromosListAdapter promoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_common_list);
        JSONArray data = DataStorage.getItemsList();
        if(data!=null){
        	promoListAdapter = new PromosListAdapter(this, data, false);
        	setListAdapter(promoListAdapter);
        }
    }
}
