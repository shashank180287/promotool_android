package com.mobile.promo.plugin.utils;


public interface Constants {

	public static final String LOG_TAG="PromoProvider";
	public static final int WIDGET_REFRESH_TIME_MILISEC=10000;//300000;
	
	public static final String PROMO_TOOL_WIDGET_RECEIVER="com.mobile.promo.plugin.PROMO_SEARCH_WIDGET_UPDATE";
	
	public static final String SERVER_BASE_URL = "http://10.0.2.2:8080/plugin";//"http://localhost:8080/promopulgin/services";
	public static final String SERVICE_TYPE_URL_EXT = "service";
	public static final String SUB_CATEGORY_URL_EXT = "subcategory";
	public static final String INVENTORY_URL_EXT = "inventory";
	public static final String SEARCH_ITEM_CONTEXT = "search";
	public static final String INVENTORY_SEARCH_REQ_PARAM_CAT = "categoryName";
	public static final String INVENTORY_SEARCH_REQ_PARAM_SUBCAT = "subCategoryName";
	
	
	public static final String SERVER_PROMOS_SEARCH_URL = "services";
	public static final String SERVER_USER_ACTIVITY_URL = "storeuseraction.jsp";
	
	public static final String APPLICATION_USER_ID = "1";
	public static final String USER_VISIT_CODE="VI";
	public static final String USER_TAG_CODE="TA";
	
	public static final String ALERT_TITLE_INTERNET_CONNECTION = "Internet";
	public static final String ALERT_MSG_INTERNET_CONNECTION = "Please connect to internet";
	public static final String ALERT_TITLE_LOCATION_SYNC = "GPS";
	public static final String ALERT_MSG_LOCATION_SYNC = "GPS is disabled in your device. Application needs GPS for determining your location. Would you like to enable it?";
	//geo fix 77.599609 12.913803
}
