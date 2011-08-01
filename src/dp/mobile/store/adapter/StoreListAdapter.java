package dp.mobile.store.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dp.mobile.store.R;
import dp.mobile.store.helper.tables.TrnRoute;

public class StoreListAdapter extends ArrayAdapter<TrnRoute> {
	public StoreListAdapter(Activity context, TrnRoute[] list) {
		super(context, R.layout.store_thumb, list);
		
		mContext	= context;
		mList		= list;
	}
	
	static class ViewHolder{
		protected TextView	storeName;
		protected TextView	storeAddr;
		protected TextView	storeReceivable;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder		= null;
		LayoutInflater inflator		= mContext.getLayoutInflater();
		final TrnRoute currItem		= getItem(position);
	
		//Always Override convertView (not efficient but it keeps the ListView item consistent)  
		convertView	= inflator.inflate(R.layout.store_thumb, null);
	
		//Bind convertView to viewHolder
		viewHolder	= new ViewHolder();
		convertView.setTag(viewHolder);
		
		//Get the View
		viewHolder.storeName		= (TextView) convertView.findViewById(R.id.store_name);
		viewHolder.storeAddr		= (TextView) convertView.findViewById(R.id.store_down);
		viewHolder.storeReceivable	= (TextView) convertView.findViewById(R.id.store_right);
		
		//Set the View
		viewHolder.storeName.setText(getItem(position).mCustomerName);
		viewHolder.storeAddr.setText(getItem(position).mCustomerAddress);
		viewHolder.storeReceivable.setText(String.valueOf(getItem(position).mReceivable));
		
		return convertView;
	}

	//ATTRIBUTES
	private final TrnRoute[]	mList;
	private final Activity		mContext;
}
