package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SalesInformationAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_information);
		
		mCheckRouteButton 	= (Button) findViewById(R.id.check_route);
		mStockReportButton 	= (Button) findViewById(R.id.stock_report);
		mCashReportButton 	= (Button) findViewById(R.id.cash_report);
		mGiroReportButton 	= (Button) findViewById(R.id.giro_report);
		
		mCheckRouteButton.setOnClickListener(this);
		mStockReportButton.setOnClickListener(this);
		mCashReportButton.setOnClickListener(this);
		mGiroReportButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mCheckRouteButton) {
			startActivity(new Intent(this, CheckRouteAct.class));
		} else if (v == mStockReportButton) {
			startActivity(new Intent(this, StockReportAct.class));
		} else if (v == mCashReportButton) {
			startActivity(new Intent(this, CashReportAct.class));
		} else if (v == mGiroReportButton) {
			startActivity(new Intent(this, GiroReportAct.class));
		}
	}
	
	private Button mCheckRouteButton;
	private Button mStockReportButton;
	private Button mCashReportButton;
	private Button mGiroReportButton;
}
