/*
 * Copyright 2019 PT Permata Bunda Bersama. All rights reserved.
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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rsbunda.myenviro.R;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class LoginUtils {

    public static final String TAG = makeLogTag(LoginUtils.class);

    public static final String PACKAGE_NAME = "com.orcalab.myenviro_";
    /**
     * String preference indicating the user token use throughout the app.
     */
    public static final String PREF_USER_TOKEN = PACKAGE_NAME+"pref_user_token";

    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_USER_NAME = PACKAGE_NAME+"pref_user_name";

    public static final String PREF_LAST_USER_NAME = PACKAGE_NAME+"pref_last_user_name";

    public static final String PREF_USER_PASSWORD = PACKAGE_NAME+"pref_user_password";

    /**
     * Boolean indicating whether user has logged in.
     */
    public static final String PREF_USER_HAS_LOGIN = PACKAGE_NAME+"pref_user_has_login";

    public static final String PREF_BPJS_ENABLED = PACKAGE_NAME+"pref_bpjs_enabled";

    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_ORGANIZATION_ID = PACKAGE_NAME+"pref_organization_id";

    public static final String PREF_ORGANIZATION_ID_LATEST = PACKAGE_NAME+"pref_organization_id_latest";

    public static final String PREF_ORGANIZATION_CODE = PACKAGE_NAME+"pref_organization_code";
    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_ORGANIZATION_NAME = PACKAGE_NAME+"pref_organization_unit";
    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_ORGANIZATION_UNIT_ADDRESS = PACKAGE_NAME+"pref_organization_unit_address";
    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_ORGANIZATION_UNIT_CITY = PACKAGE_NAME+"pref_organization_unit_city";
    /**
     * String preference indicating the user name use throughout the app.
     */
    public static final String PREF_ORGANIZATION_LOGO = PACKAGE_NAME+"pref_organization_logo";

    /**
     * Px Information
     * */
//    public static final String PREF_PX_ID = PACKAGE_NAME+"pref_px_id";

    public static final String PREF_HERO_NIP = PACKAGE_NAME+"pref_hero_nip";

    public static final String PREF_HERO_NAME = PACKAGE_NAME+"pref_hero_name";

    public static final String PREF_HERO_GELAR_DEPAN = PACKAGE_NAME+"pref_hero_gelar_depan";

    public static final String PREF_HERO_GELAR_BELAKANG = PACKAGE_NAME+"pref_hero_gelar_belakang";

    public static final String PREF_PX_BPJSID = PACKAGE_NAME+"pref_px_bpjsid";

    public static final String PREF_HERO_TIPE_USER = PACKAGE_NAME+"pref_hero_tipe_user";

    public static final String PREF_PEGAWAI_ID = PACKAGE_NAME+"pref_pegawai_id";

//    public static final String PREF_PX_AVATAR_URL = PACKAGE_NAME+"pref_px_avatar_url";

    public static final String PREF_PX_LAST_VISIT = PACKAGE_NAME+"pref_px_last_visit";

    public static final String PREF_PX_LAST_CLINIC = PACKAGE_NAME+"pref_px_last_clinic";

    public static final String PREF_PX_TEMP_PASSWORD = PACKAGE_NAME+"pref_px_temp_password";

    public static final String PREF_PX_HAS_CHANGE_PASSWORD = PACKAGE_NAME+"pref_px_has_change_password";

    public static final String PREF_PX_HAS_VALIDATED = PACKAGE_NAME+"pref_px_has_validated";

    public static final String PREF_PX_ACTIVE_TICKET = PACKAGE_NAME+"pref_px_active_ticket";

    public static final String PREF_PX_REGISTERED_BY_ME = PACKAGE_NAME+"pref_px_registered_by_me";

    public static final String PREF_PATH_DOCTOR = PACKAGE_NAME+"pref_path_doctor";

    public static final String PREF_PATH_POLY  = PACKAGE_NAME+"pref_path_poly";


