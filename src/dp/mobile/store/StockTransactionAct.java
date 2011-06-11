package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StockTransactionAct extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_transaction);
		
		mStockRequestButton = (Button) findViewById(R.id.stock_request);
		mReceivingButton	= (Button) findViewById(R.id.receiving);
		mReturnButton		= (Button) findViewById(R.id.return_stock);
		
		mStockRequestButton.setOnClickListener(this);
		mReceivingButton.setOnClickListener(this);
		mReturnButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mStockRequestButton) {
			startActivity(new Intent(this, StockRequestAct.class));
		} else if (v == mReceivingButton) {
			startActivity(new Intent(this, ReceivingAct.class));
		} else if (v == mReturnButton) {
			startActivity(new Intent(this, ReturnAct.class));
		}
	}
	
	private Button mStockRequestButton, mReceivingButton, mReturnButton; 
}
