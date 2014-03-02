package com.mobile.promo.plugin.activity;

import static com.mobile.promo.plugin.utils.Constants.APP_LOAD_URL_EXT;
import static com.mobile.promo.plugin.utils.Constants.SERVER_BASE_URL;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.mobile.promo.plugin.R;
import com.mobile.promo.plugin.json.JSONObject;
import com.mobile.promo.plugin.listener.OnlineImageDownloader;
import com.mobile.promo.plugin.listener.RemoteServerAsyncCaller;
import com.mobile.promo.plugin.listener.RemoteServerAsyncCaller.HttpMethods;
import com.mobile.promo.plugin.listener.RemoteServerInvoker;
import com.mobile.promo.plugin.utils.DataStorage;

public class AppLoadingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_loading);

		// final Handler handler = new Handler();
		//
		// handler.postDelayed(new Runnable() {
		// public void run() {
		// Intent mInHome = new Intent(AppLoadingActivity.this,
		// DealOfTheDayActivity.class);
		// AppLoadingActivity.this.startActivity(mInHome);
		// AppLoadingActivity.this.finish();
		// }
		// }, 5000);

		new RemoteServerAsyncCaller(new RemoteServerInvoker<String>() {

			@Override
			public void executeResponse(String response, Object... extras) {
				try {
						JSONObject json = new JSONObject(response);
						new OnlineImageDownloader(new RemoteServerInvoker<Bitmap>() {

							@Override
							public void executeResponse(Bitmap response, Object... extras) {
								Intent mInHome = new Intent(
										AppLoadingActivity.this,
										DealOfTheDayActivity.class);
							
								if (response != null){
									ByteArrayOutputStream stream = new ByteArrayOutputStream();
									response.compress(Bitmap.CompressFormat.PNG, 100, stream);
									byte[] byteArray = stream.toByteArray();
									DataStorage.setDealOfTheDayBytes(byteArray);
								}
								AppLoadingActivity.this.startActivity(mInHome);
								AppLoadingActivity.this.finish();

							}
						}).execute(json.getString("dod"));
					} catch (Exception e) {

					}

			}
		}).execute(SERVER_BASE_URL + APP_LOAD_URL_EXT,  HttpMethods.GET.name());
	}

}
