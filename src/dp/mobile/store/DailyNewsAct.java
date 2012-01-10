package dp.mobile.store;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dp.mobile.store.helper.DatabaseAdapter;
import dp.mobile.store.helper.Utilities;
import dp.mobile.store.helper.tables.DailyNews;

public class DailyNewsAct extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_news);
        
        initComp();
    }
    
    private void initComp(){
    	mTitle			= (TextView) findViewById(R.id.header_title);
    	mNameTop		= (TextView) findViewById(R.id.header_nametop);
    	mRouteTop		= (TextView) findViewById(R.id.header_routetop);
    	
    	mNewsTextView	= (TextView) findViewById(R.id.news_textview);
    	mPrevBtn		= (Button) findViewById(R.id.news_prev_btn);
    	mNextBtn		= (Button) findViewById(R.id.news_next_btn);
    	
    	mPrevBtn.setOnClickListener(this);
    	mNextBtn.setOnClickListener(this);
    	
    	mTitle.setText("Berita Harian");
    	Cursor userCur = Utilities.getUser(getBaseContext());
    	if(userCur.moveToFirst()){
			mNameTop.setText(userCur.getString(0));
			mRouteTop.setText(userCur.getString(1));
		}
		userCur.close();
    	
    	//Fetch DailyNews records from db
		dailyNewses = (DailyNews[])DatabaseAdapter.instance(getBaseContext()).getAll(DailyNews.getTableName(), null, DailyNews.KEY_TRNDATE + " DESC");
		newsIdx = 0;
		mNewsTextView.setText(dailyNewses[newsIdx].mDesc);
    }
    
    public void onClick(View v) {
		if (v == mPrevBtn) {
			--newsIdx;
		} else if (v == mNextBtn) {
			++newsIdx;
		}
		
		if(newsIdx < 0)
			newsIdx = dailyNewses.length-1;
		else if (newsIdx >= dailyNewses.length)
			newsIdx = 0;
		
		mNewsTextView.setText(dailyNewses[newsIdx].mDesc);
	}
    
    private DailyNews[]	dailyNewses;
    private int			newsIdx;
    private TextView	mTitle, mNameTop, mRouteTop, mNewsTextView;
    private Button		mPrevBtn, mNextBtn;
}
