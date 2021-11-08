package com.rsbunda.myenviro.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rsbunda.myenviro.util.LoginUtils;

import static com.rsbunda.myenviro.util.LogUtils.LOGI;
import static com.rsbunda.myenviro.util.LogUtils.LOGV;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class MyInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = makeLogTag("FCMIIDListenerService");

    @Override
    public void onTokenRefresh() {
        LOGV(TAG, "Set registered to false");
        ServerUtilities.setRegisteredOnServer(this, false, ServerUtilities.getDeviceId(this), null);

        // Get the correct FCM key for the user. FCM key is a somewhat non-standard
        // approach we use in this app. For more about this, check FCM.md.
        final String fcmKey = LoginUtils.getHeroName(getApplicationContext());

        // Unregister on server.
        ServerUtilities.unregister(this, ServerUtilities.getDeviceId(this));

        // Register for a new InstanceID token. This token is sent to the server to be paired with
        // the current user's FCM key.
        if(FirebaseInstanceId.getInstance().getToken() != null) {
            sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken(), fcmKey);
//            subscribeTopics(ConfMessageCardUtils.isConfMessageCardsEnabled(this),
//                    RegistrationUtils.isRegisteredAttendee(this) ==
//                            RegistrationUtils.REGSTATUS_REGISTERED);
        }
    }

    /**
     * Send the refreshed InstanceID token to the server to be paired with the user identifying
     * fcmKey.
     *
     * @param token  InstanceID token that FCM uses to send messages to this application instance.
     * @param fcmKey String used to pair a user with an InstanceID token.
     */
    private void sendRegistrationToServer(String token, String fcmKey) {
        if (!ServerUtilities.isRegisteredOnServer(this, fcmKey)) {
            LOGI(TAG, "Registering on the FCM server with FCM key: " + fcmKey);
            boolean registered = ServerUtilities.register(this, token, fcmKey);

            if (!registered) {
                // At this point all attempts to register with the app
                // server failed, the app will try to register again when
                // it is restarted.
                LOGI(TAG, "FCM registration failed.");
            } else {
                LOGI(TAG, "FCM registration successful.");
                ServerUtilities.setRegisteredOnServer(this, true, token, fcmKey);
            }
        } else {
            LOGI(TAG, "Already registered on the FCM server with FCM key " + fcmKey);
        }
    }


}
