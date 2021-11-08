package com.rsbunda.myenviro.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ServerConfigUtils {

    public static final String PACKAGE_NAME = "com.orcalab.myenviro";

    /**
     * String preference indicating the user token use throughout the app.
     */
    public static final String PREF_MIN_DAYS_REG = "MIN_DAYS_REG";

    /**
     * String preference indicating the user token use throughout the app.
     */
    public static final String PREF_LAST_UUID = PACKAGE_NAME + "_PREF_LAST_UUID";

    /**
     * String preference indicating the user token use throughout the app.
     */
    public static final String PREF_LAST_ORGANIZATION = PACKAGE_NAME + "_PREF_LAST_ORGANIZATION";


    /**
     * Set {@code newValue} Minimum Registration Days.
     *
     *
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setMinRegistrationDays(final Context context, Integer newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt (PREF_MIN_DAYS_REG, newValue).apply();
    }

    /**
     * Return MinRegistrationDays
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static Integer getMinRegistrationDays(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_MIN_DAYS_REG, 1);
    }


    /**
     * Set {@code newValue} Minimum Registration Days.
     *
     *
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setListUuid(final Context context, ArrayList<String> newValue) {
        Set<String> set = new HashSet<String>();
        set.addAll(newValue);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putStringSet (PREF_LAST_UUID, set).apply();
    }

    /**
     * Return List of UUID
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static ArrayList<String> getListUuid(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = sp.getStringSet("PREF_LAST_UUID", null);
        ArrayList<String> arrUuid = new ArrayList<>();
        arrUuid.addAll(set);
        return arrUuid;
    }

    /**
     * Set {@code newValue} Minimum Registration Days.
     *
     *
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setListOrganization(final Context context, ArrayList<String> newValue) {
        Set<String> set = new HashSet<String>();
        set.addAll(newValue);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putStringSet (PREF_LAST_ORGANIZATION, set).apply();
    }

    /**
     * Return List of Organization
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static ArrayList<String> getListOrganization(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = sp.getStringSet("PREF_LAST_ORGANIZATION", null);
        ArrayList<String> arrOrg = new ArrayList<>();
        arrOrg.addAll(set);
        return arrOrg;
    }

}
