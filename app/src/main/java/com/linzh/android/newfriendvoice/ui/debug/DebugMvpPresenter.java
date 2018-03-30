package com.linzh.android.newfriendvoice.ui.debug;

import com.linzh.android.newfriendvoice.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by linzh on 2018/3/25.
 */

public interface DebugMvpPresenter<V extends DebugMvpView> extends MvpPresenter<V> {

    void updateVoiceCodes(List<Word> words);

    void addWord(WordAdapter adapter, String key, String value);

    void deleteWord(String key);
}
