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
import android.R.anim;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFragment  extends Fragment {
	
	private PullToRefreshListView lv_news1;
	private PullToRefreshListView lv_news2;
	private PullToRefreshListView lv_news3;
	private List<News> newsList1;
	private List<News> newsList2;
	private List<News> newsList3;
	
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_news, container, false);
		
		initTabHost();
		
		initNewsListView(R.id.tab1);

		return rootView;
	}
	
	private void initNewsListView(int viewid) {
		lv_news1 = (PullToRefreshListView) rootView.findViewById(R.id.lv_news1);
		lv_news1.setEmptyView(rootView.findViewById(R.id.empty1));  
		lv_news1.setOnItemClickListener(new NewsListClickListener(1));
		lv_news1.setOnRefreshListener(new RefreshListener(HttpClientService.URL_NOTICE_1, lv_news1));
		initData(HttpClientService.URL_NOTICE_1, lv_news1);

		lv_news2 = (PullToRefreshListView) rootView.findViewById(R.id.lv_news2);
		lv_news2.setEmptyView(rootView.findViewById(R.id.empty2));  
		lv_news2.setOnItemClickListener(new NewsListClickListener(2));
		lv_news2.setOnRefreshListener(new RefreshListener(HttpClientService.URL_NOTICE_3, lv_news2));
		initData(HttpClientService.URL_NOTICE_3, lv_news2);

		lv_news3 = (PullToRefreshListView) rootView.findViewById(R.id.lv_news3);
		lv_news3.setEmptyView(rootView.findViewById(R.id.empty3));  
		lv_news3.setOnItemClickListener(new NewsListClickListener(3));
		lv_news3.setOnRefreshListener(new RefreshListener(HttpClientService.URL_NOTICE_4, lv_news3));
		initData(HttpClientService.URL_NOTICE_4, lv_news3);
	}
	
	class RefreshListener implements OnRefreshListener<ListView> {
		String url;
		PullToRefreshListView lv;
		public RefreshListener(String url, PullToRefreshListView lv) {
			this.url = url;
			this.lv = lv;
		}

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			initData(url, lv);
		}
   }

	
	private void initTabHost() {
		TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabhost);
		tabHost.setup();
		TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("学院公告").setContent(R.id.tab1);
		TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("相关公告").setContent(R.id.tab2);
		TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator("紧急通知").setContent(R.id.tab3);
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);


		TabWidget tabWidget = (TabWidget) tabHost.getTabWidget();
		final TextView tv1 = (TextView) tabWidget.getChildAt(0).findViewById(android.R.id.title);
		final TextView tv2 = (TextView) tabWidget.getChildAt(1).findViewById(android.R.id.title);
		final TextView tv3 = (TextView) tabWidget.getChildAt(2).findViewById(android.R.id.title);
		tv1.setTextColor(rootView.getResources().getColor(R.color.white));
		tv2.setTextColor(rootView.getResources().getColor(R.color.blue_main));
		tv3.setTextColor(rootView.getResources().getColor(R.color.blue_main));
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			
			tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_indicator);
		}
		
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					tv1.setTextColor(rootView.getResources().getColor(R.color.white));
					tv2.setTextColor(rootView.getResources().getColor(R.color.blue_main));
					tv3.setTextColor(rootView.getResources().getColor(R.color.blue_main));
				} else if (tabId.equals("tab2")) {
					tv1.setTextColor(rootView.getResources().getColor(R.color.blue_main));
					tv2.setTextColor(rootView.getResources().getColor(R.color.white));
					tv3.setTextColor(rootView.getResources().getColor(R.color.blue_main));	
				} else if (tabId.equals("tab3")) {
					tv1.setTextColor(rootView.getResources().getColor(R.color.blue_main));
					tv2.setTextColor(rootView.getResources().getColor(R.color.blue_main));
					tv3.setTextColor(rootView.getResources().getColor(R.color.white));	
				}
			}
		});
	}
	
	class NewsListClickListener implements OnItemClickListener {
		int tag; //标记是哪个listView调用
		public NewsListClickListener(int tag) {
			this.tag = tag;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,  //position 从1开始!?
				long id) {                                                       //view 不是一个callback？
			Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
			News news;
			if (tag == 1) {
    			news = newsList1.get(position-1);
				
			} else if (tag == 2) {
    			news = newsList2.get(position-1);
			} else {
    			news = newsList3.get(position-1);
			}
			intent.putExtra("newsID",  news.getId());
			intent.putExtra("title",   news.getTitle());
			intent.putExtra("person",  news.getPubPerson());
			intent.putExtra("pubDate", news.getPubDate());
			getActivity().startActivity(intent);
		}
		
	}

	
	private void initData(String url, PullToRefreshListView lv) {		
		new NewsListAsyncTask(url, lv).execute(0x345);
	} 
	
	
	class NewsListAsyncTask extends AsyncTask<Integer, Integer, String> {
		String url;
		PullToRefreshListView lv;
		List<News> newsList;
		
		public NewsListAsyncTask(String url, PullToRefreshListView lv) {
			this.url = url;
			this.lv = lv;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Integer... params) {
			try {
				if (url == HttpClientService.URL_NOTICE_3) {
 				    String result = HttpClientService.getData(url);
				    newsList = JsonService.getNews(result, "news3");
					newsList2 = newsList;
				} else if (url == HttpClientService.URL_NOTICE_4) {
 				    String result = HttpClientService.getData(url);
				    newsList = JsonService.getNews(result, "news4");
					newsList3 = newsList;
				} else {
 				    String result = HttpClientService.getData(url);
				    newsList = JsonService.getNews(result, "news1");
					newsList1 = newsList;
				}

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
			lv.onRefreshComplete();
			List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
			for (News n : newsList) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("title", n.getTitle());
				map.put("pubDate", n.getPubDate());
				map.put("pubPerson", n.getPubPerson());
				data.add(map);
		        lv.setAdapter(new SimpleAdapter(getActivity(), data, R.layout.item_news, 
					new String[]{"title", "pubDate"}, 
					new int[]{R.id.tv_title, R.id.tv_pub_date}));
		    }
		
	    }
	}

}
