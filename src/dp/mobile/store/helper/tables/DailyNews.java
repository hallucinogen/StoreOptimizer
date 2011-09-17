package dp.mobile.store.helper.tables;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import dp.mobile.store.helper.Utilities;

public class DailyNews extends Model {
	public static final String KEY_ROWID		= "id";
	public static final String KEY_TRNDATE		= "trndate";
    public static final String KEY_NEWSFROM		= "newsfrom";
    public static final String KEY_DESCR_SHORT	= "descr_short";
    public static final String KEY_DESCR		= "descr";
    public static final String KEY_STATUS		= "status";
    
    public static String getTableName(){
    	return "mobile_dailynews";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR, KEY_STATUS};
	}
    
    public DailyNews() {
	
	}
    
    public DailyNews(String id){
    	super(id);
    }
    
    public DailyNews(String id, Date trnDate, String newsFrom, String descrShort, String descr, long status){
    	this(id);
    	
    	mTrnDate	= (Date) trnDate.clone();	//0
    	mNewsFrom	= newsFrom;					//1
    	mDescShort	= descrShort;				//2
    	mDesc		= descr;					//3
    	mStatus		= status;					//4
    }
    
    public ContentValues getContentValues(){
    	ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,		mID);								//0
    	retval.put(KEY_TRNDATE,		Utilities.formatDate(mTrnDate));	//1
    	retval.put(KEY_NEWSFROM,	mNewsFrom);							//2
    	retval.put(KEY_DESCR_SHORT,	mDescShort);						//3
    	retval.put(KEY_DESCR,		mDesc);								//4
    	retval.put(KEY_STATUS,		mStatus);							//5
    	
    	return retval;
    }
    
    public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new DailyNews[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new DailyNews(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getLong(5));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
    
    //PROPERTIES
    public Date		mTrnDate;
    public String	mNewsFrom, mDescShort, mDesc;
    public long		mStatus;
    
    //TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_DAILYNEWS = 
		"CREATE TABLE mobile_dailynews (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate		DATETIME, "
		+ "newsfrom 	VARCHAR(32), "
		+ "descr_short	VARCHAR(128), "
		+ "descr		TEXT, "
		+ "status		NUMERIC(2,0) DEFAULT 0);";
}
