package com.rsbunda.myenviro.navigation;

import com.rsbunda.myenviro.BuildConfig;
import com.rsbunda.myenviro.navigation.NavigationModel.NavigationItemEnum;

import java.util.ArrayList;
import java.util.List;

public class NavigationConfig {
    public final static NavigationItemEnum[] ITEMS = new NavigationItemEnum[]{
            NavigationItemEnum.HOME,
            NavigationItemEnum.DASHBOARD,
//            NavigationItemEnum.HISTORY,
            NavigationItemEnum.NOTIFICATIONS,
            NavigationItemEnum.ACCOUNT,
    };

    private static NavigationItemEnum[] concatenateItems(NavigationItemEnum[] first,
                                                         NavigationItemEnum[] second) {
        NavigationItemEnum[] items = new NavigationItemEnum[first.length + second.length];
        for (int i = 0; i < first.length; i++) {
            items[i] = first[i];
        }
        for (int i = 0; i < second.length; i++) {
            items[first.length + i] = second[i];
        }
        return items;
    }

    public static NavigationItemEnum[] appendItem(NavigationItemEnum[] first,
                                                  NavigationItemEnum second) {
        return concatenateItems(first, new NavigationItemEnum[]{second});
    }

    public static NavigationItemEnum[] filterOutItemsDisabledInBuildConfig(
            NavigationItemEnum[] items) {
        List<NavigationItemEnum> enabledItems = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            boolean includeItem = true;
            switch (items[i]) {
                case HOME:
                    includeItem = BuildConfig.ENABLE_HOME_IN_NAVIGATION;
                    break;
                case DASHBOARD:
                    includeItem = BuildConfig.ENABLE_SCHEDULE_IN_NAVIGATION;
                    break;
//                case HISTORY:
//                    includeItem = BuildConfig.ENABLE_HISTORY_IN_NAVIGATION;
//                    break;
                case NOTIFICATIONS:
                    includeItem = BuildConfig.ENABLE_NEWS_IN_NAVIGATION;
                    break;
                case ACCOUNT:
                    includeItem = BuildConfig.ENABLE_ACCOUNT_IN_NAVIGATION;
                    break;
            }

            if (includeItem) {
                enabledItems.add(items[i]);
            }
        }
        return enabledItems.toArray(new NavigationItemEnum[enabledItems.size()]);
    }
}
