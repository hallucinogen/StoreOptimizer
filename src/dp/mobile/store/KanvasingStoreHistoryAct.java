package dp.mobile.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class KanvasingStoreHistoryAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanvasing_storehistory);
		
		mAdapter = new StoreHistoryListAdapter();
        
        ExpandableListView elv = (ExpandableListView)findViewById(R.id.expandable);
        elv.setAdapter(mAdapter);
    }

    public class StoreHistoryListAdapter extends BaseExpandableListAdapter {
    	/// TODO : not using dummy
        private String[] groups = { "27 Feb 2011", "25 Feb 2011", "20 Feb 2011", "12 Feb 2011" };
        private String[][] children = {
                { "nah lho lom ada isinya" },
                { "nah lho lom ada isinya" },
                { "nah lho lom ada isinya" },
                { "nah lho lom ada isinya" }
        };

        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(KanvasingStoreHistoryAct.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            final float scale = KanvasingStoreHistoryAct.this.getResources().getDisplayMetrics().density;
            final int padding = (int)(30 * scale);
            textView.setPadding(2 * padding, padding, padding, padding);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            final float scale = KanvasingStoreHistoryAct.this.getResources().getDisplayMetrics().density;
            final int padding = (int)(15 * scale);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
    
    private ExpandableListAdapter mAdapter;
}
