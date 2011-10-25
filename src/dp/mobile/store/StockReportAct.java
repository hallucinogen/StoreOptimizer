package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class StockReportAct extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_report);
		
		initComp();
		populateTableStockReport();
	}
	
	private void initComp(){
		mTitle		= (TextView) findViewById(R.id.header_title);
		mNameTop	= (TextView) findViewById(R.id.header_nametop);
		mRouteTop	= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Status Stock");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
	}
	
	private void populateTableStockReport() { //Table mobile_dtlsales
		int i=0, tempIdx;
		LayoutInflater inflater = getLayoutInflater();
		Cursor stockReportCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_product.product_code, mobile_product.product_name, mobile_dtlsales.qty_debit, " +
				"mobile_dtlsales.qty_credit " +
				"FROM mobile_dtlsales JOIN mobile_product ON " +
				"mobile_dtlsales.product_code = mobile_product.product_code " +
				"ORDER BY mobile_dtlsales.product_code ASC", null);
		
		StockReport[] stockReports = new StockReport[stockReportCur.getCount()];
		if(stockReportCur.moveToFirst()){
    		do{
    			StockReport temp = new StockReport(stockReportCur.getString(0), stockReportCur.getString(1),
    					stockReportCur.getLong(2), stockReportCur.getLong(3));
    			tempIdx = getIdx(temp.mProductCode, stockReports);
    			Log.d("TEMPIDX", "tempIdx = " + tempIdx);
    			if(tempIdx == -1)
    				stockReports[i++] = temp;
    			else{
    				stockReports[tempIdx].mQtyCredit	+= temp.mQtyCredit;
    				stockReports[tempIdx].mQtyDebit		+= temp.mQtyDebit;
    				Log.d("BUKAN -1", stockReports[tempIdx].mQtyCredit + "#" + stockReports[tempIdx].mQtyDebit);
    			}
    			
    			
    		}while(stockReportCur.moveToNext());
    		
    		stockReportCur.close();
		}
		
		for (i= 0; i < stockReports.length; ++i){
			if(stockReports[i] != null){
				TableRow row 		= (TableRow)inflater.inflate(R.layout.four_column, mStockTable, false);
				TextView item 		= (TextView)row.findViewById(R.id.item);
				TextView begin 		= (TextView)row.findViewById(R.id.num1);
				TextView trans 		= (TextView)row.findViewById(R.id.num2);
				TextView ending 	= (TextView)row.findViewById(R.id.num3);
				
				item.setText(stockReports[i].mProductName);
				begin.setText(String.valueOf(stockReports[i].mQtyDebit));
				trans.setText(String.valueOf(stockReports[i].mQtyCredit));
				ending.setText(String.valueOf(stockReports[i].mQtyDebit - stockReports[i].mQtyCredit));
				
				mStockTable.addView(row);
			}
		}
	}
	
	private int getIdx(String productCode, StockReport[] stockReports){
		for(int i=0; i<stockReports.length; ++i){
			if(stockReports[i] != null){
				if(stockReports[i].mProductCode.equals(productCode))
					return i;
			} else
				return -1;
		}
		
		return -1;
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private TableLayout mStockTable;
	
	private class StockReport {
		public StockReport(String productCode, String productName, long qtyDebit, long qtyCredit){
			mProductCode	= productCode;
			mProductName	= productName;
			mQtyDebit		= qtyDebit;
			mQtyCredit		= qtyCredit;
		}
		
		public String	mProductCode, mProductName;
		public long		mQtyDebit, mQtyCredit;
	}
}
