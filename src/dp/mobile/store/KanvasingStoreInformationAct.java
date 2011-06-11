package dp.mobile.store;

import java.util.Calendar;
import java.util.GregorianCalendar;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class KanvasingStoreInformationAct extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storeinfo);
		final int storeID = getIntent().getExtras().getInt(Utilities.INTENT_STORE_ID);
		
		/// TODO : get store from id [need database]
		mInfo = new TextView[10];
		mInfo[0] = (TextView)findViewById(R.id.info1);
		mInfo[1] = (TextView)findViewById(R.id.info2);
		mInfo[2] = (TextView)findViewById(R.id.info3);
		mInfo[3] = (TextView)findViewById(R.id.info4);
		mInfo[4] = (TextView)findViewById(R.id.info5);
		mInfo[5] = (TextView)findViewById(R.id.info6);
		mInfo[6] = (TextView)findViewById(R.id.info7);
		mInfo[7] = (TextView)findViewById(R.id.info8);
		mInfo[8] = (TextView)findViewById(R.id.info9);
		
		Calendar now = new GregorianCalendar();
		
		mInfo[0].setText("" + now.get(Calendar.DAY_OF_MONTH) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.YEAR));
		mInfo[1].setText("" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));
		
		/// TODO : populate info lainnya
		
		mHistoryButton 		= (Button) findViewById(R.id.history);
		mKanvasingButton 	= (Button) findViewById(R.id.kanvasing);
		
		mHistoryButton.setOnClickListener(this);
		mKanvasingButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mHistoryButton) {
			startActivity(new Intent(this, KanvasingStoreHistoryAct.class));
		} else if (v == mKanvasingButton) {
			startActivityForResult(new Intent(this, KanvasingTransactionAct.class), Utilities.KANVASING_TRANSACTION_RC);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_TRANSACTION_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private TextView[] mInfo;
	private Button mHistoryButton, mKanvasingButton;
}
