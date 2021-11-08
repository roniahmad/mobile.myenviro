package com.rsbunda.myenviro.inbox;

import android.os.Bundle;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.navigation.NavigationModel;
import com.rsbunda.myenviro.ui.BaseActivity;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class InboxActivity extends BaseActivity {

    private static final String TAG = makeLogTag(InboxActivity.class);

    private static final String SCREEN_LABEL = "NEWS";

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.NOTIFICATIONS;
    }

    @Override
    protected String getAnalyticsScreenLabel() {
        return SCREEN_LABEL;
    }

    @Override
    protected int getNavigationTitleId() {
        return R.string.navdrawer_item_notification;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);
        setFullscreenLayout();
    }
}
