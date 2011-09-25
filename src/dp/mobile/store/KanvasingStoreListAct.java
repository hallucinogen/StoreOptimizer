package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
		
		mStoreListView = (ListView) findViewById(R.id.storelist_view);
		
		populateTableStoreList();
	}
	
	private void populateTableStoreList() {
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
		if (requestCode == Utilities.KANVASING_STOREINFO_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private ListView			mStoreListView;
	private CheckRouteAdapter	mStoreListAdpt; 
}
