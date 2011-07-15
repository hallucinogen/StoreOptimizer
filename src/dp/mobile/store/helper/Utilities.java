package dp.mobile.store.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Utilities {
	public static final String PREFERENCES 		= "PREFERENCES";
	public static final String GET_ACTIVATED 	= "GET_ACTIVATED";
	public static final long UPDATE_DELAY		= 10000;
	public static final String TRACK_URL		= "http://167.205.32.26/truckserver/index.php/position/update";
	public static final String ORDER_URL		= "http://167.205.32.26/truckserver/index.php/order/update";
	public static final String LOGIN_URL		= "http://167.205.32.26/truckserver/index.php/home/applogin";
	public static final String NEW_STORE_URL	= "http://167.205.32.26/truckserver/index.php/shop/update";
	public static final String NEW_ITEM_URL		= "http://167.205.32.26/truckserver/index.php/shop/additem";
	public static final String ITEM_LIST_URL	= "http://167.205.32.26/truckserver/index.php/shop/getitemlist";
	public static final String STORE_LIST_URL	= "http://167.205.32.26/truckserver/index.php/shop/getshoplist";
	
	public static final String USERNAME		= "admin";
	public static final String PASSWORD		= "adminadmin";
	
	public static final String INTENT_STORE_NAME	= "store_name";
	public static final String INTENT_STORE_ID		= "store_id";
	public static final String INTENT_ITEM_NAME		= "item_name";
	public static final String INTENT_ITEM_ID		= "item_id";
	public static final String INTENT_ITEM_COUNT	= "item_count";
	public static final String INTENT_TOTAL_COST	= "total_cost";
	
	public static final int KANVASING_STOREINFO_RC		= 0;
	public static final int KANVASING_STOREHISTORY_RC	= 1;
	public static final int KANVASING_TRANSACTION_RC	= 2;
	public static final int KANVASING_FINISH_RC			= 3;
	public static final int KANVASING_STORELIST_RC		= 4;
	
	public static final String DATE_FORMAT			= "yyyy-MMM-dd hh:mm:ss";
	
	public static String formatDate(Date date){
		return android.text.format.DateFormat.format(DATE_FORMAT, date).toString();
	}
	
	public static Date formatStr(String str){
	    SimpleDateFormat  format = new SimpleDateFormat(DATE_FORMAT);
		
    	try {  
    		return format.parse(str);
		} catch (Exception e) {
			Log.d("UTILITIES", "Cannot convert string " + str + " to date");
		    e.printStackTrace();
		}
		
		return null;
	}
}
