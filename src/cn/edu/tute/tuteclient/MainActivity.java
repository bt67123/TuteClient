package cn.edu.tute.tuteclient;

import com.capricorn.RayMenu;

import cn.edu.tute.tuteclient.view.ClasstableFragment;
import cn.edu.tute.tuteclient.view.PersonFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	
	private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera, R.drawable.composer_music,
		R.drawable.composer_place};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initMenu();
	}
	
	private void initMenu() {
		RayMenu menu = (RayMenu) findViewById(R.id.ray_menu);
		final int itemCount = ITEM_DRAWABLES.length;
		for (int i = 0; i < itemCount; i++) {
		    ImageView item = new ImageView(this);
		    item.setImageResource(ITEM_DRAWABLES[i]);

		    final int position = i;
		    menu.addItem(item, new View.OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            Fragment fragment = new PersonFragment();
		            switch (position) {
					case 0:
						fragment = new PersonFragment();
						Bundle args = new Bundle();
						Intent intent = getIntent();
						Bundle bundle = intent.getExtras();
						String personName= bundle.getString("name");
						int collegeID = bundle.getInt("collegeID");
						args.putString("name", personName);
						args.putInt("collegeID", collegeID);
						fragment.setArguments(args);
						
						break;

					case 1:
						fragment = new ClasstableFragment();
					default:
						break;
					}
		            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		        }
		    });// Add a menu item
		}
	}
	
	public class MyFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			Bundle args = getArguments();
			textView.setText(args.getInt(ARG_SECTION_NUMBER) + "");
			textView.setTextSize(30);
			return textView;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
