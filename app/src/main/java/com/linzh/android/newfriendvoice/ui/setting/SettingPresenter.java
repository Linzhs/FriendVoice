package com.linzh.android.newfriendvoice.ui.setting;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/24.
 */

public class SettingPresenter<V extends SettingMvpView> extends BasePresenter<V>
        implements SettingMvpPrensetner<V> {

    @Inject
    public SettingPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }
}
