package com.linzh.android.newfriendvoice.ui.main.fragment;

import com.iflytek.cloud.RecognizerResult;
import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Linzhs on 2018/5/24.
 */

public class VoiceToTextPresenter<V extends VoiceToTextMvpView> extends BasePresenter<V> implements VoiceToTextMvpPresenter<V> {

    @Inject
    public VoiceToTextPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void printResult(RecognizerResult result) {
        String text = parselatResult(result.getResultString());
        getMvpView().updateRecyclerView(text);
    }

    private static String parselatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
