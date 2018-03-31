package com.linzh.android.newfriendvoice.ui.setting.preferences;

import android.content.Context;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;
import com.linzh.android.newfriendvoice.utils.CacheUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;

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
    public String getApplicationCacheSize(Context context) {
        return CacheUtil.getApplicationCacheSize(context);
    }

    @Override
    public void cleanApplicationCache(Context context) {

    }
}
