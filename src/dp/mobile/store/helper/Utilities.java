package dp.mobile.store.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class Utilities {
	public static final String PREFERENCES 		= "artindo_pref";
	public static final String GET_ACTIVATED 	= "GET_ACTIVATED";
	public static final long UPDATE_DELAY		= 10000;
	public static final String TRACK_URL		= "http://167.205.32.26/truckserver/index.php/position/update";
	public static final String ORDER_URL		= "http://167.205.32.26/truckserver/index.php/order/update";
	public static final String LOGIN_URL		= "http://167.205.32.26/truckserver/index.php/home/applogin";
	public static final String NEW_STORE_URL	= "http://167.205.32.26/truckserver/index.php/shop/update";
	public static final String NEW_ITEM_URL		= "http://167.205.32.26/truckserver/index.php/shop/additem";
	public static final String ITEM_LIST_URL	= "http://167.205.32.26/truckserver/index.php/shop/getitemlist";
	public static final String STORE_LIST_URL	= "http://167.205.32.26/truckserver/index.php/shop/getshoplist";
	
	public static final String USERNAME 	= "USERNAME";
	public static final String PASSWORD 	= "PASSWORD";
	public static final String IP_SERVER	= "IP_SERVER";
	public static final String IP_PUBLIC	= "IP_PUBLIC";
	public static final String HOST_STRING	= "HOST_STRING";
	public static final String DEFAULT_USER	= "DEFAULT_USER";
	
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
	
	public static void savePref(Context ctx, String IPPublic, String IPServer, String hostString, String defUser){
		SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES, 0);
		Editor sharedPrefEditor = sharedPref.edit();
		
		sharedPrefEditor.putString(IP_SERVER, IPServer);
		sharedPrefEditor.putString(IP_PUBLIC, IPPublic);
		sharedPrefEditor.putString(HOST_STRING, hostString);
		sharedPrefEditor.putString(DEFAULT_USER, defUser);
		
		sharedPrefEditor.commit();
		Toast.makeText(ctx, "Settings Saved", Toast.LENGTH_SHORT).show();
	}
	
	public static String[] loadPred(Context ctx){
		String retval[] = new String[4];
		SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES, 0);
		
		retval[0]	= sharedPref.getString(IP_PUBLIC, "");
		retval[1]	= sharedPref.getString(IP_SERVER, "");
		retval[2]	= sharedPref.getString(HOST_STRING, "");
		retval[3]	= sharedPref.getString(DEFAULT_USER, "");
		
		return retval;
	}
}
