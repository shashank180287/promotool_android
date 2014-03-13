package com.mobile.promo.plugin.view.resolver;

import android.view.View;

public interface ViewResolver {

	public View resolveView(Object... params);

	public String showSelectedData();

}
