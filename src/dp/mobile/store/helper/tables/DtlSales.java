package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.database.Cursor;

public class DtlSales extends Model {
	public static final String KEY_ROWID			= "id";
	public static final String KEY_TRNSALES_ID		= "trnsales_id";
	public static final String KEY_PRODUCT_CODE		= "product_code";
	public static final String KEY_QTY				= "qty";
    public static final String KEY_QTY_DEBIT		= "qty_debit";
    public static final String KEY_QTY_CREDIT		= "qty_credit";
    public static final String KEY_PRICE			= "price";
    public static final String KEY_AMOUNT			= "amount";
    public static final String KEY_AMOUNT_DEBIT		= "amount_debit";
    public static final String KEY_AMOUNT_CREDIT	= "amount_credit";
    public static final String KEY_AMOUNT_CASH		= "amount_cash";
    public static final String KEY_STATUS			= "status";
    
    public static String getTableName(){
    	return "mobile_dtlsales";
    }
    
    public static String[] getColumns(){
		return new String[]{KEY_ROWID, KEY_TRNSALES_ID, KEY_PRODUCT_CODE, KEY_QTY, KEY_QTY_DEBIT,
				KEY_QTY_CREDIT, KEY_PRICE, KEY_AMOUNT, KEY_AMOUNT_DEBIT, KEY_AMOUNT_CREDIT, KEY_AMOUNT_CASH,
				KEY_STATUS};
	}
    
    public DtlSales() {
    	
	}
    
    public DtlSales(String id){
    	super(id);
    }
    
    public DtlSales(String id, String trnSalesID, String productCode, long qty, long qtyDebit, long qtyCredit,
    		long price, long amount, long amountDebit, long amountCredit, long amountCash, long status){
    	this(id);
    	
    	mTrnSalesID			= trnSalesID;	//0
    	mProductCode		= productCode;	//1
    	mQty				= qty;			//2
    	mQtyDebit			= qtyDebit;		//3
    	mQtyCredit			= qtyCredit;	//4
    	mPrice				= price;		//5
    	mAmount				= amount;		//6
    	mAmountDebit		= amountDebit;	//7
    	mAmountCredit		= amountCredit;	//8
    	mAmountCash			= amountCash;	//9
    	mStatus				= status;		//10
    }
    
    @Override
	public ContentValues getContentValues() {
		ContentValues retval = new ContentValues();
    	
    	retval.put(KEY_ROWID,			mID);			//0
    	retval.put(KEY_TRNSALES_ID,		mTrnSalesID);	//1
    	retval.put(KEY_PRODUCT_CODE,	mProductCode);	//2
    	retval.put(KEY_QTY,				mQty);			//3
    	retval.put(KEY_QTY_DEBIT,		mQtyDebit);		//4
    	retval.put(KEY_QTY_CREDIT,		mQtyCredit);	//5
    	retval.put(KEY_PRICE,			mPrice);		//6
    	retval.put(KEY_AMOUNT,			mAmount);		//7
    	retval.put(KEY_AMOUNT_DEBIT,	mAmountDebit);	//8
    	retval.put(KEY_AMOUNT_CREDIT,	mAmountCredit);	//9
    	retval.put(KEY_AMOUNT_CASH,		mAmountCash);	//10
    	retval.put(KEY_STATUS,			mStatus);		//11
    	
    	return retval;
	}
    
    public static Model[] extract(Cursor cursor){
    	if(cursor != null){
    		int i = 0;
    		Model[] retval = new DtlSales[cursor.getCount()];

    		cursor.moveToFirst();
    		do{
    			retval[i++] = new DtlSales(cursor.getString(0), cursor.getString(1), cursor.getString(2),
    					cursor.getLong(3), cursor.getLong(4), cursor.getLong(5), cursor.getLong(6),
    					cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getLong(10),
    					cursor.getLong(11));
    		}while(cursor.moveToNext());
    		
    		return retval;
    	}
    	else
    		return null;
    }
    
    //PROPERTIES
    public String	mTrnSalesID, mProductCode;
    public long		mQty, mQtyDebit, mQtyCredit, mPrice, mAmount, mAmountDebit, mAmountCredit, mAmountCash, mStatus;
    
  //TABLE CREATE Query
    public static final String TABLE_CREATE_MOBILE_DTLSALES = 
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
}
