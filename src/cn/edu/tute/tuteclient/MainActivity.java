package cn.edu.tute.tuteclient;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.astuetz.PagerSlidingTabStrip;
import com.baidu.mapapi.BMapManager;
import com.devspark.appmsg.AppMsg;

import cn.edu.tute.tuteclient.view.ClasstableFragment;
import cn.edu.tute.tuteclient.view.MoreFragment;
import cn.edu.tute.tuteclient.view.NewsFragment;
import cn.edu.tute.tuteclient.view.AttendFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {
	
	private PagerSlidingTabStrip tabs;
	
	private static final String[] TITLES = new String[] { "课表", "通知", "活动", "更多"};

    public static LocationManager locationManager;
    
    public BMapManager bMapManager; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initBaiduMap();

		setContentView(R.layout.activity_main);
		
		initView();
		
		initLocationService();
		
		getSupportActionBar().hide();
	}
	
	private void initBaiduMap() {
		bMapManager = new BMapManager(getApplication());
		bMapManager.init(null);
	}
	
	private void initLocationService() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				System.out.println("-->statuschanged");
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				System.out.println("-->enabled");
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				System.out.println("-->disenabled");
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
                AppMsg.makeText(MainActivity.this, "经度：" + location.getLongitude() + " 纬度：" + location.getLatitude(), AppMsg.STYLE_INFO).show();
				System.out.println("-->locationchanged");
			}
		});
	}
	
	private void initView() {
        FragmentPagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new PageChangeListener());
        
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabStrip);
		tabs.setIndicatorColor(getResources().getColor(R.color.white1));
		tabs.setViewPager(pager);
	} 
	
	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			getSupportActionBar().setTitle(TITLES[arg0]);
		}
		
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
				fragment = new AttendFragment();
				return fragment;
			case 3:
				fragment = new MoreFragment();
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
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu); 
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//为了修复某个bug，忘了。。。
//		super.onSaveInstanceState(outState);
	}
	
	
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
				if (System.currentTimeMillis()-exitTime > 2000) {
					Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
    				exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
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
