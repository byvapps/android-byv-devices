package com.libraries.devices;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by inlacou on 11/11/16.
 */
public class DeviceController {

	private static DeviceController ourInstance = new DeviceController();
	private Callbacks callbacks;
	private Device device;
	
	public static DeviceController getInstance() {
		return ourInstance;
	}

	public void init(Context context, Callbacks callbacks){
		SharedPreferences pref = context.getSharedPreferences("ByvDevices", Context.MODE_PRIVATE);
		int previousVersion = pref.getInt("version", 0);
		Log.d("ByvDevices", "previousVersion: " + previousVersion);
		this.callbacks = callbacks;
		this.device = callbacks.getSavedDevice();
		if(this.device==null || callbacks.getVersion()>previousVersion){
			this.device = new Device();
			device.setPushId(callbacks.forceGetPushId());
			callbacks.postDevice(device);
		}else{
			device.setBadge(context, 0);
			device.setActive(true);
			callbacks.putDevice(device);
		}
		pref.edit().putInt("version", callbacks.getVersion()).apply();
	}

	private DeviceController() {
	}

	public void onRegistrationIdObtained(String registrationId){
		device.setPushId(registrationId);
		callbacks.saveDeviceLocal(device);
	}

	public void setId(JSONObject json) throws JSONException {
		if(json.has("id")) {
			device.setId(json.getString("id"));
		}else if(json.has("_id")){
			device.setId(json.getString("_id"));
		}
		callbacks.saveDeviceLocal(device);
	}

	public void postDevice(){
		callbacks.postDevice(device);
	}

	public Device getDevice(){
		return device;
	}

	public void putDevice(){
		callbacks.saveDeviceLocal(device);
		callbacks.putDevice(device);
	}

	public void setBadge(Context context, int badge){
		device.setBadge(context, badge);
	}

	public void onTerminate(Context context){
		device.setBadge(context, 0);
		device.setActive(true);
		device.setAppVersionName(callbacks.getAppVersionName());
		device.setAppVersionCode(callbacks.getAppVersionCode());
		callbacks.putDevice(device);
	}

	public interface Callbacks{
		void saveDeviceLocal(Device device);
		Device getSavedDevice();
		int getVersion();
		String getAppVersionCode();
		String getAppVersionName();
		String forceGetPushId();
		void postDevice(Device device);
		void putDevice(Device device);
	}
}
