package com.mobile.promo.plugin.activity;

import java.util.Map;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.data.DataStorage;
import com.mobile.promo.plugin.utils.WordUtils;

public class SettingsPage extends PreferenceActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.settings);   
        
        PreferenceScreen screen = (PreferenceScreen) findPreference("category_setting_key");
        Map<String, Boolean> serviceTypeStatus = DataStorage.getServiceTypeSettings();
        if(serviceTypeStatus!=null && serviceTypeStatus.keySet().size()>0){
        	for (String serviceType : serviceTypeStatus.keySet()) {
                CheckBoxPreference glossaryCheckBoxPreference = new CheckBoxPreference(this);
                glossaryCheckBoxPreference.setTitle(WordUtils.capatalize(serviceType));
                glossaryCheckBoxPreference.setChecked(serviceTypeStatus.get(serviceType));
                screen.addPreference(glossaryCheckBoxPreference);
			}
        }else{
        	Toast.makeText(this , "Some error occured. Please try after some time.", Toast.LENGTH_LONG).show();
			finish();
        }
        
    }
    
}