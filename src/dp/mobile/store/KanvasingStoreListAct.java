package dp.mobile.store;

import dp.mobile.store.helper.Utilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class KanvasingStoreListAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storelist);
		
		mLinear = (LinearLayout) findViewById(R.id.storelist_linear);
		
		populateLinearWithDummy();
	}
	
	/// TODO : not using dummy
	private void populateLinearWithDummy() {
		LayoutInflater inflater = getLayoutInflater();
		
		for (int i = 0; i < 20; ++i) {
			LinearLayout item = (LinearLayout)inflater.inflate(R.layout.store_thumb, mLinear, false);
			final int id = i;
			item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(KanvasingStoreListAct.this, KanvasingStoreInformationAct.class);
					intent.putExtra(Utilities.INTENT_STORE_ID, id);
					
					startActivityForResult(intent, Utilities.KANVASING_STOREINFO_RC);
				}
			});
			
			mLinear.addView(item);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Utilities.KANVASING_STOREINFO_RC && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private LinearLayout mLinear;
}
