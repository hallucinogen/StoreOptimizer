package dp.mobile.store.helper.tables;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import dp.mobile.store.helper.Utilities;

public class TrnRoute extends Model {	
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
	
    public static String getTableName(){
    	return "mobile_trnroute";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_USERNAME, KEY_UNITCOMPANY_CODE, KEY_ORDERNO,
				KEY_CUSTOMER_CODE, KEY_CUSTOMER_NAME, KEY_CUSTOMER_ADDRESS, KEY_CUSTOMER_POSTCODE,
				KEY_CUSTOMER_SATELLITE, KEY_CUSTOMER_TYPE, KEY_CUSTOMER_TERMPAYMENT, KEY_CREDIT_LIMIT,
				KEY_RECEIVABLE, KEY_LASTBUYDATE, KEY_DESCR};
	}
    
	public TrnRoute() {
		
	}
	
	public TrnRoute(String id){
		super(id);
	}
	
	public TrnRoute(String id, Date trnDate, String username, String unitCompanyCode, long orderNo, String customerCode,
			String customerName, String customerAddress, String customerPostCode, String customerSatellite,
			String customerType, String customerTermpayment, long creditLimit, long receivable,
			Date lastBuyDate, String descr){
		this(id);
		
		mTrnDate			= (Date) trnDate.clone();
		mUsername			= username;
		mUnitCompanyCode	= unitCompanyCode;
		mOrderNo			= orderNo;
		mCustomerCode		= customerCode;
		mCustomerName		= customerName;
		mCustomerAddress	= customerAddress;
		mCustomerPostCode	= customerPostCode;
		mCustomerSatellite	= customerSatellite;
		mCustomerType		= customerType;
		mCustomerTermPayment= customerTermpayment;
		mCreditLimit		= creditLimit;
		mReceivable			= receivable;
		mLastBuyDate		= lastBuyDate;
		mDescr				= descr;
	}
	
	public ContentValues getContentValues(){
    	ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,					mID);								//0
    	retval.put(KEY_TRNDATE,					Utilities.formatDate(mTrnDate));	//1
    	retval.put(KEY_USERNAME,				mUsername);							//2
    	retval.put(KEY_UNITCOMPANY_CODE,		mUnitCompanyCode);					//3
    	retval.put(KEY_ORDERNO,					mOrderNo);							//4
    	retval.put(KEY_CUSTOMER_CODE,			mCustomerCode);						//5
    	retval.put(KEY_CUSTOMER_NAME,			mCustomerName);						//6
    	retval.put(KEY_CUSTOMER_ADDRESS,		mCustomerAddress);					//7
    	retval.put(KEY_CUSTOMER_POSTCODE,		mCustomerPostCode);					//8
    	retval.put(KEY_CUSTOMER_SATELLITE,		mCustomerSatellite);				//9
    	retval.put(KEY_CUSTOMER_TYPE,			mCustomerType);						//10
    	retval.put(KEY_CUSTOMER_TERMPAYMENT,	mCustomerTermPayment);				//11
    	retval.put(KEY_CREDIT_LIMIT,			mCreditLimit);						//12
    	retval.put(KEY_RECEIVABLE,				mReceivable);						//13
    	retval.put(KEY_LASTBUYDATE,				Utilities.formatDate(mLastBuyDate));//14
    	retval.put(KEY_DESCR,					mDescr);							//15
    	
    	return retval;
    }
	
	public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new TrnRoute[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new TrnRoute(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
    					cursor.getString(2), cursor.getString(3), cursor.getLong(4),
    					cursor.getString(5), cursor.getString(6), cursor.getString(7),
    					cursor.getString(8), cursor.getString(9), cursor.getString(10),
    					cursor.getString(11), cursor.getLong(12), cursor.getLong(13),
    					Utilities.formatStr(cursor.getString(14)), cursor.getString(15));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
    
	//PROPERTIES
	public Date		mTrnDate;
	public String	mUsername, mUnitCompanyCode;
	public long		mOrderNo;
	public String	mCustomerCode, mCustomerName, mCustomerAddress, mCustomerPostCode, mCustomerSatellite, mCustomerType, mCustomerTermPayment;
	public long		mCreditLimit, mReceivable;
	public Date		mLastBuyDate;
	public String	mDescr;
	
    //TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_TRNROUTE = 
		"CREATE TABLE mobile_trnroute (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate				DATETIME, "
		+ "username				VARCHAR(32), "
		+ "unitcompany_code		VARCHAR(32), "
		+ "orderno				NUMERIC(8,0) DEFAULT 0, "
		+ "customer_code		VARCHAR(32), "
		+ "customer_name		VARCHAR(128), "
		+ "customer_address		VARCHAR(128), "
		+ "customer_postcode	VARCHAR(16), "
		+ "customer_satellite 	VARCHAR(32), "
		+ "customer_type 		VARCHAR(32), "
		+ "customer_termpayment	VARCHAR(128), "
		+ "credit_limit 		NUMERIC(16,2) DEFAULT 0, "
		+ "receivable 			NUMERIC(16,2) DEFAULT 0, "
		+ "lastbuydate 			DATETIME, "
		+ "descr 				TEXT);";
}
