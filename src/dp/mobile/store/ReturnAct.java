package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReturnAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.return_stock);
		
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
		
		populateTableWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateTableWithDummy() {

		LayoutInflater inflater = getLayoutInflater();
		for (int i = 0; i < 20; ++i){
			TableRow row 		= (TableRow)inflater.inflate(R.layout.four_column, mStockTable, false);
			TextView item 		= (TextView)row.findViewById(R.id.item);
			TextView stock 		= (TextView)row.findViewById(R.id.num1);
			TextView sales 		= (TextView)row.findViewById(R.id.num2);
			TextView ret 		= (TextView)row.findViewById(R.id.num3);
			
			item.setText("Rokok Merah");
			stock.setText("500");
			sales.setText(null);
			ret.setText("500");
			
			mStockTable.addView(row);
		}
	}
	
	private TableLayout mStockTable;
}
