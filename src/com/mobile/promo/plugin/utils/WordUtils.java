package com.mobile.promo.plugin.utils;

import android.annotation.SuppressLint;

public class WordUtils {

	@SuppressLint("DefaultLocale")
	public static String capatalize(String string) {
		return string.substring(0,1).toUpperCase()+string.substring(1);
	}
}
