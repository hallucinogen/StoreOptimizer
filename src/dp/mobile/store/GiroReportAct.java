package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GiroReportAct extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giro_report);
		
		initComp();
		populateLinearWithDummy();
	}
	
	private void initComp(){
		mTitle		= (TextView) findViewById(R.id.header_title);
		mNameTop	= (TextView) findViewById(R.id.header_nametop);
		mRouteTop	= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Transaksi Stok");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mLinear = (LinearLayout) findViewById(R.id.giro_linear);
	}
	
	/// TODO : not using dummy
	private void populateLinearWithDummy() {
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < 20; ++i) {
			LinearLayout item 	= (LinearLayout)inflater.inflate(R.layout.check_route_adpt, mLinear, false);
			TextView account	= (TextView) item.findViewById(R.id.store_down);
			TextView amount		= (TextView) item.findViewById(R.id.store_right);
			
			account.setText("BCA 123142");
			amount.setText("Rp 1.500.000");
			
			mLinear.addView(item);
		}
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private LinearLayout mLinear;
}
