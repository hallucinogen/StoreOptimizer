package dp.mobile.store;

import java.text.NumberFormat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import dp.mobile.store.adapter.CashReportAdapter;
import dp.mobile.store.helper.DatabaseAdapter;

public class CashReportAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cash_report);
		
		mCashReportListView = (ListView) findViewById(R.id.cash_report_listview);
		mTotal				= (TextView) findViewById(R.id.total);
		populateTableCashReport();
	}
	
	private void populateTableCashReport() {
		int i=0;
		long totalAmountCash=0;
		
		Cursor cashReportCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT " +
				"mobile_customer.customer_name, " +
				"mobile_customer.address, mobile_trnsales.amount_cash " +
				"FROM mobile_trnsales JOIN mobile_customer ON " +
				"mobile_trnsales.customer_code = mobile_customer.customer_code " +
				"WHERE mobile_trnsales.amount_cash > 0 " +
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
	
	private ListView mCashReportListView;
	private	CashReportAdapter mAdpt;
	private TextView mTotal;
	
	public class CashReport{
		public CashReport(String custName, String custAddr, long amountCash){
			mCustomerName 	= custName;
			mCustomerAddr	= custAddr;
			mAmountCash		= amountCash;
		}
		
		public String	mCustomerName, mCustomerAddr;
		public long		mAmountCash;
	}
}
