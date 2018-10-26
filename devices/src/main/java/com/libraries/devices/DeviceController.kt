package com.libraries.devices

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by inlacou on 11/11/16.
 */
object DeviceController {

	var log = false
	private var callbacks: Callbacks? = null
	val device: Device?
	get() = callbacks?.savedDevice

	fun initialize(context: Context, log: Boolean, callbacks: Callbacks) {
		this.log = log
		val pref = context.getSharedPreferences("ByvDevices", Context.MODE_PRIVATE)
		val previousVersion = pref.getInt("version", 0)
		if (log) Log.d("ByvDevices", "previousVersion: $previousVersion")
		if (log) Log.d("ByvDevices", "currentVersion: " + callbacks.version)
		this.callbacks = callbacks
		val currentDevice = device
		if (currentDevice == null || callbacks.version > previousVersion) {
			saveDevice(Device())
		} else {
			currentDevice.setBadge(context, 0)
			currentDevice.isActive = true
			saveDevice(currentDevice)
		}
		callbacks.forceGetPushId()
		if(callbacks.isDeviceSent){
			callbacks.putDevice(currentDevice)
		}else{
			callbacks.postDevice(currentDevice)
		}
		pref.edit().putInt("version", callbacks.version).apply()
	}

	fun setPushId(pushId: String){
		val currentDevice = device
		val different = currentDevice?.pushId == pushId
		currentDevice?.pushId = pushId
		saveDevice(currentDevice)
		if(different) callbacks?.putDevice(currentDevice)
	}

	fun onRegistrationIdObtained(registrationId: String) {
		setPushId(registrationId)
	}

	@Throws(JSONException::class)
	fun setId(json: JSONObject) {
		val currentDevice = device
		if (json.has("id")) {
			currentDevice?.id = json.getString("id")
		} else if (json.has("_id")) {
			currentDevice?.id = json.getString("_id")
		}
		saveDevice(currentDevice)
	}

	fun postDevice() {
		callbacks?.postDevice(device)
	}

	fun putDevice() {
		callbacks?.putDevice(device)
	}

	fun setBadge(context: Context, badge: Int) {
		val currentDevice = device
		currentDevice?.setBadge(context, badge)
		saveDevice(currentDevice)
	}

	private fun saveDevice(device: Device?){
		callbacks?.saveDeviceLocal(device)
	}

	fun onTerminate(context: Context) {
		val currentDevice = device
		currentDevice?.setBadge(context, 0)
		currentDevice?.isActive = true
		callbacks?.appVersionName?.let { currentDevice?.setAppVersionName(it) }
		currentDevice?.appVersionCode = callbacks?.appVersionCode
		saveDevice(currentDevice)
		callbacks?.putDevice(currentDevice)
	}

	interface Callbacks {
		val savedDevice: Device?
		/**
		 * Library version. If upped, device will be recreated and a new POST will be sent.
		 */
		val version: Int
		val appVersionCode: String
		val appVersionName: String
		val isDeviceSent: Boolean
		fun saveDeviceLocal(device: Device?)
		fun forceGetPushId()
		fun postDevice(device: Device?)
		fun putDevice(device: Device?)
	}
}
