package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CashReportAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cash_report);
		
		mCashTable = (TableLayout) findViewById(R.id.cash_table);
		
		populateTableWithDummy();
	}
	
	private void populateTableWithDummy() {
		
		for (int i = 0; i < 10; ++i){
			TableRow row 	= new TableRow(this);
			TextView item 	= new TextView(this);
			item.setText("Aneka Jaya");
			TextView count 	= new TextView(this);
			count.setText("Rp 10.000.000");
			count.setGravity(Gravity.RIGHT);
			row.addView(item);
			row.addView(count);
			mCashTable.addView(row);
			
			row 	= new TableRow(this);
			item 	= new TextView(this);
			item.setText("Merah Putih");
			count 	= new TextView(this);
			count.setText("Rp 15.000.000");
			count.setGravity(Gravity.RIGHT);
			row.addView(item);
			row.addView(count);
			mCashTable.addView(row);
		}
	}
	
	private TableLayout mCashTable;
}
