package com.linzh.android.newfriendvoice.ui.base;

/**
 * Created by linzh on 2017/11/5.
 */

public interface SubMvpView extends MvpView {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}
