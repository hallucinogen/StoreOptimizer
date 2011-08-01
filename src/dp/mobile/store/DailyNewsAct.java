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
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.DailyNews;

public class DailyNewsAct extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_news);
        
        // Set up adapter
        mAdapter = new DailyNewsListAdapter();
        ((DailyNewsListAdapter) mAdapter).setDailyNews();
        
        ExpandableListView elv = (ExpandableListView)findViewById(R.id.expandable);
        elv.setAdapter(mAdapter);
    }

    public class DailyNewsListAdapter extends BaseExpandableListAdapter {
		public DailyNewsListAdapter() {
		    
		}
    	
    	private String[] groups;
    	private String[][] children;
    	
        /*private String[] groups = { "27 Feb 2011", "25 Feb 2011", "20 Feb 2011", "12 Feb 2011" };
        private String[][] children = {
                { "Disini ada berita yang sangat keren sekali, tanggal 27 Februari " +
                	"tapi saya sendiri pingin nyobain kalo beritanya bakal sangat panjang sekali " +
                	"apa yang terjadi dengan tampilannya nanti? Apakah bakal jadi kecil? Atau dia akan"  +
                	"menyesuaikan? Dan anyway, anda sedang membaca berita yang sangat panjang ini, sehingga " +
                	"anda bisa menilai sendiri apakah tampilan seperti ini sudah cukup bagus atau belum?" },
                { "Disini ada berita yang sangat keren sekali, tanggal 25 Februari" },
                { "Disini ada berita yang sangat keren sekali, tanggal 20 Februari" },
                { "Disini ada berita yang sangat keren sekali, tanggal 12 Februari" }
        };*/
        
        public void setDailyNews(){
        	//Fetch DailyNews records from db
        	DatabaseAdapter.instance(getBaseContext()).open();
    		DailyNews[] dailyNewses = (DailyNews[])DatabaseAdapter.instance(getBaseContext()).getAll(DailyNews.getTableName());
    		DatabaseAdapter.instance(getBaseContext()).close();
    		
    		groups = new String[dailyNewses.length];
    		children = new String[dailyNewses.length][1];
    		
    		for(int i=0; i<dailyNewses.length; ++i){
    		    groups[i] = Utilities.formatDate(dailyNewses[i].mTrnDate);
    		    children[i][0] = dailyNewses[i].mDesc;
    		}
        }        

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
            // Layout parameters for the ExpandableListView`1
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(DailyNewsAct.this);
            textView.setLayoutParams(lp);

            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            
            // Set the text starting position
            final float scale = DailyNewsAct.this.getResources().getDisplayMetrics().density;
            final int padding = (int)(30 * scale);
            textView.setPadding(2 * padding, padding, padding, padding);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            final float scale = DailyNewsAct.this.getResources().getDisplayMetrics().density;
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
