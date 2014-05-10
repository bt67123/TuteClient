package cn.edu.tute.tuteclient.view;

import cn.edu.tute.tuteclient.R;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignFragment extends Fragment implements SensorEventListener {
	
	private TextView tv_step;
	private Button btn_sign;

	boolean flag = true;
	float lastPoint;
	int count = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
		initView(rootView);
		return rootView;
	}
	
	private void initView(View view) {
		tv_step = (TextView) view.findViewById(R.id.tv_step);
		tv_step.setText(String.valueOf(count));

		btn_sign = (Button) view.findViewById(R.id.btn_sign);
		btn_sign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SensorManager sm = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
				sm.registerListener(SignFragment.this, 
						sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
						SensorManager.SENSOR_DELAY_FASTEST);
			}
		});
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (flag) {
			lastPoint = event.values[1];
			flag = false;
		} 
		if (Math.abs(event.values[1] - lastPoint) > 8) {
			lastPoint = event.values[1];
			tv_step.setText(String.valueOf(++count));
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
