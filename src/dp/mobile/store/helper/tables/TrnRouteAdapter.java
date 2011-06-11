package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.content.Context;
import dp.mobile.store.helper.DatabaseAdapter;

public class TrnRouteAdapter extends DatabaseAdapter {
	public static final String DATABASE_TABLE	= "mobile_trnroute";
	
	public static final String KEY_ROWID				= "id";
	public static final String KEY_TRNDATE				= "trndate";
    public static final String KEY_USERNAME				= "username";
    public static final String KEY_UNITCOMPANY_CODE		= "unitcompany_code";
    public static final String KEY_ORDERNO				= "orderno";
    public static final String KEY_CUSTOMER_CODE		= "customer_code";
    public static final String KEY_CUSTOMER_NAME		= "customer_name";
    public static final String KEY_CUSTOMER_ADDRESS		= "customer_address";
    public static final String KEY_CUSTOMER_POSTCODE	= "customer_postcode";
    public static final String KEY_CUSTOMER_SATELLITE	= "customer_satellite";
    public static final String KEY_CUSTOMER_TYPE		= "customer_type";
    public static final String KEY_CUSTOMER_TERMPAYMENT	= "customer_termpayment";
    public static final String KEY_CREDIT_LIMIT			= "credit_limit";
    public static final String KEY_RECEIVABLE			= "receivable";
    public static final String KEY_LASTBUYDATE			= "lastbuydate";
    public static final String KEY_DESCR				= "descr";
	
	public TrnRouteAdapter(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param id
	 * @param date
	 * @param username
	 * @param unitCompanyCode
	 * @param orderNo
	 * @param customerCode
	 * @param customerName
	 * @param customerAddress
	 * @param customerPostcode
	 * @param customerType
	 * @param customerTermpayment
	 * @param creditLimit
	 * @param receivable
	 * @param lastBuyDate
	 * @param descr
	 * @return
	 */
    public long create(String id, String date, String username, String unitCompanyCode, long orderNo,
    		String customerCode, String customerName, String customerAddress, String customerPostcode,
    		String customerType, String customerTermpayment, long creditLimit, long receivable,
    		String lastBuyDate, String descr){
    	ContentValues args = new ContentValues();
    	
		args.put(KEY_ROWID, id);
		args.put(KEY_TRNDATE, date);
		args.put(KEY_USERNAME, username);
    	args.put(KEY_UNITCOMPANY_CODE, unitCompanyCode);
    	args.put(KEY_ORDERNO, orderNo);
    	args.put(KEY_CUSTOMER_CODE, customerCode);
    	args.put(KEY_CUSTOMER_NAME, customerName);
    	args.put(KEY_CUSTOMER_ADDRESS, customerAddress);
    	args.put(KEY_CUSTOMER_TYPE, customerType);
    	args.put(KEY_CUSTOMER_TERMPAYMENT, customerTermpayment);
    	args.put(KEY_CREDIT_LIMIT, creditLimit);
    	args.put(KEY_RECEIVABLE, receivable);
    	args.put(KEY_LASTBUYDATE, lastBuyDate);
    	args.put(KEY_DESCR, descr);
    	
    	return mDatabase.insert(DATABASE_TABLE, null, args);
    }
    
    /**
     * Delete daily news with the given rowID
     * 
     * @param rowID
     * @return number of rows affected
     */
    public long delete(long rowID){
    	return mDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null);
    }
    
    /**
     * Return a Cursor over the list of all routes in the database 
     * 
     * @return Cursor over all notes
     */
    /*public Cursor fetchAll(){
    	return mDatabase.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR},
    							null, null, null, null, null);
    }*/
    
    /**
     * Return a Cursor positioned at the daily news matches the given rowID
     * 
     * @param rowID
     * @return Cursor positioned to matching daily news, if found
     * @throws SQLException
     */
    /*public Cursor fetch(long rowID) throws SQLException{
    	Cursor retval = mDatabase.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR},
    			KEY_ROWID + "=" + rowID, null, null, null, null, null);
    	if(retval != null)
    		retval.moveToFirst();
    	
    	return retval;
    }*/
}
