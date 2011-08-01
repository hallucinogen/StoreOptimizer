package dp.mobile.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import dp.mobile.store.adapter.StoreListAdapter;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.TrnRoute;

public class KanvasingStoreListAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storelist);
		
		mStoreListView = (ListView) findViewById(R.id.storelist_view);
		
		populateLinearWithDummy();
	}
	
	private void populateLinearWithDummy() {
		//Fetch TrnRoute records from db
		TrnRoute[] trnRoutes = (TrnRoute[])DatabaseAdapter.instance(getBaseContext()).getAll(TrnRoute.getTableName());
		
		if(trnRoutes != null){
			mStoreListAdpt = new StoreListAdapter(this, trnRoutes);
			mStoreListView.setAdapter(mStoreListAdpt);
			
			mStoreListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
					String trnRouteID = ((TrnRoute)mStoreListView.getItemAtPosition(position)).mID;
					Log.d("StoreList SELECT", position + "#" + id + "=" + trnRouteID);
					
					/*Intent intent1 = new Intent(view.getContext(), SendSMSActivity.class);
	    			intent1.putExtra(GROUP_NAME, m_orgListView.getItemAtPosition(position).toString());
	    			startActivity(intent1);*/
					
					Intent intent = new Intent(KanvasingStoreListAct.this, KanvasingStoreInformationAct.class);
					intent.putExtra(Utilities.INTENT_STORE_ID, trnRouteID);
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
	private StoreListAdapter	mStoreListAdpt; 
}
