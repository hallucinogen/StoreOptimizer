package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mSettingButton 	= (Button) findViewById(R.id.setting);
		mLoginButton	= (Button) findViewById(R.id.login);
		mSettingButton.setOnClickListener(this);
		mLoginButton.setOnClickListener(this);
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
