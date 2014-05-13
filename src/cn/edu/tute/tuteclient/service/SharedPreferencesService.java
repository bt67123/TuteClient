package cn.edu.tute.tuteclient.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesService {

	public static void saveLoginInfo(Context context, String username, String password) {
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	} 
}
