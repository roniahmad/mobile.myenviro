package com.rsbunda.myenviro.navigation;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.account.AccountActivity;
import com.rsbunda.myenviro.archframework.Model;
import com.rsbunda.myenviro.archframework.QueryEnum;
import com.rsbunda.myenviro.archframework.UserActionEnum;
import com.rsbunda.myenviro.home.HomeActivity;
import com.rsbunda.myenviro.inbox.InboxActivity;
import com.rsbunda.myenviro.dashboard.DashboardActivity;

public class NavigationModel implements Model<NavigationModel.NavigationQueryEnum,
        NavigationModel.NavigationUserActionEnum>{


    private NavigationItemEnum[] mItems;

    public NavigationItemEnum[] getItems() {
        return mItems;
    }

    @Override
    public NavigationQueryEnum[] getQueries() {
        return NavigationQueryEnum.values();
    }

    @Override
    public NavigationUserActionEnum[] getUserActions() {
        return NavigationUserActionEnum.values();
    }

    @Override
    public void deliverUserAction(final NavigationUserActionEnum action,
                                  @Nullable final Bundle args,
                                  final UserActionCallback<NavigationUserActionEnum> callback) {
        switch (action) {
            case RELOAD_ITEMS:
                mItems = null;
                populateNavigationItems();
                callback.onModelUpdated(this, action);
                break;
        }
    }

    @Override
    public void requestData(final NavigationQueryEnum query,
                            final DataQueryCallback<NavigationQueryEnum> callback) {
        switch (query) {
            case LOAD_ITEMS:
                if (mItems != null) {
                    callback.onModelUpdated(this, query);
                } else {
                    populateNavigationItems();
                    callback.onModelUpdated(this, query);
                }
                break;
        }
    }

    private void populateNavigationItems() {
        NavigationItemEnum[] items = NavigationConfig.ITEMS;
        mItems = NavigationConfig.filterOutItemsDisabledInBuildConfig(items);
    }

    @Override
    public void cleanUp() {
        // no-op
    }

    /**
     * List of all possible navigation items.
     */
    public enum NavigationItemEnum {
        HOME(R.id.home_nav_item, R.string.navdrawer_item_home, R.drawable.ic_nav_home,
                HomeActivity.class, true),

        DASHBOARD(R.id.dashboard_nav_item, R.string.navdrawer_item_dashboard,
                R.drawable.ic_nav_schedule, DashboardActivity.class, true),

//        HISTORY(R.id.history_nav_item, R.string.navdrawer_item_history,
//                R.drawable.ic_nav_history, HistoryActivity.class, true),

        NOTIFICATIONS(R.id.notification_nav_item, R.string.navdrawer_item_notification,
                R.drawable.ic_nav_campaign, InboxActivity.class, true),

        ACCOUNT(R.id.account_nav_item, R.string.navdrawer_item_account,
                R.drawable.ic_nav_account, AccountActivity.class, true),

        INVALID(12, 0, 0, null);

        private int id;

        private int titleResource;

        private int iconResource;

        private Class classToLaunch;

        private boolean finishCurrentActivity;

        NavigationItemEnum(int id, int titleResource, int iconResource, Class classToLaunch) {
            this(id, titleResource, iconResource, classToLaunch, false);
        }

        NavigationItemEnum(int id, int titleResource, int iconResource, Class classToLaunch,
                           boolean finishCurrentActivity) {
            this.id = id;
            this.titleResource = titleResource;
            this.iconResource = iconResource;
            this.classToLaunch = classToLaunch;
            this.finishCurrentActivity = finishCurrentActivity;
        }

        public static NavigationItemEnum getById(int id) {
            for (NavigationItemEnum value : NavigationItemEnum.values()) {
                if (value.getId() == id) {
                    return value;
                }
            }
            return INVALID;
        }

        public int getId() {
            return id;
        }

        public int getTitleResource() {
            return titleResource;
        }

        public int getIconResource() {
            return iconResource;
        }

        public Class getClassToLaunch() {
            return classToLaunch;
        }

        public boolean finishCurrentActivity() {
            return finishCurrentActivity;
        }

    }

    public enum NavigationQueryEnum implements QueryEnum {
        LOAD_ITEMS(0);

        private int id;

        NavigationQueryEnum(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String[] getProjection() {
            return new String[0];
        }
    }

    public enum NavigationUserActionEnum implements UserActionEnum {
        RELOAD_ITEMS(0);

        private int id;

        NavigationUserActionEnum(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }
    }
}
