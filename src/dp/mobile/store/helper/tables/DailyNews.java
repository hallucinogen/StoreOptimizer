package dp.mobile.store.helper.tables;

import java.util.Date;

import dp.mobile.store.helper.Utilities;
import android.content.ContentValues;
import android.database.Cursor;

public class DailyNews extends Model {
	public static final String KEY_ROWID		= "id";
	public static final String KEY_TRNDATE		= "trndate";
    public static final String KEY_NEWSFROM		= "newsfrom";
    public static final String KEY_DESCR_SHORT	= "descr_short";
    public static final String KEY_DESCR		= "descr";
    
    public static String getTableName(){
    	return "mobile_dailynews";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR};
	}
    
    public DailyNews() {
	
	}
    
    public DailyNews(String id){
    	super(id);
    }
    
    public DailyNews(String id, Date trnDate, String newsFrom, String descrShort, String descr){
    	this(id);
    	
    	mTrnDate	= (Date) trnDate.clone();
    	mNewsFrom	= newsFrom;
    	mDescShort	= descrShort;
    	mDesc		= descr;
    }
    
    public ContentValues getContentValues(){
    	ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,		mID);
    	retval.put(KEY_TRNDATE,		Utilities.formatDate(mTrnDate));
    	retval.put(KEY_NEWSFROM,	mNewsFrom);
    	retval.put(KEY_DESCR_SHORT,	mDescShort);
    	retval.put(KEY_DESCR,		mDesc);
    	
    	return retval;
    }
    
    public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new DailyNews[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new DailyNews(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
    					cursor.getString(2), cursor.getString(3), cursor.getString(4));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
    
    //PROPERTIES
    public Date		mTrnDate;
    public String	mNewsFrom, mDescShort, mDesc;
    
    //TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_DAILYNEWS = 
		"CREATE TABLE mobile_dailynews (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate		DATETIME, "
		+ "newsfrom 	VARCHAR(32), "
		+ "descr_short	VARCHAR(128), "
		+ "descr		TEXT);";
}
