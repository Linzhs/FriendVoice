package com.linzh.android.newfriendvoice.ui.scan;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DeviceScanPresenter<V extends DeviceScanMvpView> extends BasePresenter<V>
        implements DeviceScanMvpPresenter<V> {

    @Inject
    public DeviceScanPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }
}
