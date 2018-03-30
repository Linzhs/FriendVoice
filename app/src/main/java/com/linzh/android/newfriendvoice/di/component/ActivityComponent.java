package com.linzh.android.newfriendvoice.di.component;

import com.linzh.android.newfriendvoice.di.ActivityContext;
import com.linzh.android.newfriendvoice.di.PerActivity;
import com.linzh.android.newfriendvoice.di.module.ActivityModule;
import com.linzh.android.newfriendvoice.ui.about.AboutActivity;
import com.linzh.android.newfriendvoice.ui.debug.DebugActivity;
import com.linzh.android.newfriendvoice.ui.login.LoginActivity;
import com.linzh.android.newfriendvoice.ui.main.MainActivity;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;
import com.linzh.android.newfriendvoice.ui.main.fragment.TextTabFragment;
import com.linzh.android.newfriendvoice.ui.manual.ManualActivity;
import com.linzh.android.newfriendvoice.ui.scan.DeviceScanActivity;
import com.linzh.android.newfriendvoice.ui.setting.SettingActivity;
import com.linzh.android.newfriendvoice.ui.setting.preferences.SettingFragment;
import com.linzh.android.newfriendvoice.ui.splash.SplashActivity;
import com.linzh.android.newfriendvoice.ui.writer.WriterActivity;

import dagger.Component;

/**
 * Created by linzh on 2018/3/21.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SplashActivity activity);

    void inject(ManualActivity activity);

    void inject(WriterActivity activity);

    void inject(SettingActivity activity);

    void inject(AboutActivity activity);

    void inject(LoginActivity activity);

    void inject(DebugActivity activity);

    void inject(DeviceScanActivity activity);

    void inject(SettingFragment fragment);

    void inject(TextTabFragment fragment);

    void inject(GestureTabFragment fragment);
}
