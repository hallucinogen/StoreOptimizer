package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.database.Cursor;

public class User extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_UNITCOMPANY_CODE	= "unitcompany_code";
	public static final String KEY_USERNAME			= "username";
    public static final String KEY_PASSWORD			= "password";
    public static final String KEY_DESCR			= "descr";
    public static final String KEY_STATUS			= "status";
	
    public static String getTableName(){
    	return "mobile_user";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_UNITCOMPANY_CODE, KEY_USERNAME, KEY_PASSWORD,
				KEY_DESCR, KEY_STATUS};
	}
    
    public User() {
    	
	}
    
    public User(String id){
    	super(id);
    }
    
    public User(String id, String unitCompanyCode, String username, String password,
    		String descr, long status){
    	this(id);
    	
    	mUnitCompanyCode	= unitCompanyCode;	//0
    	mUsername			= username;			//1
    	mPassword			= password;			//2
    	mDescr				= descr;			//3
    	mStatus				= status;			//4
    }
    
	@Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,			mID);				//0
    	retval.put(KEY_UNITCOMPANY_CODE,mUnitCompanyCode);	//1
    	retval.put(KEY_USERNAME,		mUsername);			//2
    	retval.put(KEY_PASSWORD,		mPassword);			//3
    	retval.put(KEY_DESCR,			mDescr);			//4
    	retval.put(KEY_STATUS,			mStatus);			//5
    	
    	return retval;
	}
	
	public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new User[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new User(cursor.getString(0), cursor.getString(1),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4),
    					cursor.getLong(5));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
	
	//PROPERTIES
	public String	mUnitCompanyCode, mUsername, mPassword, mDescr;
	public long		mStatus;

	//TABLE CREATE Query
	public static final String TABLE_CREATE_MOBILE_USER = 
		"CREATE TABLE mobile_user (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "unitcompany_code	VARCHAR(32), "
		+ "username 		VARCHAR(32), "
		+ "password			VARCHAR(32), "
		+ "descr			TEXT, "
		+ "status			NUMERIC(2,0) DEFAULT 0);";
}
