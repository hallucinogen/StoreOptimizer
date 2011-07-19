package dp.mobile.store.helper.tables;

import java.util.Date;

import dp.mobile.store.helper.Utilities;

import android.content.ContentValues;

public class TrnReceivable extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_TRNDATE			= "trndate";
	public static final String KEY_USERNAME			= "username";
    public static final String KEY_UNITCOMPANY_CODE	= "unitcompany_code";
    public static final String KEY_CUSTOMER_CODE	= "customer_code";
    public static final String KEY_AR_NO			= "ar_no";
    public static final String KEY_DUE_DATE			= "duedate";
    public static final String KEY_AMOUNT			= "amount";
    public static final String KEY_DESCR			= "descr";
    
    public static String getTableName(){
    	return "mobile_trnreceivable";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_USERNAME, KEY_UNITCOMPANY_CODE, KEY_CUSTOMER_CODE,
				KEY_AR_NO, KEY_DUE_DATE, KEY_AMOUNT, KEY_DESCR};
	}
    
    public TrnReceivable() {
    	
	}
    
    public TrnReceivable(String id){
    	super(id);
    }
    
    public TrnReceivable(String id, Date trnDate, String username, String unitCompanyCode,
    		String customerCode, String arNo, Date dueDate, long amount, String descr){
    	this(id);
    	
    	mTrnDate			= trnDate;
    	mUsername			= username;
    	mUnitCompanyCode	= unitCompanyCode;
    	mCustomerCode		= customerCode;
    	mArNo				= arNo;
    	mDueDate			= dueDate;
    	mAmount				= amount;
    	mDescr				= descr;
    }
	
	@Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,			mID);							//0
    	retval.put(KEY_TRNDATE,			Utilities.formatDate(mTrnDate));//1
    	retval.put(KEY_USERNAME,		mUsername);						//2
    	retval.put(KEY_UNITCOMPANY_CODE,mUnitCompanyCode);				//3
    	retval.put(KEY_CUSTOMER_CODE,	mCustomerCode);					//4
    	retval.put(KEY_AR_NO,			mArNo);							//5
    	retval.put(KEY_DUE_DATE,		Utilities.formatDate(mDueDate));//6
    	retval.put(KEY_AMOUNT,			mAmount);						//7
    	retval.put(KEY_DESCR,			mDescr);						//8
    	
    	return retval;
	}
	
	//PROPERTIES
	public Date		mTrnDate, mDueDate;
	public String	mUsername, mUnitCompanyCode, mCustomerCode, mArNo, mDescr;
	public long		mAmount;
	
	//TABLE CREATE Query
	public static final String TABLE_CREATE_MOBILE_TRNRECEIVABLE = 
		"CREATE TABLE mobile_trnreceivable (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate			DATETIME, "
		+ "username			VARCHAR(32), "
		+ "unitcompany_code	VARCHAR(32), "
		+ "customer_code	VARCHAR(32), "
		+ "ar_no			VARCHAR(16), "
		+ "duedate			DATETIME, "
		+ "amount			NUMERIC(16,2) DEFAULT 0, "
		+ "descr			TEXT);";
}
