package dp.mobile.store.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dp.mobile.store.R;
import dp.mobile.store.helper.tables.TrnRoute;

public class CheckRouteAdapter extends ArrayAdapter<TrnRoute> {
	public CheckRouteAdapter(Activity context, TrnRoute[] list) {
		super(context, R.layout.check_route_adpt, list);
		
		mContext	= context;
		mList		= list;
	}
	
	static class ViewHolder{
		protected TextView	customerName;
		protected TextView	customerAddr;
		protected TextView	timevisit;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder		= null;
		LayoutInflater inflator		= mContext.getLayoutInflater();
		final TrnRoute currItem		= getItem(position);
	
		//Always Override convertView (not efficient but it keeps the ListView item consistent)  
		convertView	= inflator.inflate(R.layout.check_route_adpt, null);
	
		//Bind convertView to viewHolder
		viewHolder	= new ViewHolder();
		convertView.setTag(viewHolder);
		
		//Get the View
		viewHolder.customerName		= (TextView) convertView.findViewById(R.id.store_name);
		viewHolder.customerAddr		= (TextView) convertView.findViewById(R.id.store_down);
		viewHolder.timevisit	= (TextView) convertView.findViewById(R.id.store_right);
		
		//Set the View
		viewHolder.customerName.setText(getItem(position).mCustomerName);
		viewHolder.customerAddr.setText(getItem(position).mCustomerAddress);
		viewHolder.timevisit.setText(String.valueOf(getItem(position).mTimeVisit));
		
		return convertView;
	}

	//ATTRIBUTES
	private final TrnRoute[]	mList;
	private final Activity		mContext;
}
