package com.libraries.devices;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by inlacou on 11/11/16.
 */
public class Device {

	private static final String DEBUG_TAG = Device.class.getName();

	private String uid, name, os, osVersion, device, manufacturer, model, appVersion, appVersionCode, pushId, languageCode, countryCode, currencyCode;
	private boolean active;
	private int badge;
	private long id;

	public Device(){
		uid = UUID.randomUUID().toString();
		name = "";
		os = "android";
		pushId = "TODO";
		badge = 0;
		active = true;
		updateData();
	}

	private void updateData() {
		osVersion = Build.VERSION.RELEASE;
		device = Build.DEVICE;
		manufacturer = Build.MANUFACTURER;
		model = Build.MODEL;
		appVersion = BuildConfig.VERSION_NAME;
		appVersionCode = String.valueOf(BuildConfig.VERSION_CODE);
		languageCode = Locale.getDefault().getLanguage();
		countryCode = Locale.getDefault().getCountry();
		currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getOs() {
		return os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getDevice() {
		return device;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}

	public String getPushId() {
		return pushId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public boolean isActive() {
		return active;
	}

	public int getBadge() {
		return badge;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setBadge(Context context, int badge) {
		Log.d(DEBUG_TAG, "setBadge: " + badge);
		this.badge = badge;
		try {
			ShortcutBadger.applyCount(context, this.badge);
		}catch (Exception e){
			Log.w(DEBUG_TAG, "Badges not available on current device/configuration.");
		}
	}

	public void setActive(boolean active) {
		updateData();
		this.active = active;
	}
}
