package dp.mobile.store.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import dp.mobile.store.helper.tables.DailyNews;
import dp.mobile.store.helper.tables.Model;
import dp.mobile.store.helper.tables.TrnRoute;

public class DatabaseAdapter {
	protected static final String	TAG					= "DatabaseAdapter";
	protected static final String	DATABASE_NAME		= "artindo";
	protected static final int		DATABASE_VERSION	= 2;
	
	private DatabaseAdapter(Context context){
		mContext = context;
		//Log.d(TAG, "CREATING DATABASE " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	public static DatabaseAdapter instance(Context context){
		if(mInstance == null)
			mInstance = new DatabaseAdapter(context);
		
		return mInstance;
	}
	
	public void open() throws SQLException{
		if(mDBHelper == null)
			mDBHelper = new DatabaseHelper(mContext);
		
		if(mDatabase == null)
			mDatabase = mDBHelper.getWritableDatabase();
		
		Log.d(TAG, "OPENING DATABASE " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	public void close(){
		if(mDBHelper != null){
			mDBHelper.close();
			Log.d(TAG, "CLOSING DATABASE SUCCESS : " + DATABASE_NAME + "version " + DATABASE_VERSION);
		} else
			Log.d(TAG, "CLOSING DATABASE FAILED : " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	public Cursor rawQuery(String selection, String[] selectionArgs){
		openDatabaseIfNecessary();
		Cursor retval = mDatabase.rawQuery(selection, selectionArgs);
		
		if(retval.moveToFirst()){
			for(String s : retval.getColumnNames()){
				Log.d(TAG, s + "#" + retval.getString(retval.getColumnIndex(s)));
			}
		} else {
			Log.d(TAG, "NO C");
		}
		
		retval.close();
		return retval;
	}
	
	/**
	 * Insert new record into <tableName>
	 * 
	 * @param tableName
	 * @param args
	 * @return
	 */
    public long insert(String tableName, Model model){
    	long retval = -1;
    	
    	openDatabaseIfNecessary();
		retval = mDatabase.insert(tableName, null, model.getContentValues());
		if(retval == -1){
			Log.d(TAG, "Error inserting to table " + tableName);
			Toast.makeText(mContext, "Error inserting to table " + tableName, 5000).show();
		} else {
			Log.d(TAG, "Successfully inserted to table " + tableName + " with row ID = " + retval);
		}
			
		return retval;
    }
    
    /**
     * Delete record with ID <rowID>
     * 
     * @param rowID
     * @return number of rows affected
     */
    public long delete(String tableName, long rowID){
    	openDatabaseIfNecessary();
    	return mDatabase.delete(tableName, "id=" + rowID, null);
    }
    
    /**
     * Return a Cursor over the list of all records in the table 
     * 
     * @return Cursor over all notes
     */
    public Model[] getAll(String tableName){
    	Model[] retval = null;
    	
    	openDatabaseIfNecessary();
    	Cursor cursor = mDatabase.query(tableName, getTableColumns(tableName), null, null, null, null, null);
    	
    	if(cursor != null){
    		if(tableName.equals(DailyNews.getTableName())){
    			retval = DailyNews.extract(cursor);
    		} else if(tableName.equals(TrnRoute.getTableName())){
    			retval = TrnRoute.extract(cursor);
    		}
    	}
    	
    	cursor.close();
    	return retval;
    }
    
    /**
     * Return a Cursor positioned at record matches the given rowID
     * 
     * @param rowID
     * @return Cursor positioned to matching record(s), if any
     * @throws SQLException
     */
    public Model get(String tableName, String rowID) throws SQLException{
    	Model retval = null;
    	
    	openDatabaseIfNecessary();
    	Cursor cursor = mDatabase.query(true, tableName, getTableColumns(tableName), null, null, null, null, null, rowID);
    	
    	if(cursor != null){
    		if(tableName.equals(DailyNews.getTableName())){
    			retval = DailyNews.extract(cursor)[0];
    		} else if(tableName.equals(TrnRoute.getTableName())){
    			retval = TrnRoute.extract(cursor)[0]; 
    		}
    	}
    	
    	cursor.close();
    	return retval;
    }
    
    private String[] getTableColumns(String tableName){
    	if(tableName.equals(DailyNews.getTableName())){
    		return DailyNews.getColumns();
    	} else if(tableName.equals(TrnRoute.getTableName())){
    		return TrnRoute.getColumns();
    	}
    	
    	return null;
    }
    
    private void openDatabaseIfNecessary(){
    	if(!mDatabase.isOpen())
    		mDatabase = mContext.openOrCreateDatabase("/data/data/dp.mobile.store/databases/" + DATABASE_NAME, SQLiteDatabase.OPEN_READWRITE, null);
    }
    
	//PROPERTIES
    private static DatabaseAdapter 	mInstance;
	private final Context 			mContext;
	private DatabaseHelper			mDBHelper;
	private SQLiteDatabase			mDatabase;
	
	protected static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			//Log.d(TAG, "CREATING DATABASE HELPER");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("DROP TABLE IF EXISTS " + DailyNews.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnRoute.getTableName());
			db.execSQL("DROP TABLE IF EXISTS mobile_pricelist");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnreceivable");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnsales");
			db.execSQL("DROP TABLE IF EXISTS mobile_dtlsales");
			
			db.execSQL(DailyNews.TABLE_CREATE_MOBILE_DAILYNEWS);
			db.execSQL(TrnRoute.TABLE_CREATE_MOBILE_TRNROUTE);
			db.execSQL(TABLE_CREATE_MOBILE_PRICELIST);
			db.execSQL(TABLE_CREATE_MOBILE_TRNRECEIVABLE);
			db.execSQL(TABLE_CREATE_MOBILE_TRNSALES);
			db.execSQL(TABLE_CREATE_MOBILE_DTLSALES);
			
			Log.d("DB HELPER", "DBHelper onCreate");
			//TODO: create the view ?
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS " + DailyNews.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnRoute.getTableName());
			db.execSQL("DROP TABLE IF EXISTS mobile_priceslist");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnreceivable");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnsales");
			db.execSQL("DROP TABLE IF EXISTS mobile_dtlsales");
			
			onCreate(db);
		}
	}
	
