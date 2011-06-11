package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StockReportAct extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_report);
		
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
		
		populateTableWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateTableWithDummy() {

		LayoutInflater inflater = getLayoutInflater();
		for (int i = 0; i < 20; ++i){
			TableRow row 		= (TableRow)inflater.inflate(R.layout.four_column, mStockTable, false);
			TextView item 		= (TextView)row.findViewById(R.id.item);
			TextView begin 		= (TextView)row.findViewById(R.id.num1);
			TextView trans 		= (TextView)row.findViewById(R.id.num2);
			TextView ending 	= (TextView)row.findViewById(R.id.num3);
			
			item.setText("Rokok Merah");
			begin.setText("500");
			trans.setText(null);
			ending.setText("500");
			
			mStockTable.addView(row);
			
			row 		= (TableRow)inflater.inflate(R.layout.four_column, mStockTable, false);
			item 		= (TextView)row.findViewById(R.id.item);
			begin 		= (TextView)row.findViewById(R.id.num1);
			trans 		= (TextView)row.findViewById(R.id.num2);
			ending 		= (TextView)row.findViewById(R.id.num3);
			
			item.setText("Rokok Putih");
			begin.setText("1250");
			trans.setText("500");
			ending.setText("750");
			
			mStockTable.addView(row);
		}
	}
	
	private TableLayout mStockTable;
}
