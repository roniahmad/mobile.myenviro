package com.rsbunda.myenviro.history;

import android.os.Bundle;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.navigation.NavigationModel;
import com.rsbunda.myenviro.ui.BaseActivity;

public class HistoryActivity extends BaseActivity {

    private static final String SCREEN_LABEL = "History";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return null;
//        return NavigationModel.NavigationItemEnum.HISTORY;
    }

    @Override
    protected String getAnalyticsScreenLabel() {
        return SCREEN_LABEL;
    }

    @Override
    protected int getNavigationTitleId() {
        return R.string.title_history;
    }
}
