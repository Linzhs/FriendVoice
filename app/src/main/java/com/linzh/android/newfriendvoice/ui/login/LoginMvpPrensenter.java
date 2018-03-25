package com.linzh.android.newfriendvoice.ui.login;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

/**
 * Created by linzh on 2018/3/25.
 */

public interface LoginMvpPrensenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onServerLoginClick(String email, String passwd, boolean isRememberPasswd);

    void passswdState();
}
