package com.linzh.android.newfriendvoice.di.component;

import android.app.Application;
import android.app.Service;
import android.content.Context;

import com.linzh.android.newfriendvoice.FriendVoiceApplication;
import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.di.ApplicationContext;
import com.linzh.android.newfriendvoice.di.module.ApplicationModule;
import com.linzh.android.newfriendvoice.service.BluetoothLeService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by linzh on 2018/3/21.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(FriendVoiceApplication application);

    void inject(BluetoothLeService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
