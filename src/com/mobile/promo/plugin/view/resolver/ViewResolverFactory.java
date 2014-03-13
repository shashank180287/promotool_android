package com.mobile.promo.plugin.view.resolver;

import static com.mobile.promo.plugin.utils.Constants.VIEW_TYPE_SPINNER;
import static com.mobile.promo.plugin.utils.Constants.VIEW_TYPE_TEXTFIELD;
import android.content.Context;

public class ViewResolverFactory {

	public static ViewResolver getViewResolver(String viewType, Context context) {
		if(VIEW_TYPE_SPINNER.equalsIgnoreCase(viewType)){
			return new SpinnerViewResolver(context);
		}else if(VIEW_TYPE_TEXTFIELD.equalsIgnoreCase(viewType)){
			return new TextFieldViewResolver(context);
		}
		return null;
	}
}
