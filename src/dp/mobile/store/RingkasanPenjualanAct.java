package dp.mobile.store;

import java.text.NumberFormat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;

public class RingkasanPenjualanAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ringkasan_penjualan);
		
		initComp();
		populateAmount();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Ringkasan Penjualan");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mPenjualanTotal = (TextView) findViewById(R.id.penjualan_total_txtview);
		mCashAbove		= (TextView) findViewById(R.id.cash_above_txtview);
		mKredit			= (TextView) findViewById(R.id.kredit_txtview);
		mPelunasanPiutang = (TextView) findViewById(R.id.piutang_txtview);
		mCashBelow		= (TextView) findViewById(R.id.cash_below_txtview);
		mCheckGiro		= (TextView) findViewById(R.id.cekgiro_txtview);
	}
	
	private void populateAmount(){
		Cursor cursor;
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount) " +
				"FROM mobile_trnsales " +
				"WHERE trncode = ?", new String[]{"01"});
		cursor.moveToFirst();
		mPenjualanTotal.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount_cash) " +
				"FROM mobile_trnsales " +
				"WHERE trncode = ?", new String[]{"01"});
		cursor.moveToFirst();
		mCashAbove.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount) " +
				"FROM mobile_trnsales " +
				"WHERE ar_no != ? AND trncode = ?", new String[]{"", "01"});
		cursor.moveToFirst();
		mKredit.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount) " +
				"FROM mobile_trnsales " +
				"WHERE trncode = ?", new String[]{"51"});
		cursor.moveToFirst();
		mPelunasanPiutang.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount_cash) " +
				"FROM mobile_trnsales " +
				"WHERE trncode = ?", new String[]{"51"});
		cursor.moveToFirst();
		mCashBelow.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
		
		cursor = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT SUM(amount_check) " +
				"FROM mobile_trnsales " +
				"WHERE trncode = ?", new String[]{"51"});
		cursor.moveToFirst();
		mCheckGiro.setText("Rp " + NumberFormat.getIntegerInstance().format(cursor.getInt(0)));
		cursor.close();
	}
	
	private TextView	mTitle, mNameTop, mRouteTop;
	private TextView	mPenjualanTotal, mCashAbove, mKredit, mPelunasanPiutang, mCashBelow, mCheckGiro;
}