	protected static final String TABLE_CREATE_MOBILE_PRICELIST = 
		"CREATE TABLE mobile_pricelist (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "username 		VARCHAR(32), "
		+ "unitcompany_code	VARCHAR(32), "
		+ "customer_code	VARCHAR(32), "
		+ "product_code		VARCHAR(32), "
		+ "price			NUMERIC(16,2) DEFAULT 0, "
		+ "status			NUMERIC(2,0) DEFAULT 0);";
	
	protected static final String TABLE_CREATE_MOBILE_TRNRECEIVABLE = 
		"CREATE TABLE mobile_trnreceivable (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate			DATETIME, "
		+ "username			VARCHAR(32), "
		+ "unitcompany_code	VARCHAR(32), "
		+ "customer_code	VARCHAR(32), "
		+ "ar_no			VARCHAR(16), "
		+ "duedate			DATETIME, "
		+ "amount			NUMERIC(16,2) DEFAULT 0, "
		+ "descr			TEXT);";
	
	protected static final String TABLE_CREATE_MOBILE_TRNSALES = 
		"CREATE TABLE mobile_trnsales (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "unitcompany_code		VARCHAR(32), "
		+ "trncode 				CHAR(5) DEFAULT '', "
		+ "trndate				DATETIME, "
		+ "refno				CHAR(16) DEFAULT '', "
		+ "ar_no				CHAR(16) DEFAULT '', "
		+ "descr				VARCHAR(128), "
		+ "checkno				VARCHAR(64) DEFAULT '', "
		+ "customer_code		VARCHAR(32), "
		+ "customer_postcode	VARCHAR(32), "
		+ "customer_satellite	VARCHAR(32), "
		+ "amount 				NUMERIC(16,2) DEFAULT 0, "
		+ "status 				NUMERIC(2,0) DEFAULT 0)";
	
	protected static final String TABLE_CREATE_MOBILE_DTLSALES = 
		"CREATE TABLE mobile_dtlsales (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trnsales_id		CHAR(32) NOT NULL, "
		+ "product_code		VARCHAR(32) DEFAULT '', "
		+ "qty 				NUMERIC(16,2) DEFAULT 0, "
		+ "qty_debit 		NUMERIC(16,2) DEFAULT 0, "
		+ "qty_credit 		NUMERIC(16,2) DEFAULT 0, "
		+ "price 			NUMERIC(16,2) DEFAULT 0, "
		+ "amount 			NUMERIC(16,2) DEFAULT 0, "
		+ "amount_debit		NUMERIC(16,2) DEFAULT 0, "
		+ "amount_credit 	NUMERIC(16,2) DEFAULT 0, "
		+ "amount_cash 		NUMERIC(16,2) DEFAULT 0, "
		+ "status 			NUMERIC(2,0) DEFAULT 0);";
	
	protected static final String VIEW_CREATE_MOBVIEW_PRODUCT = 
		"CREATE VIEW mobview_product as "
		+ "SELECT product.code as product_code, "
		+ "product.name as product_name, "
		+ "product.name_short as product_name_short, "
		+ "grp.code as group_code, "
		+ "grp.name as group_name "
		+ "product_code		VARCHAR(32) DEFAULT '', "
		+ "FROM master_product product, master_product_group as grp"
		+ "WHERE product.product_group_id = grp.id and product.is_active = 1"
		+ "ORDER BY product_code";
	
	protected static final String VIEW_CREATE_MOBVIEW_USERACCESS = 
		"CREATE VIEW mobview_useraccess as "
		+ "SELECT user.id as user_id, "
		+ "user.username, "
		+ "user.userpassword, "
		+ "unitcompany.id as unitcompany_id, "
		+ "unitcompany.code as unitcompany_code, "
		+ "FROM admin_user user, "
			+ "admin_user_roleaccess roleuser, "
			+ "admin_roleaccess role, "
			+ "admin_role_unitcompany unit, "
			+ "admin_unitcompany as unitcompany "
		+ "WHERE user.id = roleuser.user_id"
			+ "and role.id = roleuser.roleaccess_id"
			+ "and role.id = unit.roleaccess_id"
			+ "and unit.unitcompany_id = unitcompany.id"
			+ "and role.application_id = 'MOBILE APPS'"
			+ "GROUP BY user.id, unit.unitcompany_id";
}
