package com.linzh.android.newfriendvoice.ui.debug;

import android.text.TextUtils;

import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/25.
 */

public class DebugPresenter<V extends DebugMvpView> extends BasePresenter<V> implements DebugMvpPresenter<V> {

    @Inject
    public DebugPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void updateVoiceCodes(List<Word> words) {
        getMvpView().showLoading();
        for (Word w : words) {
            getDataManager().insertVoiceCode(w.getKey(), w.getValue());
        }

        getMvpView().hideLoading();
    }

    @Override
    public void addWord(WordAdapter adapter, String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            //getDataManager().insertVoiceCode(key, value);
            adapter.removeItem(new Word(key, value));
            getMvpView().showMessage("写入成功");
        } else {
            getMvpView().onError("请输入符合规则的词汇或编码！");
        }
    }

    @Override
    public void deleteWord(String key) {
        getDataManager().deleteVoiceCode(key);
        getMvpView().showMessage("删除成功");
    }
}
