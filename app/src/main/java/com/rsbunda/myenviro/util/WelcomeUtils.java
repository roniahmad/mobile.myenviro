package com.rsbunda.myenviro.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WelcomeUtils {
    /**
     * Boolean indicating whether ToS has been accepted.
     */
    static final String PREF_TOS_ACCEPTED = "pref_tos_accepted" ;

    /**
     * Return true if user has accepted the {@link com.rsbunda.myenviro.welcome.WelcomeActivity Tos}, false if they haven't.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_TOS_ACCEPTED, false);
    }

    /**
     * Mark that the user has accepted the TOS so the app doesn't ask again.
     *
     * @param context Context to be used to edit the {@link android.content.SharedPreferences}.
     */
    public static void markTosAccepted(final Context context, boolean newValues) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_TOS_ACCEPTED, newValues).apply();
    }

}
