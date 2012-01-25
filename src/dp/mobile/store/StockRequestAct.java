package dp.mobile.store;

import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.Product;
import dp.mobile.store.helper.tables.TrnSales;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StockRequestAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_request);
	
		initComp();
		populateTableWithProducts();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Penerimaan Stock");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mStockTable = (TableLayout) findViewById(R.id.stock_table);
		mConfirmBtn = (Button) findViewById(R.id.confirm);
		
		mConfirmBtn.setOnClickListener(this);
	}
	
	private void populateTableWithProducts() {
		mProducts = (Product[]) DatabaseAdapter.instance(getBaseContext()).getAll(Product.getTableName(), null, null);
		LayoutInflater inflater = getLayoutInflater();
			
		for (int i = 0; i < mProducts.length; ++i){
			TableRow row 		= (TableRow)inflater.inflate(R.layout.two_column_with_dialog, mStockTable, false);
			TextView item 		= (TextView)row.findViewById(R.id.item);
			TextView stock 		= (TextView)row.findViewById(R.id.num1);
			
			item.setText(mProducts[i].mProductName);
			
			stock.setText("0");
			setDialog(stock);
			
			row.setId(i);
			mStockTable.addView(row);
		}
	}
	
	private void setDialog(final TextView txtView){
		txtView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                final Dialog dialog = new Dialog(StockRequestAct.this);
                dialog.setContentView(R.layout.counter_dialog);
                dialog.setTitle("Masukkan jumlah");
                dialog.setCancelable(true);
                
                final EditText	num		= (EditText) dialog.findViewById(R.id.timepicker_input);
                Button		okBtn		= (Button) dialog.findViewById(R.id.counter_ok);
                Button		cancelBtn	= (Button) dialog.findViewById(R.id.counter_cancel);
                num.setText(txtView.getText().toString());
                
                okBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						txtView.setText(num.getText().toString());
						dialog.dismiss();
					}
				});
                
                cancelBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
                
                dialog.show();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmBtn) {
			for(int i=0; i<mProducts.length; ++i){
				TableRow row 		= (TableRow)mStockTable.findViewById(i);
				TextView stock 		= (TextView)row.findViewById(R.id.num1);
				
				//TODO: masukkan ke trnsales dan dtlsales
			}
			
			finish();
		}
	}
	
	private TableLayout mStockTable;
	private TextView	mTitle, mNameTop, mRouteTop;
	private Product		mProducts[];
	private Button		mConfirmBtn;
}
