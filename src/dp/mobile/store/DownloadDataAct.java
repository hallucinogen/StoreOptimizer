package dp.mobile.store;

import dp.mobile.store.helper.DatabaseAdapter;
import android.app.Activity;
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
	}
	
	@Override
	public void onClick(View v) {
		//TODO: connect to server and call for the webservice
	}
	
	private EditText 	mHostString, mUsername, mPassword;
	private Button		mDownloadBtn;
}
