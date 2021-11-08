package com.rsbunda.myenviro;

import java.util.TimeZone;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class Config {

    private static final String TAG = makeLogTag(Config.class);

    public static final TimeZone HALLOBUNDA_TIMEZONE =
            TimeZone.getTimeZone(BuildConfig.HALLOBUNDA_TIMEZONE);

    // Play store URL prefix
    public static final String PLAY_STORE_URL_PREFIX
            = "https://play.google.com/store/apps/details?id=";

    public static final String HTTPS = "https";

}
