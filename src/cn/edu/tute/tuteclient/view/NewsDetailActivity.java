package cn.edu.tute.tuteclient.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;

import com.actionbarsherlock.app.SherlockActivity;

public class NewsDetailActivity extends SherlockActivity {
	private CharSequence newsContent = "";
	
	private TextView tv_newsContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		
		initView();

		String newsID  = getIntent().getStringExtra("newsID");
		new NewsDetailAsyncTask(newsID).execute(0x567);
		
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void initView() {
		String title   = getIntent().getStringExtra("title");
		String person  = getIntent().getStringExtra("person");
		String pubDate = getIntent().getStringExtra("pubDate");

		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_person = (TextView) findViewById(R.id.tv_person);
		TextView tv_pubDate = (TextView) findViewById(R.id.tv_pubDate);
		tv_newsContent = (TextView) findViewById(R.id.tv_content);
		
		tv_title.setText(title);
		tv_person.setText("发布人: " + person);
		tv_pubDate.setText("发布日期: " + pubDate + "\n");
	} 
	
	class NewsDetailAsyncTask extends AsyncTask<Integer, Integer, String> {
		private String newsID;
		
		public NewsDetailAsyncTask(String newsID) {
			this.newsID = newsID;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Integer... params) {
			try {
				String result = HttpClientService.getData(HttpClientService.URL_NOTICE + newsID);
				newsContent = JsonService.getNewsContent(result);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "success";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			tv_newsContent.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动 
			tv_newsContent.setText(newsContent);
		}
		
	}
}
