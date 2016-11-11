package com.libraries.devices;

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

	public void init(Callbacks callbacks){
		this.callbacks = callbacks;
		this.device = callbacks.getSavedDevice();
		if(this.device==null){
			this.device = new Device();
			callbacks.postDevice(device);
		}
	}

	private DeviceController() {
	}

	public void onRegistrationIdObtained(String registrationId){
		device.setPushId(registrationId);
	}

	public void onTerminate(){
		callbacks.putDevice(device);
	}

	public void setId(long id) {
		device.setId(id);
		callbacks.saveDevice(device);
	}

	public void saveDevice(){
		callbacks.saveDevice(device);
	}

	public interface Callbacks{
		void saveDevice(Device device);
		Device getSavedDevice();
		void postDevice(Device device);
		void putDevice(Device device);
	}
}
