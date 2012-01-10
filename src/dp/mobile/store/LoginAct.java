package dp.mobile.store;

import java.util.Date;

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
import dp.mobile.store.helper.tables.Counter;
import dp.mobile.store.helper.tables.Customer;
import dp.mobile.store.helper.tables.DailyNews;
import dp.mobile.store.helper.tables.DtlSales;
import dp.mobile.store.helper.tables.PriceList;
import dp.mobile.store.helper.tables.Product;
import dp.mobile.store.helper.tables.TrnReceivable;
import dp.mobile.store.helper.tables.TrnRoute;
import dp.mobile.store.helper.tables.TrnSales;
import dp.mobile.store.helper.tables.User;

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
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mDownloadDataButton 	= (Button) findViewById(R.id.download_data);
		
		mSettingButton.setOnClickListener(this);
		mDownloadDataButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
		
		insertDummyData();
		
		//Set username with the only one mobile_user record available (supposedly)
		Cursor userCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT username,password " +
				"FROM mobile_user", null);
		if(userCur.moveToFirst()){
			mUsername.setText(userCur.getString(0));
			mPassword.setText(userCur.getString(1));
		}
		userCur.close();
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
		DatabaseAdapter.instance(getBaseContext()).insert(Counter.getTableName(),
				new Counter("001", "IMEI", "JKT111", new Date(), 500, 0));
		
		//Dummy mobile_user
		DatabaseAdapter.instance(getBaseContext()).insert(User.getTableName(), 
				new User("001", "unitCompanyARTJKT", "Herlan Silaban", "user1", "W001-Jakarta Utara", 0));

		//Dummy mobile_dailynews
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), 
				new DailyNews("004", Utilities.formatStr("2011-January-01 00:00:00"), "darma", 
						"pengumuman tidak seberapa penting, ini hanya tampilan saja", 
						"pengumuman tidak seberapa penting, ini hanya tampilan saja, cuman yang di dibuat agak panjang karena memang begitu permintaannya", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DailyNews.getTableName(), 
				new DailyNews("002", Utilities.formatStr("2011-February-02 00:00:00"), "SBY", 
						"besok libur, bagi yang tidak libur yang masuk saja", 
						"besok libur, bagi yang tidak libur yang masuk saja, karena ini bukan paksaan", 0));
		
		//Dummy mobile_product
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("001", "RKM", "Rokok Merah", "Rkk Merah", "Kretek", "Rokok Kretek", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("002", "RKB", "Rokok Biru", "Rkk Biru", "Kretek", "Rokok Kretek", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("003", "RKH", "Rokok Hijau", "Rkk Hijau", "Kretek", "Rokok Kretek", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("004", "RKP", "Rokok Putih", "Rkk Putih", "Filter", "Rokok Filter", 0));
		DatabaseAdapter.instance(getBaseContext()).insert(Product.getTableName(),
				new Product("005", "RKA", "Rokok Abu Abu", "Rkk Abu Abu", "Filter", "Rokok Filter", 0));
		
		//Dummy mobile_trnroute
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C1", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 101, "A001", "Abadi Jaya",
						"Jl. Pahlawan 101 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C2", Utilities.formatStr("2011-January-0 00:00:001"),"user1",
						"ARTJKT", 102, "B001", "Berkat",
						"Jl. Gajah Mada 201 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C3", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 103, "C001", "Cendana",
						"Jl. Untung Suropati 301 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C4", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 104, "D001", "Delta Persada",
						"Jl. Anugrah 401 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C5", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 105, "E001", "Enoki",
						"Jl. Pajajaran 501 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C6", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 106, "F001", "Fajar Sejahtera",
						"Jl. Raya 601 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C7", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 107, "G001", "Gajah Mada",
						"Jl. Hayam Wuruk 701 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C8", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 108, "H001", "Hujan",
						"Jl. Sudirman 801 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnRoute.getTableName(), 
				new TrnRoute("C9", Utilities.formatStr("2011-January-01 00:00:00"),"user1",
						"ARTJKT", 109, "I001", "Indah",
						"Jl. Tamrin 901 Jakarta", "12345", "12",
						"Retail", "0", 0, 0, Utilities.formatStr("2011-January-01 00:00:00"), "descr", "Not Yet"));
				
		//Dummy mobile_pricelist
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("001", "user1", "ARTJKT", "A001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("002", "user1", "ARTJKT", "A001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("003", "user1", "ARTJKT", "A001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("004", "user1", "ARTJKT", "A001", "RKK", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("005", "user1", "ARTJKT", "A001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("006", "user1", "ARTJKT", "A001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("007", "user1", "ARTJKT", "B001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("008", "user1", "ARTJKT", "B001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("009", "user1", "ARTJKT", "B001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("010", "user1", "ARTJKT", "B001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("011", "user1", "ARTJKT", "B001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("012", "user1", "ARTJKT", "B001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("013", "user1", "ARTJKT", "C001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("014", "user1", "ARTJKT", "C001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("015", "user1", "ARTJKT", "C001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("016", "user1", "ARTJKT", "C001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("017", "user1", "ARTJKT", "C001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("018", "user1", "ARTJKT", "C001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("019", "user1", "ARTJKT", "D001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("020", "user1", "ARTJKT", "D001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("021", "user1", "ARTJKT", "D001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("022", "user1", "ARTJKT", "D001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("023", "user1", "ARTJKT", "D001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("024", "user1", "ARTJKT", "D001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("025", "user1", "ARTJKT", "E001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("026", "user1", "ARTJKT", "E001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("027", "user1", "ARTJKT", "E001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("028", "user1", "ARTJKT", "E001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("029", "user1", "ARTJKT", "E001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("030", "user1", "ARTJKT", "E001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("031", "user1", "ARTJKT", "F001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("032", "user1", "ARTJKT", "F001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("033", "user1", "ARTJKT", "F001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("034", "user1", "ARTJKT", "F001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("035", "user1", "ARTJKT", "F001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("036", "user1", "ARTJKT", "F001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("037", "user1", "ARTJKT", "G001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("038", "user1", "ARTJKT", "G001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("039", "user1", "ARTJKT", "G001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("040", "user1", "ARTJKT", "G001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("041", "user1", "ARTJKT", "G001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("042", "user1", "ARTJKT", "G001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("043", "user1", "ARTJKT", "H001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("044", "user1", "ARTJKT", "H001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("045", "user1", "ARTJKT", "H001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("046", "user1", "ARTJKT", "H001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("047", "user1", "ARTJKT", "H001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("048", "user1", "ARTJKT", "H001", "RKA", 7600, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("049", "user1", "ARTJKT", "I001", "RKM", 7100, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("050", "user1", "ARTJKT", "I001", "RKB", 7200, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("051", "user1", "ARTJKT", "I001", "RKH", 7300, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("052", "user1", "ARTJKT", "I001", "RKP", 7400, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("053", "user1", "ARTJKT", "I001", "RKK", 7500, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(PriceList.getTableName(),
				new PriceList("054", "user1", "ARTJKT", "I001", "RKA", 7600, 0));
		
		//Dummy mobile_trnreceivable
		DatabaseAdapter.instance(getBaseContext()).insert(TrnReceivable.getTableName(),
				new TrnReceivable("001", Utilities.formatStr("2011-January-01 00:00:00"), 
						"user1", "ARTJKT", "A001", "ar001/2011",
						Utilities.formatStr("2011-January-01 00:00:00"),
						25000000, 0, "descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnReceivable.getTableName(),
				new TrnReceivable("002", Utilities.formatStr("2011-January-01 00:00:00"), 
						"user1", "ARTJKT", "B001", "ar002/2011",
						Utilities.formatStr("2011-January-01 00:00:00"),
						75000000, 0, "descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnReceivable.getTableName(),
				new TrnReceivable("003", Utilities.formatStr("2011-January-01 00:00:00"), 
						"user1", "ARTJKT", "C001", "ar003/2011",
						Utilities.formatStr("2011-January-01 00:00:00"),
						50000000, 0, "descr"));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnReceivable.getTableName(),
				new TrnReceivable("004", Utilities.formatStr("2011-January-01 00:00:00"), 
						"user1", "ARTJKT", "C001", "ar004/2011",
						Utilities.formatStr("2011-January-01 00:00:00"),
						65000000, 0, "descr"));

		//Dummy mobile_customer
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C1", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"A001", "Abadi Jaya", "Jl. Pahlawan 101 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C2", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"B001", "Berkat", "Jl. Gajah Mada 201 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C3", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"C001", "Cendana", "Jl. Untung Suropati 301 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C4", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"D001", "Delta Persada", "Jl. Anugrah 301 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C5", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"E001", "Enoki", "Jl. Pajajaran 501 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C6", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"F001", "Fajar Sejahtera", "Jl. Raya 601 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C7", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"G001", "Gajah Mada", "Jl. Hayam Wuruk 701 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C8", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"H001", "Hujan", "Jl. Sudirman 801 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		DatabaseAdapter.instance(getBaseContext()).insert(Customer.getTableName(),
				new Customer("C9", Utilities.formatStr("2011-January-01 00:00:00"), "user1",
						"I001", "Indah", "Jl. Tamrin 901 Jakarta", "12345",
						"city", "country", "contact", "customerGroupCode", "group 001",
						"customerCategoryCode", "Retail", "customerTypeCode", 
						"type 001", 10000000, 0, "unitCompanyID", 
						"pricelistGroupCode", "group 001"));
		
		//Dummy mobile_dtlsales
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("1", "1", "RKM", 500, 500, 0, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("2", "1", "RKB", 750, 1250, 500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("3", "1", "RKH", 2000, 4500, 2500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("4", "1", "RKP", 0, 500, 500, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("5", "1", "RKK", 6000, 6000, 0, 1500, 100, 50, 50, 50, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(DtlSales.getTableName(),
				new DtlSales("6", "1", "RKA", 1000, 1000, 0, 1500, 100, 50, 50, 50, 0));
		
		//Dummy mobile_trnsales
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("1", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref01", "ar01", "desc01", "checkNo01", Utilities.formatStr("2011-October-15 10:15:07"),
						"A001", "40115", "SAT01", 10000000, 10000000, 10000000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("2", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref02", "ar02", "desc02", "checkNo02", Utilities.formatStr("2011-October-15 10:15:07"),
						"B001", "40115", "SAT02", 15000000, 15000000, 15000000, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("3", "ARTJKT", "01", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref03", "ar03", "desc03", "checkNo03", Utilities.formatStr("2011-October-15 10:15:07"),
						"C001", "40115", "SAT03", 0, 0, 0, 0));
		DatabaseAdapter.instance(getBaseContext()).insert(TrnSales.getTableName(),
				new TrnSales("4", "ARTJKT", "51", Utilities.formatStr("2011-October-15 10:15:07"),
						"ref04", "ar04", "desc04", "checkNo04", Utilities.formatStr("2011-October-15 10:15:07"),
						"D001", "40115", "SAT04", 20000000, 20000001, 20000002, 0));
		
		//Tes raw/standard query
		//DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT * FROM mobile_dailynews JOIN mobile_trnroute ON mobile_dailynews.id = mobile_trnroute.id", null);
		
		//Tes close
		DatabaseAdapter.instance(getBaseContext()).close();
	}
}
