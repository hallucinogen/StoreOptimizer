package dp.mobile.store;

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
			startActivity(new Intent(this, KanvasingStoreListAct.class));
		} else if (v == mOtherTransaction) {
			startActivity(new Intent(this, OtherTransactionAct.class));
		} else if ( v == mReceivableButton) {
			startActivity(new Intent(this, ReceivablePaymentAct.class));
		}
	}

	private Button mKanvasingButton, mOtherTransaction, mReceivableButton; 
}
