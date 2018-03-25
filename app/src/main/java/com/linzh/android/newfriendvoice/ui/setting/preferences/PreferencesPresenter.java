package com.linzh.android.newfriendvoice.ui.setting.preferences;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/24.
 */

public class PreferencesPresenter<V extends PreferencesMvpView> extends BasePresenter<V>
        implements PreferencesMvpPresenter<V> {

    @Inject
    public PreferencesPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public String getApplicationCacheSize() {
        return " ";
    }
}
