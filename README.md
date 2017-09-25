# android-byv-devices

[![](https://jitpack.io/v/byvapps/android-byv-devices.svg)](https://jitpack.io/#byvapps/android-byv-devices)

Add this (for the badger library) to your Project build.gradle:

```java
buildscript {
	repositories {
		...
    mavenCentral()
	}
  ...
}
```

And initialize the library on your aplication class:

```java
DeviceController.getInstance().init(this, new DeviceController.Callbacks() {
			@Override
			public void saveDeviceLocal(Device device) {
				//TODO return save device locally
				//For example save it on SharedPreferences
			}

			@Override
			public Device getSavedDevice() {
				//TODO return locally saved device
				//For example load it from SharedPreferences and return it
			}

			@Override
			public void postDevice(final Device device) {
				//TODO POST device to the server
				/*Example
				VolleyController.getInstance().onCall(InternetCall()
						.setUrl(BuildConfig.HOST + "/device/api/devices")
						.setCode("code_post_device")
						.putHeader("Content-Type", VolleyController.ContentType.JSON.toString())
						.setRawBody(Gson().toJson(device))
						.setMethod(InternetCall.Method.POST)
						.addCallback(object : VolleyController.IOCallbacks {
							override fun onResponse(customResponse: CustomResponse, s1: String) {
								Log.d(DEBUG_TAG, "Code: " + s1 + " | Response: " + customResponse.data)
								DeviceController.getInstance().setId(Gson().fromJson(customResponse.data, Device::class.java).id)
							}

							override fun onResponseError(volleyError: VolleyError, s: String?) {
								Log.d(DEBUG_TAG, "Code: $s | Error: $volleyError")
							}
						})
				)
				*/
			}

			@Override
			public void putDevice(Device device) {
				//TODO PUT device to the server
				/*Example
				VolleyController.getInstance().onCall(InternetCall()
						.setUrl(BuildConfig.HOST + "/device/api/devices" + device.id)
						.setCode("code_put_device")
						.putHeader("Content-Type", VolleyController.ContentType.JSON.toString())
						.setRawBody(Gson().toJson(device))
						.setMethod(InternetCall.Method.PUT)
						.addCallback(object : VolleyController.IOCallbacks {
							override fun onResponse(customResponse: CustomResponse, s1: String) {
								Log.d(DEBUG_TAG, "Code: " + s1 + " | Response: " + customResponse.data)
								DeviceController.getInstance().setId(Gson().fromJson(customResponse.data, Device::class.java).id)
							}

							override fun onResponseError(volleyError: VolleyError, s: String?) {
								Log.d(DEBUG_TAG, "Code: $s | Error: $volleyError")
							}
						})
				)
				*/
			}
		});
```

When get FCM or GCM registrationId:

```java

			DeviceController.getInstance().onRegistrationIdObtained(token);
			DeviceController.getInstance().postDevice();
```

Or, if it's not a new device:

```java

			DeviceController.getInstance().onRegistrationIdObtained(token);
			DeviceController.getInstance().putDevice();
```


Do not forget to create the project on Firebase (https://firebase.google.com/docs/android/setup?hl=en-419) console and add the `google-services.json` file to your `/app` folder!
