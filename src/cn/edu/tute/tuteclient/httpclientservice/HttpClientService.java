package cn.edu.tute.tuteclient.httpclientservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.R.string;

import cn.edu.tute.tuteclient.R.color;

public class HttpClientService {
	public static final String URL_LOGIN = "http://202.113.244.45:8080/api/User";
	public static final String URL_HOME = "http://202.113.244.45:8080/api/Home";
	public static final String URL_COURSE = "http://202.113.244.45:8080/api/Course";
	public static final String URL_NOTICE_0 = "http://202.113.244.45:8080/api/Notice?newsType=0";
	public static final String URL_NOTICE_1 = "http://202.113.244.45:8080/api/Notice?newsType=1";
	public static final String URL_NOTICE_3 = "http://202.113.244.45:8080/api/Notice?newsType=3";
	public static final String URL_NOTICE_4 = "http://202.113.244.45:8080/api/Notice?newsType=4";
	// URL_NOTICE + newID = new Detail
	public static final String URL_NOTICE = "http://202.113.244.45:8080/api/Notice/";
	public static final String URL_SWITCH = "http://202.113.244.45:8080/api/Attend?type=1";
	public static final String URL_STUDENT_ATTEND_STATUS = "http://202.113.244.45:8080/api/Attend?type=2";
	// post { StudentID Longitude Latitude Status SwitchID }
	public static final String URL_ATTEND = "http://202.113.244.45:8080/api/Attend";

	private static HttpClient httpClient;

	public static String getLoginData(String url, String account,
			String password) throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("Pwd", password));
		data.add(new BasicNameValuePair("LoginName", account));
		httpPost.setEntity(new UrlEncodedFormEntity(data));
		HttpResponse response = httpClient.execute(httpPost);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = "";
		String result = "";
		while ((line = rd.readLine()) != null) {
			result += line;
		}
		return result;
	}

	public static String postData(String url, Map<String, String> map)
			throws ClientProtocolException, IOException {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		for (String key : map.keySet()) {
			data.add(new BasicNameValuePair(key, map.get(key)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(data));
		HttpResponse response = httpClient.execute(httpPost);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String result = "";
		String line = "";
		while ((line = rd.readLine()) != null) {
			result += line;
		}
		return result;
	}

	public static String getData(String url) throws ClientProtocolException,
			IOException {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = "";
		String result = "result:";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			result += line;
		}
		result = result.substring(8, result.length() - 1);

		result = result.replace("\r\n", "").replace("\\\"", "\"")
				.replace("\\\\", "\\");
		return result;
	}

}
