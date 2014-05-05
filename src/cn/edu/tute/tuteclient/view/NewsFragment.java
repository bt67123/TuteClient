package cn.edu.tute.tuteclient.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.News;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsFragment  extends Fragment {
	
	private PullToRefreshListView lv_news;
	private List<News> news;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container, false);
		lv_news = (PullToRefreshListView) rootView.findViewById(R.id.lv_news);
		lv_news.setEmptyView(rootView.findViewById(R.id.empty));  
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				initData();
			}
		});
		return rootView;
	}
	
	private void initData() {		
		new MyAsyncTask().execute(0x345);
	} 
	
	
	class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Integer... params) {
			try {
				String result = HttpClientService.getData(HttpClientService.URL_NOTICE_1);
				try {
					news = JsonService.getNews(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			lv_news.onRefreshComplete();
			List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
			for (News n : news) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("title", n.getTitle());
				map.put("pubDate", n.getPubDate());
				map.put("pubPerson", n.getPubPerson());
				data.add(map);
			}
			lv_news.setAdapter(new SimpleAdapter(getActivity(), data, R.layout.item_news, 
					new String[]{"title", "pubDate"}, 
					new int[]{R.id.tv_title, R.id.tv_pub_date}));
		}
		
	}
}
