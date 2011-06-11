package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class KanvasingFinishAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_finish);
		
		mConfirmButton = (Button) findViewById(R.id.confirm);
		mConfirmButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mConfirmButton) {
			setResult(RESULT_OK);
			Toast.makeText(this, "Transaction Complete", 1000).show();
			finish();
		}
	}
	
	private Button mConfirmButton;
}
