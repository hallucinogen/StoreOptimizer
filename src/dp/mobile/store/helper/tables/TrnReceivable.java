package dp.mobile.store.helper.tables;

import java.util.Date;

import dp.mobile.store.helper.Utilities;

import android.content.ContentValues;
import android.database.Cursor;

public class TrnReceivable extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_TRNDATE			= "trndate";
	public static final String KEY_USERNAME			= "username";
    public static final String KEY_UNITCOMPANY_CODE	= "unitcompany_code";
    public static final String KEY_CUSTOMER_CODE	= "customer_code";
    public static final String KEY_AR_NO			= "ar_no";
    public static final String KEY_DUE_DATE			= "duedate";
    public static final String KEY_AMOUNT			= "amount";
    public static final String KEY_PAYMENT			= "payment";
    public static final String KEY_DESCR			= "descr";
    
    public static String getTableName(){
    	return "mobile_trnreceivable";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_USERNAME, KEY_UNITCOMPANY_CODE, KEY_CUSTOMER_CODE,
				KEY_AR_NO, KEY_DUE_DATE, KEY_AMOUNT, KEY_PAYMENT, KEY_DESCR};
	}
    
    public TrnReceivable() {
    	
	}
    
    public TrnReceivable(String id){
    	super(id);
    }
    
    public TrnReceivable(String id, Date trnDate, String username, String unitCompanyCode,
    		String customerCode, String arNo, Date dueDate, long amount, long payment, String descr){
    	this(id);
    	
    	mTrnDate			= trnDate;			//0
    	mUsername			= username;			//1
    	mUnitCompanyCode	= unitCompanyCode;	//2
    	mCustomerCode		= customerCode;		//3
    	mArNo				= arNo;				//4
    	mDueDate			= dueDate;			//5
    	mAmount				= amount;			//6
    	mPayment			= payment;			//7
    	mDescr				= descr;			//8
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
    	retval.put(KEY_PAYMENT,			mPayment);						//8
    	retval.put(KEY_DESCR,			mDescr);						//9
    	
    	return retval;
	}
	
	public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new TrnReceivable[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new TrnReceivable(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
    					Utilities.formatStr(cursor.getString(6)), cursor.getLong(7), cursor.getLong(8),
    					cursor.getString(9));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
	
	//PROPERTIES
	public Date		mTrnDate, mDueDate;
	public String	mUsername, mUnitCompanyCode, mCustomerCode, mArNo, mDescr;
	public long		mAmount, mPayment;
	
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
		+ "payment			NUMERIC(16,2) DEFAULT 0, "
		+ "descr			TEXT);";
}
