package com.linzh.android.newfriendvoice.ui.debug;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/25.
 */

public class DebugPresenter<V extends DebugMvpView> extends BasePresenter<V> implements DebugMvpPresenter<V> {

    @Inject
    public DebugPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }
}
