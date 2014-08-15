package cn.edu.tute.tuteclient.view;

import com.actionbarsherlock.app.SherlockActivity;

import cn.edu.tute.tuteclient.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LotteryActivity extends SherlockActivity {
	
	public static void startLotteryActivity(Context context) {
		Intent intent = new Intent(context, LotteryActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
	}

}
