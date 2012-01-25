package dp.mobile.store.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dp.mobile.store.R;
import dp.mobile.store.helper.tables.TrnRoute;

public class NumberStoreNameAdapter extends ArrayAdapter<TrnRoute> {
	public NumberStoreNameAdapter(Activity context, TrnRoute[] list) {
		super(context, R.layout.check_route_adpt, list);
		
		mContext	= context;
		mList		= list;
	}
	
	static class ViewHolder{
		protected TextView	number;
		protected TextView	customerName;
		protected TextView	customerAddr;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder		= null;
		LayoutInflater inflator		= mContext.getLayoutInflater();
		final TrnRoute currItem		= getItem(position);
	
		//Always Override convertView (not efficient but it keeps the ListView item consistent)  
		convertView	= inflator.inflate(R.layout.number_store_name_adpt, null);
	
		//Bind convertView to viewHolder
		viewHolder	= new ViewHolder();
		convertView.setTag(viewHolder);
		
		//Get the View
		viewHolder.customerName		= (TextView) convertView.findViewById(R.id.store_name);
		viewHolder.customerAddr		= (TextView) convertView.findViewById(R.id.store_down);
		viewHolder.number			= (TextView) convertView.findViewById(R.id.number);
		
		//Set the View
		viewHolder.customerName.setText(getItem(position).mCustomerName);
		viewHolder.customerAddr.setText(getItem(position).mCustomerAddress);
		viewHolder.number.setText(String.valueOf(String.valueOf(position+1)+"."));

		// set view color
		viewHolder.customerName.setTextColor(0xff000000);
		viewHolder.customerAddr.setTextColor(0xff000000);
		viewHolder.number.setTextColor(0xff000000);
		
		return convertView;
	}

	//ATTRIBUTES
	private final TrnRoute[]	mList;
	private final Activity		mContext;
}
