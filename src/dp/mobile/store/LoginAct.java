package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dp.mobile.store.helper.tables.DailyNewsAdapter;
import dp.mobile.store.helper.tables.TrnRouteAdapter;

public class LoginAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mSettingButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		
		//Tes database
		DailyNewsAdapter mDailyNews = new DailyNewsAdapter(getBaseContext());
		TrnRouteAdapter mTrnRoute = new TrnRouteAdapter(getBaseContext());
		
		mDailyNews.open();
		mTrnRoute.open();
		
		Log.d("DAILY NEWS CREATE", "rowID = " + mDailyNews.create("1", null, null, "short description", "long descr"));
		
		mDailyNews.close();
		mTrnRoute.close();
	}
	
	public void onClick(View v) {
		if (v == mSettingButton) {
			startActivity(new Intent(LoginAct.this, SettingAct.class));
		}
		if (v == mLoginButton) {
			/// TODO : verify login
			finish();
			startActivity(new Intent(LoginAct.this, MainMenuAct.class));
		}
	}
	
	private Button mSettingButton, mLoginButton;
}
