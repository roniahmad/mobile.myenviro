package com.rsbunda.myenviro.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.navigation.AppNavigationView;
import com.rsbunda.myenviro.navigation.AppNavigationViewAsBottomNavImpl;
import com.rsbunda.myenviro.navigation.NavigationModel;
import com.rsbunda.myenviro.navigation.NavigationModel.NavigationItemEnum;
import com.rsbunda.myenviro.ui.widget.BadgedBottomNavigationView;
import com.rsbunda.myenviro.util.RecentTasksStyler;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class BaseActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = makeLogTag(BaseActivity.class);

    // Navigation drawer
    private AppNavigationView mAppNavigationView;

    // Toolbar
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    /**
     * This utility method handles Up navigation intents by searching for a parent activity and
     * navigating there if defined. When using this for an activity make sure to define both the
     * native parentActivity as well as the AppCompat one when supporting API levels less than 16.
     * when the activity has a single parent activity. If the activity doesn't have a single parent
     * activity then don't define one and this method will use back button functionality. If "Up"
     * functionality is still desired for activities without parents then use {@code
     * syntheticParentActivity} to define one dynamically.
     * <p/>
     * Note: Up navigation intents are represented by a back arrow in the top left of the Toolbar in
     * Material Design guidelines.
     *
     * @param currentActivity         Activity in use when navigate Up action occurred.
     * @param syntheticParentActivity Parent activity to use when one is not already configured.
     */
    public static void navigateUpOrBack(Activity currentActivity,
                                        Class<? extends Activity> syntheticParentActivity) {
        // Retrieve parent activity from AndroidManifest.
        Intent intent = NavUtils.getParentActivityIntent(currentActivity);

        // Synthesize the parent activity when a natural one doesn't exist.
        if (intent == null && syntheticParentActivity != null) {
            try {
                intent = NavUtils.getParentActivityIntent(currentActivity, syntheticParentActivity);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (intent == null) {
            // No parent defined in manifest. This indicates the activity may be used by
            // in multiple flows throughout the app and doesn't have a strict parent. In
            // this case the navigation up button should act in the same manner as the
            // back button. This will result in users being forwarded back to other
            // applications if currentActivity was invoked from another application.
            currentActivity.onBackPressed();
        } else {
            if (NavUtils.shouldUpRecreateTask(currentActivity, intent)) {
                // Need to synthesize a backstack since currentActivity was probably invoked by a
                // different app. The preserves the "Up" functionality within the app according to
                // the activity hierarchy defined in AndroidManifest.xml via parentActivity
                // attributes.
                TaskStackBuilder builder = TaskStackBuilder.create(currentActivity);
                builder.addNextIntentWithParentStack(intent);
                builder.startActivities();
            } else {
                // Navigate normally to the manifest defined "Up" activity.
                NavUtils.navigateUpTo(currentActivity, intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        RecentTasksStyler.styleRecentTasksEntry(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/productsans_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses of
     * BaseActivity override this to indicate what nav drawer item corresponds to them Return
     * NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.INVALID;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //TODO:
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final BadgedBottomNavigationView bottomNav = (BadgedBottomNavigationView)
                findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            mAppNavigationView = new AppNavigationViewAsBottomNavImpl(bottomNav);
            mAppNavigationView.activityReady(this, getSelfNavDrawerItem());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_refresh) {
//            requestDataRefresh();
//        }
        return super.onOptionsItemSelected(item);
    }

    protected void requestDataRefresh() {
        //TODO: set content refresh
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                mToolbar.setNavigationContentDescription(R.string.navdrawer_description_a11y);
                mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
                if (mToolbarTitle != null) {
                    int titleId = getNavigationTitleId();
                    if (titleId != 0) {
                        mToolbarTitle.setText(titleId);
                    }
                }

                // We use our own toolbar title, so hide the default one
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        return mToolbar;
    }

    /**
     * @param clickListener The {@link android.view.View.OnClickListener} for the navigation icon of
     *                      the toolbar.
     */
    protected void setToolbarAsUp(View.OnClickListener clickListener) {
        // Initialise the toolbar
        getToolbar();
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_up);
            mToolbar.setNavigationContentDescription(R.string.close_and_go_back);
            mToolbar.setNavigationOnClickListener(clickListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onRefreshingStateChanged(boolean refreshing) {
        //TODO
    }
    protected String getAnalyticsScreenLabel() {
        return null;
    }

    protected int getNavigationTitleId() {
        return 0;
    }

    protected void setFullscreenLayout() {
        View decor = getWindow().getDecorView();
        int flags = decor.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decor.setSystemUiVisibility(flags);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }



}
