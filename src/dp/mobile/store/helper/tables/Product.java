package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.database.Cursor;

public class Product extends Model {
	public static final String KEY_ROWID				= "id";
    public static final String KEY_PRODUCT_CODE			= "product_code";
    public static final String KEY_PRODUCT_NAME			= "product_name";
    public static final String KEY_PRODUCT_NAME_SHORT	= "product_name_short";
    public static final String KEY_GROUP_CODE			= "group_code";
    public static final String KEY_GROUP_NAME			= "group_name";
    public static final String KEY_STATUS				= "status";

    public static String getTableName(){
    	return "mobile_product";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_PRODUCT_CODE, KEY_PRODUCT_NAME, KEY_PRODUCT_NAME_SHORT,
				KEY_GROUP_CODE, KEY_GROUP_NAME, KEY_STATUS};
	}
    
    public Product() {
    	
	}
    
    public Product(String id){
    	super(id);
    }
    
    public Product(String id, String productCode, String productName, String productNameShort,
    		String groupCode, String groupName, long status){
    	this(id);
    	
    	mProductCode		= productCode;		//0
    	mProductName		= productName;		//1
    	mProductNameShort	= productNameShort;	//2
    	mGroupCode			= groupCode;		//3
    	mGroupName			= groupName;		//4
    	mStatus				= status;			//5
    }
    
	@Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,				mID);				//0
    	retval.put(KEY_PRODUCT_CODE,		mProductCode);		//1
    	retval.put(KEY_PRODUCT_NAME,		mProductName);		//2
    	retval.put(KEY_PRODUCT_NAME_SHORT,	mProductNameShort);	//3
    	retval.put(KEY_GROUP_CODE,			mGroupCode);		//4
    	retval.put(KEY_GROUP_NAME,			mGroupName);		//5
    	retval.put(KEY_STATUS,				mStatus);			//6
    	
    	return retval;
	}
	
	public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new Product[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new Product(cursor.getString(0), cursor.getString(1),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4),
    					cursor.getString(5), cursor.getLong(6));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }

	//PROPERTIES
	public String	mProductCode, mProductName, mProductNameShort, mGroupCode, mGroupName;
	public long		mStatus;
	
	//TABLE CREATE Query
	public static final String TABLE_CREATE_MOBILE_PRODUCT = 
		"CREATE TABLE mobile_product (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
       + "product_code           VARCHAR(32) NOT NULL, "
       + "product_name           VARCHAR(128) DEFAULT '', "
       + "product_name_short     VARCHAR(32) DEFAULT '', "
       + "group_code             VARCHAR(32) NOT NULL, "
       + "group_name             VARCHAR(128) DEFAULT '', "
       + "status                 NUMERIC(2,0) DEFAULT 0);";
}
