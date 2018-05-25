package com.linzh.android.newfriendvoice.ui.main.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.di.component.ActivityComponent;
import com.linzh.android.newfriendvoice.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceToTextFragment extends BaseFragment implements VoiceToTextMvpView {

    @Inject
    VoiceToTextMvpPresenter<VoiceToTextMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    MsgAdapter mMsgAdapter;

    @BindView(R.id.voice_to_text_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.voice_start_btn)
    Button btnVoiceStart;

    public VoiceToTextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voice_to_text, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        requestPermission();
    }

    @Override
    protected void setUp(View view) {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMsgAdapter);

        updateRecyclerView("语音转文本");
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.RECORD_AUDIO  }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getActivity(), "无法获取麦克风权限, 语音转文本功能无法使用", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRecyclerView(String content) {
        mMsgAdapter.addItem(content);
        mRecyclerView.scrollToPosition(mMsgAdapter.getItemCount() - 1);
    }

    @OnClick(R.id.voice_start_btn)
    void onBtnVoiceStartClick() {
        RecognizerDialog dialog = new RecognizerDialog(getActivity(), null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                mPresenter.printResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {
                switch (speechError.getErrorCode()) {
                    case 20001:
                        Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                        break;
                    case 20006:
                        Toast.makeText(getActivity(), "启动录音失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 20016:
                        Toast.makeText(getActivity(), "请允许程序获取录音权限", Toast.LENGTH_SHORT).show();
                        break;
                    case 10118:
                        Toast.makeText(getActivity(), "您好像没有说话", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "异常", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        dialog.show();
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
