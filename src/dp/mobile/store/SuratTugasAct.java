package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import dp.mobile.store.adapter.CheckRouteAdapter;
import dp.mobile.store.adapter.NumberStoreNameAdapter;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.TrnRoute;

public class SuratTugasAct extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surat_tugas);
		
		initComp();
		populateTableStoreList();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		mOKBtn					= (Button) findViewById(R.id.surat_tugas_ok_btn);
		mOKBtn.setOnClickListener(this);
		
		mTitle.setText("Surat Tugas");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mStoreListView = (ListView) findViewById(R.id.surat_tugas_listview);
	}
	
	private void populateTableStoreList() {
		mStoreListView.removeAllViewsInLayout();
		
		//Fetch TrnRoute records from db
		TrnRoute[] trnRoutes = (TrnRoute[])DatabaseAdapter.instance(getBaseContext()).getAll(TrnRoute.getTableName(), null, null);
		
		if(trnRoutes != null){
			mNumberStoreAdpt = new NumberStoreNameAdapter(this, trnRoutes);
			mStoreListView.setAdapter(mNumberStoreAdpt);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v == mOKBtn){
		}
	}
	
	private ListView				mStoreListView;
	private NumberStoreNameAdapter	mNumberStoreAdpt; 
	private TextView				mTitle, mNameTop, mRouteTop;
	private Button					mOKBtn;
	
	public static final int REQUEST_CODE = 0;
}
