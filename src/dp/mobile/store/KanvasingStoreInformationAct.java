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
import dp.mobile.store.helper.tables.TrnRoute;

public class KanvasingStoreInformationAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storeinfo);
		mStoreID = getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		initComp();
		populateInfo(getTrnRouteByCustCode(mStoreID));
		
		//Calendar now = new GregorianCalendar();
		/*mInfo[0].setText("" + now.get(Calendar.DAY_OF_MONTH) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.YEAR));
		mInfo[1].setText("" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));*/
	}
	
	private void initComp(){
		mInfo = new TextView[10];
		mInfo[0] = (TextView)findViewById(R.id.info1);
		mInfo[1] = (TextView)findViewById(R.id.info2);
		mInfo[2] = (TextView)findViewById(R.id.info3);
		mInfo[3] = (TextView)findViewById(R.id.info4);
		mInfo[4] = (TextView)findViewById(R.id.info5);
		mInfo[5] = (TextView)findViewById(R.id.info6);
		mInfo[6] = (TextView)findViewById(R.id.info7);
		mInfo[7] = (TextView)findViewById(R.id.info8);
		mInfo[8] = (TextView)findViewById(R.id.info9);
	}
	
	//Get corresponding mobile_trnroute, WHERE customer_code == mStoreID
	private TrnRoute getTrnRouteByCustCode(String custCode){
		Cursor cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT * " +
				"FROM mobile_trnroute " +
				"WHERE customer_code =?", new String[]{custCode});
		
		cursor.moveToFirst();
		TrnRoute retval = new TrnRoute(cursor.getString(0), Utilities.formatStr(cursor.getString(1)),
				cursor.getString(2), cursor.getString(3), cursor.getLong(4),
				cursor.getString(5), cursor.getString(6), cursor.getString(7),
				cursor.getString(8), cursor.getString(9), cursor.getString(10),
				cursor.getString(11), cursor.getLong(12), cursor.getLong(13),
				Utilities.formatStr(cursor.getString(14)), cursor.getString(15),
				cursor.getString(16)); 
		cursor.close();
		
		return retval;
	}
	
	private void populateInfo(TrnRoute selectedTrnRoute){
		//Date and Time
		String originalTrnDate = Utilities.formatDate(selectedTrnRoute.mTrnDate);
			int spaceIdx = originalTrnDate.indexOf(" ");
			String date = originalTrnDate.substring(0, spaceIdx);
			String time = originalTrnDate.substring(spaceIdx+1, originalTrnDate.length());
		
		mInfo[0].setText(date);
		mInfo[1].setText(time);
		
		//Current Kilometer
		
		//Current Name
		mInfo[3].setText(selectedTrnRoute.mCustomerName);
		
		//Customer Type
		mInfo[4].setText(selectedTrnRoute.mCustomerType);
		
		//Term of Payment
		mInfo[5].setText(selectedTrnRoute.mCustomerTermPayment);
		
		//Receivable
		mInfo[6].setText(String.valueOf(selectedTrnRoute.mReceivable));
		
		//Limit Credit
		mInfo[7].setText(String.valueOf(selectedTrnRoute.mCreditLimit));
		
		//Last Buy
		mInfo[8].setText(Utilities.formatDate(selectedTrnRoute.mLastBuyDate));
		
		mHistoryButton 		= (Button) findViewById(R.id.history);
		mKanvasingButton 	= (Button) findViewById(R.id.kanvasing);
		
		mHistoryButton.setOnClickListener(this);
		mKanvasingButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mHistoryButton) {
			startActivity(new Intent(this, KanvasingStoreHistoryAct.class));
		} else if (v == mKanvasingButton) {
			Intent intent = new Intent(this, KanvasingTransactionAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			
			startActivityForResult(intent, Utilities.KANVASING_TRANSACTION_RC);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_TRANSACTION_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private TextView[] mInfo;
	private Button mHistoryButton, mKanvasingButton;
	private String mStoreID;
}
