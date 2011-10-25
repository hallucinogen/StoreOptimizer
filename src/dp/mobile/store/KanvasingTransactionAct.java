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

public class KanvasingTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_transaction);
		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		initComp();
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
		
		mSalesTransactionButton = (Button) findViewById(R.id.sales_transaction);
		mReturButton			= (Button) findViewById(R.id.retur_transaction);
		mReceivableButton		= (Button) findViewById(R.id.receivable_payment);
		mDoneKanvasingButton	= (Button) findViewById(R.id.transaction_done_btn);
		
		mTransCustCode = (TextView) findViewById(R.id.transaction_cust_code);
		mTransCustName = (TextView) findViewById(R.id.transaction_cust_name);
		mTransCustAddr = (TextView) findViewById(R.id.transaction_cust_address);
		
		mSalesTransactionButton.setOnClickListener(this);
		mReturButton.setOnClickListener(this);
		mReceivableButton.setOnClickListener(this);
		mDoneKanvasingButton.setOnClickListener(this);
		
		setCustInfo();
	}
	
	private void setCustInfo(){
		//Get Customer where customer_code = mStodeID
		Cursor cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT customer_name, address " +
				"FROM mobile_customer " +
				"WHERE customer_code=?", new String[]{mStoreID});
		cursor.moveToFirst();
		
		mTempCust = new TempCustomer(cursor.getString(0), cursor.getString(1)); 
		cursor.close();
		
		mTransCustCode.setText("Code\t\t: " + mStoreID);
		mTransCustName.setText("Name\t\t: " + mTempCust.mCustName);
		mTransCustAddr.setText("Address\t: " + mTempCust.mCustAddr);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mSalesTransactionButton) {
			Intent intent = new Intent(this, SalesTransactionAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			
			startActivity(intent);
		} else if(v == mReturButton) {
			//Intent intent = new Intent(this, KanvasingFinishAct.class);
			
			/// TODO : don't use dummy calculation
			//intent.putExtra(Utilities.INTENT_TOTAL_COST, calculateCostWithDummy());
			//startActivityForResult(intent, Utilities.KANVASING_FINISH_RC);
		} else if(v == mDoneKanvasingButton){
			Log.i("KANVASING", getIntent().getExtras().getString(Utilities.INTENT_KANVASING_START));
			Log.i("KANVASING", mStoreID);
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
			
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_FINISH_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private Button mSalesTransactionButton, mReturButton, mReceivableButton;
	private Button mDoneKanvasingButton;
	private TextView mTransCustCode, mTransCustName, mTransCustAddr;
	private TextView mTitle, mNameTop, mRouteTop;
	private String mStoreID;
	
	private TempCustomer mTempCust;
	
	private class TempCustomer{
		public TempCustomer(String custName, String custAddr){
			mCustName	= custName;
			mCustAddr	= custAddr;
		}
		
		public String mCustName, mCustAddr;
	}
}
