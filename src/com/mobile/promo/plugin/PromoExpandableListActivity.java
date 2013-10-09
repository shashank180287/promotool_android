package com.mobile.promo.plugin;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.mobile.promo.model.ExpandListChild;
import com.mobile.promo.model.ExpandListGroup;
import com.mobile.promo.plugin.list.ExpandListAdapter;

public class PromoExpandableListActivity extends Activity {

	/** Called when the activity is first created. */
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expanding_list);
		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandListAdapter(PromoExpandableListActivity.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
	}

	public ArrayList<ExpandListGroup> SetStandardGroups() {
		ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
		ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
		ExpandListGroup gru1 = new ExpandListGroup();
		gru1.setName("Comedy");
		gru1.setImageResourse(R.drawable.apparel_total);
		ExpandListChild ch1_1 = new ExpandListChild();
		ch1_1.setName("A movie");
		ch1_1.setTag(null);
		list2.add(ch1_1);
		ExpandListChild ch1_2 = new ExpandListChild();
		ch1_2.setName("An other movie");
		ch1_2.setTag(null);
		list2.add(ch1_2);
		ExpandListChild ch1_3 = new ExpandListChild();
		ch1_3.setName("And an other movie");
		ch1_3.setTag(null);
		list2.add(ch1_3);
		gru1.setItems(list2);
		list2 = new ArrayList<ExpandListChild>();

		ExpandListGroup gru2 = new ExpandListGroup();
		gru2.setName("Action");
		gru2.setImageResourse(R.drawable.grocery_total);
		ExpandListChild ch2_1 = new ExpandListChild();
		ch2_1.setName("A movie");
		ch2_1.setTag(null);
		list2.add(ch2_1);
		ExpandListChild ch2_2 = new ExpandListChild();
		ch2_2.setName("An other movie");
		ch2_2.setTag(null);
		list2.add(ch2_2);
		ExpandListChild ch2_3 = new ExpandListChild();
		ch2_3.setName("And an other movie");
		ch2_3.setTag(null);
		list2.add(ch2_3);
		gru2.setItems(list2);
		list.add(gru1);
		list.add(gru2);

		return list;
	}

}
