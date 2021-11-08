package com.rsbunda.myenviro.fcm.command;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.fcm.FcmCommand;
import com.rsbunda.myenviro.inbox.InboxActivity;

import static com.rsbunda.myenviro.util.LogUtils.LOGI;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class AnnouncementCommand extends FcmCommand {
    private static final String TAG = makeLogTag("AnnouncementCommand");

    @Override
    public void execute(Context context, String type, String extraData) {
        LOGI(TAG, "Received FCM message: " + type);
        displayNotification(context, extraData);
    }

    private void displayNotification(Context context, String message) {
        LOGI(TAG, "Displaying notification: " + message);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(0, new NotificationCompat.Builder(context)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_stat_notification)
                        .setTicker(message)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(message)
                        //.setColor(context.getResources().getColor(R.color.theme_primary))
                        // Note: setColor() is available in the support lib v21+.
                        // We commented it out because we want the source to compile
                        // against support lib v20. If you are using support lib
                        // v21 or above on Android L, uncomment this line.
                        .setContentIntent(
                                PendingIntent.getActivity(context, 0,
                                        new Intent(context, InboxActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                        Intent.FLAG_ACTIVITY_SINGLE_TOP),
                                        0))
                        .setAutoCancel(true)
                        .build());
    }
}
