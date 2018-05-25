package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.iflytek.cloud.RecognizerResult;
import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;
/**
 * Created by Linzhs on 2018/5/24.
 */

public interface VoiceToTextMvpPresenter<V extends VoiceToTextMvpView> extends MvpPresenter<V> {

    void printResult(RecognizerResult result);
}
