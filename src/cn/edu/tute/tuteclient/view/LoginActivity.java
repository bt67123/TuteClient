package cn.edu.tute.tuteclient.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dd.CircularProgressButton;
import com.devspark.appmsg.AppMsg;

import cn.edu.tute.tuteclient.MainActivity;
import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.User;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import cn.edu.tute.tuteclient.service.SharedPreferencesService;
import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends SherlockActivity {

	private EditText et_account;
	private EditText et_password;
	private ProgressDialog progressDialog;
	private Spinner sp_user;
	private CircularProgressButton btn_login;

	static String account = "";
	static String password = "";
	static String data = "";
	public static User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initJPush();

		initView();

		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	private void initJPush() {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	private void initView() {
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);

		SharedPreferences sharedPre = getSharedPreferences("config",
				MODE_PRIVATE);
		String username = sharedPre.getString("username", "");
		String password = sharedPre.getString("password", "");
		et_account.setText(username);
		et_password.setText(password);

		if (!username.equals("")) {
			et_account.setEnabled(false);

			login();
		}

		sp_user = (Spinner) findViewById(R.id.sp_user);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this, android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.user_type));
		sp_user.setAdapter(adapter);
		sp_user.setOnItemSelectedListener(new SpinnerItemSelectedListener());

		btn_login = (CircularProgressButton) findViewById(R.id.btn_login);
        btn_login.setIndeterminateProgressMode(true);
		btn_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
	}

	private class SpinnerItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(LoginActivity.this, position + "",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

	private void login() {
		new LoginAsyncTask().execute();
	}

	class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
		boolean isValid = false;

		@Override
		protected void onPreExecute() {
			account = et_account.getText().toString();
			password = et_password.getText().toString();

			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				data = HttpClientService.getLoginData(
						HttpClientService.URL_LOGIN, account, password);
				try {
					data = data.substring(1, data.length() - 1);
					data = data.replace("\\", "");
					user = JsonService.getPerson(data);
					user = new User(account, user.getName(),
							user.getCollegeID());

					isValid = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (isValid) {
				progressDialog.cancel();

				// 保存登陆信息
				SharedPreferencesService.saveLoginInfo(LoginActivity.this,
						account, password);

				startMainActivity();

				// 退出login activity
				finish();
			} else {
				progressDialog.cancel();

				AppMsg.makeText(LoginActivity.this, "账号或者密码有误",
						AppMsg.STYLE_ALERT).show();
			}
		}
	}

	private void startMainActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		Bundle args = new Bundle();
		args.putString("name", user.getName());
		args.putInt("collegeID", user.getCollegeID());
		intent.putExtras(args);
		LoginActivity.this.startActivity(intent);
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
