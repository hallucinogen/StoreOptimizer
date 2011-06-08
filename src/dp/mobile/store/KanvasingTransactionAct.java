package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class KanvasingTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_transaction);
		
		mTransactionTable 	= (TableLayout) findViewById(R.id.transaction_table);
		mConfirmButton		= (Button) findViewById(R.id.confirm);
		mConfirmButton.setOnClickListener(this);
		
		populateTableWithDummy();
	}
	
	private void populateTableWithDummy() {
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < 5; ++i) {
			TableRow item = (TableRow) inflater.inflate(R.layout.transaction_item, mTransactionTable, false);
			TextView name = (TextView) item.findViewById(R.id.item);
			TextView price = (TextView) item.findViewById(R.id.num1);
			
			name.setText("Rokok Jantan");
			price.setText("Rp 5.000");
			
			mTransactionTable.addView(item);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmButton) {
			Intent intent = new Intent(this, KanvasingFinishAct.class);
			/// TODO : put extra what's bought
			startActivityForResult(intent, Utilities.KANVASING_FINISH_RC);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_FINISH_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private Button mConfirmButton;
	private TableLayout mTransactionTable;
}
