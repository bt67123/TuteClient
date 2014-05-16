package cn.edu.tute.tuteclient.view;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import cn.edu.tute.tuteclient.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

public class SignFragment extends Fragment {
	
	private BootstrapButton btn_sign;
	private Chronometer chronometer;
	private View rootView;

	boolean isSign = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sign, container, false);
		initView();
		return rootView;
	}
	
	private void initView() {

		btn_sign = (BootstrapButton) rootView.findViewById(R.id.btn_sign);
		btn_sign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                float xValue = btn_sign.getLeft();
                float yValue = btn_sign.getTop();

				if (isSign) {

                    btn_sign.setText("签到");
                    animate(btn_sign).x(xValue).y(yValue);
                    isSign = false;
                    
                    chronometer.stop();
                    animate(chronometer).alpha(0);

				} else {

                    btn_sign.setText("完成");
                    animate(btn_sign).x(xValue).y(yValue + 250);
                    isSign = true;
                    
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(
                    		ObjectAnimator.ofFloat(chronometer, "alpha", 1),
                    		ObjectAnimator.ofFloat(chronometer, "scaleX", 1, 3f),
                    		ObjectAnimator.ofFloat(chronometer, "scaleY", 1, 3f)
                    );
                    animatorSet.setDuration(500).start();
//                    animate(chronometer).alpha(1);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

				}
			}
		});
        animate(btn_sign).setDuration(500);
        
        
        chronometer = (Chronometer) rootView.findViewById(R.id.cm);
	}


}
