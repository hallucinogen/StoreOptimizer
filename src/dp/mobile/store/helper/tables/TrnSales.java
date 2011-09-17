package dp.mobile.store.helper.tables;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
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
    public static final String KEY_CHECKDATE			= "checkdate";
    public static final String KEY_CUSTOMER_CODE		= "customer_code";
    public static final String KEY_CUSTOMER_POSTCODE	= "customer_postcode";
    public static final String KEY_CUSTOMER_SATELLITE	= "customer_satellite";
    public static final String KEY_AMOUNT				= "amount";
    public static final String KEY_AMOUNT_CASH			= "amount_cash";
    public static final String KEY_AMOUNT_CHECK			= "amount_check";
    public static final String KEY_STATUS				= "status";
    
    
    public static String getTableName(){
    	return "mobile_trnsales";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_UNITCOMPANY_CODE, KEY_TRNCODE, KEY_TRNDATE, KEY_REFNO,
				KEY_AR_NO, KEY_DESCR, KEY_CHECKNO, KEY_CHECKDATE, KEY_CUSTOMER_CODE, KEY_CUSTOMER_POSTCODE,
				KEY_CUSTOMER_SATELLITE, KEY_AMOUNT, KEY_AMOUNT_CASH, KEY_AMOUNT_CHECK, KEY_STATUS};
	}
    
    public TrnSales() {
    	
	}
    
    public TrnSales(String id){
    	super(id);
    }
    
    public TrnSales(String id, String unitCompanyCode, String trnCode, Date trnDate, String refNo, String arNo,
    		String descr, String checkNo, Date checkDate, String customerCode, String customerPostCode,
    		String customerSatellite, long amount, long amountCash, long amountCheck, long status){
    	this(id);
    	
    	mUnitCompanyCode	= unitCompanyCode;	//0
    	mTrnCode			= trnCode;			//1
    	mTrnDate			= trnDate;			//2
    	mRefNo				= refNo;			//3
    	mArNo				= arNo;				//4
    	mDesc				= descr;			//5
    	mCheckNo			= checkNo;			//6
    	mCheckDate			= checkDate;		//7
    	mCustomerCode		= customerCode;		//8
    	mCustomerPostCode	= customerPostCode;	//9
    	mCustomerSatellite	= customerSatellite;//10
    	mAmount				= amount;			//11
    	mAmountCash			= amountCash;		//12
    	mAmountCheck		= amountCheck;		//13
    	mStatus				= status;			//14
    }
    
    @Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,				mID);							//0
    	retval.put(KEY_UNITCOMPANY_CODE,	mUnitCompanyCode);				//1
    	retval.put(KEY_TRNCODE,				mTrnCode);						//3
    	retval.put(KEY_TRNDATE,				Utilities.formatDate(mTrnDate));//2
    	retval.put(KEY_REFNO,				mRefNo);						//4
    	retval.put(KEY_AR_NO,				mArNo);							//5
    	retval.put(KEY_DESCR,				mDesc);							//6
    	retval.put(KEY_CHECKNO,				mCheckNo);						//7
    	retval.put(KEY_CHECKDATE,			Utilities.formatDate(mCheckDate));//8
    	retval.put(KEY_CUSTOMER_CODE,		mCustomerCode);					//9
    	retval.put(KEY_CUSTOMER_POSTCODE,	mCustomerPostCode);				//10
    	retval.put(KEY_CUSTOMER_SATELLITE,	mCustomerSatellite);			//11
    	retval.put(KEY_AMOUNT,				mAmount);						//12
    	retval.put(KEY_AMOUNT_CASH,			mAmountCash);					//13
    	retval.put(KEY_AMOUNT_CHECK,		mAmountCheck);					//14
    	retval.put(KEY_STATUS,				mStatus);						//15
    	
    	return retval;
	}
    
    public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new TrnSales[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new TrnSales(cursor.getString(0), cursor.getString(1),
    					cursor.getString(2), Utilities.formatStr(cursor.getString(3)),
    					cursor.getString(4), cursor.getString(5), cursor.getString(6),
    					cursor.getString(7), Utilities.formatStr(cursor.getString(8)),
    					cursor.getString(9), cursor.getString(10), cursor.getString(11),
    					cursor.getLong(12), cursor.getLong(13), cursor.getLong(14),
    					cursor.getLong(15));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
    
    //ATTRIBUTES
    public Date		mTrnDate, mCheckDate;
    public String	mUnitCompanyCode, mTrnCode, mRefNo, mArNo, mDesc, mCheckNo, mCustomerCode, mCustomerPostCode, mCustomerSatellite;
    public long		mAmount, mAmountCash, mAmountCheck, mStatus;
    
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
		+ "checkdate			DATETIME, " //Tambah
		+ "customer_code		VARCHAR(32), "
		+ "customer_postcode	VARCHAR(32), "
		+ "customer_satellite	VARCHAR(32), "
		+ "amount 				NUMERIC(16,2) DEFAULT 0, "
		+ "amount_cash 			NUMERIC(16,2) DEFAULT 0, " //Tambah
		+ "amount_check 		NUMERIC(16,2) DEFAULT 0, " //Tambah
		+ "status 				NUMERIC(2,0) DEFAULT 0);";
}