//    /**
//     * Return user token.
//     *
//     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
//     */
//    public static String getPxAvatarUrl(final Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getString(PREF_PX_AVATAR_URL, null);
//    }
//
//    /**
//     * Set {@code newValue} user token.
//     * Managed by {@link com.orcalab.myenviro.ui.BaseActivity the}
//     * {@link com.orcalab.myenviro.ui.BaseActivity two} base activities.
//     *
//     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
//     * @param newValue New value that will be set.
//     */
//    public static void setPxAvatarUrl(final Context context, String newValue) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putString (PREF_PX_AVATAR_URL, newValue).apply();
//    }

    public static String getBpjsEnabled(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_BPJS_ENABLED, null);
    }

    public static void setBpjsEnabled(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_BPJS_ENABLED, newValue).apply();
    }

    /**
     * Return user token.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getHeroGelarDepan(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_HERO_GELAR_DEPAN, null);
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setHeroGelarDepan(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_HERO_GELAR_DEPAN, newValue).apply();
    }

    /**
     * Return user token.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getHeroName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_HERO_NAME, null);
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setHeroName(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_HERO_NAME, newValue).apply();
    }

    /**
     * Return user IDRM.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getHeroNip(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_HERO_NIP, null);
    }

    /**
     * Set {@code newValue} user IDRM.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setHeroNip(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_HERO_NIP, newValue).apply();
    }

    public static String getHeroGelarBelakang(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_HERO_GELAR_BELAKANG, null);
    }

    public static void setHeroGelarBelakang(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_HERO_GELAR_BELAKANG, newValue).apply();
    }

    public static int getHeroTipeUser(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_HERO_TIPE_USER, 1);
    }

    public static void setHeroTipeUser(final Context context, int newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt (PREF_HERO_TIPE_USER, newValue).apply();
    }

    public static int getHeroPegawaiId(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_PEGAWAI_ID, 1);
    }

    public static void setHeroPegawaiId(final Context context, int newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt (PREF_PEGAWAI_ID, newValue).apply();
    }

    public static String getPxBpjsId(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PX_BPJSID, null);
    }

    public static void setPxBpjsId(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_PX_BPJSID, newValue).apply();
    }

    /**
     * Return user last hospital visit.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static long getPxLastVisit(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(PREF_PX_LAST_VISIT, 0);
    }
    /**
     * Set {@code newValue} user last hospital visit.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPxLastVisit(final Context context, long newValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong (PREF_PX_LAST_VISIT, newValue).apply();
    }

    /**
     * Return user token.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getPxLastVisitedClinic(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PX_LAST_CLINIC, null);
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPxLastVisitedClinic(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_PX_LAST_CLINIC, newValue).apply();
    }

//    /**
//     * Return px id.
//     *
//     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
//     */
//    public static Integer getPxId(final Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getInt(PREF_PX_ID, 0);
//    }
//
//
//    /**
//     * Set {@code newValue} px id.
//     * Managed by {@link com.orcalab.myenviro.ui.BaseActivity the}
//     * {@link com.orcalab.myenviro.ui.BaseActivity two} base activities.
//     *
//     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
//     * @param newValue New value that will be set.
//     */
//    public static void setPxId(final Context context, Integer newValue) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putInt (PREF_PX_ID, newValue).apply();
//    }

    /**
     * Return true if user has change password.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean hasUserChangePasword(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_PX_HAS_CHANGE_PASSWORD, false);
    }
    /**
     * Mark {@code newValue whether} use has change password.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void markUserHasChangePassword(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_PX_HAS_CHANGE_PASSWORD, newValue).apply();
    }

    /**
     * Return user temporary password.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getPxTempPassword(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PX_TEMP_PASSWORD, null);
    }

    /**
     * Set {@code newValue} user temporary password.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPxLastTempPassword(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_PX_TEMP_PASSWORD, newValue).apply();
    }

    /**
     * Return true if user has been validated.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean hasUserValidated(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_PX_HAS_VALIDATED, false);
    }

    /**
     * Mark {@code newValue whether} use has validated.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void markUserHasValidated(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_PX_HAS_VALIDATED, newValue).apply();
    }

    /**
     * Return true if user has logged in.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isUserHasLogin(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_USER_HAS_LOGIN, false);
    }

    /**
     * Mark {@code newValue whether} use has logged in.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void markUserHasLogin(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_USER_HAS_LOGIN, newValue).apply();
    }

    /**
     * Return user token.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getUserToken(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_USER_TOKEN, "token");
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setUserToken(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_USER_TOKEN, newValue).apply();
    }

    /**
     * Return user name.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getUserName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_USER_NAME, "email");
    }

    /**
     * Set {@code newValue} user name.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setUserName(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_USER_NAME, newValue).apply();
    }


    public static boolean isUsingLastUserName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.key_use_last_login_id), true);
    }

    public static void markUsingLastUserName(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean (context.getString(R.string.key_use_last_login_id), newValue).apply();
    }

    public static String getLastUserName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_LAST_USER_NAME, null);
    }

    public static void setLastUserName(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_LAST_USER_NAME, newValue).apply();
    }

    public static String getUserPassword(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_USER_PASSWORD, null);
    }

    public static void setUserPassword(final Context context, String secret) {
        String encrypted = "";
        try {
            encrypted = AESUtils.encrypt(secret);
            LOGD(TAG, "encrypted:" + encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_USER_PASSWORD, encrypted).apply();
    }

    /**
     * Return organization id.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getOrganizationId(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_ID, null);
    }
    /**
     * Set {@code newValue} organization unit.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setOrganizationId(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_ID, newValue).apply();
    }


    public static String getOrganizationIdLatest(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_ID_LATEST, null);
    }

    public static void setOrganizationIdLatest(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_ID_LATEST, newValue).apply();
    }

    public static String getOrganizationCode(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_CODE, "RSBKD");
    }

    public static void setOrganizationCode(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_CODE, newValue).apply();
    }

    /**
     * Return organization unit.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getOrganizationName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_NAME, context.getResources().getString(R.string.org_name));
    }

    /**
     * Set {@code newValue} organization unit.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setOrganizationName(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_NAME, newValue).apply();
    }

    /**
     * Return organization unit Address
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getOrganizationUnitAddress(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_UNIT_ADDRESS, null);
    }

    /**
     * Set {@code newValue} organization unit address.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setOrganizationUnitAddress(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_UNIT_ADDRESS, newValue).apply();
    }

    /**
     * Return organization unit City
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getOrganizationUnitCity(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_UNIT_CITY, null);
    }

    /**
     * Set {@code newValue} organization unit city.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPrefOrganizationUnitCity(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_UNIT_CITY, newValue).apply();
    }

    /**
     * Return organization logo.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getOrganizationLogo(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORGANIZATION_LOGO, "");
    }

    /**
     * Set {@code newValue} organization logo.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setOrganizationLogo(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_ORGANIZATION_LOGO, newValue).apply();
    }

        /**
     * Return px id.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static Integer getPxActiveTicket(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_PX_ACTIVE_TICKET, 0);
    }


    /**
     * Set {@code newValue} px id.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPxActiveTicket(final Context context, Integer newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt (PREF_PX_ACTIVE_TICKET, newValue).apply();
    }

    /**
     * Return px id.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static Integer getPxRegisteredByMe(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_PX_REGISTERED_BY_ME, 0);
    }


    /**
     * Set {@code newValue} px id.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPxRegisteredByMe(final Context context, Integer newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt (PREF_PX_REGISTERED_BY_ME, newValue).apply();
    }

    /**
     * Return path dcoter.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getPathDoctor(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PATH_DOCTOR, null);
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPathDoctor(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_PATH_DOCTOR, newValue).apply();
    }

    /**
     * Return path poly.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static String getPathPoly(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_PATH_POLY, null);
    }

    /**
     * Set {@code newValue} user token.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setPathPoly(final Context context, String newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString (PREF_PATH_POLY, newValue).apply();
    }


    public static void unAuthorizedUser(final Context ctx){
        LoginUtils.markUserHasLogin(ctx, false);
        LoginUtils.setUserToken(ctx, null);
        LoginUtils.setUserName(ctx, null);

        LoginUtils.setHeroNip(ctx, null);
        LoginUtils.setHeroName(ctx, null);
        LoginUtils.setHeroGelarDepan(ctx, null);

        LoginUtils.setPxLastVisit(ctx, 0l);
        LoginUtils.setPxLastVisitedClinic(ctx, null);

        LoginUtils.setOrganizationId(ctx, null);
        LoginUtils.setOrganizationName(ctx, null);
        LoginUtils.setOrganizationLogo(ctx, null);
        LoginUtils.setOrganizationUnitAddress(ctx, null);
        LoginUtils.setPrefOrganizationUnitCity(ctx, null);

        LoginUtils.setPxActiveTicket(ctx, 0);

        LoginUtils.setPathDoctor(ctx, null);
    }

    public static void doRelogin(final Context context){
        markUserHasLogin(context, false);
        setUserToken(context, "");
        setUserName(context, "");

//        LoginActivity.startLoginActivity(context);
    }

}
