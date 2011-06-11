package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SalesTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_transaction);
		
		mKanvasingButton 	= (Button) findViewById(R.id.kanvasing);
		mOtherTransaction	= (Button) findViewById(R.id.other_transaction);
		mReceivableButton	= (Button) findViewById(R.id.receivable_payment);
		
		mKanvasingButton.setOnClickListener(this);
		mOtherTransaction.setOnClickListener(this);
		mReceivableButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mKanvasingButton) {
			startActivityForResult(new Intent(this, KanvasingStoreListAct.class), Utilities.KANVASING_STORELIST_RC);
		} else if (v == mOtherTransaction) {
			startActivity(new Intent(this, OtherTransactionAct.class));
		} else if ( v == mReceivableButton) {
			startActivity(new Intent(this, ReceivablePaymentAct.class));
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_STORELIST_RC && resultCode == RESULT_OK)
			finish();
	}

	private Button mKanvasingButton, mOtherTransaction, mReceivableButton; 
}
