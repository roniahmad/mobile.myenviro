package com.rsbunda.myenviro.dashboard;

import android.os.Bundle;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.home.HomeActivity;
import com.rsbunda.myenviro.navigation.NavigationModel;
import com.rsbunda.myenviro.ui.BaseActivity;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class DashboardActivity extends BaseActivity {

    private static final String TAG = makeLogTag(HomeActivity.class);

    private static final String SCREEN_LABEL = "DASHBOARD";

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.DASHBOARD;
    }

    @Override
    protected String getAnalyticsScreenLabel() {
        return SCREEN_LABEL;
    }

    @Override
    protected int getNavigationTitleId() {
        return R.string.title_dashboard;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        setFullscreenLayout();
    }
}
