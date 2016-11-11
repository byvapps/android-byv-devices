package com.libraries.byvplayground_devices;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by inlacou on 30/03/15.
 */
public class SharedPreferencesManager {

	private static final String DEBUG_TAG = "SharedPrefManager";

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String SHAREDPREFERENCES_ACCESS_TOKEN = "SHAREDPREFERENCES_ACCESS_TOKEN";
	private static final String SHAREDPREFERENCES_REFRESH_TOKEN = "SHAREDPREFERENCES_REFRESH_TOKEN";
	//GCM
	private static final String SHAREDPREFERENCES_DEVICE = "SHAREDPREFERENCES_DEVICE";
	private static final String SHAREDPREFERENCES_GCM_TOKEN_SENT = "SHAREDPREFERENCES_GCM_TOKEN_SENT";
	private static final String SHAREDPREFERENCES_GCM_DEVICE_ID = "SHAREDPREFERENCES_GCM_DEVICE_ID";

	private static SharedPreferencesManager mInstance = new SharedPreferencesManager();

	public static SharedPreferencesManager getInstance(){
		return mInstance;
	}

	public static void eraseAll(Context context){
		PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
	}

	public String getAccessToken() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance());
		return sharedPreferences.getString(SHAREDPREFERENCES_ACCESS_TOKEN, "");
	}

	public void setAccessToken(String access_token) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance()).edit();
		editor.putString(SHAREDPREFERENCES_ACCESS_TOKEN, access_token);
		editor.apply();
	}

	public String getRefreshToken() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance());
		return sharedPreferences.getString(SHAREDPREFERENCES_REFRESH_TOKEN, "");
	}

	public void setRefreshToken(String refresh_token) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance()).edit();
		editor.putString(SHAREDPREFERENCES_REFRESH_TOKEN, refresh_token);
		editor.apply();
	}

	//GCM
	public void setDevice(String token) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance()).edit();
		Log.d(DEBUG_TAG, "setDevice... " + token);
		editor.putString(SHAREDPREFERENCES_DEVICE, token);
		editor.apply();
	}

	public String getDevice() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance());
		return sharedPreferences.getString(SHAREDPREFERENCES_DEVICE, "");
	}

	public void setGCMTokenSent(boolean b) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance()).edit();
		Log.d(DEBUG_TAG, "setGCMTokenSent... " + b);
		editor.putBoolean(SHAREDPREFERENCES_GCM_TOKEN_SENT, b);
		editor.apply();
	}

	public boolean getGCMTokenSent() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance());
		return sharedPreferences.getBoolean(SHAREDPREFERENCES_GCM_TOKEN_SENT, false);
	}
	//GCM
}
