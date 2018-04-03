package com.libraries.byvplayground_devices;

import android.app.Application;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.libraries.devices.Device;
import com.libraries.devices.DeviceController;
import com.libraries.inlacou.volleycontroller.CustomResponse;
import com.libraries.inlacou.volleycontroller.VolleyController;
import com.libraries.inlacou.volleycontroller.InternetCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by inlacou on 25/11/14.
 */
public class ApplicationController extends Application {
	
	private static final String DEBUG_TAG = ApplicationController.class.getName();
	
	/**
	 * A singleton instance of the application class for easy access in other places
	 */
	private static ApplicationController sInstance;
	
	public static ApplicationController getInstance(){
		return sInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		// initialize the singleton
		sInstance = this;
		VolleyController.getInstance().init(this, true, new VolleyController.LogicCallbacks() {
			@Override
			public void setTokens(JSONObject jsonObject) {
				//Save authToken
				//Save refreshToken
			}
			
			@Override
			public String getRefreshToken() {
				//get refreshToken
				return SharedPreferencesManager.getInstance().getRefreshToken();
			}
			
			@Override
			public String getAuthToken() {
				//get authToken
				return SharedPreferencesManager.getInstance().getAccessToken();
			}
			
			@Override
			public InternetCall doRefreshToken(java.util.ArrayList<VolleyController.IOCallbacks> arrayList) {
				//make call to refresh token, for example:
				//Dont need it here
				return null;
			}
			
			@Override
			public void onRefreshTokenInvalid() {
			
			}
			
			@Override
			public void onRefreshTokenExpired() {
			
			}
			
			@Override
			public String getRefreshTokenInvalidMessage() {
				return null;
			}
			
			@Override
			public String getRefreshTokenExpiredMessage() {
				return null;
			}
			
			@Override
			public String getAuthTokenExpiredMessage() {
				return null;
			}
		});
		DeviceController.getInstance().init(this, new DeviceController.Callbacks() {
			@Override
			public void saveDeviceLocal(Device device) {
				SharedPreferencesManager.getInstance().setDevice(new Gson().toJson(device));
			}
			
			@Override
			public Device getSavedDevice() {
				return new Gson().fromJson(SharedPreferencesManager.getInstance().getDevice(), Device.class);
			}
			
			@Override
			public int getVersion() {
				return 3;
			}
			
			@Override
			public String getAppVersionCode() {
				return BuildConfig.VERSION_CODE+"";
			}
			
			@Override
			public String getAppVersionName() {
				return BuildConfig.VERSION_NAME;
			}
			
			@Override
			public String forceGetPushId() {
				return FirebaseInstanceId.getInstance().getToken();
			}
			
			@Override
			public void postDevice(final Device device) {
				Map<String, String> map = new HashMap<>();
				try {
					JSONObject jsonObject = new JSONObject(new Gson().toJson(device));
					for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
						String key = it.next();
						map.put(key, jsonObject.get(key)+"");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				VolleyController.getInstance().onCall(new InternetCall()
						.setMethod(InternetCall.Method.POST)
						.setUrl("http://playground.byvapps.com/device/api/devices")
						.setCode("code_post_device")
						.putParams(map)
						.addCallback(new VolleyController.IOCallbacks() {
							@Override
							public void onResponse(CustomResponse customResponse, String s) {
								try {
									DeviceController.getInstance().setId(new JSONObject(customResponse.getData()));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void onResponseError(VolleyError volleyError, String s) {
								Log.d(DEBUG_TAG, "Code: " + s + " | Error: " + volleyError);
							}
						}));
			}
			
			@Override
			public void putDevice(Device device) {
				Map<String, String> map = new HashMap<>();
				try {
					JSONObject jsonObject = new JSONObject(new Gson().toJson(device));
					for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
						String key = it.next();
						map.put(key, jsonObject.get(key)+"");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				VolleyController.getInstance().onCall(new InternetCall()
						.setMethod(InternetCall.Method.PUT)
						.setUrl("http://playground.byvapps.com/device/api/devices"+device.getId())
						.setCode("code_put_device")
						.putParams(map)
						.addCallback(new VolleyController.IOCallbacks() {
							@Override
							public void onResponse(CustomResponse customResponse, String s) {
								try {
									DeviceController.getInstance().setId(new JSONObject(customResponse.getData()));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void onResponseError(VolleyError volleyError, String s) {
								Log.d(DEBUG_TAG, "Code: " + s + " | Error: " + volleyError);
							}
						}));
			}
		});
		VolleyController.getInstance().addInterceptor(new InternetCall.Interceptor() {
			@Override
			public void intercept(InternetCall internetCall) {
				internetCall.getHeaders().put("deviceId", DeviceController.getInstance().getDevice().getId()+"");
			}
		});
		Log.d(DEBUG_TAG, "Device: " + new Gson().toJson(DeviceController.getInstance().getDevice()));
	}
	
	@Override
	public void onTerminate() {
		DeviceController.getInstance().onTerminate(this);
		super.onTerminate();
	}
}