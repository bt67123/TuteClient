package cn.edu.tute.tuteclient.view;

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

public class MoreFragment extends Fragment {
	
	private View rootView;
	private ListView lv_more;
	
	private final static String[] TITLES = {"抽奖", "设置"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_more, container, false);
		
		initView();

		return rootView;
	}
	
	private void initView() {
		lv_more = (ListView) rootView.findViewById(R.id.lv_more);
		lv_more.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, TITLES));
		lv_more.setOnItemClickListener(new ListViewItemClickListener());
	}
	
	private class ListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				Intent intent = new Intent(getActivity(), LotteryActivity.class);
				getActivity().startActivity(intent);
				break;

			default:
				break;
			}
			
		}
		
	}
}
