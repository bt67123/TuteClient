package cn.edu.tute.tuteclient.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.News;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import android.app.ProgressDialog;
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
	
	private ListView lv_news;
	private List<News> news;
	
	private ProgressDialog mProgressDialog; 
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x333) {
			    mProgressDialog.cancel();
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
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container, false);
		lv_news = (ListView) rootView.findViewById(R.id.lv_news);
		Button bt_fresh = (Button) rootView.findViewById(R.id.bt_fresh);
		bt_fresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initData();
			}
		});
		return rootView;
	}
	
	private void initData() {
		new Thread(){
			@Override
			public void run() {
				try {
					String result = HttpClientService.getData(HttpClientService.URL_NOTICE_1);
					try {
						news = JsonService.getNews(result);
						mHandler.sendEmptyMessage(0x333);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle("更新通知");
		mProgressDialog.show();
	} 
}
