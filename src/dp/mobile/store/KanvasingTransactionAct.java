package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class KanvasingTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_transaction);
		
		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		mSalesTransactionButton = (Button) findViewById(R.id.sales_transaction);
		mReturButton			= (Button) findViewById(R.id.retur_transaction);
		mReceivableButton		= (Button) findViewById(R.id.receivable_payment);
		mDoneKanvasingButton	= (Button) findViewById(R.id.transaction_done_btn);
		
		mSalesTransactionButton.setOnClickListener(this);
		mReturButton.setOnClickListener(this);
		mReceivableButton.setOnClickListener(this);
		mDoneKanvasingButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mSalesTransactionButton) {
			Intent intent = new Intent(this, SalesTransactionAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			
			startActivity(intent);
		} else if(v == mReturButton) {
			//Intent intent = new Intent(this, KanvasingFinishAct.class);
			/// TODO : don't use dummy calculation
			//intent.putExtra(Utilities.INTENT_TOTAL_COST, calculateCostWithDummy());
			//startActivityForResult(intent, Utilities.KANVASING_FINISH_RC);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_FINISH_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private Button mSalesTransactionButton, mReturButton, mReceivableButton;
	private Button mDoneKanvasingButton;
	private String mStoreID;
}
