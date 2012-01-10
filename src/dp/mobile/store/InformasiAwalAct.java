package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dp.mobile.store.helper.Utilities;

public class InformasiAwalAct extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.informasi_awal);
		
		initComp();
	}
	
	private void initComp(){
		mTitle			= (TextView) findViewById(R.id.header_title);
		mNameTop		= (TextView) findViewById(R.id.header_nametop);
		mRouteTop		= (TextView) findViewById(R.id.header_routetop);
		
		mBeritaHarian	= (Button) findViewById(R.id.daily_news);
		mSuratTugas		= (Button) findViewById(R.id.surat_tugas);

		mTitle.setText("Informasi Awal");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mBeritaHarian.setOnClickListener(this);
		mSuratTugas.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v == mBeritaHarian) {
			startActivity(new Intent(this, DailyNewsAct.class));
		} else if (v == mSuratTugas) {
			startActivity(new Intent(this, SuratTugasAct.class));
		}
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private Button mBeritaHarian, mSuratTugas;
}
