package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.content.Context;
import android.speech.tts.Voice;
import android.text.TextUtils;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;
import com.linzh.android.newfriendvoice.utils.AppConstants;
import com.linzh.android.newfriendvoice.utils.CommonUtils;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/22.
 */

public class GesturePresenter<V extends GestureMvpView> extends BasePresenter<V> implements GestureMvpPresenter<V> {

    @Inject
    public GesturePresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public Map<String, String> loadVoicCodes(Context context) {
        String fileName = AppConstants.DATA_URL + context.getPackageName()
                + AppConstants.SHARED_PREFS_URL + AppConstants.PREF_NAME;
        if (!CommonUtils.fileExist(fileName)) {
            return VoiceUtils.getVoiceCodes();
        }
        return null;
    }

    @Override
    public String showTime(String content) {
        if (!TextUtils.isEmpty(content)) {
            String word = VoiceUtils.getVoiceValue(content.substring(0, 2));
            VoiceUtils.speak(word);
        }

        return VoiceUtils.getVoiceValue(content);
    }
}
