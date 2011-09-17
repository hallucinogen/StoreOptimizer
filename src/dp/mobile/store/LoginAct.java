package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.*;

public class LoginAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mSettingButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		
		insertDummyData();
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
	
	private void insertDummyData(){
		//Tes open database
		DatabaseAdapter.instance(getBaseContext()).open();
		
		//Dummy mobile_counter
		DatabaseAdapter.instance(getBaseContext()).insert(Counter.getTableName(), new Counter("001", "356735040640022", "JKT111", Utilities.formatStr("2011-September-15 00:00:00"), 500, 0));
		
		//Dummy mobile_user
		DatabaseAdapter.instance(getBaseContext()).insert(User.getTableName(), 
				new User("001", "ARTJKT", "user1", "user1", "User pertama", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(User.getTableName(), 
				new User("002", "ARTJKT", "user2", "user2", "User kedua", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(User.getTableName(), 
				new User("003", "ARTJKT", "user3", "user3", "User ketiga", 0));

		//Dummy mobile_dailynews
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), 
				new DailyNews("001", Utilities.formatStr("2011-January-1 12:23:23"), "darma", 
						"pengumuman tidak seberapa penting, ini hanya tampilan saja", 
						"pengumuman tidak seberapa penting, ini hanya tampilan saja, cuman yang di dibuat agak panjang karena memang begitu permintaannya", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), 
				new DailyNews("002", Utilities.formatStr("2011-February-2 11:23:23"), "SBY", 
						"besok libur, bagi yang tidak libur yang masuk saja", 
						"besok libur, bagi yang tidak libur yang masuk saja, karena ini bukan paksaan", 0));
		
		//Dummy mobile_trnroute
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("1", Utilities.formatStr("2011-February-24 12:23:23"), "usernameTest", 
						"unitCompanyCodeTest", 1, "customerCodeTest", "Aneka Jaya", 
						"Jalan Diponegoro 10 Jakarta", "customerPostCodeTest", "customerSatelliteTest", 
						"customerTypeTest", "customerTermpaymentTest", 2, 2, 
						Utilities.formatStr("2011-February-24 12:23:23"), "descrTest", "08:30-08:45"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("2", Utilities.formatStr("2011-July-21 11:24:10"), "usernameTest2", 
						"unitCompanyCodeTest2", 2, "customerCodeTest2", "Merpati Putih", 
						"Jalan Besar 100 Jakarta", "customerPostCodeTest2", "customerSatelliteTest2", 
						"customerTypeTest2", "customerTermpaymentTest2", 3, 4, 
						Utilities.formatStr("2011-July-21 11:24:10"), "descrTest2", "09:00-09:30"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("3", Utilities.formatStr("2011-October-15 10:15:07"), "usernameTest3", 
						"unitCompanyCodeTest3", 3, "customerCodeTest3", "Photo Indah", 
						"Jalan Wisata 200 Jakarta", "customerPostCodeTest3", "customerSatelliteTest3", 
						"customerTypeTest3", "customerTermpaymentTest3", 5, 6, 
						Utilities.formatStr("2011-October-15 10:15:07"), "descrTest3", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("4", Utilities.formatStr("2011-October-15 10:15:07"), "usernameTest4", 
						"unitCompanyCodeTest3", 4, "customerCodeTest4", "Duta Perkasa", 
						"Jalan Juanda 200 Jakarta", "customerPostCodeTest4", "customerSatelliteTest4", 
						"customerTypeTest4", "customerTermpaymentTest4", 5, 6, 
						Utilities.formatStr("2011-October-15 10:15:07"), "descrTest4", "Not Yet"));
		
		//Dummy mobile_product
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("1", "CIGAR01", "Rokok Merah", "RokMer", "GROUP01", "Grup Pertama", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("2", "CIGAR02", "Rokok Putih", "RokPut", "GROUP01", "Grup Pertama", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("3", "CIGAR03", "Rokok Hijau", "RokHij", "GROUP02", "Grup Kedua", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("4", "CIGAR04", "Rokok Kuning", "RokKun", "GROUP02", "Grup Kedua", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("5", "CIGAR05", "Rokok Ungu", "RokUng", "GROUP03", "Grup Ketiga", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("6", "CIGAR06", "Rokok", "Rok", "GROUP04", "Grup Keempat", 0));
				
		//Dummy Stock Report
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("1", "1", "CIGAR01", 500, 500, 0, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("2", "1", "CIGAR02", 750, 1250, 500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("3", "1", "CIGAR03", 2000, 4500, 2500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("4", "1", "CIGAR04", 0, 500, 500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("5", "1", "CIGAR05", 6000, 6000, 0, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("6", "1", "CIGAR06", 1000, 1000, 0, 1500, 100, 50, 50, 50, 0));
		
		//Tes raw/standard query
		//DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT * FROM mobile_dailynews JOIN mobile_trnroute ON mobile_dailynews.id = mobile_trnroute.id", null);
		
		//Tes close
		DatabaseAdapter.instance(getBaseContext()).close();
	}
}
