package com.rsbunda.myenviro.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

import static com.rsbunda.myenviro.util.LogUtils.LOGD;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class WelcomeActivity extends AppCompatActivity
        implements WelcomeFragment.WelcomeFragmentContainer {

    private static final String TAG = makeLogTag(WelcomeActivity.class);

    WelcomeFragment mContentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/productsans_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.welcome_activity);

        mContentFragment = getCurrentFragment(this);
        // If there's no fragment to use, we're done.
        if (mContentFragment == null) {
            finish();
        } else {
            // Wire up the fragment.
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment existing = fragmentManager.findFragmentById(R.id.welcome_content);
            if (existing != null) {
                fragmentTransaction.remove(existing);
            }
            fragmentTransaction.add(R.id.welcome_content, mContentFragment);
            fragmentTransaction.commit();
            AppBarLayout ctl = (AppBarLayout) findViewById(R.id.appbar);
            ctl.setBackgroundResource(mContentFragment.getHeaderColorRes());
            TextView title = (TextView) ctl.findViewById(R.id.toolbar_title);
            title.setText(mContentFragment.getTitleRes());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Gets the current fragment to display.
     *
     * @param context the application context.
     * @return the fragment to display, or null if there is no fragment.
     */
    private static WelcomeFragment getCurrentFragment(Context context) {
        List<WelcomeFragment> welcomeActivityContents = getWelcomeFragments();

        for (WelcomeFragment fragment : welcomeActivityContents) {
            if (fragment.shouldDisplay(context)) {
                return fragment;
            }
        }
        return null;
    }

    /**
     * Tracks whether to display this activity.
     *
     * @param context the application context.
     * @return true if the activity should be displayed, otherwise false.
     */
    public static boolean shouldDisplay(Context context) {
        return getCurrentFragment(context) != null;
    }

    /**
     * Returns all fragments displayed by {@link WelcomeActivity}.
     */
    private static List<WelcomeFragment> getWelcomeFragments() {
        return new ArrayList<WelcomeFragment>() {{
            add(new TosFragment());
        }};
    }

    @Override
    public Button getPrimaryButton() {
        return (Button) findViewById(R.id.button_accept);
    }

    @Override
    public void setPrimaryButtonEnabled(Boolean enabled) {
        getPrimaryButton().setEnabled(enabled);
    }

    @Override
    public Button getSecondaryButton() {
        return (Button) findViewById(R.id.button_decline);
    }

    @Override
    public void setButtonBarVisibility(boolean isVisible) {
        findViewById(R.id.welcome_button_bar).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (!isVisible) {
            ((ViewGroup.MarginLayoutParams) findViewById(R.id.welcome_scrolling_content)
                    .getLayoutParams()).bottomMargin = 0;
        }
    }

    /**
     * Proceed to the next activity.
     */
    public void doNext() {
        LOGD(TAG, "Proceeding to next activity");
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


}
