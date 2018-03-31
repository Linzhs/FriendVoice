package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.content.Context;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

import java.util.Map;

/**
 * Created by linzh on 2018/3/22.
 */

public interface GestureMvpPresenter<V extends GestureMvpView> extends MvpPresenter<V> {

    Map<String, String> loadVoicCodes(Context context);

    String showTime(String content);
}
