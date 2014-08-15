package cn.edu.tute.tuteclient.view;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.tute.tuteclient.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MoreFragment extends Fragment {

	private View rootView;
	private ListView lv_more;

	private final static String[] TITLES = { "我是新生", "活动", "抽奖", "设置" };
	private final static int[] IMGS = { R.drawable.ic_baby_64,
			R.drawable.ic_loc_64, R.drawable.ic_gift_64,
			R.drawable.ic_settings_75 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_more, container, false);

		initView();

		return rootView;
	}

	private void initView() {
		lv_more = (ListView) rootView.findViewById(R.id.lv_more);
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < TITLES.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("img", IMGS[i]);
			map.put("title", TITLES[i]);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.item_more, new String[] { "img", "title" }, new int[] {
						R.id.iv_more, R.id.tv_more });
		lv_more.setAdapter(adapter);
//		lv_more.setAdapter(new ArrayAdapter<String>(getActivity(),
//				android.R.layout.simple_list_item_1, TITLES));
		lv_more.setOnItemClickListener(new ListViewItemClickListener());
	}

	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 1:
				AttendActivity.startAttendActivity(getActivity());
				break;
			case 2:
				LotteryActivity.startLotteryActivity(getActivity());
				break;

			default:
				break;
			}

		}

	}
}
