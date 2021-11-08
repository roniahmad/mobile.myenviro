/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.rsbunda.myenviro.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.archframework.PresenterImpl;
import com.rsbunda.myenviro.archframework.UpdatableView;
import com.rsbunda.myenviro.navigation.NavigationModel.NavigationItemEnum;
import com.rsbunda.myenviro.navigation.NavigationModel.NavigationQueryEnum;
import com.rsbunda.myenviro.navigation.NavigationModel.NavigationUserActionEnum;

public class AppNavigationViewAbstractImpl implements
        UpdatableView<NavigationModel, NavigationModel.NavigationQueryEnum, NavigationUserActionEnum>,
        AppNavigationView {

    private static final long BOTTOM_NAV_ANIM_GRACE = 115L;
    private UserActionListener<NavigationUserActionEnum> mUserActionListener;

    protected Activity mActivity;

    private final Handler mHandler = new Handler();

    protected NavigationItemEnum mSelfItem;

    @Override
    public void displayData(NavigationModel model, NavigationQueryEnum query) {
        switch (query) {
            case LOAD_ITEMS:
                displayNavigationItems(model.getItems());
                break;
        }
    }

    @Override
    public void displayErrorMessage(NavigationQueryEnum query) {
        switch (query) {
            case LOAD_ITEMS:
                // No error message displayed
                break;
        }
    }

    @Override
    public void displayUserActionResult(NavigationModel model,
                                        NavigationUserActionEnum userAction, boolean success) {
        switch (userAction) {
            case RELOAD_ITEMS:
                displayNavigationItems(model.getItems());
                break;
        }
    }

    @Override
    public Uri getDataUri(NavigationQueryEnum query) {
        // This feature has no Uri
        return null;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void addListener(UserActionListener<NavigationUserActionEnum> listener) {
        mUserActionListener = listener;
    }

    @Override
    public void activityReady(Activity activity, NavigationItemEnum self) {
        mActivity = activity;
        mSelfItem = self;

        setUpView();

        NavigationModel model = new NavigationModel();
        PresenterImpl<NavigationModel, NavigationQueryEnum, NavigationUserActionEnum> presenter
                = new PresenterImpl<>(model, this, NavigationUserActionEnum.values(),
                NavigationQueryEnum.values());
        presenter.loadInitialQueries();
        addListener(presenter);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void updateNavigationItems() {
        mUserActionListener.onUserAction(NavigationUserActionEnum.RELOAD_ITEMS, null);
    }

    @Override
    public void displayNavigationItems(NavigationItemEnum[] items) {

    }

    @Override
    public void itemSelected(final NavigationItemEnum item) {
        if (item.getClassToLaunch() != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mActivity.startActivity(new Intent(mActivity, item.getClassToLaunch()));
                    if (item.finishCurrentActivity()) {
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }, BOTTOM_NAV_ANIM_GRACE);
        }
    }

    @Override
    public void showNavigation() {

    }

    @Override
    public void showItemBadge(NavigationItemEnum item) {

    }

    @Override
    public void clearItemBadge(NavigationItemEnum item) {

    }
}
