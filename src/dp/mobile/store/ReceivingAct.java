package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReceivingAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiving);
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
		
		populateTableWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateTableWithDummy() {

		LayoutInflater inflater = getLayoutInflater();
		for (int i = 0; i < 20; ++i){
			TableRow row 		= (TableRow)inflater.inflate(R.layout.three_column, mStockTable, false);
			TextView item 		= (TextView)row.findViewById(R.id.item);
			TextView request 	= (TextView)row.findViewById(R.id.num1);
			TextView receive 	= (TextView)row.findViewById(R.id.num2);
			
			item.setText("Rokok Merah");
			request.setText("500");
			receive.setText("250");
			
			mStockTable.addView(row);
		}
	}
	
	private TableLayout mStockTable;
}
