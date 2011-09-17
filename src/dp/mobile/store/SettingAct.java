package dp.mobile.store;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import dp.mobile.store.helper.Utilities;

public class SettingAct extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		initComp();
	}
	
	private void initComp(){
		mIMEI		= (TextView) findViewById(R.id.imei_txt);
		mPhoneNumber= (TextView) findViewById(R.id.phone_number_txt);
		mSaveBtn	= (Button) findViewById(R.id.save_btn);
		
		mIPPublic	= (EditText) findViewById(R.id.ip_public_edit_txt);
		mIPServer	= (EditText) findViewById(R.id.ip_server_edit_txt);
		mHostString	= (EditText) findViewById(R.id.host_string_edit_txt);
		mDefUser	= (EditText) findViewById(R.id.default_user_edit_txt);
		
		mTelManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mIMEI.setText(mTelManager.getDeviceId());
		mPhoneNumber.setText(mTelManager.getLine1Number());
		
		mSaveBtn.setOnClickListener(this);
		populateField();
	}
	
	private void populateField(){
		String[] loadedPref = Utilities.loadPred(getBaseContext());
		
		if(!loadedPref[0].equals(""))
			mIPPublic.setText(loadedPref[0]);
		if(!loadedPref[1].equals(""))
			mIPServer.setText(loadedPref[1]);
		if(!loadedPref[2].equals(""))
			mHostString.setText(loadedPref[2]);
		if(!loadedPref[3].equals(""))
			mDefUser.setText(loadedPref[3]);
	}
	
	@Override
	public void onClick(View v) {
		if(v == mSaveBtn){
			Utilities.savePref(getBaseContext(), mIPServer.getText().toString(), mIPPublic.getText().toString(), mHostString.getText().toString(), mDefUser.getText().toString());
			finish();
		}
	}
	
	private TextView	mIMEI, mPhoneNumber;
	private EditText	mIPPublic, mIPServer, mHostString, mDefUser;
	private Button		mSaveBtn;
	private TelephonyManager mTelManager;
}
