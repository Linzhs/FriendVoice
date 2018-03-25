package com.linzh.android.newfriendvoice.ui.login;

import com.linzh.android.newfriendvoice.ui.base.MvpView;

/**
 * Created by linzh on 2018/3/25.
 */

public interface LoginMvpView extends MvpView {

    void openDebugActivity();

    void updatePasswdState(String email, String passwd, boolean state);
}
