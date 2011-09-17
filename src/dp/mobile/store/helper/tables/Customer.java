package dp.mobile.store.helper.tables;

import java.util.Date;

import dp.mobile.store.helper.Utilities;

import android.content.ContentValues;
import android.database.Cursor;

public class Customer extends Model {
	public static final String KEY_ROWID				= "id";
	public static final String KEY_TRNDATE				= "trndate";
    public static final String KEY_USERNAME				= "username";
    public static final String KEY_CUSTOMER_CODE		= "customer_code";
    public static final String KEY_CUSTOMER_NAME		= "customer_name";
    public static final String KEY_ADDRESS				= "address";
    public static final String KEY_POSTCODE_CODE		= "postcode_code";
    public static final String KEY_CITY					= "city";
    public static final String KEY_COUNTRY				= "country";
    public static final String KEY_CONTACT				= "contact";
    public static final String KEY_CUSTOMER_GROUP_CODE	= "customer_group_code";
    public static final String KEY_CUSTOMER_GROUP_NAME	= "customer_group_name";
    public static final String KEY_CUSTOMER_CATEGORY_CODE	= "customer_category_code";
    public static final String KEY_CUSTOMER_CATEGORY_NAME	= "customer_category_name";
    public static final String KEY_CUSTOMER_TYPE_CODE	= "customer_type_code";
    public static final String KEY_CUSTOMER_TYPE_NAME	= "customer_type_name";
    public static final String KEY_CREDIT_LIMIT			= "credit_limit";
    public static final String KEY_TERMCREDIT			= "termcredit";
    public static final String KEY_UNITCOMPANY_ID		= "unitcompany_id";
    public static final String KEY_PRICELIST_GROUP_CODE	= "pricelist_group_code";
    public static final String KEY_PRICELIST_GROUP_NAME	= "pricelist_group_name";

