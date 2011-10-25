package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
		
		SharedPreferences sharedPref = getSharedPreferences(Utilities.PREFERENCES, 0);
		Editor sharedPrefEditor = sharedPref.edit();

		int mainMenuStatus = sharedPref.getInt(Utilities.MAIN_MENU_STATUS, 0);
		mainMenuStatus = mainMenuStatus < Utilities.MAIN_MENU_AFTER_STOCK ? Utilities.MAIN_MENU_AFTER_STOCK : mainMenuStatus;
		sharedPrefEditor.putInt(Utilities.MAIN_MENU_STATUS, mainMenuStatus);

		sharedPrefEditor.commit();
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
