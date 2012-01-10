package dp.mobile.store;

import java.text.NumberFormat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import dp.mobile.store.adapter.CashReport;
import dp.mobile.store.adapter.CashReportAdapter;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class StatusCashAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cash_report);
		
		initComp();
		populateTableCashReport();
	}
	
	private void initComp(){
		mTitle		= (TextView) findViewById(R.id.header_title);
		mNameTop	= (TextView) findViewById(R.id.header_nametop);
		mRouteTop	= (TextView) findViewById(R.id.header_routetop);
		
		mCashReportListView = (ListView) findViewById(R.id.cash_report_listview);
		mTotal				= (TextView) findViewById(R.id.total);
		
		mTitle.setText("Status Cash");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
	}
	
	private void populateTableCashReport() {
		int i=0;
		long totalAmountCash=0;
		
		Cursor cashReportCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_customer.customer_name, " +
				"mobile_customer.address, mobile_trnsales.amount_cash " +
				"FROM mobile_trnsales JOIN mobile_customer ON " +
				"mobile_trnsales.customer_code = mobile_customer.customer_code " +
				"WHERE mobile_trnsales.amount_cash > 0 AND mobile_trnsales.trncode = '51'" +
				"ORDER BY mobile_trnsales.refno ASC", null);
		
		CashReport[] cashReports = new CashReport[cashReportCur.getCount()];
		if(cashReportCur.moveToFirst()){
    		do{
    			cashReports[i++] = new CashReport(cashReportCur.getString(0), cashReportCur.getString(1),
    					cashReportCur.getLong(2));
    			totalAmountCash += cashReportCur.getLong(2);
    		}while(cashReportCur.moveToNext());
    		
    		cashReportCur.close();
		}
		
		mAdpt = new CashReportAdapter(this, cashReports);
		mCashReportListView.setAdapter(mAdpt);
		mTotal.setText("Rp " + NumberFormat.getIntegerInstance().format(totalAmountCash));
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private ListView mCashReportListView;
	private	CashReportAdapter mAdpt;
	private TextView mTotal;
}
