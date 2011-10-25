package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import dp.mobile.store.adapter.CheckRouteAdapter;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.TrnRoute;

public class CheckRouteAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_route);
		
		initComp();
		populateTableCheckRoute();
	}
	
	private void initComp(){
		mTitle		= (TextView) findViewById(R.id.header_title);
		mNameTop	= (TextView) findViewById(R.id.header_nametop);
		mRouteTop	= (TextView) findViewById(R.id.header_routetop);
		
		mTitle.setText("Cek Rute");
		Cursor userCur = Utilities.getUser(getBaseContext());
		if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
		
		mCheckRouteListView = (ListView) findViewById(R.id.check_route_listview);
	}
	
	private void populateTableCheckRoute() { //Table mobile_trnroute
		TrnRoute[] trnRoutes = (TrnRoute[])DatabaseAdapter.instance(getBaseContext()).getAll(TrnRoute.getTableName(), null, TrnRoute.KEY_ORDERNO + " ASC");
		//LayoutInflater inflater = getLayoutInflater();
		
		mAdpt = new CheckRouteAdapter(this, trnRoutes);	
		mCheckRouteListView.setAdapter(mAdpt);
		/*for (int i = 0; i < trnRoutes.length; ++i) {
			LinearLayout item = (LinearLayout)inflater.inflate(R.layout.check_route_adpt, mLinear, false);
			mLinear.addView(item);
		}*/
	}
	
	private TextView mTitle, mNameTop, mRouteTop;
	private ListView mCheckRouteListView;
	private	CheckRouteAdapter mAdpt;
}
