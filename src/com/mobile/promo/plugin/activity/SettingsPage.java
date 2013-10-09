package com.mobile.promo.plugin.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;

import com.mobile.promo.plugin.R;

public class SettingsPage extends PreferenceActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.settings);   
        
        PreferenceScreen screen = (PreferenceScreen) findPreference("category_setting_key");
        
        CheckBoxPreference glossaryCheckBoxPreference = new CheckBoxPreference(this);
        glossaryCheckBoxPreference.setTitle("Glossary");
        screen.addPreference(glossaryCheckBoxPreference);
        
        CheckBoxPreference apprealCheckBoxPreference = new CheckBoxPreference(this);
        apprealCheckBoxPreference.setTitle("Appreal");
        screen.addPreference(apprealCheckBoxPreference);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "Show current settings");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
 //               startActivity(new Intent(this, ShowSettingsActivity.class));
                return true;
        }
        return false;
    }
}