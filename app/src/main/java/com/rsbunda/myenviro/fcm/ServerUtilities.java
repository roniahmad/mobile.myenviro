package com.rsbunda.myenviro.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.io.response.FcmResponse;
import com.rsbunda.myenviro.io.HalloGenerator;
import com.rsbunda.myenviro.io.HalloService;
import com.rsbunda.myenviro.util.LoginUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.LOGI;
import static com.rsbunda.myenviro.util.LogUtils.LOGV;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class ServerUtilities {

    private static final String TAG = makeLogTag("FCMs");

    private static final String PREFERENCES = "com.orcalab.myenviro.fcm";
    private static final String PROPERTY_REGISTERED_TS = "registered_ts";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_USER_ID = "user_id";
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;

    private static final Random sRandom = new Random();

    private static boolean checkFcmEnabled() {
        //TODO(36700561) Check google-services.json instead of API keys.
        if (TextUtils.isEmpty(BuildConfig.FCM_API_KEY)) {
            LOGD(TAG, "FCM feature disabled (no API key configured)");
            return false;
        } else if (TextUtils.isEmpty(BuildConfig.FCM_SENDER_ID)) {
            LOGD(TAG, "FCM feature disabled (no sender ID configured)");
            return false;
        }
        return true;
    }

    /**
     * Register this account/device pair within the server.
     *
     * @param context Current context
     * @param deviceId   The FCM registration ID for this device
     * @param userId  The user identifier used to pair a user with an InstanceID token.
     * @return whether the registration succeeded or not.
     */
    public static boolean register(final Context context, final String deviceId,
                                   final String userId) {
        if (!checkFcmEnabled()) {
            return false;
        }

        try {
            LOGD(TAG, "registering device (reg_id = " + deviceId + ")");

            final String token = LoginUtils.getUserToken(context);

            HalloService service = HalloGenerator.createService(HalloService.class, context);
            Call<FcmResponse> call = service.pxregtoken(token,userId, deviceId );
            call.enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {
                    final int status = response.code();
                    final int success = response.body().getSuccess();

                    if(response.isSuccessful() && success>0){
                        final String message = response.body().getMessage();
                        LOGD(TAG, response.toString());

                    }else{
                        LOGE(TAG, response.toString() );
                    }
                }

                @Override
                public void onFailure(Call<FcmResponse> call, Throwable t) {
                    //TODO: xxx
                }
            });

            LOGI(TAG, "registering on FCM with FCM key: " + deviceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGI(TAG, "Failed to register with user ID: " + userId + " and device ID: " + deviceId);
            return false;
        }


    }

    /**
     * Unregister this account/device pair within the server.
     *
     * @param userId  The InstanceID token for this application instance.
     */
    public static void unregister(final Context context, final String userId) {
        if (!checkFcmEnabled()) {
            return;
        }

        if (userId != null) {
            try {
                LOGI(TAG, "unregistering device (deviceId = " + userId + ")");
                final String token = LoginUtils.getUserToken(context);

                HalloService service = HalloGenerator.createService(HalloService.class, context);
                Call<FcmResponse> call = service.pxunregtoken(token,userId);
                call.enqueue(new Callback<FcmResponse>() {
                    @Override
                    public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {
                        final int status = response.code();
                        final int success = response.body().getSuccess();

                        if(response.isSuccessful() && success>0){
                            final String message = response.body().getMessage();
                            LOGD(TAG, response.toString());

                        }else{
                            LOGE(TAG, response.toString() );
                        }
                    }

                    @Override
                    public void onFailure(Call<FcmResponse> call, Throwable t) {
                        //TODO: xxx
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
                LOGD(TAG, "Unable to unregister from application server", e);
            }
        } else {
            LOGD(TAG, "User must be signed in to unregister device and device ID must be non null");
        }
    }

    /**
     * Sets whether the device was successfully registered in the server side.
     *
     * @param context Current context
     * @param flag    True if registration was successful, false otherwise
     * @param deviceId   InstanceID token generated to represent the current instance of the
     *                Application.
     * @param userId  User identifier paired with deviceId on server
     */
    static void setRegisteredOnServer(Context context, boolean flag, String deviceId,
                                      String userId) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES, Context.MODE_PRIVATE);
        LOGD(TAG, "Setting registered on server status as: " + flag + ", fcmKey="
                + userId);
        Editor editor = prefs.edit();
        if (flag) {
            editor.putLong(PROPERTY_REGISTERED_TS, new Date().getTime());
            editor.putString(KEY_USER_ID, userId == null ? "" : userId);
            editor.putString(KEY_DEVICE_ID, deviceId);
        } else {
            editor.remove(KEY_DEVICE_ID);
        }
        editor.apply();
    }

    /**
     * Checks whether the device was successfully registered in the server side.
     *
     * @param context Current context
     * @return True if registration was successful, false otherwise
     */
    static boolean isRegisteredOnServer(Context context, String userId) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES, Context.MODE_PRIVATE);
        // Find registration threshold
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        long yesterdayTS = cal.getTimeInMillis();
        long regTS = prefs.getLong(PROPERTY_REGISTERED_TS, 0);

        userId = userId == null ? "" : userId;

        if (regTS > yesterdayTS) {
            LOGV(TAG, "FCM registration current. regTS=" + regTS + " yesterdayTS=" + yesterdayTS);

            final String registeredUserId = prefs.getString(KEY_USER_ID, "");
            if (registeredUserId.equals(userId)) {
                LOGD(TAG, "FCM registration is valid and for the correct fcm key: "
                        + registeredUserId);
                return true;
            }
            LOGD(TAG, "FCM registration is for DIFFERENT FCM key "
                    + registeredUserId + ". We were expecting "
                    + userId);
            return false;
        } else {
            LOGV(TAG, "FCM registration expired. regTS=" + regTS + " yesterdayTS=" + yesterdayTS);
            return false;
        }
    }

    public static String getDeviceId(Context context) {
        final SharedPreferences prefs =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(KEY_DEVICE_ID, null);
    }



}
