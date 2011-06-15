package dp.mobile.store.helper.tables;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class Model {
	public static String getTableName(){
		return null;
	}
	
	public static String[] getColumns(){
		return null;
	}
	
	public static Model[] extract(Cursor cursor){
    	return null;
    }
	
	public Model(){
		
	}
	
	public Model(String id){
		mID = id;
	}
	
	public abstract ContentValues getContentValues();
	
	//PROPERTIES
	public String mID;
}
