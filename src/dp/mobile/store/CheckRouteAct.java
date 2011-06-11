package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CheckRouteAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_route);
		
		mLinear = (LinearLayout) findViewById(R.id.route_linear);
		
		populateLinearWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateLinearWithDummy() {
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < 20; ++i) {
			LinearLayout item = (LinearLayout)inflater.inflate(R.layout.store_thumb, mLinear, false);
			
			mLinear.addView(item);
		}
	}
	
	private LinearLayout mLinear;
}
