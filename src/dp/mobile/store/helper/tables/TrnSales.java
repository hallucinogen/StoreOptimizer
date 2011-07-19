package dp.mobile.store.helper.tables;

import java.util.Date;

import android.content.ContentValues;
import dp.mobile.store.helper.Utilities;

public class TrnSales extends Model {
	public static final String KEY_ROWID				= "id";
	public static final String KEY_UNITCOMPANY_CODE		= "unitcompany_code";
	public static final String KEY_TRNCODE				= "trncode";
    public static final String KEY_TRNDATE				= "trndate";
    public static final String KEY_REFNO				= "refno";
    public static final String KEY_AR_NO				= "ar_no";
    public static final String KEY_DESCR				= "descr";
    public static final String KEY_CHECKNO				= "checkno";
    public static final String KEY_CUSTOMER_CODE		= "customer_code";
    public static final String KEY_CUSTOMER_POSTCODE	= "customer_postcode";
    public static final String KEY_CUSTOMER_SATELLITE	= "customer_satellite";
    public static final String KEY_AMOUNT				= "amount";
    public static final String KEY_STATUS				= "status";
    
    
    public static String getTableName(){
    	return "mobile_trnsales";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_UNITCOMPANY_CODE, KEY_TRNCODE, KEY_TRNDATE, KEY_REFNO,
				KEY_AR_NO, KEY_DESCR, KEY_CHECKNO, KEY_CUSTOMER_CODE, KEY_CUSTOMER_POSTCODE,
				KEY_CUSTOMER_SATELLITE, KEY_AMOUNT, KEY_STATUS};
	}
    
    public TrnSales() {
    	
	}
    
    public TrnSales(String id){
    	super(id);
    }
    
    public TrnSales(String id, String unitCompanyCode, String trnCode, Date trnDate, String refNo, String arNo,
    		String descr, String checkNo, String customerCode, String customerPostCode,
    		String customerSatellite, long amount, long status){
    	this(id);
    	
    	mUnitCompanyCode	= unitCompanyCode;	//0
    	mTrnDate			= trnDate;			//1
    	mTrnCode			= trnCode;			//2
    	mRefNo				= refNo;			//3
    	mArNo				= arNo;				//4
    	mDesc				= descr;			//5
    	mCheckNo			= checkNo;			//6
    	mCustomerCode		= customerCode;		//7
    	mCustomerPostCode	= customerPostCode;	//8
    	mCustomerSatellite	= customerSatellite;//9
    	mAmount				= amount;			//10
    	mStatus				= status;			//11
    }
    
    @Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,				mID);							//0
    	retval.put(KEY_UNITCOMPANY_CODE,	mUnitCompanyCode);				//1
    	retval.put(KEY_TRNCODE,				mTrnCode);						//2
    	retval.put(KEY_TRNDATE,				Utilities.formatDate(mTrnDate));//3
    	retval.put(KEY_REFNO,				mRefNo);						//4
    	retval.put(KEY_AR_NO,				mArNo);							//5
    	retval.put(KEY_DESCR,				mDesc);							//6
    	retval.put(KEY_CHECKNO,				mCheckNo);						//7
    	retval.put(KEY_CUSTOMER_CODE,		mCustomerCode);					//8
    	retval.put(KEY_CUSTOMER_POSTCODE,	mCustomerPostCode);				//9
    	retval.put(KEY_CUSTOMER_SATELLITE,	mCustomerSatellite);			//10
    	retval.put(KEY_AMOUNT,				mAmount);						//11
    	retval.put(KEY_STATUS,				mStatus);						//12
    	
    	return retval;
	}
    
    //ATTRIBUTES
    public Date		mTrnDate;
    public String	mUnitCompanyCode, mTrnCode, mRefNo, mArNo, mDesc, mCheckNo, mCustomerCode, mCustomerPostCode, mCustomerSatellite;
    public long		mAmount, mStatus;
    
  //TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_TRNSALES = 
		"CREATE TABLE mobile_trnsales (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "unitcompany_code		VARCHAR(32), "
		+ "trncode 				CHAR(5) DEFAULT '', "
		+ "trndate				DATETIME, "
		+ "refno				CHAR(16) DEFAULT '', "
		+ "ar_no				CHAR(16) DEFAULT '', "
		+ "descr				VARCHAR(128), "
		+ "checkno				VARCHAR(64) DEFAULT '', "
		+ "customer_code		VARCHAR(32), "
		+ "customer_postcode	VARCHAR(32), "
		+ "customer_satellite	VARCHAR(32), "
		+ "amount 				NUMERIC(16,2) DEFAULT 0, "
		+ "status 				NUMERIC(2,0) DEFAULT 0)";
}
