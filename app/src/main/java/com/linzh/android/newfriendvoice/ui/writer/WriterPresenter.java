package com.linzh.android.newfriendvoice.ui.writer;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/22.
 */

public class WriterPresenter<V extends WriterMvpView> extends BasePresenter<V> implements WriterMvpPresenter<V> {

    @Inject
    public WriterPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }
}
