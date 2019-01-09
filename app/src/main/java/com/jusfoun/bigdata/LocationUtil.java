package com.jusfoun.bigdata;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 
 * @author cxy
 * @version create time:2015-7-30
 * @Description 定位util
 */
public class LocationUtil {

	private LocationClient locationClient;
	private MyLocationListener myLocationListener;
	private MyLocationListenner loationListenner;



	private Context mContext;
	private static final boolean openGps = true;
	private int count = 0;
	private int errorCount = 0;
	private int timeOut=10000;

	public LocationUtil(Context context) {
		this.mContext = context;
		loationListenner = new MyLocationListenner();
		locationClient = new LocationClient(context);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setOpenGps(openGps);
		option.setCoorType("bd09ll");
		option.setProdName("jusfoun");
		option.setScanSpan(1000);
		option.setTimeOut(timeOut);
		option.setAddrType("all");
		option.disableCache(true);
		locationClient.setLocOption(option);	
		locationClient.registerLocationListener(loationListenner);
	}

	/**
	 * @Description 开始定位
	 */
	public void startLocation() {
		Log.e("tag","initBaiduMapLocation8");
		count =0;
		if (locationClient != null) {
			Log.e("tag","initBaiduMapLocation9");
			if (locationClient.isStarted()) {
				Log.e("tag","initBaiduMapLocation11");
				locationClient.stop(); // 关闭定位API
			}
			Log.e("tag","initBaiduMapLocation10");
			locationClient.start(); // 启动定位API
			locationClient.requestLocation(); // 发起定位
		} 
	}

	public boolean isStart(){
		if (locationClient != null && locationClient.isStarted()) {
			Log.d("TAG","定位已开启");
			return true;
		}
		Log.d("TAG","定位暂未开启");
		return false;
	}

	/**
	 * @Description 停止定位
	 */
	public void stopLocation() {
		if (locationClient != null) {
			locationClient.stop();
		}
	}

	public interface MyLocationListener {
		void locationSucc(BDLocation location);
		void locationFail();
	}
	public MyLocationListener getMyLocationListener() {
		return myLocationListener;
	}
	public void setMyLocationListener(MyLocationListener myLocationListener) {
		this.myLocationListener = myLocationListener;
	}


	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager
				= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}

		return false;
	}

	public boolean isLocationProvider(){
		LocationManager locationManager = (LocationManager)mContext.
				getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	final private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			count++;
			Log.d("tag","count=="+count);
			if (location == null) {
				if (count > 3) {// 定位三次否则失败
					if (myLocationListener != null) {
						Log.d("TAG","count > 3定位失败");
						myLocationListener.locationFail();
					}
					stopLocation();
				}
				return;
			}
			if (location.getLocType() == BDLocation.TypeNetWorkLocation || location.getLocType() == BDLocation.TypeGpsLocation) {
				if (myLocationListener != null) {
					myLocationListener.locationSucc(location);
				}
				stopLocation();
				return;
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				Log.d("TAG","BDLocation.TypeCriteriaException:");

			} else if (location.getLocType() == BDLocation.TypeServerError) {
				Log.d("TAG","BDLocation.TypeServerError:");

			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				Log.d("TAG","BDLocation.TypeNetWorkException:");

			} else if(location.getLocType() == BDLocation.TypeOffLineLocation){
				Log.d("TAG","BDLocation.TypeOffLineLocation:");

			}

			if (count > 3) {// 定位三次否则失败
				if (myLocationListener != null) {
					Log.d("TAG","count > 3定位结果location 不为空");
					myLocationListener.locationFail();
				}
				stopLocation();
			}

		}

		@Override
		public void onConnectHotSpotMessage(String s, int i) {

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

}
