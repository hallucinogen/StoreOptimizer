package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mSalesInformationButton = (Button) findViewById(R.id.sales_information);
		mSalesKanvasingButton = (Button) findViewById(R.id.sales_kanvasing);
		mStockTransactionButton = (Button) findViewById(R.id.stock_transaction);
		mDailyNewsButton 		= (Button) findViewById(R.id.daily_news);
		mSettingButton 			= (Button) findViewById(R.id.setting);
		
		mSalesInformationButton.setOnClickListener(this);
		mSalesKanvasingButton.setOnClickListener(this);
		mStockTransactionButton.setOnClickListener(this);
		mDailyNewsButton.setOnClickListener(this);
		mSettingButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mSalesInformationButton) {
			startActivity(new Intent(MainMenuAct.this, SalesInformationAct.class));
		} else if (v == mSalesKanvasingButton) {
			startActivity(new Intent(MainMenuAct.this, KanvasingStoreListAct.class));
		} else if (v == mStockTransactionButton) {
			startActivity(new Intent(MainMenuAct.this, StockTransactionAct.class));
		} else if (v == mDailyNewsButton) {
			startActivity(new Intent(MainMenuAct.this, DailyNewsAct.class));
		} else if (v == mSettingButton) {
			startActivity(new Intent(MainMenuAct.this, SettingAct.class));
		}
	}
	
	private Button mSalesInformationButton;
	private Button mSalesKanvasingButton;
	private Button mStockTransactionButton;
	private Button mDailyNewsButton;
	private Button mSettingButton;
}
