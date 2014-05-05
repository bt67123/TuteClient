package cn.edu.tute.tuteclient;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TitlePageIndicator;

import cn.edu.tute.tuteclient.view.ClasstableFragment;
import cn.edu.tute.tuteclient.view.NewsFragment;
import cn.edu.tute.tuteclient.view.PersonFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends SherlockFragmentActivity {
	
	private static final String[] TITLES = new String[] { "课表", "通知", "我" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}
	
	private void initView() {
        FragmentPagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
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
				fragment = new PersonFragment();
				Bundle args = new Bundle();
				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				String personName= bundle.getString("name");
				int collegeID = bundle.getInt("collegeID");
				args.putString("name", personName);
				args.putInt("collegeID", collegeID);
				fragment.setArguments(args);
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
		switch (item.getItemId()) {
		case R.id.action_settings:
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
}
