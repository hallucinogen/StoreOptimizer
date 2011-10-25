package dp.mobile.store.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import dp.mobile.store.helper.tables.Counter;
import dp.mobile.store.helper.tables.Customer;
import dp.mobile.store.helper.tables.DailyNews;
import dp.mobile.store.helper.tables.DtlSales;
import dp.mobile.store.helper.tables.Model;
import dp.mobile.store.helper.tables.PriceList;
import dp.mobile.store.helper.tables.Product;
import dp.mobile.store.helper.tables.TrnReceivable;
import dp.mobile.store.helper.tables.TrnRoute;
import dp.mobile.store.helper.tables.TrnSales;
import dp.mobile.store.helper.tables.User;

public class DatabaseAdapter {
	protected static final String	TAG					= "DatabaseAdapter";
	protected static final String	DATABASE_NAME		= "artindo.db";
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
			mDatabase.close();
			Log.d(TAG, "CLOSING DATABASE SUCCESS : " + DATABASE_NAME + "version " + DATABASE_VERSION);
		} else
			Log.d(TAG, "CLOSING DATABASE FAILED : " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	public Cursor rawQuery(String selection, String[] selectionArgs){
		openDatabaseIfNecessary();
		if(selection != null)
			Log.i("DATABASE", "selection = " + selection);
		
		if(selectionArgs != null){
			for(int i=0; i<selectionArgs.length; ++i){
				Log.i("DATABASE", selectionArgs[i]);
			}
		}
		
		Cursor retval = mDatabase.rawQuery(selection, selectionArgs);
		
		/*if(retval.moveToFirst()){
			for(String s : retval.getColumnNames()){
				Log.d(TAG, s + "#" + retval.getString(retval.getColumnIndex(s)));
			}
		} else {
			Log.d(TAG, "NO C");
		}*/
		
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
			//Toast.makeText(mContext, "Error inserting to table " + tableName, 5000).show();
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
    public Model[] getAll(String tableName, String groupBy, String orderBy){
    	Model[] retval = null;
    	
    	openDatabaseIfNecessary();
    	Cursor cursor = mDatabase.query(tableName, getTableColumns(tableName), null, null, groupBy, null, orderBy);
    	
    	if(cursor != null){
    		if(tableName.equals(Counter.getTableName()))			retval = Counter.extract(cursor);
    		else if(tableName.equals(Customer.getTableName()))		retval = Customer.extract(cursor);
    		else if(tableName.equals(DailyNews.getTableName()))		retval = DailyNews.extract(cursor);
    		else if(tableName.equals(DtlSales.getTableName())) 		retval = DtlSales.extract(cursor);
    		else if(tableName.equals(PriceList.getTableName()))		retval = PriceList.extract(cursor);
    		else if(tableName.equals(Product.getTableName()))		retval = Product.extract(cursor);
    		else if(tableName.equals(TrnReceivable.getTableName()))	retval = TrnReceivable.extract(cursor);
    		else if(tableName.equals(TrnRoute.getTableName()))		retval = TrnRoute.extract(cursor);
    		else if(tableName.equals(TrnSales.getTableName())) 		retval = TrnSales.extract(cursor);
    		else if(tableName.equals(User.getTableName()))			retval = User.extract(cursor);
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
    	Cursor cursor = mDatabase.query(true, tableName, getTableColumns(tableName), "id=?", new String[]{rowID}, null, null, null, null);
    	
    	Log.d(TAG, "cursor = " + cursor.getCount());
    	
    	if(cursor != null){
    		if(tableName.equals(DailyNews.getTableName()))			retval = DailyNews.extract(cursor)[0];
    		else if(tableName.equals(TrnRoute.getTableName()))		retval = TrnRoute.extract(cursor)[0];
    		else if(tableName.equals(PriceList.getTableName()))		retval = PriceList.extract(cursor)[0];
    		else if(tableName.equals(TrnReceivable.getTableName()))	retval = TrnReceivable.extract(cursor)[0];
    		else if(tableName.equals(TrnSales.getTableName())) 		retval = TrnSales.extract(cursor)[0];
    		else if(tableName.equals(DtlSales.getTableName())) 		retval = DtlSales.extract(cursor)[0];
    	}
    	
    	cursor.close();
    	return retval;
    }
    
    private String[] getTableColumns(String tableName){
    	if(tableName.equals(DailyNews.getTableName()))			return DailyNews.getColumns();
    	else if(tableName.equals(TrnRoute.getTableName()))		return TrnRoute.getColumns();
    	else if(tableName.equals(PriceList.getTableName()))		return PriceList.getColumns();
		else if(tableName.equals(TrnReceivable.getTableName()))	return TrnReceivable.getColumns();
		else if(tableName.equals(TrnSales.getTableName())) 		return TrnSales.getColumns();
		else if(tableName.equals(DtlSales.getTableName())) 		return DtlSales.getColumns();
    	
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
			db.execSQL("DROP TABLE IF EXISTS " + Counter.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + Customer.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + DailyNews.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + DtlSales.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + PriceList.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + Product.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnReceivable.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnRoute.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnSales.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + User.getTableName());
			
			db.execSQL(Counter.TABLE_CREATE_MOBILE_COUNTER);
			db.execSQL(Customer.TABLE_CREATE_MOBILE_CUSTOMER);
			db.execSQL(DailyNews.TABLE_CREATE_MOBILE_DAILYNEWS);
			db.execSQL(DtlSales.TABLE_CREATE_MOBILE_DTLSALES);
			db.execSQL(PriceList.TABLE_CREATE_MOBILE_PRICELIST);
			db.execSQL(Product.TABLE_CREATE_MOBILE_PRODUCT);
			db.execSQL(TrnReceivable.TABLE_CREATE_MOBILE_TRNRECEIVABLE);
			db.execSQL(TrnRoute.TABLE_CREATE_MOBILE_TRNROUTE);
			db.execSQL(TrnSales.TABLE_CREATE_MOBILE_TRNSALES);
			db.execSQL(User.TABLE_CREATE_MOBILE_USER);
			
			Log.d(TAG, "DBHelper onCreate");
			//TODO: create the view ?
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS " + Counter.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + Customer.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + DailyNews.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + DtlSales.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + PriceList.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + Product.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnReceivable.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnRoute.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + TrnSales.getTableName());
			db.execSQL("DROP TABLE IF EXISTS " + User.getTableName());
			
			onCreate(db);
		}
	}
	
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
