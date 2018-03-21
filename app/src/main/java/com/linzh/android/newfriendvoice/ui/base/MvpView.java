package com.linzh.android.newfriendvoice.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by linzh on 2017/11/5.
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void hideKeyboard();
}
