package com.linzh.android.newfriendvoice.ui.main;

import com.linzh.android.newfriendvoice.ui.base.MvpView;

/**
 * Created by linzh on 2018/3/21.
 */

public interface MainMvpView extends MvpView {

    void showExitDialog();

    void lockDrawer();

    void unlockDrawer();

    void openLoginActivity();

    void openWriterActivity();

    void openManualActivity();

    void openSettingActivity();

    void showTempAlert();
}
