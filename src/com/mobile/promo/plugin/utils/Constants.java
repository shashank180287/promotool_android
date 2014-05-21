package com.mobile.promo.plugin.utils;


public interface Constants {

	public static final String LOG_TAG="PromoProvider";
	public static final int WIDGET_REFRESH_TIME_MILISEC=10000;//300000;
	
	public static final String PROMO_TOOL_WIDGET_RECEIVER="com.mobile.promo.plugin.PROMO_SEARCH_WIDGET_UPDATE";
	
	public static final String SERVER_BASE_URL ="http://1-dot-promotooldummy.appspot.com/plugin";// "http://10.0.2.2:8080/plugin";//"http://localhost:8080/promopulgin/services";
	public static final String SERVICE_TYPE_URL_EXT = "service";
	public static final String SUB_CATEGORY_URL_EXT = "subcategory";
	public static final String APP_LOAD_URL_EXT = "loaddata";
	public static final String INVENTORY_URL_EXT = "inventory";
	public static final String SEARCH_ITEM_CONTEXT = "search";
	public static final String REQUEST_TYPES_URL_EXT = "requestintr/types";
	public static final String REQUEST_TYPES_DETAILS_URL_EXT = "requestintr/processordetails";
	public static final String INVENTORY_SEARCH_REQ_PARAM_CAT = "service-type";
	public static final String REQUEST_SUBMIT_URL_EXT = "requestintr";
	public static final String INVENTORY_SEARCH_REQ_PARAM_SUBCAT = "sub-category";
	public static final String STATUS_TRACKER_URL = "tracker/status";	
	public static final String STATUS_TRACKER_REQ_PARAM_ID = "id";	
	public static final String STATUS_TRACKER_REQ_PARAM_LAT = "lat";	
	public static final String STATUS_TRACKER_REQ_PARAM_LONG = "lon";	
	public static final String USER_DETAIL_REQ_URL_EXT = "userdetails";
	public static final String SETTINGS_REQ_URL_EXT = "settings";
	
	public static final String SERVICE_TYPES_RESP_KEY_NAME = "name";
	public static final String SERVICE_TYPES_RESP_KEY_CODE = "code";
	public static final String SUB_CATEGORY_RESP_KEY_SUBCAT ="subCategoryModels";
	public static final String SUB_CATEGORY_RESP_KEY_SUBCAT_NAME = "name";
	public static final String REQUEST_DETAIL_RESP_KEY_SELECTION="selection";
	public static final String SERVER_PROMOS_SEARCH_URL = "services";
	public static final String SERVER_USER_ACTIVITY_URL = "storeuseraction.jsp";
	public static final String REQUEST_TYPE_DETAIL_RESP_KEY_TYPE = "type";
	public static final String REQUEST_TYPE_DETAIL_RESP_KEY_VALUE = "values";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_ID = "id";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_CODE = "code";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_SUBCAT = "subCat";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_REQTYPE = "reqTypeName";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_CTX = "ctx";
	public static final String REQUEST_TYPE_SUBMIT_REQ_KEY_SUBCTX = "subCtx";
	public static final String SETTINGS_UPDATE_REQ_BODY_USERID = "userId";
	public static final String SETTINGS_UPDATE_REQ_BODY_SELECTEDSERVICE = "userSelectedServiceTypes";
	public static final String SETTINGS_UPDATE_RES_STATUS = "status";
	public static final String SETTINGS_UPDATE_RES_STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_TRACKER_REQ_USER_ID = "id";
	public static final String STATUS_TRACKER_REQ_LATITUDE = "lat";
	public static final String STATUS_TRACKER_REQ_LONGITUDE = "lon";
	public static final String STATUS_TRACKER_RES_ISSERVICEAVAIL = "isServiceAvailable";	
	public static final String STATUS_TRACKER_RES_SERVTYPERESP = "serviceTypesResponse";	
	public static final String STATUS_TRACKER_RES_INVSEARCHRESP = "inventorySearchResponse";	
	public static final String STATUS_TRACKER_RES_REQ_BRAND = "brand";	
	public static final String STATUS_TRACKER_RES_REQ_MESSAGE = "message";	
	public static final String STATUS_TRACKER_RES_REQ_EFFECTIVE_PRICE = "effectivePrice";	
	public static final String STATUS_TRACKER_RES_REQ_PRICE = "price";	
	public static final String STATUS_TRACKER_RES_INVSEARCHITEMS="inventorySearchItems";
	public static final String SERVICE_LIST_REQ_PARAM_ID="Id";
	public static final String SERVICE_LIST_REQ_PARAM_LATITUDE="lat";
	public static final String SERVICE_LIST_REQ_PARAM_LONGITUDE="lon";
	public static final String SERVICE_LIST_RESP_MESSAGE="message";
	public static final String SERVICE_LIST_RESP_TIME="time";
	public static final String SERVICE_LIST_RESP_NAME="name";
	public static final String SERVICE_LIST_RESP_TYPE="type";

	public static final String APPLICATION_USER_ID = "1";
	public static final String USER_VISIT_CODE="VI";
	public static final String USER_TAG_CODE="TA";
	
	public static final String ALERT_TITLE_INTERNET_CONNECTION = "Internet";
	public static final String ALERT_MSG_INTERNET_CONNECTION = "Please connect to internet";
	public static final String ALERT_TITLE_LOCATION_SYNC = "GPS";
	public static final String ALERT_MSG_LOCATION_SYNC = "GPS is disabled in your device. Application needs GPS for determining your location. Would you like to enable it?";
	//geo fix 77.599609 12.913803
	
	public static final String VIEW_TYPE_SPINNER = "list";
	public static final String VIEW_TYPE_TEXTFIELD = "input";
}
