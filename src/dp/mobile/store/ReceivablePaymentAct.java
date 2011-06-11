package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ReceivablePaymentAct extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receivable_payment);
		
		mLinear = (LinearLayout) findViewById(R.id.payment_linear);
		
		populateLinearWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateLinearWithDummy() {
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < 20; ++i) {
			LinearLayout item = (LinearLayout)inflater.inflate(R.layout.receivable_payment_item, mLinear, false);
			
			mLinear.addView(item);
		}
	}
	
	private LinearLayout mLinear;
}
