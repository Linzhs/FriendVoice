package com.linzh.android.newfriendvoice;

import android.app.Application;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.di.component.ApplicationComponent;
import com.linzh.android.newfriendvoice.di.component.DaggerApplicationComponent;
import com.linzh.android.newfriendvoice.di.module.ApplicationModule;
import com.linzh.android.newfriendvoice.utils.AppLogger;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by linzh on 2017/11/1.
 */

public class FriendVoiceApplication extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);

        AppLogger.init();// initial logger tools

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
