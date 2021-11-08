package com.rsbunda.myenviro.account;

import android.os.Bundle;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.navigation.NavigationModel;
import com.rsbunda.myenviro.ui.BaseActivity;

public class AccountActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.ACCOUNT;
    }

    @Override
    protected int getNavigationTitleId() {
        return R.string.navdrawer_item_account;
    }

}
