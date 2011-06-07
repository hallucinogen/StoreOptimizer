package dp.mobile.store.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GPSService extends Service implements LocationListener{
	private static final String TAG = "GPS Service";
	@Override
	public void onCreate() {
		super.onCreate();
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		sLastUpdate = System.currentTimeMillis();
		
		Log.i(TAG, "GPS Service started");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.removeUpdates(this);
		sLastUpdate = System.currentTimeMillis();
		
		Log.i(TAG, "GPS Service stopped");
	}
	
	public void onLocationChanged(Location location) {
		if (location != null) {
			sLatitude = location.getLatitude();
			sLongitude = location.getLongitude();
			
			if (System.currentTimeMillis() - sLastUpdate > Utilities.UPDATE_DELAY) {
				Calendar calendar = Calendar.getInstance();
				String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
				String toLog = currentTime + " " + sLatitude + " " + sLongitude;
				logToFile(toLog);
				sLastUpdate = System.currentTimeMillis();
				
				Map<String, Object> param = new HashMap<String, Object>();
				
				final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
				param.put("Position[phone_id]", tm.getDeviceId());
				param.put("Position[latitude]", GPSService.getLatitude());
				param.put("Position[longitude]", GPSService.getLongitude());
				
				RestClient.post(Utilities.TRACK_URL, param, true);
			}
		}
	}
	
	public void logToFile(String toWrite){
		Calendar calendar = Calendar.getInstance();
		String currentDate = "" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" +calendar.get(Calendar.DAY_OF_MONTH);
		
		Log.i("Logger", "[DATE]" + currentDate + " = " + toWrite);
		FileWriter fstream = null;
		try {
			fstream = new FileWriter(getFilesDir().toString() + "/" + currentDate, true);
			fstream.append(toWrite);
			fstream.append("\n");
			fstream.close();
		} catch (IOException iEx) {
			
		}
	}
	
	public void onProviderDisabled(String arg0) {
		
		
	}
	
	public void onProviderEnabled(String provider) {
		
		
	}
	
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		
	}
	
	public static double getLatitude(){
		return sLatitude;
	}
	
	public static double getLongitude(){
		return sLongitude;
	}
	
	private static double sLatitude, sLongitude;
	private static long sLastUpdate;
}
