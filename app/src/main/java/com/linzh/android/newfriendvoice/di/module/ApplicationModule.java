package com.linzh.android.newfriendvoice.di.module;

import android.app.Application;
import android.content.Context;

import com.linzh.android.newfriendvoice.BuildConfig;
import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.di.ApiInfo;
import com.linzh.android.newfriendvoice.di.ApplicationContext;
import com.linzh.android.newfriendvoice.di.DatabaseInfo;
import com.linzh.android.newfriendvoice.di.PreferenceInfo;
import com.linzh.android.newfriendvoice.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by linzh on 2018/3/21.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "";
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
