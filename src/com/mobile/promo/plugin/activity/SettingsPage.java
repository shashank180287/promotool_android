package com.mobile.promo.plugin.activity;

import static com.mobile.promo.plugin.utils.Constants.SERVER_BASE_URL;
import static com.mobile.promo.plugin.utils.Constants.SETTINGS_REQ_URL_EXT;
import static com.mobile.promo.plugin.utils.Constants.SETTINGS_UPDATE_REQ_BODY_SELECTEDSERVICE;
import static com.mobile.promo.plugin.utils.Constants.SETTINGS_UPDATE_REQ_BODY_USERID;
import static com.mobile.promo.plugin.utils.Constants.SETTINGS_UPDATE_RES_STATUS;
import static com.mobile.promo.plugin.utils.Constants.SETTINGS_UPDATE_RES_STATUS_SUCCESS;
import static com.mobile.promo.plugin.utils.Constants.USER_DETAIL_REQ_URL_EXT;
import static com.mobile.promo.plugin.data.DataStorage.getServiceTypeByName;
import static com.mobile.promo.plugin.utils.Constants.SERVICE_TYPES_RESP_KEY_CODE;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.json.JSONException;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.utils.HttpUrlHitUtils;
import com.mobile.promo.plugin.utils.PhoneUtils;
import com.mobile.promo.plugin.utils.WordUtils;

public class SettingsPage extends PreferenceActivity {
    
	Map<String, Boolean> changedSettings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {        
    	
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.settings);   
        
        changedSettings = new HashMap<String, Boolean>();
        
        PreferenceScreen screen = (PreferenceScreen) findPreference("category_setting_key");
        final Map<String, Boolean> serviceTypeStatus = DataStorage.getServiceTypeSettings();
        if(serviceTypeStatus!=null && serviceTypeStatus.keySet().size()>0){
        	for (final String serviceType : serviceTypeStatus.keySet()) {
                CheckBoxPreference glossaryCheckBoxPreference = new CheckBoxPreference(this);
                glossaryCheckBoxPreference.setTitle(WordUtils.capatalize(serviceType));
                glossaryCheckBoxPreference.setChecked(serviceTypeStatus.get(serviceType));
                glossaryCheckBoxPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					
					public boolean onPreferenceClick(Preference preference) {
						changedSettings.put(serviceType, (changedSettings.containsKey(serviceType)?!changedSettings.get(serviceType):!serviceTypeStatus.get(serviceType)));
						return false;
					}
				});
                screen.addPreference(glossaryCheckBoxPreference);
			}
        }else{
        	Toast.makeText(this , "Some error occured. Please try after some time.", Toast.LENGTH_LONG).show();
			finish();
        }
        
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	updateSettings();
    }
    
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	updateSettings();
    }
    
    private void updateSettings() {
    	if(changedSettings.size()>0){
    		for (String serviceType : changedSettings.keySet()) {
				DataStorage.addServiceType(serviceType, changedSettings.get(serviceType));
			}
    		Map<String, Boolean> serviceTypeStatus = DataStorage.getServiceTypeSettings();
    		try{
	    		StringBuilder services = new StringBuilder();
	    		for (String serviceType : serviceTypeStatus.keySet()) {
	    			if(serviceTypeStatus.get(serviceType)){
	    				services.append(getServiceTypeByName(serviceType).getString(SERVICE_TYPES_RESP_KEY_CODE)).append(",");
	    			}
	    		}
				String userSettingUpdateUrl = SERVER_BASE_URL+"/"+ USER_DETAIL_REQ_URL_EXT+"/"+SETTINGS_REQ_URL_EXT;
				JSONObject requestBody = new JSONObject();
				requestBody.put(SETTINGS_UPDATE_REQ_BODY_USERID, PhoneUtils.getUserTelephoneNumber(this));
				requestBody.put(SETTINGS_UPDATE_REQ_BODY_SELECTEDSERVICE, services.length()>0?services.substring(0,  services.length()-1):"");
				String response = HttpUrlHitUtils.putResponseByHittingUrlWithRequestBody(userSettingUpdateUrl, requestBody.toString());
				if(! (new JSONObject(response).getString(SETTINGS_UPDATE_RES_STATUS).equalsIgnoreCase(SETTINGS_UPDATE_RES_STATUS_SUCCESS))){
					Toast.makeText(this,"Settings can not changed now. Try after some time.", Toast.LENGTH_LONG).show();
				}
    		}catch (JSONException e) {
	    		Toast.makeText(this,"Settings can not changed now. Try after some time.", Toast.LENGTH_LONG).show();
			}
    	}
    	changedSettings = new HashMap<String, Boolean>();
	}
}