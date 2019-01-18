package com.libraries.devices

import android.content.Context
import android.util.Log

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by inlacou on 11/11/16.
 */
object DeviceController {

	var log = false
	private var callbacks: Callbacks? = null
	var device: Device? = null
		private set

	private fun log(text: String){
		if (log) Log.d("ByvDevices", text)
	}

	fun initialize(context: Context, log: Boolean, callbacks: Callbacks) {
		this.log = log
		val pref = context.getSharedPreferences("ByvDevices", Context.MODE_PRIVATE)
		val previousVersion = pref.getInt("version", 0)
		log("previousVersion: $previousVersion")
		log("previousVersion: ${callbacks.version}")
		this.callbacks = callbacks
		update()
		device = device.let {
			if (it == null || callbacks.version > previousVersion) {
				val newDevice = Device()
				log("created new device0: $newDevice")
				saveDevice(newDevice)
				newDevice
			} else {
				it.setBadge(context, 0)
				it.isActive = true
				saveDevice(it)
				it
			}
		}
		callbacks.forceGetPushId()
		if(callbacks.isDeviceSent){
			callbacks.putDevice(device)
		}else{
			callbacks.postDevice(device)
		}
		pref.edit().putInt("version", callbacks.version).apply()
	}

	fun setPushId(pushId: String){
		update()
		log("setPushId | param: $pushId")
		log("setPushId | device: $device")
		val different = device!!.pushId != pushId
		log("setPushId | different: $different")
		log("setPushId | device.pushId before: ${device?.pushId}")
		device!!.pushId = pushId
		log("setPushId | device.pushId after: ${device?.pushId}")
		saveDevice(device)
		if(different) callbacks?.putDevice(device)
	}

	fun onRegistrationIdObtained(registrationId: String) {
		setPushId(registrationId)
	}

	@Throws(JSONException::class)
	fun setId(json: JSONObject) {
		update()
		if (json.has("id")) {
			device?.id = json.getString("id")
		} else if (json.has("_id")) {
			device?.id = json.getString("_id")
		}
		saveDevice(device)
	}

	fun postDevice() {
		callbacks?.postDevice(device)
	}

	fun putDevice() {
		callbacks?.putDevice(device)
	}

	fun setBadge(context: Context, badge: Int) {
		update()
		device?.setBadge(context, badge)
		saveDevice(device)
	}

	private fun saveDevice(device: Device?){
		callbacks?.saveDeviceLocal(device)
	}

	fun update(){
		device = callbacks?.savedDevice
	}

	fun onTerminate(context: Context) {
		update()
		device?.setBadge(context, 0)
		device?.isActive = true
		callbacks?.appVersionName?.let { device?.setAppVersionName(it) }
		device?.appVersionCode = callbacks?.appVersionCode
		saveDevice(device)
		callbacks?.putDevice(device)
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
