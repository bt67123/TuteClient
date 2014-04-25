package cn.edu.tute.tuteclient.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.Course;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ClasstableFragment extends Fragment {
	public static final String ARG_CLASSTABLE = "ARG_CLASSTABLE";
	
	public static String classtableData = "";
	private List<Course> courses = new ArrayList<Course>();

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x222) {
				progressDialog.cancel();
				
				for (Course course : courses) {
					int r = course.getCourseTime().getDayOfWeek();
					int c = course.getCourseTime().getCourseOfDay();
					adapterData[r + c*5] = course;
				}
				
				gv_classtable.setAdapter(new ClasstableGridViewAdapter());

			}
		}
	};
	

    private ProgressDialog progressDialog;
    private GridView gv_classtable;
    
    
    static final int row = 5;
    static final int column = 5;
    private Course[] adapterData = new Course[row*column];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_classtable, container, false);
		gv_classtable = (GridView) rootView.findViewById(R.id.gv_classtable);
		gv_classtable.setAdapter(new ClasstableGridViewAdapter());
		initClassTableData();
		return rootView;

	}
	
	public void initClassTableData() {
		if (classtableData != "") {
			return;
		} else {
			new Thread(){
				@Override
				public void run() {
					try {
						classtableData = HttpClientService.getData(HttpClientService.URL_COURSE);
						try {
							courses = JsonService.getCourse(classtableData);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(0x222);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
    		progressDialog = new ProgressDialog(getActivity());
	    	progressDialog.show();
		}
	}
	

	private class ClasstableGridViewAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater;

		public ClasstableGridViewAdapter() {
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return row*column;
		}

		@Override
		public Course getItem(int position) {
			return adapterData[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_classtable, null);
			} 
			TextView tv_course = (TextView) convertView.findViewById(R.id.tv_course);
			if (getItem(position) != null) {
    			tv_course.setText(getItem(position).getName());
			}
			tv_course.setOnClickListener(new CourseClickListener(position));
			return convertView;
		}
		
		private class CourseClickListener implements View.OnClickListener {
			
			public int position;
			public CourseClickListener(int position) {
				this.position = position;
			}

			@Override
			public void onClick(View v) {
				Course course = adapterData[position];
				System.out.println("out:" + position);
				if (course != null) {
	    			StringBuffer buffer = new StringBuffer();
		    		buffer.append("\n" + course.getName() + "\n");
		    		buffer.append("任课老师：" + course.getTeacher().getName() + "\n");
		    		buffer.append("上课教室：" + course.getClassroom() + "\n");
		    		Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage(buffer).create();
		    		dialog.setCanceledOnTouchOutside(true);
		    		dialog.show();
				}
				
			}
			
		}
		
	}

}
