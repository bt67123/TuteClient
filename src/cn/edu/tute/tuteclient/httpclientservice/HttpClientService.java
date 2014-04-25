package cn.edu.tute.tuteclient.httpclientservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientService {
	public static final String URL_LOGIN    = "http://202.113.244.45:8080/api/User";
	public static final String URL_HOME     = "http://202.113.244.45:8080/api/Home";
	public static final String URL_COURSE   = "http://202.113.244.45:8080/api/Course";
	public static final String URL_NOTICE_0 = "http://202.113.244.45:8080/api/Notice?newsType=0";
	public static final String URL_NOTICE_1 = "http://202.113.244.45:8080/api/Notice?newsType=1";
	public static final String URL_NOTICE_2 = "http://202.113.244.45:8080/api/Notice?newsType=2";
	public static final String URL_NOTICE_3 = "http://202.113.244.45:8080/api/Notice?newsType=3";
	public static final String URL_NOTICE_4 = "http://202.113.244.45:8080/api/Notice?newsType=4";
	
	private static HttpClient httpClient;

	public static String getLoginData(String url, String account, String password) throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> data = new ArrayList<NameValuePair>();
	    data.add(new BasicNameValuePair("LoginName", account));
	    data.add(new BasicNameValuePair("Pwd", password));
	    httpPost.setEntity(new UrlEncodedFormEntity(data));
	    HttpResponse response = httpClient.execute(httpPost);
	    BufferedReader rd = new BufferedReader(
    			new InputStreamReader(response.getEntity().getContent()));  
        String line = "";  
        String result = "result:";
        while((line = rd.readLine()) != null) {  
        	result += line;
	    } 
		return result;
	} 
	
	public static String getData(String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
	    BufferedReader rd = new BufferedReader(
    			new InputStreamReader(response.getEntity().getContent()));  
        String line = "";  
        String result = "result:";
        while((line = rd.readLine()) != null) {  
        	result += line;
	    } 
		result = result.substring(8, result.length()-1);
		result = result.replace("\\", "");
		return result;
	}

}
