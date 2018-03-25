package com.linzh.android.newfriendvoice.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.linzh.android.newfriendvoice.di.ActivityContext;
import com.linzh.android.newfriendvoice.di.PerActivity;
import com.linzh.android.newfriendvoice.ui.about.AboutMvpPresenter;
import com.linzh.android.newfriendvoice.ui.about.AboutMvpView;
import com.linzh.android.newfriendvoice.ui.about.AboutPresenter;
import com.linzh.android.newfriendvoice.ui.debug.DebugMvpPresenter;
import com.linzh.android.newfriendvoice.ui.debug.DebugMvpView;
import com.linzh.android.newfriendvoice.ui.debug.DebugPresenter;
import com.linzh.android.newfriendvoice.ui.debug.Word;
import com.linzh.android.newfriendvoice.ui.debug.WordAdapter;
import com.linzh.android.newfriendvoice.ui.login.LoginMvpPrensenter;
import com.linzh.android.newfriendvoice.ui.login.LoginMvpView;
import com.linzh.android.newfriendvoice.ui.login.LoginPresenter;
import com.linzh.android.newfriendvoice.ui.main.MainMvpPresenter;
import com.linzh.android.newfriendvoice.ui.main.MainMvpView;
import com.linzh.android.newfriendvoice.ui.main.MainPresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureMvpPresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureMvpView;
import com.linzh.android.newfriendvoice.ui.main.fragment.GesturePresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;
import com.linzh.android.newfriendvoice.ui.main.fragment.MsgAdapter;
import com.linzh.android.newfriendvoice.ui.main.fragment.TextMvpPresenter;
import com.linzh.android.newfriendvoice.ui.main.fragment.TextMvpView;
import com.linzh.android.newfriendvoice.ui.main.fragment.TextPresenter;
import com.linzh.android.newfriendvoice.ui.manual.ManualMvpPresenter;
import com.linzh.android.newfriendvoice.ui.manual.ManualMvpView;
import com.linzh.android.newfriendvoice.ui.manual.ManualPresenter;
import com.linzh.android.newfriendvoice.ui.setting.SettingActivity;
import com.linzh.android.newfriendvoice.ui.setting.SettingMvpPrensetner;
import com.linzh.android.newfriendvoice.ui.setting.SettingMvpView;
import com.linzh.android.newfriendvoice.ui.setting.SettingPresenter;
import com.linzh.android.newfriendvoice.ui.setting.preferences.PreferencesMvpPresenter;
import com.linzh.android.newfriendvoice.ui.setting.preferences.PreferencesMvpView;
import com.linzh.android.newfriendvoice.ui.setting.preferences.PreferencesPresenter;
import com.linzh.android.newfriendvoice.ui.splash.SplashActivity;
import com.linzh.android.newfriendvoice.ui.splash.SplashMvpPresenter;
import com.linzh.android.newfriendvoice.ui.splash.SplashMvpView;
import com.linzh.android.newfriendvoice.ui.splash.SplashPresenter;
import com.linzh.android.newfriendvoice.ui.writer.WriterMvpPresenter;
import com.linzh.android.newfriendvoice.ui.writer.WriterMvpView;
import com.linzh.android.newfriendvoice.ui.writer.WriterPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

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
    SettingMvpPrensetner<SettingMvpView> provideSettingPresenter(SettingPresenter<SettingMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    LoginMvpPrensenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    DebugMvpPresenter<DebugMvpView> provideDebugPresenter(DebugPresenter<DebugMvpView> presenter) {
        return presenter;
    }

    @Provides
    GestureMvpPresenter<GestureMvpView> provideGestureTabPresenter(GesturePresenter<GestureMvpView> presenter) {
        return presenter;
    }

    @Provides
    TextMvpPresenter<TextMvpView> provideTextTabPresenter(TextPresenter<TextMvpView> presenter) {
        return presenter;
    }

    @Provides
    PreferencesMvpPresenter<PreferencesMvpView> providePreferencesPresenter(PreferencesPresenter<PreferencesMvpView> presenter) {
        return presenter;
    }

    @Provides
    MsgAdapter provideMsgAdapter() {
        return new MsgAdapter(new ArrayList<String>());
    }

    @Provides
    WordAdapter provideWordAdapter() {
        return new WordAdapter(new ArrayList<Word>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity appCompatActivity) {
        return new LinearLayoutManager(appCompatActivity);
    }
}
