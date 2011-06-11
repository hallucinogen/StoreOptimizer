package dp.mobile.store.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseAdapter {
	protected static final String	TAG					= "DatabaseAdapter";
	protected static final String	DATABASE_NAME		= "artindo";
	protected static final int		DATABASE_VERSION	= 2;

	public DatabaseAdapter(Context context){
		mContext = context;
		Log.d(TAG, "CREATING DATABASE " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	public DatabaseAdapter open() throws SQLException{
		mDBHelper = new DatabaseHelper(mContext);
		mDatabase = mDBHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close(){
		mDBHelper.close();
		Log.d(TAG, "CLOSING DATABASE " + DATABASE_NAME + "version " + DATABASE_VERSION);
	}
	
	//PROPERTIES
	protected final Context		mContext;
	protected DatabaseHelper	mDBHelper;
	protected SQLiteDatabase	mDatabase;
	
	protected static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d(TAG, "CREATING DATABASE HELPER");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("DROP TABLE IF EXISTS mobile_dailynews");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnroute");
			db.execSQL("DROP TABLE IF EXISTS mobile_pricelist");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnreceivable");
			db.execSQL("DROP TABLE IF EXISTS mobile_trnsales");
			db.execSQL("DROP TABLE IF EXISTS mobile_dtlsales");
			
			db.execSQL(TABLE_CREATE_MOBILE_DAILYNEWS);
			db.execSQL(TABLE_CREATE_MOBILE_TRNROUTE);
			db.execSQL(TABLE_CREATE_MOBILE_PRICELIST);
			db.execSQL(TABLE_CREATE_MOBILE_TRNRECEIVABLE);
			db.execSQL(TABLE_CREATE_MOBILE_TRNSALES);
			db.execSQL(TABLE_CREATE_MOBILE_DTLSALES);
			
			//TODO: create the view ?
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS");
			//TODO : DROP another table
			onCreate(db);
		}
	}
	
	protected static final String TABLE_CREATE_MOBILE_DAILYNEWS = 
		"CREATE TABLE mobile_dailynews (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate		DATETIME, "
		+ "newsfrom 	VARCHAR(32), "
		+ "descr_short	VARCHAR(128), "
		+ "descr		TEXT);";
	
	protected static final String TABLE_CREATE_MOBILE_TRNROUTE = 
		"CREATE TABLE mobile_trnroute (id CHAR(32) PRIMARY KEY NOT NULL DEFAULT '', "
		+ "trndate				DATETIME, "
		+ "username				VARCHAR(32), "
		+ "unitcompany_code		VARCHAR(32), "
		+ "orderno				NUMERIC(8,0) DEFAULT 0, "
		+ "customer_code		VARCHAR(32), "
		+ "customer_name		VARCHAR(128), "
		+ "customer_address		VARCHAR(128), "
		+ "customer_postcode	VARCHAR(16), "
		+ "customer_satellite 	VARCHAR(32), "
		+ "customer_type 		VARCHAR(32), "
		+ "customer_termpayment	VARCHAR(128), "
		+ "credit_limit 		NUMERIC(16,2) DEFAULT 0, "
		+ "receivable 			NUMERIC(16,2) DEFAULT 0, "
		+ "lastbuydate 			DATETIME, "
		+ "descr 				TEXT);";
	
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
