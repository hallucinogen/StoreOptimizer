package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dp.mobile.store.helper.Utilities;

public class MainMenuAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		
		initComp();
		setClickability();
	}

	private void setClickability() {
		SharedPreferences sharedPref = getSharedPreferences(Utilities.PREFERENCES, 0);
		mMainMenuStatus = sharedPref.getInt(Utilities.MAIN_MENU_STATUS, 0);
		
		switch (mMainMenuStatus) {
			case Utilities.MAIN_MENU_INITIAL:
				mSalesInformationButton.setEnabled(true);
				mPenerimaanStockButton.setEnabled(false);
				mStockTransactionButton.setEnabled(false);
				mInformasiAwal.setEnabled(false);
				mSettingButton.setEnabled(false);
				break;
			case Utilities.MAIN_MENU_AFTER_INFO:
				mSalesInformationButton.setEnabled(true);
				mPenerimaanStockButton.setEnabled(false);
				mStockTransactionButton.setEnabled(true);
				mInformasiAwal.setEnabled(false);
				mSettingButton.setEnabled(false);
				break;
			case Utilities.MAIN_MENU_AFTER_STOCK:
				mSalesInformationButton.setEnabled(true);
				mPenerimaanStockButton.setEnabled(true);
				mStockTransactionButton.setEnabled(true);
				mInformasiAwal.setEnabled(true);
				mSettingButton.setEnabled(true);
				break;
			case Utilities.MAIN_MENU_NO_TRANSACTION:
				mSalesInformationButton.setEnabled(true);
				mPenerimaanStockButton.setEnabled(false);
				mStockTransactionButton.setEnabled(true);
				mInformasiAwal.setEnabled(true);
				mSettingButton.setEnabled(true);
				break;
		}
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Main Menu");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mSalesInformationButton = (Button) findViewById(R.id.sales_information);
		//mSalesKanvasingButton 	= (Button) findViewById(R.id.sales_kanvasing);
		mPenerimaanStockButton	= (Button) findViewById(R.id.penerimaan_stock);
		mStockTransactionButton = (Button) findViewById(R.id.stock_transaction);
		//mDailyNewsButton 		= (Button) findViewById(R.id.daily_news);
		mSettingButton 			= (Button) findViewById(R.id.setting);
		mInformasiAwal			= (Button) findViewById(R.id.informasi_awal);
		mTransaksiSales			= (Button) findViewById(R.id.transaksi_sales);
		
		mSalesInformationButton.setOnClickListener(this);
		//mSalesKanvasingButton.setOnClickListener(this);
		mStockTransactionButton.setOnClickListener(this);
		//mDailyNewsButton.setOnClickListener(this);
		mInformasiAwal.setOnClickListener(this);
		mSettingButton.setOnClickListener(this);
		mTransaksiSales.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mSalesInformationButton) {//Informasi Data / Informasi Penjualan / Sales Information
			startActivityForResult(new Intent(MainMenuAct.this, SalesInformationAct.class), SalesInformationAct.REQUEST_CODE);
		} else if (v == mPenerimaanStockButton) { //Terima Stock
			//startActivityForResult(new Intent(MainMenuAct.this, KanvasingStoreListAct.class), 0);
		} else if (v == mTransaksiSales){
			startActivityForResult(new Intent(MainMenuAct.this, KanvasingStoreListAct.class), 0);
		} else if (v == mStockTransactionButton) { //
			startActivityForResult(new Intent(MainMenuAct.this, StockTransactionAct.class), 0);
		} else if (v == mInformasiAwal) {
			startActivityForResult(new Intent(MainMenuAct.this, InformasiAwalAct.class), 0);
		} else if (v == mSettingButton) {
			startActivityForResult(new Intent(MainMenuAct.this, SettingAct.class), 0);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		setClickability();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private Button mSalesInformationButton;
	//private Button mSalesKanvasingButton;
	private Button mPenerimaanStockButton;
	private Button mStockTransactionButton;
	//private Button mDailyNewsButton;
	private Button mSettingButton;
	private Button mInformasiAwal;
	private Button mTransaksiSales;
	
	private int mMainMenuStatus;
	private TextView mTitle, mNameTop, mRouteTop;
}