    public static String getTableName(){
    	return "mobile_customer";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_USERNAME, KEY_CUSTOMER_CODE, KEY_CUSTOMER_NAME,
				KEY_ADDRESS, KEY_POSTCODE_CODE, KEY_CITY, KEY_COUNTRY, KEY_CONTACT,
				KEY_CUSTOMER_GROUP_CODE, KEY_CUSTOMER_GROUP_NAME, KEY_CUSTOMER_TYPE_CODE,
				KEY_CUSTOMER_TYPE_NAME, KEY_CREDIT_LIMIT, KEY_TERMCREDIT, KEY_UNITCOMPANY_ID,
				KEY_PRICELIST_GROUP_CODE, KEY_PRICELIST_GROUP_NAME};
	}
    
    public Customer() {
		
	}
	
	public Customer(String id){
		super(id);
	}
	
	public Customer(String id, Date trnDate, String username, String customerCode, String customerName,
			String address, String postCodeCode, String city, String country, String contact,
			String customerGroupCode, String customerGroupName, String customerCategoryCode,
			String customerCategoryName, String customerTypeCode, String customerTypeName,
			long creditLimit, long termCredit, String unitCompanyID, String pricelistGroupCode,
			String pricelistGroupName){
		this(id);
		
		mTrnDate				= trnDate;			//0
		mUsername				= username;			//1
		mCustomerCode			= customerCode;		//2
		mCustomerName			= customerName;		//3
		mAddress				= address;			//4
		mPostCodeCode			= postCodeCode;		//5
		mCity					= city;				//6
		mCountry				= country;			//7
		mContact 				= contact;			//8
		mCustomerGroupCode 		= customerGroupCode;//9
		mCustomerGroupName		= customerGroupName;//10
		mCustomerCategoryCode	= customerCategoryCode;//11
		mCustomerCategoryName 	= customerCategoryName;//12
		mCustomerTypeCode 		= customerTypeCode;	//13
		mCustomerTypeName 		= customerTypeName;	//14
		mCreditLimit			= creditLimit;		//15
		mTermCredit				= termCredit;		//16
		mUnitCompanyID			= unitCompanyID;	//17
		mPricelistGroupCode		= pricelistGroupCode;//18
		mPricelistGroupName		= pricelistGroupName;//19
	}
    
	@Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,					mID);								//0
    	retval.put(KEY_TRNDATE,					Utilities.formatDate(mTrnDate));	//1								//0
    	retval.put(KEY_USERNAME,				mUsername);							//2
    	retval.put(KEY_CUSTOMER_CODE,			mCustomerCode);						//3
    	retval.put(KEY_CUSTOMER_NAME,			mCustomerName);						//4
    	retval.put(KEY_ADDRESS,					mAddress);							//5
    	retval.put(KEY_POSTCODE_CODE,			mPostCodeCode);						//6
    	retval.put(KEY_CITY,					mCity);								//7
    	retval.put(KEY_COUNTRY,					mCountry);							//8
    	retval.put(KEY_CONTACT,					mContact);							//9
    	retval.put(KEY_CUSTOMER_GROUP_CODE,		mCustomerGroupCode);				//10
    	retval.put(KEY_CUSTOMER_GROUP_NAME,		mCustomerGroupName);				//11
    	retval.put(KEY_CUSTOMER_CATEGORY_CODE,	mCustomerCategoryCode);				//12
    	retval.put(KEY_CUSTOMER_CATEGORY_NAME,	mCustomerCategoryName);				//13
    	retval.put(KEY_CUSTOMER_TYPE_CODE,		mCustomerTypeCode);					//14
    	retval.put(KEY_CUSTOMER_TYPE_NAME,		mCustomerTypeName);					//15
    	retval.put(KEY_CREDIT_LIMIT,			mCreditLimit);						//16
    	retval.put(KEY_TERMCREDIT,				mTermCredit);						//17
    	retval.put(KEY_UNITCOMPANY_ID,			mUnitCompanyID);					//18
    	retval.put(KEY_PRICELIST_GROUP_CODE,	mPricelistGroupCode);				//19
    	retval.put(KEY_PRICELIST_GROUP_NAME,	mPricelistGroupName);				//20
    	
    	return retval;
	}
	
	public static Model[] extract(Cursor cursor){
		int i = 0;
		Model[] retval = new Customer[cursor.getCount()];

		if(cursor.moveToFirst()){
    		do{
    			retval[i++] = new Customer(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4),
    					cursor.getString(5), cursor.getString(6), cursor.getString(7),
    					cursor.getString(8), cursor.getString(9), cursor.getString(10),
    					cursor.getString(11), cursor.getString(12), cursor.getString(13),
    					cursor.getString(14), cursor.getString(15), cursor.getLong(16),
    					cursor.getLong(17), cursor.getString(18), cursor.getString(19),
    					cursor.getString(20));
    		}while(cursor.moveToNext());
    		
    		return retval;
		} else
			return null;
    }
	
	//PROPERTIES
	public Date		mTrnDate;
	public String	mUsername, mCustomerCode, mCustomerName, mAddress, mPostCodeCode, mCity, mCountry, mContact;
	public String	mCustomerGroupCode, mCustomerGroupName, mCustomerCategoryCode, mCustomerCategoryName;
	public String	mCustomerTypeCode, mCustomerTypeName, mUnitCompanyID;
	public String	mPricelistGroupCode, mPricelistGroupName;
	public long		mCreditLimit, mTermCredit;

	//TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_CUSTOMER = 
		"CREATE TABLE mobile_customer (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
        + "trndate                DATETIME, "
        + "username               VARCHAR(32), "
        + "customer_code          VARCHAR(64) DEFAULT '', "
        + "customer_name          VARCHAR(128) DEFAULT '', "
        + "address                VARCHAR(128) DEFAULT '', " 
        + "postcode_code          CHAR(32) DEFAULT NULL, "
        + "city                   VARCHAR(64) DEFAULT '', " 
        + "country                VARCHAR(64) DEFAULT '', " 
        + "contact                VARCHAR(64) DEFAULT '', " 
        + "customer_group_code    VARCHAR(32) DEFAULT '', "
        + "customer_group_name    VARCHAR(128) DEFAULT '', "
        + "customer_category_code VARCHAR(32) DEFAULT '', "
        + "customer_category_name VARCHAR(128) DEFAULT '', "
        + "customer_type_code     VARCHAR(32) DEFAULT '', "
        + "customer_type_name     VARCHAR(128) DEFAULT '', "
        + "credit_limit           NUMERIC(16,2) DEFAULT 0, " 
        + "termcredit             NUMERIC(3) DEFAULT 0, "
        + "unitcompany_id         CHAR(32) DEFAULT NULL, "
        + "pricelist_group_code   VARCHAR(32) DEFAULT '', "
        + "pricelist_group_name   VARCHAR(128) DEFAULT '');";
}
