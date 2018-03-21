package com.linzh.android.newfriendvoice.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by linzh on 2018/3/21.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
