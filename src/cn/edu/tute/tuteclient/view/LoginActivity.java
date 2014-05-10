package cn.edu.tute.tuteclient.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.capricorn.RayMenu;
import com.devspark.appmsg.AppMsg;

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
				AppMsg.makeText(LoginActivity.this, "账号或者密码有误", AppMsg.STYLE_ALERT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
	}
	
	
	private void login() {
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_check:
			login();
			break;

		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
	}
	
}
