/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rsbunda.myenviro.util;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.settings.SettingsUtils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

import static com.rsbunda.myenviro.util.LogUtils.LOGW;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class TimeUtils {
    private static final String TAG = makeLogTag(TimeUtils.class);

    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    private static final int FORMAT_SHORT_DATETIME_FLAGS = DateUtils.FORMAT_ABBREV_ALL
            | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_TIME;

    private static final SimpleDateFormat[] ACCEPTED_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.US)
    };

    private static final SimpleDateFormat VALID_IFMODIFIEDSINCE_FORMAT =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public static Date parseTimestamp(String timestamp) {
        for (SimpleDateFormat format : ACCEPTED_TIMESTAMP_FORMATS) {
            // TODO: We shouldn't be forcing the time zone when parsing dates.
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                return format.parse(timestamp);
            } catch (ParseException ex) {
                continue;
            }
        }

        // All attempts to parse have failed
        return null;
    }

    public static boolean isValidFormatForIfModifiedSinceHeader(String timestamp) {
        try {
            return VALID_IFMODIFIEDSINCE_FORMAT.parse(timestamp) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public static long timestampToMillis(String timestamp, long defaultValue) {
        if (TextUtils.isEmpty(timestamp)) {
            return defaultValue;
        }
        Date d = parseTimestamp(timestamp);
        return d == null ? defaultValue : d.getTime();
    }

    public static String formatShortDayDateTime(Context context, Date date){
//        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Format dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
//        String res = iso8601Format.format(date);

        String res = dateFormat.format(date);
        return res;
    }

    public static String formatShortDate(Context context, Date date) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        return DateUtils.formatDateRange(context, formatter, date.getTime(), date.getTime(),
                DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_YEAR,
                SettingsUtils.getDisplayTimeZone(context).getID()).toString();
    }

    public static String formatShortTime(Context context, Date time) {
        // Android DateFormatter will honor the user's current settings.
        DateFormat format = android.text.format.DateFormat.getTimeFormat(context);
        // Override with Timezone based on settings since users can override their phone's timezone
        // with Pacific time zones.
        TimeZone tz = SettingsUtils.getDisplayTimeZone(context);
        if (tz != null) {
            format.setTimeZone(tz);
        }
        return format.format(time).toLowerCase(Locale.getDefault());
    }

    public static String formatShortDateTime(Context context, Date date) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        String timezone = SettingsUtils.getDisplayTimeZone(context).getID();
        return DateUtils.formatDateRange(context, formatter, date.getTime(), date.getTime(),
                FORMAT_SHORT_DATETIME_FLAGS, timezone).toString().toUpperCase(Locale.getDefault());
    }

    public static String formatDuration(@NonNull Context context, long startTime, long endTime) {
        long duration = endTime - startTime;
        Float hours = duration / (float) HOUR;
        if (hours >= 1f) {
            return context.getResources().getQuantityString(R.plurals.duration_hours,
                    (int) Math.ceil(hours), (hours == hours.intValue()) ?
                            String.valueOf(hours.intValue()) : hours.toString());
        } else {
            long mins = duration / MINUTE;
            return context.getResources().getQuantityString(
                    R.plurals.duration_minutes, (int) mins, mins);
        }
    }

    /**
     * Returns "Today", "Tomorrow", "Yesterday", or a short date format.
     */
    public static String formatHumanFriendlyShortDate(final Context context, long timestamp) {
        long localTimestamp, localTime;
        long now = getCurrentTime(context);

        TimeZone tz = SettingsUtils.getDisplayTimeZone(context);
        localTimestamp = timestamp + tz.getOffset(timestamp);
        localTime = now + tz.getOffset(now);

        long dayOrd = localTimestamp / 86400000L;
        long nowOrd = localTime / 86400000L;

        if (dayOrd == nowOrd) {
            return context.getString(R.string.today);
        } else if (dayOrd == nowOrd - 1) {
            return context.getString(R.string.yesterday);
        } else if (dayOrd == nowOrd + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            return formatShortDate(context, new Date(timestamp));
        }
    }


    public static String formatHumanFriendlyShortDayDateTime(final Context context, long timestamp) {
        long localTimestamp, localTime;
        long now = getCurrentTime(context);

        TimeZone tz = SettingsUtils.getDisplayTimeZone(context);
        localTimestamp = timestamp + tz.getOffset(timestamp);
        localTime = now + tz.getOffset(now);

        long dayOrd = localTimestamp / 86400000L;
        long nowOrd = localTime / 86400000L;

        if (dayOrd == nowOrd) {
            return context.getString(R.string.today);
        } else if (dayOrd == nowOrd - 1) {
            return context.getString(R.string.yesterday);
        } else if (dayOrd == nowOrd + 1) {
            return context.getString(R.string.tomorrow);
        } else {
            return formatShortDayDateTime(context, new Date(timestamp));
        }
    }

    public static boolean hasSessionEnded(final Context context, final Date end) {
        long now = getCurrentTime(context);
        return now > end.getTime();
    }


    /**
     * Retrieve the current time. If the current build is a debug build, the mock time is returned
     * when set, taking into account the passage of time by adding the difference between the
     * current system time and the system time when the application was created.
     */
    public static long getCurrentTime(final Context context) {
        if (BuildConfig.DEBUG) {
            return context.getSharedPreferences(UIUtils.MOCK_DATA_PREFERENCES, Context.MODE_PRIVATE)
                    .getLong(UIUtils.PREFS_MOCK_CURRENT_TIME, System.currentTimeMillis())
                    + System.currentTimeMillis() - getAppStartTime(context);
        } else {
            return System.currentTimeMillis();
        }
    }

    /**
     * Set the current time only when the current build is a debug build.
     */
    private static void setCurrentTime(Context context, long newTime) {
        if (BuildConfig.DEBUG) {
            Date currentTime = new Date(TimeUtils.getCurrentTime(context));
            LOGW(TAG, "Setting time from " + currentTime + " to " + newTime);
            context.getSharedPreferences(UIUtils.MOCK_DATA_PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putLong(UIUtils.PREFS_MOCK_CURRENT_TIME, newTime).apply();
        }
    }

    /**
     * Retrieve the app start time,set when the application was created. This is used to calculate
     * the current time, in debug mode only.
     */
    private static long getAppStartTime(final Context context) {
        return context.getSharedPreferences(UIUtils.MOCK_DATA_PREFERENCES, Context.MODE_PRIVATE)
                .getLong(UIUtils.PREFS_MOCK_APP_START_TIME, System.currentTimeMillis());
    }

    /**
     * Set the app start time only when the current build is a debug build.
     */
    public static void setAppStartTime(Context context, long newTime) {
        if (BuildConfig.DEBUG) {
            Date previousAppStartTime = new Date(TimeUtils.getAppStartTime(
                    context));
            LOGW(TAG, "Setting app startTime from " + previousAppStartTime + " to " + newTime);
            context.getSharedPreferences(UIUtils.MOCK_DATA_PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putLong(UIUtils.PREFS_MOCK_APP_START_TIME, newTime).apply();
        }
    }

}
