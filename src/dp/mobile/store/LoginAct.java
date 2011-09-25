package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.*;

public class LoginAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		loadComponent();
	}
	
	private void loadComponent(){
		mUsername		= (EditText) findViewById(R.id.username);
		mPassword		= (EditText) findViewById(R.id.password);
		mPassword.setText("user1");
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mDownloadDataButton 	= (Button) findViewById(R.id.download_data);
		
		mSettingButton.setOnClickListener(this);
		mDownloadDataButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		
		insertDummyData();
		
		//Set username with the only one mobile_user record available (supposedly)
		Cursor userCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT username " +
				"FROM mobile_user", null);
		if(userCur.moveToFirst())
			mUsername.setText(userCur.getString(0));
	}
	
	public void onClick(View v) {
		if (v == mSettingButton) {
			startActivity(new Intent(LoginAct.this, SettingAct.class));
		} else if (v == mDownloadDataButton) {
			startActivity(new Intent(LoginAct.this, DownloadDataAct.class));
		} else if (v == mLoginButton) {
			String username = mUsername.getText().toString();
			String password	= mPassword.getText().toString();
			
			if(!username.equals("") && !password.equals("")){
				if(loginSucceded(username, password)){
					finish();
					startActivity(new Intent(LoginAct.this, MainMenuAct.class));
				} else {
					Toast.makeText(getBaseContext(), "Username and Password do not match", Toast.LENGTH_SHORT).show();
				}
			} else {
				if(username.equals("") && password.equals(""))
					Toast.makeText(getBaseContext(), "Please enter your username and password", Toast.LENGTH_SHORT).show();
				else if(username.equals(""))
					Toast.makeText(getBaseContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
				else if(password.equals(""))
					Toast.makeText(getBaseContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private boolean loginSucceded(String username, String password){
		Cursor checkUserCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT username, password " +
				"FROM mobile_user WHERE username=? AND password=?", new String[]{username, password});
		
		if(checkUserCur.getCount() > 0){
			checkUserCur.close();
			return true;
		}
		else{
			checkUserCur.close();
			return false;
		}
	}
	
	private Button mSettingButton, mLoginButton, mDownloadDataButton;
	private EditText mUsername, mPassword;
	
	private void insertDummyData(){
		//Tes open database
		DatabaseAdapter.instance(getBaseContext()).open();
		
		//Dummy mobile_counter
		DatabaseAdapter.instance(getBaseContext()).insert(Counter.getTableName(), new Counter("1", "356735040640022", "COUNTER01", Utilities.formatStr("2011-September-15 00:00:00"), 123456, 0));
		
		//Dummy mobile_user
		DatabaseAdapter.instance(getBaseContext()).insert(User.getTableName(), 
				new User("001", "ARTJKT", "user1", "user1", "User pertama", 0));

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
						"ARTJKT", 1, "CUST01", "Aneka Jaya", 
						"Jalan Diponegoro 10 Jakarta", "customerPostCodeTest", "customerSatelliteTest", 
						"customerTypeTest", "customerTermpaymentTest", 4, 2, 
						Utilities.formatStr("2011-February-24 12:23:23"), "descrTest", "08:30-08:45"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("2", Utilities.formatStr("2011-July-21 11:24:10"), "usernameTest2", 
						"ARTJKT", 2, "CUST02", "Merpati Putih", 
						"Jalan Besar 100 Jakarta", "customerPostCodeTest2", "customerSatelliteTest2", 
						"customerTypeTest2", "customerTermpaymentTest2", 3, 4, 
						Utilities.formatStr("2011-July-21 11:24:10"), "descrTest2", "09:00-09:30"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("3", Utilities.formatStr("2011-October-15 10:15:07"), "usernameTest3", 
						"ARTJKT", 3, "CUST03", "Photo Indah", 
						"Jalan Wisata 200 Jakarta", "customerPostCodeTest3", "customerSatelliteTest3", 
						"customerTypeTest3", "customerTermpaymentTest3", 5, 6, 
						Utilities.formatStr("2011-October-15 10:15:07"), "descrTest3", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("4", Utilities.formatStr("2011-October-15 10:15:07"), "usernameTest4", 
						"ARTJKT", 4, "CUST04", "Duta Perkasa", 
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
				
		//Dummy mobile_dtlsales
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
		
		//Dummy mobile_customer
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("1", Utilities.formatStr("2011-October-15 10:15:07"), "user1", "CUST01",
						"Aneka Jaya", "Jalan Diponegoro 10 Jakarta", "postCode01", "Jakarta", "Indonesia", "contact01"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("2", Utilities.formatStr("2011-October-15 10:15:07"), "user1", "CUST02",
						"Merpati Putih", "Jalan Besar 100 Jakarta", "postCode02", "Jakarta", "Indonesia", "contact02"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("3", Utilities.formatStr("2011-October-15 10:15:07"), "user1", "CUST03",
						"Photo Indah", "Jalan Wisata 200 Jakarta", "postCode03", "Jakarta", "Indonesia", "contact03"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("4", Utilities.formatStr("2011-October-15 10:15:07"), "user1", "CUST04",
						"Duta Perkasa", "Jalan Juanda 200 Jakarta", "postCode04", "Jakarta", "Indonesia", "contact04"));
		
		//Dummy mobile_trnsales
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("1", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref01", "ar01", "desc01", "checkNo01", Utilities.formatStr("2011-October-15 10:15:07"),
						"CUST01", "40115", "SAT01", 10000000, 10000000, 10000000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("2", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref02", "ar02", "desc02", "checkNo02", Utilities.formatStr("2011-October-15 10:15:07"),
						"CUST02", "40115", "SAT02", 15000000, 15000000, 15000000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("3", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref03", "ar03", "desc03", "checkNo03", Utilities.formatStr("2011-October-15 10:15:07"),
						"CUST03", "40115", "SAT03", 0, 0, 0, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("4", "ARTJKT", "02", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref04", "ar04", "desc04", "checkNo04", Utilities.formatStr("2011-October-15 10:15:07"),
						"CUST04", "40115", "SAT04", 0, 0, 0, 0));
		
		//Dummy mobile_pricelist
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("1", "user1", "ARTJKT", "CUST01", "CIGAR01", 10000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("2", "user1", "ARTJKT", "CUST01", "CIGAR02", 5000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("3", "user1", "ARTJKT", "CUST01", "CIGAR03", 6000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("4", "user1", "ARTJKT", "CUST01", "CIGAR04", 7000, 0));
		
		//Tes raw/standard query
		//DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT * FROM mobile_dailynews JOIN mobile_trnroute ON mobile_dailynews.id = mobile_trnroute.id", null);
		
		//Tes close
		DatabaseAdapter.instance(getBaseContext()).close();
	}
}
