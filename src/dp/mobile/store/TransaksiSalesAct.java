package dp.mobile.store;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class TransaksiSalesAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaksi_sales);

		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		mKanvasingStartDate	= getIntent().getExtras().getString(Utilities.INTENT_KANVASING_START);
		
		initComp();
	}
	
	private void initComp(){
		mTitle		= (TextView) findViewById(R.id.header_title);
		mNameTop	= (TextView) findViewById(R.id.header_nametop);
		mRouteTop	= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Transaksi Stok");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mInfoPelangganBtn		= (Button) findViewById(R.id.informasi_pelanggan);
		mLunasPiutangBtn		= (Button) findViewById(R.id.pelunasan_piutang);
		mTransaksiPenjualanBtn	= (Button) findViewById(R.id.start_kanvasing);
		mReturPenjualanBtn		= (Button) findViewById(R.id.retur_penjualan);
		mSaranBtn				= (Button) findViewById(R.id.saran);
		mSurveyBtn				= (Button) findViewById(R.id.survey);
		
		mDoneKanvasingButton	= (Button) findViewById(R.id.transaction_done_btn);
		
		mInfoPelangganBtn.setOnClickListener(this);
		mLunasPiutangBtn.setOnClickListener(this);
		mTransaksiPenjualanBtn.setOnClickListener(this);
		mReturPenjualanBtn.setOnClickListener(this);
		mSaranBtn.setOnClickListener(this);
		mSurveyBtn.setOnClickListener(this);
		mDoneKanvasingButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mInfoPelangganBtn) {//Informasi Data / Informasi Penjualan / Sales Information
			finish();
		} else if(v == mLunasPiutangBtn){
			Intent intent = new Intent(this, PelunasanPiutangAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			
			startActivity(intent);
		}
		else if (v == mTransaksiPenjualanBtn) { //Terima Stock
			Intent intent = new Intent(this, SalesTransactionAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			
			startActivity(intent);
			
			/*Intent intent = new Intent(this, KanvasingTransactionAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			intent.putExtra(Utilities.INTENT_KANVASING_START, mKanvasingStartDate);
			
			startActivityForResult(intent, Utilities.KANVASING_TRANSACTION_RC);*/
		} else if(v == mDoneKanvasingButton){
			Date startTime = Utilities.formatStr(getIntent().getExtras().getString(Utilities.INTENT_KANVASING_START));
			Date finishTime = new Date();
			
			String[] time = new String[4];
			
			time[0]	= String.valueOf(startTime.getHours());
			time[1]	= String.valueOf(startTime.getMinutes());
			time[2]	= String.valueOf(finishTime.getHours());
			time[3]	= String.valueOf(finishTime.getMinutes());
			for(int i=0; i<4; ++i){
				if(time[i].length() < 2)
					time[i] = "0" + time[i];
			}
			String timeVisit = time[0] + ":" + time[1] + "-" + time[2] + ":" + time[3];
			
			Log.i("KANVASING", timeVisit);
			Cursor cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("UPDATE mobile_trnroute "+
					"SET timevisit='" + timeVisit + "' " +
					"WHERE customer_code='" + mStoreID + "'", null);
			
			if(cursor.moveToFirst()){
				for(String s : cursor.getColumnNames()){
					Log.d("KANVASING", s + "#" + cursor.getString(cursor.getColumnIndex(s)));
				}
			} else {
				Log.d("KANVASING", "NO C");
			}
			cursor.close();
			
			setResult(RESULT_OK);
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_TRANSACTION_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private Button mInfoPelangganBtn, mLunasPiutangBtn, mTransaksiPenjualanBtn, mReturPenjualanBtn;
	private Button mSaranBtn, mSurveyBtn;
	private Button mDoneKanvasingButton;
	
	private String mStoreID, mKanvasingStartDate;
}
