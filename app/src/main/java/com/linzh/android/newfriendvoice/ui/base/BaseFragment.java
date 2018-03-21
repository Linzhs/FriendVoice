package com.linzh.android.newfriendvoice.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.linzh.android.newfriendvoice.di.component.ActivityComponent;
import com.linzh.android.newfriendvoice.utils.CommonUtils;

import butterknife.Unbinder;

/**
 * Created by linzh on 2018/3/20.
 */

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mBaseActivity;
    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
            mBaseActivity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);// 不显示菜单栏
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        if (mBaseActivity != null) {
            mBaseActivity.openActivityOnTokenExpire();
        }
    }

    @Override
    public void onError(int resId) {
        if (mBaseActivity != null) {
            mBaseActivity.onError(resId);
        }
    }

    @Override
    public void onError(String message) {
        if (mBaseActivity != null) {
            mBaseActivity.onError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mBaseActivity != null) {
            mBaseActivity.showMessage(message);
        }
    }

    @Override
    public void hideKeyboard() {
        if (mBaseActivity != null) {
            mBaseActivity.hideKeyboard();
        }
    }

    @Override
    public void onDetach() {
        mBaseActivity = null;
        super.onDetach();
    }

    public ActivityComponent getActivityComponent() {
        if (mBaseActivity != null) {
            return mBaseActivity.getActivityComponent();
        }

        return null;
    }

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.mUnbinder = unbinder;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
