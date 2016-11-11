package com.libraries.byvplayground_devices;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String DEBUG_TAG = MyFirebaseMessagingService.class.getName();

    /**
     * Called when message is received.
     *
     * @param message message received.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.d(DEBUG_TAG, "Message received from: " + message.getFrom());

        if (message.getFrom().startsWith("/topics/")) {
            // message received from some topic.
			//TODO handle notification from topic
			return;
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        //if(((String)data.get("gcm.notification.creatorId")!=null
                //&& ((String)data.get("gcm.notification.creatorId").equalsIgnoreCase(SharedPreferencesManager.getUserId())
	            //){
            //return;
        //}
        //TODO handle notification
        //In some cases it may be useful to show a notification indicating to the user
        //that a message was received.

        // [END_EXCLUDE]
    }

	private void logBundle(String previousKey, Bundle bundle){
		Log.d(DEBUG_TAG+".logBundle", previousKey);
		for (String key: bundle.keySet()) {
			String newKey = previousKey + key + " -> ";
			if(bundle.get(key) instanceof Bundle){
				logBundle(newKey, bundle.getBundle(key));
			} else {
				Log.d(DEBUG_TAG+".logBundle", newKey + bundle.getString(key));
			}
		}
	}
	// [END receive_message]

	public static String bundle2string(Bundle bundle) {
		if (bundle == null) {
			return null;
		}
		String string = "Bundle{";
		for (String key : bundle.keySet()) {
			string += " " + key + " => " + bundle.get(key) + ";";
		}
		string += " }Bundle";
		return string;
	}

}