package com.linzh.android.newfriendvoice.ui.setting.preferences;

import android.content.Context;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

/**
 * Created by linzh on 2018/3/24.
 */

public interface PreferencesMvpPresenter<V extends PreferencesMvpView> extends MvpPresenter<V> {

    String getApplicationCacheSize(Context context);

    void cleanApplicationCache(Context context);
}
