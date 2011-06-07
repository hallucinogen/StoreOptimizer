package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class DailyNewsAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_news);
		mLinear = (LinearLayout)findViewById(R.id.news_linear);
		
		populateLinearWithDummy();
	}
	
	private void populateLinearWithDummy() {
		
	}
	
	private LinearLayout mLinear;
}
