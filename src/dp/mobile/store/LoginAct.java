package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.DailyNews;

public class LoginAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mSettingButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		
		//Tes open database
		DatabaseAdapter.instance(getBaseContext()).open();
		
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), new DailyNews("1", Utilities.formatStr("2011-February-24 12:23:23"), "Dari Pertama", "Ini namanya berita", "Ini namanya deskripsi panjang dari berita pertama"));
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), new DailyNews("2", Utilities.formatStr("2011-July-21 11:23:23"), "Dari Kedua", "Ini namanya bukan berita", "Ini namanya deskripsi panjang dari berita kedua"));
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), new DailyNews("3", Utilities.formatStr("2011-October-15 10:23:23"), "Dari Ketiga", "Ini baru berita yang benar", "Ini namanya deskripsi panjang dari berita ketiga"));
		
		/*//Tes inserting
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), new DailyNews("1", new Date(), "newsFrom", "short description", "long descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), new DailyNews("2", new Date(), "newsFrom2", "1st short description", "2nd long descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), new TrnRoute("1", new Date(), "username", "unitCompanyCode", -1, "customerCode", "customerName", "customerAddress", "customerPostCode", "customerSatellite", "customerType", "customerTermPayment", -1, -1, new Date(), "descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), new TrnRoute("2", new Date(), "2username", "2unitCompanyCode", -1, "2customerCode", "2customerName", "2customerAddress", "2customerPostCode", "2customerSatellite", "2customerType", "2customerTermPayment", -1, -1, new Date(), "2descr"));
		
		//Tes raw/standard query
		DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT * FROM mobile_dailynews JOIN mobile_trnroute ON mobile_dailynews.id = mobile_trnroute.id", null);
		
		//Tes getAll
		DailyNews[] dailyNewses = (DailyNews[])DatabaseAdapter.instance(getBaseContext()).getAll(DailyNews.getTableName());
		for(DailyNews dailyNews : dailyNewses){
			Log.d("TES DAILY NEWS", dailyNews.mID + "#" + dailyNews.mNewsFrom + "#" + dailyNews.mDescShort + "#" + dailyNews.mDesc);
		}
		
		//Tes getAll
		TrnRoute[] trnRoutes = (TrnRoute[])DatabaseAdapter.instance(getBaseContext()).getAll(TrnRoute.getTableName());
		for(TrnRoute trnRoute : trnRoutes){
			Log.d("TES TRN ROUTE", trnRoute.mID + "#" + trnRoute.mCustomerCode + "#" + trnRoute.mCustomerName);
		}*/
		
		//Tes close
		DatabaseAdapter.instance(getBaseContext()).close();
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
