package com.linzh.android.newfriendvoice.ui.manual;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/22.
 */

public class ManualPresenter<V extends ManualMvpView> extends BasePresenter<V> implements ManualMvpPresenter<V> {

    @Inject
    public ManualPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public String getApplicationVersionInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.find_application_version_info_error);
        }
    }
}
