package dp.mobile.store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KanvasingFinishAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_finish);
		
		mStoreID = getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		decodeJSON();
		
		mConfirmButton = (Button) findViewById(R.id.confirm);
		mConfirmButton.setOnClickListener(this);
		
		mTotal 			= (TextView)findViewById(R.id.info1);
		mDiscount		= (TextView)findViewById(R.id.info2);
		mNetto			= (TextView)findViewById(R.id.info3);
		mPayment		= (TextView)findViewById(R.id.info4);
		mCreditLimit	= (TextView)findViewById(R.id.info5);
		mReceivable		= (TextView)findViewById(R.id.info6);
		
		mTotal.setText(String.valueOf(calculateCost()));
		
		/// TODO : not use dummy discount
		mDiscount.setText("" + 0);
		/// TODO : what is credit limit?
		mCreditLimit.setText("" + 0);
		
		dummyCalculate();
	}
	
	//Decode JSON to FinishTransactionPriceList array
	private void decodeJSON(){
		String jsonStr = getIntent().getExtras().getString(Utilities.INTENT_TRANSACTION_DETAILS);
		JSONArray jsonArr = null; 
		try {
			jsonArr = new JSONArray(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mFinishTransPL = new FinishTransactionPriceList[jsonArr.length()];
		for(int i=0; i<mFinishTransPL.length; ++i){
			JSONObject json;
			try {
				json = jsonArr.getJSONObject(i);
				Log.i("JSON DEBUG", json.toString());
				mFinishTransPL[i] = new FinishTransactionPriceList(json.getString("product_code"), 
						json.getLong("quantity"), json.getLong("price"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private long calculateCost(){
		long cost = 0;		
		for(int i=0; i<mFinishTransPL.length; ++i){
			cost += mFinishTransPL[i].mPrice * mFinishTransPL[i].mQuantity;
		}
		
		return cost;
	}
	
	private void dummyCalculate() {
		long total 	= Long.parseLong(mTotal.getText().toString());
		long disc	= Long.parseLong(mDiscount.getText().toString());
		long netto	= total - disc;
		
		mNetto.setText(Long.toString(total - disc));
		mPayment.setText(Long.toString(netto));
		mReceivable.setText(Long.toString(netto));
	}
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmButton) {
			/// TODO : insert to database, get mobile_trnroute where customer_code = mStoreID to get credit_limit & receivable, trus masukin semuanya ke trnsales dan dtlsales
			setResult(RESULT_OK);
			Toast.makeText(this, "Transaction Complete", 1000).show();
			finish();
		}
	}
	
	private TextView	mTotal, mDiscount, mNetto, mPayment, mCreditLimit, mReceivable;
	private Button		mConfirmButton;
	private String		mStoreID;
	private FinishTransactionPriceList[] mFinishTransPL;
	
	private class FinishTransactionPriceList{
		public FinishTransactionPriceList(String productCode, long productQuantity, long price) {
			mProductCode	= productCode;
			mQuantity		= productQuantity;
			mPrice			= price;
		}
		
		public String	mProductCode;
		public long		mQuantity, mPrice;
	}
}
