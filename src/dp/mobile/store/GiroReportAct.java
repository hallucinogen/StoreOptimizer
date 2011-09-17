package dp.mobile.store;

import android.app.Activity;
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
		
		mLinear = (LinearLayout) findViewById(R.id.giro_linear);
		
		populateLinearWithDummy();
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
	
	private LinearLayout mLinear;
}
