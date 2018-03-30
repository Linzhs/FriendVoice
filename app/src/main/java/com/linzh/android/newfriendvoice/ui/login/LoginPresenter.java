package com.linzh.android.newfriendvoice.ui.login;

import android.os.Handler;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.data.DataManager;
import com.linzh.android.newfriendvoice.ui.base.BasePresenter;
import com.linzh.android.newfriendvoice.utils.CommonUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/25.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPrensenter<V> {

    @Inject
    public LoginPresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void onServerLoginClick(final String email, final String passwd, final boolean isRememberPasswd) {
        // 验证Email和密码
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }

        if (passwd == null || passwd.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
        }

        getMvpView().showLoading();

        if (email.equals("admin@friend.com") && passwd != null && passwd.equals("rootad")) {
            if (isRememberPasswd) {
                getDataManager().setRememberPasswdState(true);
                getDataManager().setCurrentUserEmail(email);
                getDataManager().setCurrentUserPassword(passwd);
            }
            getMvpView().openDebugActivity();
        } else {
            getMvpView().onError("登录失败，请检验您的邮箱和密码");
        }

        getMvpView().hideLoading();
    }

    @Override
    public void passswdState() {
        boolean state = getDataManager().getRememberPasswdState();
        if (state) {
            getMvpView().updatePasswdState(getDataManager().getCurrentUserEmail(),
                    getDataManager().getCurrentUserPassword(), true);
        }
    }

    @Override
    public void setRememberPasswdState(boolean state) {
        getDataManager().setRememberPasswdState(state);
    }
}
