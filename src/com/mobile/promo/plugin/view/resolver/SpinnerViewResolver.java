package com.mobile.promo.plugin.view.resolver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerViewResolver implements ViewResolver {

	private Context context;
	private Spinner spinner;
	
	public SpinnerViewResolver(Context context) {
		this.context = context;
	}
	
	@Override
	public View resolveView(Object... params) {
		spinner = new Spinner(context);
		List<String> list = new ArrayList<String>();
		list.add("Select One...");
		for (Object param : params) {
			if(param.toString().contains("[") && param.toString().contains("]")){
				String[] internalFields = param.toString().substring(2, param.toString().length()-2).split("\", \"");
				for (String field : internalFields) {
					list.add(field);
				}
				continue;
			}
			list.add(param.toString());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item,
				list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return spinner;
	}

	@Override
	public String showSelectedData() {
		int selectedItemPosition = this.spinner.getSelectedItemPosition();
		if(selectedItemPosition==0)
			throw new IllegalArgumentException("Please select proper option");
		return this.spinner.getSelectedItem().toString();
	}

}
