package com.libraries.devices

import android.content.Context
import android.os.Build
import android.util.Log

import java.util.Calendar
import java.util.Currency
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

import me.leolin.shortcutbadger.ShortcutBadger

/**
 * Created by inlacou on 11/11/16.
 */
class Device {

	val uid: String
	val name: String
	val os: String
	var osVersion: String? = null
		private set
	var device: String? = null
		private set
	var manufacturer: String? = null
		private set
	var model: String? = null
		private set
	var appVersion: String? = null
		private set
	var appVersionCode: String? = null
	var pushId: String? = null
	var badge: Int = 0
		private set
	var languageCode: String? = null
		private set
	var countryCode: String? = null
		private set
	var currencyCode: String? = null
		private set
	var timezone: String? = null
		private set
	private var active: Boolean = false
	var id: String? = null

	var isActive: Boolean
		get() = active
		set(active) {
			updateData()
			this.active = active
		}

	init {
		uid = UUID.randomUUID().toString()
		name = ""
		os = "android"
		pushId = "default_initial_value"
		badge = 0
		active = true
		updateData()
	}

	private fun updateData() {
		try {
			osVersion = Build.VERSION.RELEASE
		} catch (ignored: Exception) {
		}

		try {
			device = Build.DEVICE
		} catch (ignored: Exception) {
		}

		try {
			manufacturer = Build.MANUFACTURER
		} catch (ignored: Exception) {
		}

		try {
			model = Build.MODEL
		} catch (ignored: Exception) {
		}

		try {
			languageCode = Locale.getDefault().language
		} catch (ignored: Exception) {
		}

		try {
			countryCode = Locale.getDefault().country
		} catch (ignored: Exception) {
		}

		try {
			currencyCode = Currency.getInstance(Locale.getDefault()).currencyCode
		} catch (ignored: Exception) {
		}

		try {
			timezone = TimeZone.getDefault().id
		} catch (ignored: Exception) {
		}

	}

	fun setAppVersionName(appVersion: String) {
		this.appVersion = appVersion
	}

	fun setBadge(context: Context, badge: Int) {
		if(DeviceController.instance.log) Log.d(DEBUG_TAG, "setBadge: $badge")
		this.badge = badge
		try {
			ShortcutBadger.applyCount(context, this.badge)
		} catch (e: Exception) {
			if(DeviceController.instance.log) Log.w(DEBUG_TAG, "Badges not available on current device/configuration.")
		}

	}

	companion object {
		private val DEBUG_TAG = Device::class.java.name
	}
}
