package com.rsbunda.myenviro.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rsbunda.myenviro.Config;
import com.rsbunda.myenviro.R;

import java.util.TimeZone;

public class SettingsUtils {

    public static final String PACKAGE_NAME = "com.orcalab.myenviro";

    public static final String PREF_USER_HISTORY_LIST = PACKAGE_NAME+"pref_user_history_list";

    public static final String PREF_USER_NEWS_LIST = PACKAGE_NAME+"pref_user_news_list";

    public static final String PREF_USER_PRIVACY = PACKAGE_NAME+"pref_user_privacy";

    public static final String PREF_USER_TOS = PACKAGE_NAME+"pref_user_tos";

    public static final String PREF_USER_FAQ = PACKAGE_NAME+"pref_user_faq";

    public static final String PREF_USER_HELP = PACKAGE_NAME+"pref_user_help";

    public static final String PREF_APP_OSL = PACKAGE_NAME+"pref_app_osl";

    public static final String PREF_APP_CONTRIBUTOR = PACKAGE_NAME+"pref_app_contributor";

    public static final String PREF_APP_VERSION = PACKAGE_NAME+"pref_app_version";

    public static final String PREF_ENABLE_ESTIMATE_TIME = PACKAGE_NAME+"pref_enable_estimate_time";

    /**
     * Boolean preference indicating the user would like to see times in their local timezone
     * throughout the app.
     */
    public static final String PREF_LOCAL_TIMES = "pref_local_times";

    /**
     * Return the {@link TimeZone} the app is set to use (either user or conference).
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static TimeZone getDisplayTimeZone(Context context) {
        TimeZone defaultTz = TimeZone.getDefault();
        return (isUsingLocalTime(context) && defaultTz != null)
                ? defaultTz : Config.HALLOBUNDA_TIMEZONE;
    }

    /**
     * Return true if the user has indicated they want the schedule in local times, false if they
     * want to use the conference time zone. This preference is enabled/disabled by the user in the
     * {@link SettingsActivity}. If not explicitly set, it will default to conference time if
     * they are attending and local time if they are not attending.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isUsingLocalTime(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_LOCAL_TIMES, false);
    }

    /**
     * Return MinRegistrationDays
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getPxHistoryList(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_USER_HISTORY_LIST, "5");
    }

    public static String getPxNewsList(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_USER_NEWS_LIST, "5");
    }

    public static boolean isAlwaysDisplayPopupAlert(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.key_display_popup_alert), true );
    }

    public static void markEnableEstimateTime(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_ENABLE_ESTIMATE_TIME, newValue).apply();
    }

    public static boolean isEnableEstimateTime(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_ENABLE_ESTIMATE_TIME, false );
    }

}
