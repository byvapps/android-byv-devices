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
class DeviceController private constructor() {

	var log = false
	private var callbacks: Callbacks? = null
	var device: Device? = null
		private set

	fun initialize(context: Context, log: Boolean, callbacks: Callbacks) {
		this.log = log
		val pref = context.getSharedPreferences("ByvDevices", Context.MODE_PRIVATE)
		val previousVersion = pref.getInt("version", 0)
		if (log) Log.d("ByvDevices", "previousVersion: $previousVersion")
		if (log) Log.d("ByvDevices", "currentVersion: " + callbacks.version)
		this.callbacks = callbacks
		this.device = callbacks.savedDevice
		if (this.device == null || callbacks.version > previousVersion) {
			this.device = Device()
			device!!.pushId = callbacks.forceGetPushId()
			callbacks.postDevice(device)
		} else {
			device!!.setBadge(context, 0)
			device!!.isActive = true
			callbacks.putDevice(device)
		}
		pref.edit().putInt("version", callbacks.version).apply()
	}

	fun onRegistrationIdObtained(registrationId: String) {
		device!!.pushId = registrationId
		callbacks!!.saveDeviceLocal(device)
	}

	@Throws(JSONException::class)
	fun setId(json: JSONObject) {
		if (json.has("id")) {
			device!!.id = json.getString("id")
		} else if (json.has("_id")) {
			device!!.id = json.getString("_id")
		}
		callbacks!!.saveDeviceLocal(device)
	}

	fun postDevice() {
		callbacks!!.postDevice(device)
	}

	fun putDevice() {
		callbacks!!.saveDeviceLocal(device)
		callbacks!!.putDevice(device)
	}

	fun setBadge(context: Context, badge: Int) {
		device!!.setBadge(context, badge)
	}

	fun onTerminate(context: Context) {
		device!!.setBadge(context, 0)
		device!!.isActive = true
		device!!.setAppVersionName(callbacks!!.appVersionName)
		device!!.appVersionCode = callbacks!!.appVersionCode
		callbacks!!.putDevice(device)
	}

	interface Callbacks {
		val savedDevice: Device?
		/**
		 * Library version. If upped, device will be recreated and a new POST will be sent.
		 */
		val version: Int
		val appVersionCode: String
		val appVersionName: String
		fun saveDeviceLocal(device: Device?)
		fun forceGetPushId(): String
		fun postDevice(device: Device?)
		fun putDevice(device: Device?)
	}

	companion object {
		val instance = DeviceController()
	}
}
