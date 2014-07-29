package cn.edu.tute.tuteclient.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import cn.edu.tute.tuteclient.R;
import cn.edu.tute.tuteclient.domain.AttendStatus;
import cn.edu.tute.tuteclient.domain.Switch;
import cn.edu.tute.tuteclient.httpclientservice.HttpClientService;
import cn.edu.tute.tuteclient.service.JsonService;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

public class AttendFragment extends Fragment {

	private BootstrapButton btn_attend;
	private Chronometer chronometer;

	private View rootView;

	boolean isAttend = false;

	// 地图相关
	private MapView mMapView;
	MapController mMapController;

	// 定位相关
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	MyLocationOverlay myLocationOverlay = null;
	LocationData locationData = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sign, container, false);
		initView();
		return rootView;
	}

	private void initView() {
		initMap();

		initChronometer();
	}

	/**
	 * init 计时器
	 */
	private void initChronometer() {
		btn_attend = (BootstrapButton) rootView.findViewById(R.id.btn_sign);
		btn_attend.setOnClickListener(new AttendBtnClickListener());
		animate(btn_attend).setDuration(500);

		chronometer = (Chronometer) rootView.findViewById(R.id.cm);
	}

	private class AttendBtnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// float xValue = btn_attend.getLeft();
			// float yValue = btn_attend.getTop();
			//
			// if (isAttend) {
			//
			// btn_attend.setText("签到");
			// animate(btn_attend).x(xValue).y(yValue);
			// isAttend = false;
			//
			// chronometer.stop();
			// animate(chronometer).alpha(0);
			//
			// } else {
			//
			// btn_attend.setText("完成");
			// animate(btn_attend).x(xValue).y(yValue + 250);
			// isAttend = true;
			//
			// AnimatorSet animatorSet = new AnimatorSet();
			// animatorSet.playTogether(
			// ObjectAnimator.ofFloat(chronometer, "alpha", 1),
			// ObjectAnimator.ofFloat(chronometer, "scaleX", 1, 3f),
			// ObjectAnimator.ofFloat(chronometer, "scaleY", 1, 3f)
			// );
			// animatorSet.setDuration(500).start();
			// chronometer.setBase(SystemClock.elapsedRealtime());
			// chronometer.start();
			// }

			new AttendTask().execute();
		}

	}

	private class AttendTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		Switch sw = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(getActivity(), "wait", "wait...",
					false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {

				sw = JsonService.getSwitch(HttpClientService
						.getData(HttpClientService.URL_SWITCH));

				if (sw.getSwitchStatus().equals("1")) {
					String statusData = HttpClientService
							.getData(HttpClientService.URL_STUDENT_ATTEND_STATUS);
					AttendStatus status = JsonService
							.getAttendStatus(statusData);
					System.out.println("--" + status);

					String result = "";
					if (status.getStatus().equals("0")
							|| status.getStatus().equals("2")) {
						result = HttpClientService.postData(
								HttpClientService.URL_ATTEND, getMap("1"));
					} else {
						result = HttpClientService.postData(
								HttpClientService.URL_ATTEND, getMap("2"));
					}
					System.out.println("--" + result);
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private Map<String, String> getMap(String status) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("StudentID", LoginActivity.user.getID());
			map.put("Longitude", locationData.longitude + "");
			map.put("Latitude", locationData.latitude + "");
			map.put("Status", status);
			map.put("SwitchID", sw.getSwitchID());
			return map;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.cancel();
		}

	}

	private void initMap() {
		mMapView = (MapView) rootView.findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);// 设置地图zoom级别

		mLocationClient = new LocationClient(getActivity());// 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(false);// 返回的定位结果包含手机机头的方向
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();

		locationData = new LocationData();
		// 定位图层初始化
		myLocationOverlay = new MyLocationOverlay(mMapView);
		myLocationOverlay.setData(locationData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		mMapView.refresh();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locationData.latitude = location.getLatitude();
			locationData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locationData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locationData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locationData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();

			// 移动到定位点！！
			mMapController.animateTo(new GeoPoint(
					(int) (locationData.latitude * 1e6),
					(int) (locationData.longitude * 1e6)));

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}

			// logMsg(sb.toString());
			System.out.println(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			// logMsg(sb.toString());
			System.out.println(sb.toString());
		}
	}

	@Override
	public void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

}
