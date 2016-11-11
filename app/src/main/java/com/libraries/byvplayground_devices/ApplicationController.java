package com.libraries.byvplayground_devices;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.libraries.devices.Device;
import com.libraries.devices.DeviceController;
import com.libraries.inlacou.volleycontroller.VolleyController;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

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
		VolleyController.getInstance().init(this, new VolleyController.LogicCallbacks() {
			@Override
			public void setTokens(String authToken, String refreshToken) {
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
			public void doRefreshToken(VolleyController.IOCallbacks ioCallbacks) {
				//make call to refresh token, for example:
				//Dont need it here
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
		DeviceController.getInstance().init(new DeviceController.Callbacks() {
			@Override
			public void saveDevice(Device device) {
				SharedPreferencesManager.getInstance().setDevice(new Gson().toJson(device));
			}

			@Override
			public Device getSavedDevice() {
				return new Gson().fromJson(SharedPreferencesManager.getInstance().getDevice(), Device.class);
			}

			@Override
			public void postDevice(final Device device) {
				VolleyController.getInstance().doPost("http://playground.byvapps.com/device/api/devices", null, null, new Gson().toJson(device), "code_post_device", new VolleyController.IOCallbacks() {
					@Override
					public void onResponse(JSONObject jsonObject, String s) {
						Log.d(DEBUG_TAG, "Code: " + s + " | ResponseJson: " + jsonObject);
					}

					@Override
					public void onResponse(String s, String s1) {
						Log.d(DEBUG_TAG, "Code: " + s1 + " | Response: " + s);
						DeviceController.getInstance().setId(new Gson().fromJson(s, Device.class).getId());
					}

					@Override
					public void onResponseError(VolleyError volleyError, String s) {
						Log.d(DEBUG_TAG, "Code: " + s + " | Error: " + volleyError);
					}
				});
			}

			@Override
			public void putDevice(Device device) {
				VolleyController.getInstance().doPost("http://playground.byvapps.com/device/api/devices/"+device.getId(), null, null, new Gson().toJson(device), "code_post_device", new VolleyController.IOCallbacks() {
					@Override
					public void onResponse(JSONObject jsonObject, String s) {
						Log.d(DEBUG_TAG, "Code: " + s + " | ResponseJson: " + jsonObject);
					}

					@Override
					public void onResponse(String s, String s1) {
						Log.d(DEBUG_TAG, "Code: " + s1 + " | Response: " + s);
					}

					@Override
					public void onResponseError(VolleyError volleyError, String s) {
						Log.d(DEBUG_TAG, "Code: " + s + " | Error: " + volleyError);
					}
				});
			}
		});
	}
}