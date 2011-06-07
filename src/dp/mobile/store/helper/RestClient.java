package dp.mobile.store.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class RestClient {
 
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
 
    public static JSONObject get(String url, boolean needCookie)
    {
    	DefaultHttpClient httpclient = getApplicationHttpClient();
        HttpGet httpget = new HttpGet(url); 
 
        HttpResponse response;
        JSONObject json = null;
        
        try {
        	if (needCookie) getLoginCookie();
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
                InputStream instream = entity.getContent();
                
                String result= convertStreamToString(instream);
                json=new JSONObject(result);
                instream.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return json;
    }
    
    public static HttpResponse post(String url, JSONObject json, boolean needCookie){
    	DefaultHttpClient httpclient = getApplicationHttpClient();
        HttpPost httppost = new HttpPost(url);
    	HttpResponse response;
    	
        try {
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("json", json.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            if (needCookie) getLoginCookie();
            response = httpclient.execute(httppost);
            /** debug response */
            
            HttpEntity entity = response.getEntity();
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("RestClient loh",result);
                instream.close();
            }
            return response;
        } catch (UnsupportedEncodingException uEx){
        	
        } catch (IOException iEx){
        	Log.d("RestClient", "IO Error = " + iEx);
        }
        
        return null;
    }
    
    public static HttpResponse post(String url, Map<String, Object> param, boolean needCookie){
    	DefaultHttpClient httpclient = getApplicationHttpClient();
        HttpPost httppost = new HttpPost(url);
    	HttpResponse response;
    	
        try {
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	for (Map.Entry<String, Object> item : param.entrySet()) {
        		nameValuePairs.add(new BasicNameValuePair(item.getKey(), item.getValue().toString()));
        	}
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        	if (needCookie) getLoginCookie();
            response = httpclient.execute(httppost);
            
            /** debug response */
            HttpEntity entity = response.getEntity();
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("RestClient loh",result);
                instream.close();
            }
            return response;
        } catch (UnsupportedEncodingException uEx){
        	
        } catch (IOException iEx){
        	Log.d("RestClient", "IO Error = " + iEx);
        }
        
        return null;
    }
    
    public static void getLoginCookie() {
    	DefaultHttpClient httpClient = getApplicationHttpClient();
    	HttpPost httppost = new HttpPost(Utilities.LOGIN_URL);
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("LoginForm[username]", Utilities.USERNAME));
		nameValuePairs.add(new BasicNameValuePair("LoginForm[password]", Utilities.PASSWORD));
		nameValuePairs.add(new BasicNameValuePair("LoginForm[rememberMe]", "" + 1));
		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpClient.execute(httppost);
		} catch (UnsupportedEncodingException uEx){
			Log.d("RestClient", "Unsupported Encoding Error = " + uEx);
        } catch (IOException iEx){
        	Log.d("RestClient", "IO Error = " + iEx);
        }
    }
    
	public static DefaultHttpClient getApplicationHttpClient() {
		if (sHttpClient == null) {
			sHttpClient = new DefaultHttpClient();
		}
		
		return sHttpClient;
	}
	
    public static DefaultHttpClient sHttpClient	= null;
}
