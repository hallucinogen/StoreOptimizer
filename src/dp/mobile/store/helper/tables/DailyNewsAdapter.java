package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import dp.mobile.store.helper.DatabaseAdapter;

public class DailyNewsAdapter extends DatabaseAdapter {
	public static final String DATABASE_TABLE	= "mobile_dailynews";
	
	public static final String KEY_ROWID		= "id";
	public static final String KEY_TRNDATE		= "trndate";
    public static final String KEY_NEWSFROM		= "newsfrom";
    public static final String KEY_DESCR_SHORT	= "descr_short";
    public static final String KEY_DESCR		= "descr";
    
    public DailyNewsAdapter(Context context) {
		super(context);
	}
    
    /**
     * Create a new daily news
     * 
     * @param id
     * @param date
     * @param newsfrom
     * @param descrShort
     * @param descr
     * @return rowID if success or -1 if failed
     */
    public long create(String id, String date, String newsfrom, String descrShort, String descr){
    	ContentValues args = new ContentValues();
    	
		args.put(KEY_ROWID, id);
		args.put(KEY_TRNDATE, date);
		args.put(KEY_NEWSFROM, newsfrom);
		args.put(KEY_DESCR_SHORT, descrShort);
		args.put(KEY_DESCR, descr);
    	
    	return mDatabase.insert(DATABASE_TABLE, null, args);
    }
    
    /**
     * Delete daily news with the given rowID
     * 
     * @param rowID
     * @return number of rows affected
     */
    public long delete(long rowID){
    	return mDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null);
    }
    
    /**
     * Return a Cursor over the list of all routes in the database 
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAll(){
    	return mDatabase.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR},
    							null, null, null, null, null);
    }
    
    /**
     * Return a Cursor positioned at the daily news matches the given rowID
     * 
     * @param rowID
     * @return Cursor positioned to matching daily news, if found
     * @throws SQLException
     */
    public Cursor fetch(long rowID) throws SQLException{
    	Cursor retval = mDatabase.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TRNDATE, KEY_NEWSFROM, KEY_DESCR_SHORT, KEY_DESCR},
    			KEY_ROWID + "=" + rowID, null, null, null, null, null);
    	if(retval != null)
    		retval.moveToFirst();

    	return retval;
    }
}
