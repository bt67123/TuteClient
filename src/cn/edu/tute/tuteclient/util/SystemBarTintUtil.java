package cn.edu.tute.tuteclient.util;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.os.Build;
import cn.edu.tute.tuteclient.R;


public class SystemBarTintUtil {
	public static void initSystemBar(Activity activity) {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
	        tintManager.setStatusBarTintEnabled(true);
	        tintManager.setStatusBarTintResource(R.color.blue_main);
	    }
	}
	
}
