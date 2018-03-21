package com.linzh.android.newfriendvoice.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import butterknife.Unbinder;

/**
 * Created by linzh on 2018/3/20.
 */

public abstract class BaseDialog extends DialogFragment implements DialogMvpView {

    private BaseActivity mBaseActivity;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
            mBaseActivity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading() {
        if (mBaseActivity != null) {
            mBaseActivity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mBaseActivity != null) {
            mBaseActivity.hideLoading();
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
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.mUnbinder = unbinder;
    }

    protected abstract void setUp(View view);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void dismissDialog(String tag) {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
