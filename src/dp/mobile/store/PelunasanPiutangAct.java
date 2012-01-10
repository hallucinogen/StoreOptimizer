package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class PelunasanPiutangAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pelunasan_piutang);
		mStoreID	= getIntent().getExtras().getString(Utilities.INTENT_STORE_ID);
		
		initComp();
		populatePiutangList();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Pelunasan Piutang");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mPiutangTable 	= (TableLayout) findViewById(R.id.pelunasanpiutang_table);
	}
	
	private Cursor getReceivable(){
		int i=0;
		Cursor retval = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_trnreceivable.ar_no,mobile_trnreceivable.amount,mobile_trnreceivable.payment " +
				"FROM mobile_trnreceivable " +
				"WHERE mobile_trnreceivable.customer_code =? "
				, new String[]{mStoreID});

		return retval;
	}
	
	private void populatePiutangList(){
		int i = 0;
		Cursor receivable = getReceivable();		
		LayoutInflater inflater = getLayoutInflater();
		
		if(receivable.moveToFirst()){
			do{
				TableRow item		= (TableRow) inflater.inflate(R.layout.three_column, mPiutangTable, false);
				TextView ar_no		= (TextView) item.findViewById(R.id.item);
				TextView amount		= (TextView) item.findViewById(R.id.num1);
				TextView payment	= (TextView) item.findViewById(R.id.num2);
				
				item.setId(i);
				item.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//TODO: ke halaman pengisian pelunasan piutang (di Mobile Application.docx hal 25)
					}
				});
				
				ar_no.setText(receivable.getString(0));
				amount.setText(String.valueOf(receivable.getLong(1)));
				payment.setText(String.valueOf(receivable.getLong(2)));
				
				mPiutangTable.addView(item);
				++i;
			}while(receivable.moveToNext());
			
			receivable.close();
		}
	}

	private TableLayout	mPiutangTable; 
	private String		mStoreID;
	private TextView	mTitle, mNameTop, mRouteTop;
}
