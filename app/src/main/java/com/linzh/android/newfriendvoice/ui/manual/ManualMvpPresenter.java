package com.linzh.android.newfriendvoice.ui.manual;

import android.content.Context;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

/**
 * Created by linzh on 2018/3/22.
 */

public interface ManualMvpPresenter<V extends ManualMvpView> extends MvpPresenter<V> {

    String getApplicationVersionInfo(Context context);
}
