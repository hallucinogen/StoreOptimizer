package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.database.Cursor;

public class PriceList extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_USERNAME			= "username";
    public static final String KEY_UNITCOMPANY_CODE	= "unitcompany_code";
    public static final String KEY_CUSTOMER_CODE	= "customer_code";
    public static final String KEY_PRODUCT_CODE		= "product_code";
    public static final String KEY_PRICE			= "price";
    public static final String KEY_STATUS			= "status";
    
    public static String getTableName(){
    	return "mobile_pricelist";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_USERNAME, KEY_UNITCOMPANY_CODE, KEY_CUSTOMER_CODE, KEY_PRODUCT_CODE,
				KEY_PRICE, KEY_STATUS};
	}
    
    public PriceList() {
    	
	}
    
    public PriceList(String id){
    	super(id);
    }
    
    public PriceList(String id, String username, String unitCompanyCode, String customerCode, String productCode, long price, long status){
    	this(id);
    	
    	mUsername			= username;
    	mUnitCompanyCode	= unitCompanyCode;
    	mCustomerCode		= customerCode;
    	mProductCode		= productCode;
    	mPrice				= price;
    	mStatus				= status;
    }
    
    @Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,			mID);				//0
    	retval.put(KEY_USERNAME,		mUsername);			//1
    	retval.put(KEY_UNITCOMPANY_CODE,mUnitCompanyCode);	//2
    	retval.put(KEY_CUSTOMER_CODE,	mCustomerCode);		//3
    	retval.put(KEY_PRODUCT_CODE,	mProductCode);		//4
    	retval.put(KEY_PRICE,			mPrice);			//5
    	retval.put(KEY_STATUS,			mStatus);			//6
    	
    	return retval;
	}
    
    public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new PriceList[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new PriceList(cursor.getString(0), cursor.getString(1),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getLong(5), cursor.getLong(6));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }

	//PROPERTIES
	public String	mUsername, mUnitCompanyCode, mCustomerCode, mProductCode;
	public long		mPrice, mStatus;
	
	//TABLE CREATE Query
	public static final String TABLE_CREATE_MOBILE_PRICELIST = 
		"CREATE TABLE mobile_pricelist (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "username 		VARCHAR(32), "
		+ "unitcompany_code	VARCHAR(32), "
		+ "customer_code	VARCHAR(32), "
		+ "product_code		VARCHAR(32), "
		+ "price			NUMERIC(16,2) DEFAULT 0, "
		+ "status			NUMERIC(2,0) DEFAULT 0);";
}
