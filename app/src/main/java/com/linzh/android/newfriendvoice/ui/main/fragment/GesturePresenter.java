package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/22.
 */

public class GesturePresenter<V extends GestureMvpView> extends BasePresenter<V> implements GestureMvpPresenter<V> {

    @Inject
    public GesturePresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }
}
