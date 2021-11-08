package com.rsbunda.myenviro.account;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.R;

public class RateItDialog extends DialogFragment {

    public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;

    /**
     * String preference indicating the user token use throughout the app.
     */
    public static final String PREF_USER_HAS_GIVEN_RATE = PACKAGE_NAME+"pref_user_has_given_rate";

    public static void show(Context context, FragmentManager fragmentManager) {

        boolean hasGivenRating = hasGivenRating(context);

        if (!hasGivenRating) {
            new RateItDialog().show(fragmentManager, null);
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.user_give_rating) , Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.rate_title)
                .setMessage(R.string.rate_message)
                .setPositiveButton(R.string.rate_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rateMyApp();
                        setGivenRating(getContext(), true);
                        dismiss();
                    }
                })
                .setNeutralButton(R.string.rate_remind_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.rate_never, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setGivenRating(getContext(), false);
                        dismiss();
                    }
                }).create();
    }

    private void rateMyApp(){

        final String packageName = PACKAGE_NAME;

        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }


    /**
     * Return user has given rate
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean hasGivenRating(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_USER_HAS_GIVEN_RATE, false);
    }

    /**
     * Set {@code newValue} rating.
     * Managed by {@link com.rsbunda.myenviro.ui.BaseActivity the}
     * {@link com.rsbunda.myenviro.ui.BaseActivity two} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void setGivenRating(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_USER_HAS_GIVEN_RATE, newValue).apply();
    }

}
