package com.mobile.promo.plugin.listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.mobile.promo.plugin.utils.Constants;

public class RemoteServerAsyncCaller extends AsyncTask<String, Void, String> {

	private RemoteServerInvoker invoker;
	private String[] params;
	
	public RemoteServerAsyncCaller(RemoteServerInvoker invoker, String... params) {
		this.invoker = invoker;
		this.params = params;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		String methodName = params[1];
		if(methodName!=null && methodName.equalsIgnoreCase(HttpMethods.GET.name())){
			return getResponseByHittingUrl(url);
		}else if(methodName!=null && methodName.equalsIgnoreCase(HttpMethods.POST.name())){
			Map<String, String> requestParams = new HashMap<String, String>();
			if(params.length>2)
				for (int i = 2; i < params.length; i=i+2) {
					requestParams.put(params[i], params[i+1]);
				}
			return postResponseByHittingUrl(url, requestParams);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		invoker.executeResponse(result, params);
	}
	
	public static enum HttpMethods {
		GET, POST
	}
	
	private String getResponseByHittingUrl(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setDoInput(true);
			con.setDoOutput(false);
			con.connect();
			System.out.println(con.getResponseCode());
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception e) {
			Log.e(Constants.LOG_TAG, "Error while GET request as "+e.getMessage());
		}
		return null;
	}
	
	private String postResponseByHittingUrl(String request, Map<String, String> requestParams) {
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
			
			Log.d(Constants.LOG_TAG, "Response code is "+connection.getResponseCode());
			
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
			Log.e(Constants.LOG_TAG, "Error while GET request as "+e.getMessage());
		}
		return null;
	}
}
