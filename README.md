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
			}

			@Override
			public Device getSavedDevice() {
				//TODO return locally saved device
			}

			@Override
			public void postDevice(final Device device) {
				//TODO POST device to the server
			}

			@Override
			public void putDevice(Device device) {
				//TODO PUT device to the server
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
