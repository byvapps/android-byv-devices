package com.libraries.devices;

import android.os.Build;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

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
		osVersion = Build.VERSION.RELEASE;
		device = Build.DEVICE;
		manufacturer = Build.MANUFACTURER;
		model = Build.MODEL;
		appVersion = BuildConfig.VERSION_NAME;
		appVersionCode = String.valueOf(BuildConfig.VERSION_CODE);
		pushId = "TODO";
		languageCode = Locale.getDefault().getLanguage();
		countryCode = Locale.getDefault().getCountry();
		currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
		badge = 0;
		active = true;
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
}
