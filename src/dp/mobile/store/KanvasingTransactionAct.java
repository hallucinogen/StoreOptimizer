package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.Customer;
import dp.mobile.store.helper.tables.DtlSales;

public class KanvasingTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_transaction);
		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		initComp();
	}
	
	private void initComp(){
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
