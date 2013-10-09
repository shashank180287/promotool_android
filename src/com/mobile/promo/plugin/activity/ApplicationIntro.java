package com.mobile.promo.plugin.activity;


import android.app.Activity;
import android.os.Bundle;

import com.mobile.promo.plugin.R;

public class ApplicationIntro extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//      SipManager manager = SipManager.newInstance(this);
//    	Log.i("sip_test", "manager: " + manager.toString());
//   	Log.i("sip_test", "isApiSupported: " + new Boolean(SipManager.isApiSupported(this)).toString());
//    	Log.i("sip_test", "isSipWifiOnly: " + new Boolean(SipManager.isSipWifiOnly(this)).toString());
//    	Log.i("sip_test", "isVoipSupported: " + new Boolean(SipManager.isVoipSupported(this)).toString());
        
        //lat: 12.916648
        //long: 77.599976
    }
}