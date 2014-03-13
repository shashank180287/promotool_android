package com.mobile.promo.plugin.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneUtils {

	public static String getUserTelephoneNumber(Context mAppContext) {
		TelephonyManager tMgr = (TelephonyManager) mAppContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = tMgr.getLine1Number();
		if (phoneNumber != null && phoneNumber.trim().length() > 0)
			return trimToTenDigit(tMgr.getLine1Number());
		else
			return generateUniqueIdentifier(tMgr);
	}

	private static String generateUniqueIdentifier(
			TelephonyManager telephonyManager) {
		String simSerialNumber = telephonyManager.getSimSerialNumber();
		String networkOp = telephonyManager.getNetworkOperator();
		String countryCode = telephonyManager.getNetworkCountryIso();
		return simSerialNumber+networkOp+countryCode;
	}

	private static String trimToTenDigit(String number) {
		if (number != null && number.length() > 10) {
			return number.substring(number.length() - 10);
		}
		return number;
	}
}
