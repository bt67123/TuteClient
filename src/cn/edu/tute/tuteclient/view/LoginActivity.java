package cn.edu.tute.tuteclient.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.actionbarsherlock.app.SherlockActivity;
import com.capricorn.RayMenu;

import cn.edu.tute.tuteclient.MainActivity;
import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.Person;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends SherlockActivity {
		private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera, R.drawable.composer_music,
			R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought, R.drawable.composer_with };
	
	private EditText et_account;
	private EditText et_password;
    private ProgressDialog progressDialog;
	
	static String account="";
	static String password="";
    static String data="";
	Person person = null;

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x111) {
				progressDialog.cancel();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				Bundle args = new Bundle();
				args.putString("name", person.getName());
				args.putInt("collegeID", person.getCollegeID());
				intent.putExtras(args);
				System.out.println("Login: " + data);
				LoginActivity.this.startActivity(intent);
			} else if(msg.what == 0x110) {
				progressDialog.cancel();
				Toast.makeText(LoginActivity.this, "账号或者密码有误", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new LoginBtnClickListener());
		
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		
		

		RayMenu menu = (RayMenu) findViewById(R.id.ray_menu);
		final int itemCount = ITEM_DRAWABLES.length;
		for (int i = 0; i < itemCount; i++) {
		    ImageView item = new ImageView(this);
		    item.setImageResource(ITEM_DRAWABLES[i]);

		    final int position = i;
		    menu.addItem(item, new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            Toast.makeText(LoginActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
		        }
		    });// Add a menu item
		}
	}
	
	private class LoginBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			account = et_account.getText().toString();
			password = et_password.getText().toString();
			new Thread() {
				@Override
				public void run() {
					try {
						data = HttpClientService.getLoginData(HttpClientService.URL_LOGIN, account, password);
						try {
							data = data.substring(8, data.length()-1);
							data = data.replace("\\", "");
							person = JsonService.getPerson(data);
        					System.out.println(data);
        					mHandler.sendEmptyMessage(0x111);
						} catch (JSONException e) {
							e.printStackTrace();
							mHandler.sendEmptyMessage(0x110);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
    		progressDialog = new ProgressDialog(LoginActivity.this);
    		progressDialog.show();
		}
		
	}
	
}
