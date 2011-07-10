package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KanvasingFinishAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_finish);
		
		mConfirmButton = (Button) findViewById(R.id.confirm);
		mConfirmButton.setOnClickListener(this);
		
		mTotal 			= (TextView)findViewById(R.id.info1);
		mDiscount		= (TextView)findViewById(R.id.info2);
		mNetto			= (TextView)findViewById(R.id.info3);
		mPayment		= (TextView)findViewById(R.id.info4);
		mCreditLimit	= (TextView)findViewById(R.id.info5);
		mReceivable		= (TextView)findViewById(R.id.info6);
		
		mTotal.setText(Long.toString(getIntent().getExtras().getLong(Utilities.INTENT_TOTAL_COST)));
		
		/// TODO : not use dummy discount
		mDiscount.setText("" + 0);
		/// TODO : what is credit limit?
		mCreditLimit.setText("" + 0);
		
		dummyCalculate();
	}
	
	private void dummyCalculate() {
		long total 	= Long.parseLong(mTotal.getText().toString());
		long disc	= Long.parseLong(mDiscount.getText().toString());
		long netto	= total - disc;
		
		mNetto.setText(Long.toString(total - disc));
		mPayment.setText(Long.toString(netto));
		mReceivable.setText(Long.toString(netto));
	}
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmButton) {
			/// TODO : insert to database
			setResult(RESULT_OK);
			Toast.makeText(this, "Transaction Complete", 1000).show();
			finish();
		}
	}
	
	private TextView mTotal, mDiscount, mNetto, mPayment, mCreditLimit, mReceivable;
	private Button mConfirmButton;
}
