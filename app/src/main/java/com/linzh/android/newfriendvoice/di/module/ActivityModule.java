package com.linzh.android.newfriendvoice.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.linzh.android.newfriendvoice.di.ActivityContext;
import com.linzh.android.newfriendvoice.di.PerActivity;
import com.linzh.android.newfriendvoice.ui.main.MainMvpPresenter;
import com.linzh.android.newfriendvoice.ui.main.MainMvpView;
import com.linzh.android.newfriendvoice.ui.main.MainPresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureMvpPresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureMvpView;
import com.linzh.android.newfriendvoice.ui.main.fragment.GesturePresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;
import com.linzh.android.newfriendvoice.ui.manual.ManualMvpPresenter;
import com.linzh.android.newfriendvoice.ui.manual.ManualMvpView;
import com.linzh.android.newfriendvoice.ui.manual.ManualPresenter;
import com.linzh.android.newfriendvoice.ui.splash.SplashActivity;
import com.linzh.android.newfriendvoice.ui.splash.SplashMvpPresenter;
import com.linzh.android.newfriendvoice.ui.splash.SplashMvpView;
import com.linzh.android.newfriendvoice.ui.splash.SplashPresenter;
import com.linzh.android.newfriendvoice.ui.writer.WriterMvpPresenter;
import com.linzh.android.newfriendvoice.ui.writer.WriterMvpView;
import com.linzh.android.newfriendvoice.ui.writer.WriterPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/21.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManualMvpPresenter<ManualMvpView> provideManualPresenter(ManualPresenter<ManualMvpView> presenter) {
        return presenter;
    }

    @Provides
    WriterMvpPresenter<WriterMvpView> provideWriterPresenter(WriterPresenter<WriterMvpView> presenter) {
        return presenter;
    }

    @Provides
    GestureMvpPresenter<GestureMvpView> provideGestureTabPresenter(GesturePresenter<GestureMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity appCompatActivity) {
        return new LinearLayoutManager(appCompatActivity);
    }
}
