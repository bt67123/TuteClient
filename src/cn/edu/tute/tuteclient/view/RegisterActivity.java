package cn.edu.tute.tuteclient.view;

import android.content.Context;
import android.content.Intent;
import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.util.SystemBarTintUtil;

import com.actionbarsherlock.app.SherlockActivity;

public class RegisterActivity extends SherlockActivity {
	
	public static void startRegisterActivity(Context context) {
		Intent intent  = new Intent(context, RegisterActivity.class);
		context.startActivity(intent);
	}

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		SystemBarTintUtil.initSystemBar(RegisterActivity.this);
	}
}
