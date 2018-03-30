package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.di.component.ActivityComponent;
import com.linzh.android.newfriendvoice.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linzh on 2018/3/22.
 */

public class TextTabFragment extends BaseFragment implements TextMvpView {

    private static final String TAG = "TextTabFragment";

    @Inject
    TextMvpPresenter<TextMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    MsgAdapter mMsgAdapter;

    @BindView(R.id.msg_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.msg_send_button)
    Button mSendButton;

    @BindView(R.id.msg_input_text)
    EditText mMsgInputText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMsgAdapter);

        mPresenter.onViewPrepared();
    }

    @OnClick(R.id.msg_send_button)
    void onSpeakClick() {
        String string = mMsgInputText.getText().toString().trim();
        if (!TextUtils.isEmpty(string)) {
            mMsgAdapter.addItem(string);
            mRecyclerView.scrollToPosition(mMsgAdapter.getItemCount() - 1);
            mPresenter.onSpeakText(string);
            mMsgInputText.setText("");
        }
    }

    @Override
    public void updateRecyclerView(String content) {
        mMsgAdapter.addItem(content);
    }

    @Override
    public void onPause() {
        super.onPause();
        //获取输入法实例
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {//输入法是打开的
            //关闭输入法
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
