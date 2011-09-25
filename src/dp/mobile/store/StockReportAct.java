package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;

public class StockReportAct extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_report);
		
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
		
		populateTableStockReport();
	}
	
	private void populateTableStockReport() { //Table mobile_dtlsales
		int i=0;
		LayoutInflater inflater = getLayoutInflater();
		Cursor stockReportCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_dtlsales.product_code, mobile_product.product_name, " +
				"mobile_dtlsales.qty_debit, mobile_dtlsales.qty_credit " +
				"FROM mobile_dtlsales JOIN mobile_product ON " +
				"mobile_dtlsales.product_code = mobile_product.product_code " +
				"GROUP BY mobile_dtlsales.product_code " +
				"ORDER BY mobile_dtlsales.product_code ASC", null);
		
		StockReport[] stockReports = new StockReport[stockReportCur.getCount()];
		if(stockReportCur.moveToFirst()){
    		do{
    			stockReports[i++] = new StockReport(stockReportCur.getString(0), stockReportCur.getString(1),
    					stockReportCur.getLong(2), stockReportCur.getLong(3));
    		}while(stockReportCur.moveToNext());
    		
    		stockReportCur.close();
		}
		
		for (i= 0; i < stockReports.length; ++i){
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
