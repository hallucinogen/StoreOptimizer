package dp.mobile.store;

import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DownloadDataAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		
		initComp();
	}
	
	private void initComp(){
		mHostString	= (EditText) findViewById(R.id.host_string_edit_txt);
		mUsername	= (EditText) findViewById(R.id.username_edit_txt);
		mPassword	= (EditText) findViewById(R.id.password_edit_txt);
		
		mDownloadBtn	= (Button) findViewById(R.id.download_btn);
		mDownloadBtn.setOnClickListener(this);
		
		//Set username with the only one mobile_user record available (supposedly)
		Cursor userCur = DatabaseAdapter.instance(getBaseContext()).rawQuery("SELECT username " +
				"FROM mobile_user", null);
		if(userCur.moveToFirst())
			mUsername.setText(userCur.getString(0));
		
		SharedPreferences sharedPref = getSharedPreferences(Utilities.PREFERENCES, 0);
		Editor sharedPrefEditor = sharedPref.edit();
		
		sharedPrefEditor.putInt(Utilities.MAIN_MENU_STATUS, Utilities.MAIN_MENU_INITIAL);
		sharedPrefEditor.commit();		
	}
	
	@Override
	public void onClick(View v) {
		//TODO: connect to server and call for the webservice
	}
	
	private EditText 	mHostString, mUsername, mPassword;
	private Button		mDownloadBtn;
}
