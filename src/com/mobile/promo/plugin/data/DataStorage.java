package com.mobile.promo.plugin.data;

import java.util.HashMap;
import java.util.Iterator;
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
	private static JSONArray jsonArray;
	private static int selectedMovieIndex;
	private static boolean isLiveData;
	
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
	
	public static JSONArray getServiceInfoByLatAndLong(double latitide, double longitude){
		String movieSearchUrl = SERVER_BASE_URL+"/"+SERVER_PROMOS_SEARCH_URL;
//		movieSearchUrl += "?Id=" + APPLICATION_USER_ID;
//		movieSearchUrl += "&lat=" + latitide;
//		movieSearchUrl += "&lon=" + longitude;
//		String response = HttpUrlHitter.getResponseByHittingUrl(movieSearchUrl);
		
		Map<String, String> requestParams= new HashMap<String, String>();
		requestParams.put("id", APPLICATION_USER_ID);
		requestParams.put("lat", latitide+"");
		requestParams.put("lon", longitude+"");
		String response = HttpUrlHitUtils.postResponseByHittingUrl(movieSearchUrl, requestParams);
		if(response!=null){
			try{
				JSONArray tempArray = new JSONArray(response);
				if(tempArray!=null && tempArray.length()>0){
					setLiveData(true);
					jsonArray= new JSONArray(response);
				}else{
					setLiveData(false);
				}
			}catch (JSONException e) {
				setLiveData(false);
				Log.e(LOG_TAG, "exception occur while parsing object "+e.getMessage());
			}
		}else{
			setLiveData(false);
		}
		return jsonArray;
	}

	public static boolean isLiveData() {
		return DataStorage.isLiveData;
	}

	public static void setLiveData(boolean isLiveData) {
		DataStorage.isLiveData = isLiveData;
	}

	public static int getSelectedMovieIndex() {
		return selectedMovieIndex;
	}

	public static JSONArray getMovieListAsJsonArray() {
		return jsonArray;
	}
	
	public static void setSelectedMovieIndex(int selectedMovieIndex) {
		try{
//			String storeUserActivityUrl = SERVER_BASE_URL+"/"+SERVER_USER_ACTIVITY_URL;
//			storeUserActivityUrl += "?userId=" + APPLICATION_USER_ID;
//			storeUserActivityUrl += "&serviceTypeCode=M";
//			storeUserActivityUrl += "&timeMiliSec="+System.currentTimeMillis();
//			storeUserActivityUrl += "&serviceId="+selectedServiceInfo.getServiceId();
//			storeUserActivityUrl += "&userAction="+USER_VISIT_CODE;
//			HttpUrlHitter.getResponseByHittingUrl(storeUserActivityUrl);
			DataStorage.selectedMovieIndex = selectedMovieIndex;
		
		}catch (Exception e) {
			DataStorage.selectedMovieIndex = -1;
		}
	}

	public static boolean isUserLocationChanged(Location userLocation) {
		if(DataStorage.userCurrentLocation!=null && userLocation!=null){
			return !(DataStorage.userCurrentLocation.getLatitude()==userLocation.getLatitude() && DataStorage.userCurrentLocation.getLongitude()==userLocation.getLongitude());
		}else if(DataStorage.userCurrentLocation==null && userLocation!=null){
			return true;
		}
		return false;
	}

	public static JSONArray getPromoListJSArrayForCurrentLocation() {
		try {
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Iterator<String> keys = jsonObject.keys();
			return new JSONArray(jsonObject.getString(keys.next()));
		} catch (JSONException e) {
			Log.e(LOG_TAG, "exception occured to parse the result.");
			return new JSONArray();
		}
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
	
//	public static void storeUserTagging(ServiceInfo serviceInfo) {
//		try{
//			String storeUserActivityUrl = SERVER_BASE_URL+"/"+SERVER_USER_ACTIVITY_URL;
//			storeUserActivityUrl += "?userId=" + APPLICATION_USER_ID;
//			storeUserActivityUrl += "&serviceTypeCode=M";
//			storeUserActivityUrl += "&timeMiliSec="+System.currentTimeMillis();
////			storeUserActivityUrl += "&serviceId="+selectedServiceInfo.getServiceId();
//			storeUserActivityUrl += "&userAction="+USER_TAG_CODE;
//			HttpUrlHitter.getResponseByHittingUrl(storeUserActivityUrl);
//		
//		}catch (Exception e) {
//		}
//	}
}
