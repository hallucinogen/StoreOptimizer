package dp.mobile.store.adapter;

import java.text.NumberFormat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dp.mobile.store.R;
import dp.mobile.store.CashReportAct.CashReport;

public class CashReportAdapter extends ArrayAdapter<CashReport> {
	public CashReportAdapter(Activity context, CashReport[] list) {
		super(context, R.layout.check_route_adpt, list);
		
		mContext	= context;
	}
	
	static class ViewHolder{
		protected TextView	customerName;
		protected TextView	customerAddr;
		protected TextView	amountCash;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder		= null;
		LayoutInflater inflator		= mContext.getLayoutInflater();
		final CashReport currItem	= getItem(position);
	
		//Always Override convertView (not efficient but it keeps the ListView item consistent)  
		convertView	= inflator.inflate(R.layout.check_route_adpt, null);
	
		//Bind convertView to viewHolder
		viewHolder	= new ViewHolder();
		convertView.setTag(viewHolder);
		
		//Get the View
		viewHolder.customerName		= (TextView) convertView.findViewById(R.id.store_name);
		viewHolder.customerAddr		= (TextView) convertView.findViewById(R.id.store_down);
		viewHolder.amountCash		= (TextView) convertView.findViewById(R.id.store_right);
		
		//Set the View
		viewHolder.customerName.setText(getItem(position).mCustomerName);
		viewHolder.customerAddr.setText(getItem(position).mCustomerAddr);
		viewHolder.amountCash.setText("Rp " + NumberFormat.getIntegerInstance().format(getItem(position).mAmountCash));
		
		return convertView;
	}

	//ATTRIBUTES
	private final Activity		mContext;
}
