package com.mobile.promo.plugin.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class HttpUrlHitUtils implements Constants{
	
	public static String getResponseByHittingUrl(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setDoInput(true);
			con.setDoOutput(false);
			con.connect();
			int responseCode = con.getResponseCode();
			Log.d(LOG_TAG, "Response status is "+responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception e) {
			Log.e(LOG_TAG, e.toString());
		}
		return null;
	}
	
	public static String postResponseByHittingUrl(String request, Map<String, String> requestParams) {
		try{
			StringBuilder sbl = new StringBuilder();
			for (String paramName : requestParams.keySet()) {
				sbl.append(paramName+"="+requestParams.get(paramName)+"&");
			}
			
			String urlParameters = sbl.toString();
			urlParameters = urlParameters.substring(0, urlParameters.length()-1);
			
			URL url = new URL(request); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false); 
			connection.setRequestMethod("POST"); 
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setUseCaches (false);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			InputStream myInputStream = connection.getInputStream();
	
			BufferedReader rd = new BufferedReader(new InputStreamReader(myInputStream), 4096);
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			connection.disconnect();
			return sb.toString();
		} catch (Exception e) {
			Log.d(LOG_TAG, e.toString());
		}
		return null;
	}

	public static String postResponseByHittingUrlWithRequestBody(String request, String requestBody) {
		try{
			URL url = new URL(request); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false); 
			connection.setRequestMethod("POST"); 
			connection.setRequestProperty("Content-Type", "application/json"); 
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(requestBody.getBytes().length));
			connection.setUseCaches (false);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(requestBody);
			wr.flush();
			wr.close();
			
			InputStream myInputStream = connection.getInputStream();
	
			BufferedReader rd = new BufferedReader(new InputStreamReader(myInputStream), 4096);
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			connection.disconnect();
			return sb.toString();
		} catch (Exception e) {
			Log.d(LOG_TAG, e.toString());
		}
		return null;
	}
}
