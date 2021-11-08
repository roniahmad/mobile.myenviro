package com.rsbunda.myenviro.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rsbunda.myenviro.fcm.command.AnnouncementCommand;
import com.rsbunda.myenviro.util.LogUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;

public class MyFcmListenerService extends FirebaseMessagingService {
    private static final String TAG = LogUtils.makeLogTag("MsgListenerService");
    private static final String ACTION = "action";
    private static final String EXTRA_DATA = "extraData";

    private static final Map<String, FcmCommand> MESSAGE_RECEIVERS;

    static {
        // Known messages and their FCM message receivers
        Map<String, FcmCommand> receivers = new HashMap<>();
        receivers.put("announcement", new AnnouncementCommand());
        MESSAGE_RECEIVERS = Collections.unmodifiableMap(receivers);
    }

    /**
     * Handle data in FCM message payload. The action field within the data determines the
     * type of Command that is initiated and executed.
     *
     * @param message Contains the message sender and a map of actions and associated extra data.
     */
    @Override
    public void onMessageReceived(RemoteMessage message) {
        String from = message.getFrom();

        // A map containing the action and extra data associated with that action.
        Map<String, String> data = message.getData();

        // Handle received FCM data messages.
        String action = data.get(ACTION);
        String extraData = data.get(EXTRA_DATA);
        LOGD(TAG, "Got FCM message, " + ACTION + "=" + action +
                ", " + EXTRA_DATA + "=" + extraData);
        if (action == null) {
            LOGE(TAG, "Message received without command action");
            return;
        }
        //noinspection DefaultLocale
        action = action.toLowerCase();
        FcmCommand command = MESSAGE_RECEIVERS.get(action);
        if (command == null) {
            LOGE(TAG, "Unknown command received: " + action);
        } else {
            command.execute(this, action, extraData);
        }
    }
}
