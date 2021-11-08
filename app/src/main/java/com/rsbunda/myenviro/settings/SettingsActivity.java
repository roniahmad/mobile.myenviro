package com.rsbunda.myenviro.settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.account.FaqActivity;
import com.rsbunda.myenviro.account.HelpActivity;
import com.rsbunda.myenviro.account.PrivacyActivity;
import com.rsbunda.myenviro.account.TosActivity;
import com.rsbunda.myenviro.util.AboutUtils;
import com.rsbunda.myenviro.util.ContributorUtils;
import com.rsbunda.myenviro.util.LogUtils;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static final String TAG = LogUtils.makeLogTag(SettingsActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(SettingsActivity.this);
            }
        });

    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            // gallery EditText change listener
            bindPreferenceSummaryToValue(findPreference(SettingsUtils.PREF_USER_HISTORY_LIST));

            // notification preference change listener
//            bindPreferenceSummaryToValue(findPreference(SettingsUtils.PREF_USER_HISTORY_LIST)));

            //APP Version
            Preference mPrefAppVersion = findPreference(SettingsUtils.PREF_APP_VERSION);
            mPrefAppVersion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return false;
                }
            });

            //Contributor
            Preference mPrefContributor = findPreference(SettingsUtils.PREF_APP_CONTRIBUTOR);
            mPrefContributor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ContributorUtils.showContributors(getActivity());
                    return true;
                }
            });

            Preference mPrefOSL = findPreference(SettingsUtils.PREF_APP_OSL);
            mPrefOSL.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AboutUtils.showOpenSourceLicenses(getActivity());
                    return true;
                }
            });

            //FAQ
            Preference mPrefFaq = findPreference(SettingsUtils.PREF_USER_FAQ);
            mPrefFaq.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FaqActivity.startFaqActivity(getActivity());
                    return true;
                }
            });

            //ToS
            Preference mPrefTos = findPreference(SettingsUtils.PREF_USER_TOS);
            mPrefTos.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    TosActivity.startTosActivity(getActivity());
                    return true;
                }
            });

            Preference mPrefPolicy = findPreference(SettingsUtils.PREF_USER_PRIVACY);
            mPrefPolicy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    PrivacyActivity.startPrivacyActivity(getActivity());
                    return true;
                }
            });

            Preference mPrefHelp = findPreference(SettingsUtils.PREF_USER_HELP);
            mPrefHelp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    HelpActivity.startHelpActivity(getActivity());
                    return true;
                }
            });

            Preference mPrefVersion = findPreference(SettingsUtils.PREF_APP_VERSION);
            mPrefVersion.setSummary(BuildConfig.VERSION_NAME);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals(SettingsUtils.PREF_USER_HISTORY_LIST)) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    public static void startSettingsActivity(final Activity activity) {
        Intent settingsIntent = new Intent(activity,
                SettingsActivity.class);
        activity.startActivity(settingsIntent);
    }

}
