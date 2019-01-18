package com.libraries.devices

import android.content.Context
import android.os.Build
import android.util.Log

import java.util.Currency
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

import me.leolin.shortcutbadger.ShortcutBadger

/**
 * Created by inlacou on 11/11/16.
 */
data class Device(
		val uid: String = UUID.randomUUID().toString(),
		val name: String = "",
		val os: String = "android",
		var pushId: String = "default_initial_value",
		var badge: Int = 0,
		var osVersion: String? = null,
		var device: String? = null,
		var manufacturer: String? = null,
		var model: String? = null,
		var appVersion: String? = null,
		var appVersionCode: String? = null,
		var languageCode: String? = null,
		var countryCode: String? = null,
		var currencyCode: String? = null,
		var timezone: String? = null,
		private var active: Boolean = true,
		var id: String? = null
) {

	override fun toString(): String {
		return "\"uid\": \"$uid\", " +
				"\"name\": \"$name\", " +
				"\"os\": \"$os\", " +
				"\"pushId\": \"$pushId\", " +
				"\"badge\": $badge, " +
				"\"osVersion\": \"$osVersion\", " +
				"\"device\": \"$device\", " +
				"\"manufacturer\": \"$manufacturer\", etc..."
	}

	var isActive: Boolean
		get() = active
		set(active) {
			updateData()
			this.active = active
		}

	init {
		updateData()
	}

	private fun updateData() {
		try {
			osVersion = Build.VERSION.RELEASE
		} catch (ignored: Exception) {}
		try {
			device = Build.DEVICE
		} catch (ignored: Exception) {}
		try {
			manufacturer = Build.MANUFACTURER
		} catch (ignored: Exception) {}
		try {
			model = Build.MODEL
		} catch (ignored: Exception) {}
		try {
			languageCode = Locale.getDefault().language
		} catch (ignored: Exception) {}
		try {
			countryCode = Locale.getDefault().country
		} catch (ignored: Exception) {}
		try {
			currencyCode = Currency.getInstance(Locale.getDefault()).currencyCode
		} catch (ignored: Exception) {}
		try {
			timezone = TimeZone.getDefault().id
		} catch (ignored: Exception) {}
	}

	fun setAppVersionName(appVersion: String) {
		this.appVersion = appVersion
	}

	fun setBadge(context: Context, badge: Int) {
		if(DeviceController.log) Log.d(DEBUG_TAG, "setBadge: $badge")
		this.badge = badge
		try {
			ShortcutBadger.applyCount(context, this.badge)
		} catch (e: Exception) {
			if(DeviceController.log) Log.w(DEBUG_TAG, "Badges not available on current device/configuration.")
		}
	}

	companion object {
		private val DEBUG_TAG = Device::class.java.name
	}
}
