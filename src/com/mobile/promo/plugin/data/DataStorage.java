package com.mobile.promo.plugin.data;

import java.util.HashMap;
import java.util.Map;

import android.location.Location;
import android.util.Log;

import com.mobile.promo.plugin.json.JSONArray;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.utils.Constants;
import com.mobile.promo.plugin.utils.HttpUrlHitUtils;

public class DataStorage implements Constants{

	private static Location userCurrentLocation;
	private static byte[] dealOfTheDayBytes;
	private static JSONArray serviceTypes;
	private static JSONArray itemsList;
	private static Map<String, Boolean> serviceTypeSettings;
	
	public static JSONArray getItemsList() {
		return itemsList;
	}

	public static void setItemsList(JSONArray itemsList) {
		DataStorage.itemsList = itemsList;
	}

	public static JSONArray getServiceTypes() {
		return serviceTypes;
	}

	public static void setServiceTypes(JSONArray serviceTypes) {
		DataStorage.serviceTypes = serviceTypes;
	}

	public static byte[] getDealOfTheDayBytes() {
		return dealOfTheDayBytes;
	}

	public static void setDealOfTheDayBytes(byte[] dealOfTheDayBytes) {
		DataStorage.dealOfTheDayBytes = dealOfTheDayBytes;
	}

	public static Location getUserCurrentLocation() {
		return userCurrentLocation;
	}

	public static void setUserCurrentLocation(Location userCurrentLocation) {
		DataStorage.userCurrentLocation = userCurrentLocation;
	}
	
	
	public static boolean isUserLocationChanged(Location userLocation) {
		if(DataStorage.userCurrentLocation!=null && userLocation!=null){
			return !(DataStorage.userCurrentLocation.getLatitude()==userLocation.getLatitude() && DataStorage.userCurrentLocation.getLongitude()==userLocation.getLongitude());
		}else if(DataStorage.userCurrentLocation==null && userLocation!=null){
			return true;
		}
		return false;
	}

	public static Map<String, Boolean> getServiceTypeSettings() {
		if(serviceTypeSettings==null){
			serviceTypeSettings = new HashMap<String, Boolean>();
			for (int i = 0; i < serviceTypes.length(); i++) {
				try {
					serviceTypeSettings.put(serviceTypes.getJSONObject(i).getString(SERVICE_TYPES_RESP_KEY_NAME), true);
				} catch (JSONException e) {
					
				}
			}
		}
		return serviceTypeSettings;
	}

	public static JSONObject getServiceTypeByName(String serviceName) {
		for (int i = 0; i < serviceTypes.length(); i++) {
			try {
				if(serviceTypes.getJSONObject(i).getString(SERVICE_TYPES_RESP_KEY_NAME).equalsIgnoreCase(serviceName)){
					return serviceTypes.getJSONObject(i);
				}
			} catch (JSONException e) {
				
			}
		}
		return null;
	}
	
	public static void setServiceTypeSettings(
			Map<String, Boolean> serviceTypeSettings) {
		DataStorage.serviceTypeSettings = serviceTypeSettings;
	}
	
	public static void addServiceType(String serviceName, boolean status) {
		DataStorage.serviceTypeSettings.put(serviceName, Boolean.valueOf(status));
	}

	public static JSONArray getPromoListJSArrayForLocation(double latitide, double longitude, String userId) {
		String movieSearchUrl = SERVER_BASE_URL+"/"+SERVER_PROMOS_SEARCH_URL;
		movieSearchUrl += "?"+SERVICE_LIST_REQ_PARAM_ID +"=" + userId;
		movieSearchUrl += "&"+ SERVICE_LIST_REQ_PARAM_LATITUDE +"=" + latitide;
		movieSearchUrl += "&"+ SERVICE_LIST_REQ_PARAM_LONGITUDE +"=" + longitude;
		String response = HttpUrlHitUtils.getResponseByHittingUrl(movieSearchUrl);
		JSONArray jsonArray = null;
		if(response!=null){
			try{
				JSONObject services = new JSONObject(response.substring(1, response.length()-1));
				jsonArray= services.getJSONArray(latitide+":"+longitude);
			}catch (JSONException e) {
				Log.e(LOG_TAG, "exception occur while parsing object "+e.getMessage());
			}
		}else{
		}
		return jsonArray;
	}
}
