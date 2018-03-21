package com.linzh.android.newfriendvoice.di.component;

import com.linzh.android.newfriendvoice.di.ActivityContext;
import com.linzh.android.newfriendvoice.di.PerActivity;
import com.linzh.android.newfriendvoice.di.module.ActivityModule;
import com.linzh.android.newfriendvoice.ui.main.MainActivity;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;
import com.linzh.android.newfriendvoice.ui.manual.ManualActivity;
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

    void inject(GestureTabFragment fragment);
}
