package com.rsbunda.myenviro.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.AttrRes;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.appcompat.content.res.AppCompatResources;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.transition.Transition;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.settings.SettingsUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static com.rsbunda.myenviro.util.LogUtils.LOGE;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class UIUtils {

    private static final String TAG = makeLogTag(UIUtils.class);

    /**
     * Factor applied to session color to derive the background color on panels and when a session
     * photo could not be downloaded (or while it is being downloaded)
     */
    public static final float SESSION_BG_COLOR_SCALE_FACTOR = 0.75f;

    private static final float SESSION_PHOTO_SCRIM_ALPHA = 0.25f; // 0=invisible, 1=visible image
    private static final float SESSION_PHOTO_SCRIM_SATURATION = 0.2f; // 0=gray, 1=color image

    /**
     * Flags used with {@link DateUtils#formatDateRange}.
     */
    private static final int TIME_FLAGS = DateUtils.FORMAT_SHOW_TIME
            | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR
            | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY;
    /**
     * Flags used with {@link DateUtils#formatDateRange}.
     */
    private static final int DAY_FLAGS = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR
            | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY;

    /**
     * Regex to search for HTML escape sequences. <p/> <p></p>Searches for any continuous string of
     * characters starting with an ampersand and ending with a semicolon. (Example: &amp;amp;)
     */
    private static final Pattern REGEX_HTML_ESCAPE = Pattern.compile(".*&\\S;.*");
    public static final String MOCK_DATA_PREFERENCES = "mock_data";
    public static final String PREFS_MOCK_CURRENT_TIME = "mock_current_time";
    public static final String PREFS_MOCK_APP_START_TIME = "mock_app_start_time";

    public static final String GOOGLE_PLUS_PACKAGE_NAME = "com.google.android.apps.plus";
    public static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";
    public static final String TWITTER_PACKAGE_NAME = "com.twitter.app";

    public static final String GOOGLE_PLUS_COMMON_NAME = "Google Plus";
    public static final String TWITTER_COMMON_NAME = "Twitter";

    public static String formatEventAddress(String address, String state, String postCode, Context context){
        return address + " " + state + " " + postCode;
    }

    public static String formatEventContact(String phone, String email, Context context){
        return context.getString(R.string.event_contact_subtitle, phone, email);
    }

    public static String formatEventDetailSubtitle(long intervalStart, long intervalEnd,
                                                   StringBuilder recycle,
                                                   Context context) {
        return formatEventDetailSubtitle(intervalStart, intervalEnd, recycle, context, false);
    }

    public static String formatEventDetailSubtitle(long intervalStart, long intervalEnd,
                                                   StringBuilder recycle,
                                                   Context context, boolean shortFormat) {

        // Determine if the session is in the past
        long currentTimeMillis = TimeUtils.getCurrentTime(context);
        boolean sessionEnded = currentTimeMillis > intervalEnd;
        if (sessionEnded ) {
            return context.getString(R.string.event_finished);
        }

        if (shortFormat) {
            TimeZone timeZone = SettingsUtils.getDisplayTimeZone(context);
            Date intervalStartDate = new Date(intervalStart);
            SimpleDateFormat shortDateFormat = new SimpleDateFormat("MMM dd");
            DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            shortDateFormat.setTimeZone(timeZone);
            shortTimeFormat.setTimeZone(timeZone);
            return shortDateFormat.format(intervalStartDate) + " "
                    + shortTimeFormat.format(intervalStartDate);
        } else {
            String timeInterval = formatIntervalTimeString(intervalStart, intervalEnd, recycle,
                    context);
            return context.getString(R.string.event_detail_subtitle, timeInterval);
        }
    }

    public static String formatScheduleTimeOnly(long timeStart, long timeEnd,
                                            Integer onCall, Integer untilFinish,
                                            StringBuilder recycle,
                                            Context context){
        return formatScheduleTimeOnly(timeStart, timeEnd, onCall, untilFinish, recycle,
                context, false);
    }

    public static String formatScheduleTimeOnly(long timeStart, long timeEnd,
                                            Integer onCall, Integer untilFinish,
                                            StringBuilder recycle,
                                            Context context, boolean shortFormat){

        String result = "";

        TimeZone timeZone = SettingsUtils.getDisplayTimeZone(context);
        Date intervalStartDate = new Time(timeStart);
        Date intervalEndDate = new Time(timeEnd);
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        shortDateFormat.setTimeZone(timeZone);
        shortTimeFormat.setTimeZone(timeZone);

        if(onCall==1){
            result = "ON CALL";
        }else if(untilFinish==1){
            result = shortTimeFormat.format(intervalStartDate) + " - SELESAI";
        }else {
            result = shortTimeFormat.format(intervalStartDate) + " - " + shortTimeFormat.format(intervalEndDate);
        }

        return result;
    }


    public static String formatScheduleTime(long timeStart, long timeEnd,
                                            String dayName, Integer onCall, Integer untilFinish,
                                            StringBuilder recycle,
                                            Context context){
        return formatScheduleTime(timeStart, timeEnd, dayName, onCall, untilFinish, recycle,
                context, false);
    }

    public static String formatScheduleTime(long timeStart, long timeEnd,
                                            String dayName, Integer onCall, Integer untilFinish,
                                            StringBuilder recycle,
                                            Context context, boolean shortFormat){

        String result = "";

        TimeZone timeZone = SettingsUtils.getDisplayTimeZone(context);
        Date intervalStartDate = new Time(timeStart);
        Date intervalEndDate = new Time(timeEnd);
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        shortDateFormat.setTimeZone(timeZone);
        shortTimeFormat.setTimeZone(timeZone);

        if(onCall==1){
            result = dayName.toUpperCase()+" ON CALL";
        }else if(untilFinish==1){
            result = dayName.toUpperCase()+ " " +shortTimeFormat.format(intervalStartDate) + " - SELESAI";
        }else {
            result = dayName.toUpperCase()+ " " + shortTimeFormat.format(intervalStartDate) + " - " + shortTimeFormat.format(intervalEndDate);
        }

        return result;
    }

    /**
     * Format and return the given time interval using {@link Config#CONFERENCE_TIMEZONE} (unless
     * local time was explicitly requested by the user).
     */
    public static String formatIntervalTimeString(long intervalStart, long intervalEnd,
                                                  StringBuilder recycle, Context context) {
        if (recycle == null) {
            recycle = new StringBuilder();
        } else {
            recycle.setLength(0);
        }
        Formatter formatter = new Formatter(recycle);
        return DateUtils.formatDateRange(context, formatter, intervalStart, intervalEnd, TIME_FLAGS,
                SettingsUtils.getDisplayTimeZone(context).getID()).toString();
    }

    public static String formatDaySeparator(Context context, StringBuilder recycle, long time) {
        if (recycle == null) {
            recycle = new StringBuilder();
        } else {
            recycle.setLength(0);
        }
        Formatter formatter = new Formatter(recycle);
        return DateUtils.formatDateRange(context, formatter, time, time, DAY_FLAGS,
                SettingsUtils.getDisplayTimeZone(context).getID()).toString();
    }

    /**
     * Populate the given {@link TextView} with the requested text, formatting through {@link
     * Html#fromHtml(String)} when applicable. Also sets {@link TextView#setMovementMethod} so
     * inline links are handled.
     */
    public static void setTextMaybeHtml(TextView view, String text) {
        if (TextUtils.isEmpty(text)) {
            view.setText("");
            return;
        }
        if ((text.contains("<") && text.contains(">")) || REGEX_HTML_ESCAPE.matcher(text).find()) {
            view.setText(Html.fromHtml(text));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            view.setText(text);
        }
    }

    /**
     * Given a snippet string with matching segments surrounded by curly braces, turn those areas
     * into bold spans, removing the curly braces.
     */
    public static Spannable buildStyledSnippet(String snippet) {
        final SpannableStringBuilder builder = new SpannableStringBuilder(snippet);

        // Walk through string, inserting bold snippet spans
        int startIndex, endIndex = -1, delta = 0;
        while ((startIndex = snippet.indexOf('{', endIndex)) != -1) {
            endIndex = snippet.indexOf('}', startIndex);

            // Remove braces from both sides
            builder.delete(startIndex - delta, startIndex - delta + 1);
            builder.delete(endIndex - delta - 1, endIndex - delta);

            // Insert bold style
            builder.setSpan(new StyleSpan(Typeface.BOLD),
                    startIndex - delta, endIndex - delta - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //builder.setSpan(new ForegroundColorSpan(0xff111111),
            //        startIndex - delta, endIndex - delta - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            delta += 2;
        }

        return builder;
    }

    /**
     * This allows the app to specify a {@code packageName} to handle the {@code intent}, if the
     * {@code packageName} is available on the device and can handle it. An example use is to open a
     * Google + stream directly using the Google + app.
     */
    public static void preferPackageForIntent(Context context, Intent intent, String packageName) {
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            for (ResolveInfo resolveInfo : pm.queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo.packageName.equals(packageName)) {
                    intent.setPackage(packageName);
                    break;
                }
            }
        }
    }

    private static final int BRIGHTNESS_THRESHOLD = 130;

    /**
     * Calculate whether a color is light or dark, based on a commonly known brightness formula.
     *
     * @see {@literal http://en.wikipedia.org/wiki/HSV_color_space%23Lightness}
     */
    public static boolean isColorDark(int color) {
        return ((30 * Color.red(color) +
                59 * Color.green(color) +
                11 * Color.blue(color)) / 100) <= BRIGHTNESS_THRESHOLD;
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    private static final int[] RES_IDS_ACTION_BAR_SIZE = {R.attr.actionBarSize};

    public static int setColorOpaque(int color) {
        return Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int scaleColor(int color, float factor, boolean scaleAlpha) {
        return Color
                .argb(scaleAlpha ? (Math.round(Color.alpha(color) * factor)) : Color.alpha(color),
                        Math.round(Color.red(color) * factor),
                        Math.round(Color.green(color) * factor),
                        Math.round(Color.blue(color) * factor));
    }

    public static int scaleSessionColorToDefaultBG(int color) {
        return scaleColor(color, SESSION_BG_COLOR_SCALE_FACTOR, false);
    }


    public static void fireSocialIntent(Context context, Uri uri, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        UIUtils.preferPackageForIntent(context, intent, packageName);
        context.startActivity(intent);
    }

    /**
     * @return If on SDK 17+, returns false if setting for animator duration scale is set to 0.
     * Returns true otherwise.
     */
    public static boolean animationEnabled(ContentResolver contentResolver) {
        boolean animationEnabled = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                if (Settings.Global.getFloat(contentResolver,
                        Settings.Global.ANIMATOR_DURATION_SCALE) == 0.0f) {
                    animationEnabled = false;

                }
            } catch (Settings.SettingNotFoundException e) {
                LOGE(TAG, "Setting ANIMATOR_DURATION_SCALE not found");
            }
        }
        return animationEnabled;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return false;
        } else {
            return context.getResources().getConfiguration().getLayoutDirection()
                    == View.LAYOUT_DIRECTION_RTL;
        }
    }

    public static void setAccessibilityIgnore(View view) {
        view.setClickable(false);
        view.setFocusable(false);
        view.setContentDescription("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        }
    }

    public static float getProgress(int value, int min, int max) {
        if (min == max) {
            throw new IllegalArgumentException("Max (" + max + ") cannot equal min (" + min + ")");
        }

        return (value - min) / (float) (max - min);
    }

    /**
     * Calculate a darker variant of the given color to make it suitable for setting as the status
     * bar background.
     *
     * @param color the color to adjust.
     * @return the adjusted color.
     */
    public static
    @ColorInt
    int adjustColorForStatusBar(@ColorInt int color) {
        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color, hsl);

        // darken the color by 7.5%
        float lightness = hsl[2] * 0.925f;
        // constrain lightness to be within [0–1]
        lightness = Math.max(0f, Math.min(1f, lightness));
        hsl[2] = lightness;
        return ColorUtils.HSLToColor(hsl);
    }

    /**
     * Queries the theme of the given {@code context} for a theme color.
     *
     * @param context            the context holding the current theme.
     * @param attrResId          the theme color attribute to resolve.
     * @param fallbackColorResId a color resource id tto fallback to if the theme color cannot be
     *                           resolved.
     * @return the theme color or the fallback color.
     */
    public static
    @ColorInt
    int getThemeColor(@NonNull Context context, @AttrRes int attrResId,
                      @ColorRes int fallbackColorResId) {
        final TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(attrResId, tv, true)) {
            return tv.data;
        }
        return ContextCompat.getColor(context, fallbackColorResId);
    }

    /**
     * Sets the status bar of the given {@code activity} based on the given {@code color}. Note that
     * {@code color} will be adjusted per {@link #adjustColorForStatusBar(int)}.
     *
     * @param activity The activity to set the status bar color for.
     * @param color    The color to be adjusted and set as the status bar background.
     */
    public static void adjustAndSetStatusBarColor(@NonNull Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(adjustColorForStatusBar(color));
        }
    }

    /**
     * Retrieves the rootView of the specified {@link Activity}.
     */
    public static View getRootView(Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static Bitmap drawableToBitmap(@NonNull Context context, @DrawableRes int drawableId) {
        Drawable d = AppCompatResources.getDrawable(context, drawableId);
        final Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);
        return bitmap;
    }

    /**
     * Tints a bitmap using a color and {@link PorterDuff.Mode#MULTIPLY} mode.
     */
    @CheckResult
    static Bitmap tintBitmap(@NonNull Bitmap iconBitmap, @ColorInt int color) {
        Paint paint = new Paint();

        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        Bitmap newIcon = Bitmap.createBitmap(iconBitmap.getWidth(), iconBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newIcon);
        canvas.drawBitmap(iconBitmap, 0, 0, paint);
        return newIcon;
    }

    /**
     * A {@link Property} used for more efficiently animating a Views background color i.e. avoiding
     * using reflection to locate the getters and setters.
     */
    public static final Property<View, Integer> BACKGROUND_COLOR
            = new Property<View, Integer>(Integer.class, "backgroundColor") {

        @Override
        public void set(View view, Integer value) {
            view.setBackgroundColor(value);
        }

        @Override
        public Integer get(View view) {
            Drawable d = view.getBackground();
            if (d instanceof ColorDrawable) {
                return ((ColorDrawable) d).getColor();
            }
            return Color.TRANSPARENT;
        }
    };

    /**
     * A {@link Property} used for more efficiently animating a Views background tint i.e. avoiding
     * using reflection to locate the getters and setters.
     */
    public static final Property<View, Integer> BACKGROUND_TINT
            = new Property<View, Integer>(Integer.class, "backgroundTint") {

        @Override
        public void set(View view, Integer color) {
            view.setBackgroundTintList(ColorStateList.valueOf(color));
        }

        @Override
        public Integer get(View view) {
            return view.getBackgroundTintList().getDefaultColor();
        }
    };

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static class TransitionListenerAdapter implements Transition.TransitionListener {

        @Override
        public void onTransitionStart(final Transition transition) {

        }

        @Override
        public void onTransitionEnd(final Transition transition) {

        }

        @Override
        public void onTransitionCancel(final Transition transition) {

        }

        @Override
        public void onTransitionPause(final Transition transition) {

        }

        @Override
        public void onTransitionResume(final Transition transition) {

        }
    }


}
