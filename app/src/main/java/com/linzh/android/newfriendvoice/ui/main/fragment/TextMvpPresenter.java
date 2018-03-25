package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

/**
 * Created by linzh on 2018/3/25.
 */

public interface TextMvpPresenter<V extends TextMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void onSpeakText(String content);
}
