package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.linzh.android.newfriendvoice.ui.base.MvpView;

/**
 * Created by Linzhs on 2018/5/24.
 */

public interface VoiceToTextMvpView extends MvpView {

    void updateRecyclerView(String content);
}
