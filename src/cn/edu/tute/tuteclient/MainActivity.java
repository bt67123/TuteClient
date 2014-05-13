package cn.edu.tute.tuteclient;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.special.ResideMenu.ResideMenu;
import com.viewpagerindicator.TitlePageIndicator;

import cn.edu.tute.tuteclient.view.ClasstableFragment;
import cn.edu.tute.tuteclient.view.NewsFragment;
import cn.edu.tute.tuteclient.view.PersonFragment;
import cn.edu.tute.tuteclient.view.SignFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {
	
	private ResideMenu resideMenu;
	
	private static final String[] TITLES = new String[] { "课表", "通知", "活动签到" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		initMenu();
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}
	
	private void initMenu() {
		// attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.stars);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
	}
	
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };
	
	private void initView() {
        FragmentPagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TitlePageIndicator mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
	} 
	
    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	Fragment fragment;
        	switch (position) {
			case 0:
				fragment = new ClasstableFragment();
				return fragment; 
			case 1:
				fragment = new NewsFragment();
				return fragment;
			case 2:
//				fragment = new PersonFragment();
//				Bundle args = new Bundle();
//				Intent intent = getIntent();
//				Bundle bundle = intent.getExtras();
//				String personName= bundle.getString("name");
//				int collegeID = bundle.getInt("collegeID");
//				args.putString("name", personName);
//				args.putInt("collegeID", collegeID);
//				fragment.setArguments(args);
				fragment = new SignFragment();
				return fragment;
			}
        	return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	return TITLES[position];
        }

        @Override
        public int getCount() {
        	return TITLES.length;
        }
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	if (resideMenu.isOpened()) {
            return resideMenu.onInterceptTouchEvent(ev) || super.dispatchTouchEvent(ev);
		} 
    	return super.dispatchTouchEvent(ev);
    }
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu); 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
	}
	
	
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	    	if (resideMenu.isOpened()) {
	    		resideMenu.closeMenu();
			} else {
				if (System.currentTimeMillis()-exitTime > 2000) {
					Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
    				exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}
			}
	        return true;
	    } else if(keyCode == KeyEvent.KEYCODE_MENU) {
	        //监控/拦截菜单键
	    } else if(keyCode == KeyEvent.KEYCODE_HOME) {
	        //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
	    }
	    return super.onKeyDown(keyCode, event);
	}	
}
