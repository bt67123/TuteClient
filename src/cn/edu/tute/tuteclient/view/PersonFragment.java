package cn.edu.tute.tuteclient.view;

import cn.edu.tute.tuteclient.domain.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonFragment extends Fragment {
	
	User person;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setWidth(200);
		textView.setTextSize(28);
		Bundle args = getArguments();
		person = new User(args.getString("name"), args.getInt("collegeID"));
		StringBuffer buffer = new StringBuffer();
		buffer.append("姓名: ");
		buffer.append(person.getName() + "\n");
		buffer.append("学院编号: ");
		buffer.append(person.getCollegeID());
		textView.setText(buffer);
		return textView;
	}
}
