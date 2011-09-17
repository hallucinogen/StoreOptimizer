package dp.mobile.store.helper.tables;

import java.util.Date;

import dp.mobile.store.helper.Utilities;

import android.content.ContentValues;
import android.database.Cursor;

public class Counter extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_IMEI				= "imei";
	public static final String KEY_CODE				= "code";
	public static final String KEY_ACTIVEDATE		= "activedate";
	public static final String KEY_COUNTER			= "counter";
	public static final String KEY_STATUS			= "status";
	
	public static String getTableName(){
    	return "mobile_counter";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_IMEI, KEY_CODE, KEY_ACTIVEDATE,
				KEY_COUNTER, KEY_STATUS};
	}
    
    public Counter() {
    	
	}
    
    public Counter(String id){
    	super(id);
    }
    
    public Counter(String id, String imei, String code, Date activeDate,
    		long counter, long status){
    	this(id);
    	
    	mImei			= imei;			//0
    	mCode			= code;			//1
    	mActiveDate		= activeDate;	//2
    	mCounter		= counter;		//3
    	mStatus			= status;		//4
    }

	@Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,		mID);								//0
    	retval.put(KEY_IMEI,		mImei);								//1
    	retval.put(KEY_CODE,		mCode);								//2
    	retval.put(KEY_ACTIVEDATE,	Utilities.formatDate(mActiveDate));	//3
    	retval.put(KEY_COUNTER,		mCounter);							//4
    	retval.put(KEY_STATUS,		mStatus);							//5
    	
    	return retval;
	}
	
	public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new Counter[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new Counter(cursor.getString(0), cursor.getString(1),
    					cursor.getString(2), Utilities.formatStr(cursor.getString(3)),
    					cursor.getLong(4), cursor.getLong(5));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
	
	//PROPERTIES
	public String	mImei, mCode;
	public Date		mActiveDate;
	public long		mCounter, mStatus;

	//TABLE CREATE Query
	public static final String TABLE_CREATE_MOBILE_COUNTER = 
	"CREATE TABLE mobile_counter (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
	+ "imei                   VARCHAR(32), "
	+ "code                   VARCHAR(16), "
	+ "activedate             DATETIME, "
	+ "counter                NUMERIC(6,0), "
	+ "status                 NUMERIC(2,0) DEFAULT 0); ";
}
