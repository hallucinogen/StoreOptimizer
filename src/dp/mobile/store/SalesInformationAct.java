package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SalesInformationAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_information);
		
		initComp();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Informasi Data");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mCheckRouteButton 	= (Button) findViewById(R.id.check_route);
		mStockReportButton 	= (Button) findViewById(R.id.stock_report);
		mCashReportButton 	= (Button) findViewById(R.id.cash_report);
		mGiroReportButton 	= (Button) findViewById(R.id.giro_report);
		
		mCheckRouteButton.setOnClickListener(this);
		mStockReportButton.setOnClickListener(this);
		mCashReportButton.setOnClickListener(this);
		mGiroReportButton.setOnClickListener(this);
		
		// change clickable
		SharedPreferences sharedPref = getSharedPreferences(Utilities.PREFERENCES, 0);
		Editor sharedPrefEditor = sharedPref.edit();

		int mainMenuStatus = sharedPref.getInt(Utilities.MAIN_MENU_STATUS, 0);
		mainMenuStatus = mainMenuStatus < Utilities.MAIN_MENU_AFTER_INFO ? Utilities.MAIN_MENU_AFTER_INFO : mainMenuStatus;
		sharedPrefEditor.putInt(Utilities.MAIN_MENU_STATUS, mainMenuStatus);

		sharedPrefEditor.commit();
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
	
	private TextView mTitle, mNameTop, mRouteTop;
	
	public static final int REQUEST_CODE = 0;
}
