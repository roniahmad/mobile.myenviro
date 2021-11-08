package com.rsbunda.myenviro.fcm;

import com.google.firebase.messaging.FirebaseMessaging;

import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class FcmUtilities  {

    private static final String TAG = makeLogTag(FcmUtilities.class);

    private static final String RSBUNDA_MESSAGES_TOPIC_ONSITE = "/topics/rsbundamessagesonsite";
    private static final String RSBUNDA_MESSAGES_TOPIC_OFFSITE = "/topics/rsbundamessagesoffsite";

    public static void subscribeTopics(boolean isConfMessageCardsEnabled, boolean isRegisteredAttendee) {
        try {
            FirebaseMessaging pubSub = FirebaseMessaging.getInstance();
            if (isConfMessageCardsEnabled) {
                if (isRegisteredAttendee) {
                    pubSub.unsubscribeFromTopic(RSBUNDA_MESSAGES_TOPIC_OFFSITE);
                    pubSub.subscribeToTopic(RSBUNDA_MESSAGES_TOPIC_ONSITE);
                } else {
                    pubSub.subscribeToTopic(RSBUNDA_MESSAGES_TOPIC_OFFSITE);
                    pubSub.unsubscribeFromTopic(RSBUNDA_MESSAGES_TOPIC_ONSITE);
                }
            } else {
                pubSub.unsubscribeFromTopic(RSBUNDA_MESSAGES_TOPIC_ONSITE);
                pubSub.unsubscribeFromTopic(RSBUNDA_MESSAGES_TOPIC_OFFSITE);
            }
        } catch (Throwable throwable) {
            // Just in case.
            LOGE(TAG, "Exception updating conference message cards subscription.", throwable);
        }
    }
}
