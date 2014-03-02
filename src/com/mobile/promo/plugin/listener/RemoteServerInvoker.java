package com.mobile.promo.plugin.listener;

public interface RemoteServerInvoker <T>{

	public void executeResponse(T response, Object... extras) ;
	
}
