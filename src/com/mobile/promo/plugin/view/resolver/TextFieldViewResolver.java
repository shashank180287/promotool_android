package com.mobile.promo.plugin.view.resolver;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;

public class TextFieldViewResolver implements ViewResolver {

	private Context context;
	private EditText text;
	
	public TextFieldViewResolver( Context context) {
		this.context = context;
	}
	
	@Override
	public View resolveView(Object... params) {
		this.text = new EditText(context);
		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return text;
	}

	@Override
	public String showSelectedData() {
			String text = this.text.getText().toString();
			if(text!=null && text.length()>0)
				return text;
			throw new IllegalArgumentException("Text Field can not be empty");
	}

}
