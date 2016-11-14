package com.libraries.devices;

import android.content.Context;

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
		this.callbacks = callbacks;
		this.device = callbacks.getSavedDevice();
		if(this.device==null){
			this.device = new Device();
			callbacks.postDevice(device);
		}else{
			device.setBadge(context, 0);
			device.setActive(true);
			callbacks.putDevice(device);
		}
	}

	private DeviceController() {
	}

	public void onRegistrationIdObtained(String registrationId){
		device.setPushId(registrationId);
		callbacks.saveDeviceLocal(device);
	}

	public void setId(long id) {
		device.setId(id);
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
		callbacks.putDevice(device);
	}

	public interface Callbacks{
		void saveDeviceLocal(Device device);
		Device getSavedDevice();
		void postDevice(Device device);
		void putDevice(Device device);
	}
}
