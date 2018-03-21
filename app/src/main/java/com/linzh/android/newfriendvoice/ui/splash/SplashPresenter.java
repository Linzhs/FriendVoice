package com.linzh.android.newfriendvoice.ui.splash;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/21.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements  SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);


    }
}
