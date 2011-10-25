package dp.mobile.store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class SalesTransactionAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_transaction);
		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		initComp();
		populateTablePriceList();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Sales Transaction");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mTransactionTable 	= (TableLayout) findViewById(R.id.transaction_table);
		mConfirmButton		= (Button) findViewById(R.id.confirm);
		mConfirmButton.setOnClickListener(this);
	}
	
	private SalesTransactionPriceList[] getSalesTransPriceList(){
		int i=0;
		Cursor priceListCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_pricelist.customer_code, mobile_pricelist.product_code, " +
				"mobile_product.product_name, mobile_pricelist.price " +
				"FROM mobile_pricelist JOIN mobile_product ON " +
				"mobile_pricelist.product_code = mobile_product.product_code " +
				"WHERE mobile_pricelist.customer_code =? " +
				"ORDER BY mobile_pricelist.product_code ASC", new String[]{mStoreID});
		
		SalesTransactionPriceList[] retval = new SalesTransactionPriceList[priceListCur.getCount()];
		if(priceListCur.moveToFirst()){
    		do{
    			retval[i++] = new SalesTransactionPriceList(priceListCur.getString(0),
    					priceListCur.getString(1), priceListCur.getString(2),
    					priceListCur.getLong(3));
    		}while(priceListCur.moveToNext());
    		
    		priceListCur.close();
		}
		
		return retval;
	}
	
	private void populateTablePriceList() {
		mPriceLists = getSalesTransPriceList();
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < mPriceLists.length; ++i) {
			TableRow item = (TableRow) inflater.inflate(R.layout.transaction_item, mTransactionTable, false);
			TextView name = (TextView) item.findViewById(R.id.item);
			TextView price = (TextView) item.findViewById(R.id.num1);
			item.setId(i);
			
			name.setText(mPriceLists[i].mProductName);
			price.setText(String.valueOf(mPriceLists[i].mPrice));
			
			mTransactionTable.addView(item);
		}
	}
	
	/*private long calculateCost() {
		long res = 0;
		
		for (int i = 0; i < mPriceLists.length; ++i) {
			TableRow item = (TableRow)mTransactionTable.findViewById(i);
			EditText textbox 	= (EditText) item.findViewById(R.id.num2);
			int price 			= Integer.parseInt(((TextView) item.findViewById(R.id.num1)).getText().toString());
			int count			= Integer.parseInt(textbox.getText().toString());
			res += count * price;
		}
		
		return res;
	}*/
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmButton) {
			//Make JSON Array of product_code, quantity, price
			JSONArray jsonArray = new JSONArray();
			for(int i=0; i<mPriceLists.length; ++i){
				JSONObject json = new JSONObject();
				try {
					json.put("product_code", mPriceLists[i].mProductCode);
					
					//Get Quantity
					TableRow	item	= (TableRow)mTransactionTable.findViewById(i);
					EditText	textbox = (EditText) item.findViewById(R.id.num2);
					EditText	bonus	= (EditText) item.findViewById(R.id.num3);
					String		qty		= textbox.getText().toString();
					
					if(qty.equals("0") || qty == null || qty.trim().length() == 0)
						continue;
					json.put("quantity", Long.parseLong(textbox.getText().toString()));
					json.put("bonus", Long.parseLong(bonus.getText().toString()));
					json.put("price", mPriceLists[i].mPrice);
					
					jsonArray.put(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			Intent intent = new Intent(this, KanvasingFinishAct.class);
			intent.putExtra(Utilities.INTENT_STORE_ID, mStoreID);
			intent.putExtra(Utilities.INTENT_TRANSACTION_DETAILS, jsonArray.toString());
			startActivityForResult(intent, Utilities.KANVASING_FINISH_RC);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_FINISH_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private Button		mConfirmButton;
	private TableLayout	mTransactionTable; 
	private String		mStoreID;
	private SalesTransactionPriceList[]	mPriceLists;
	private TextView	mTitle, mNameTop, mRouteTop;
	
	private class SalesTransactionPriceList{
		public SalesTransactionPriceList(String customerCode, String productCode, String productName, long price) {
			mCustomerCode	= customerCode;
			mProductCode	= productCode;
			mProductName	= productName;
			mPrice			= price;
		}
		
		public String	mCustomerCode, mProductCode, mProductName;
		public long		mPrice;
	}
}
