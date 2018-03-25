package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/25.
 */

public class TextPresenter<V extends TextMvpView> extends BasePresenter<V> implements TextMvpPresenter<V> {

    @Inject
    public TextPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().updateRecyclerView("输入文本，点击“播报”即可播报语音");
    }

    @Override
    public void onSpeakText(String content) {
        VoiceUtils.speak(content);
    }
}
