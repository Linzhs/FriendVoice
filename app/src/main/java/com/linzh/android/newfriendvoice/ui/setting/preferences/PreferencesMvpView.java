package com.linzh.android.newfriendvoice.ui.setting.preferences;

import com.linzh.android.newfriendvoice.ui.base.MvpView;

/**
 * Created by linzh on 2018/3/24.
 */

public interface PreferencesMvpView extends MvpView {

    void openManualActivity();

    void openLoginActivity();

    void openAboutActivity();

    void showCacheCleanTip();
}
