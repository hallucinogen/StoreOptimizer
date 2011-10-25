package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import dp.mobile.store.adapter.CheckRouteAdapter;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.TrnRoute;

public class KanvasingStoreListAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storelist);
		
		initComp();
		populateTableStoreList();
	}
	
	private void initComp(){
		mTitle					= (TextView) findViewById(R.id.header_title);
		mNameTop				= (TextView) findViewById(R.id.header_nametop);
		mRouteTop				= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Penerimaan Stock");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mStoreListView = (ListView) findViewById(R.id.storelist_view);
	}
	
	private void populateTableStoreList() {
		mStoreListView.removeAllViewsInLayout();
		
		//Fetch TrnRoute records from db
		TrnRoute[] trnRoutes = (TrnRoute[])DatabaseAdapter.instance(getBaseContext()).getAll(TrnRoute.getTableName(), null, null);
		
		if(trnRoutes != null){
			mStoreListAdpt = new CheckRouteAdapter(this, trnRoutes);
			mStoreListView.setAdapter(mStoreListAdpt);
			
			mStoreListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
					String custCode = ((TrnRoute)mStoreListView.getItemAtPosition(position)).mCustomerCode;					
					
					Intent intent = new Intent(KanvasingStoreListAct.this, KanvasingStoreInformationAct.class);
					intent.putExtra(Utilities.INTENT_STORE_ID, custCode);
					startActivityForResult(intent, Utilities.KANVASING_STOREINFO_RC);
				}
			});
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_STOREINFO_RC) {
			//populateTableStoreList();
			finish();
		}
	}
	
	private ListView			mStoreListView;
	private CheckRouteAdapter	mStoreListAdpt; 
	private TextView			mTitle, mNameTop, mRouteTop;
	
	public static final int REQUEST_CODE = 0;
}
