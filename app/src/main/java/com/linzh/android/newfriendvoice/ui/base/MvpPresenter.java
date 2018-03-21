package com.linzh.android.newfriendvoice.ui.base;

import com.androidnetworking.error.ANError;

/**
 * Created by linzh on 2018/3/19.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);
}
