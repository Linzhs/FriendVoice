package com.linzh.android.newfriendvoice.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.ui.debug.DebugActivity;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPrensenter<LoginMvpView> mPrensenter;

    @BindView(R.id.et_email)
    EditText mEmailEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    @BindView(R.id.login_remember_passwd)
    CheckBox mCheckBox;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        mPrensenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp() {
        mPrensenter.passswdState();
    }

    @OnClick(R.id.btn_server_login)
    void onServerLogin() {
        mPrensenter.onServerLoginClick(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(), mCheckBox.isChecked());
    }

    @Override
    public void openDebugActivity() {
        Intent intent = DebugActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void updatePasswdState(String email, String passwd, boolean state) {
        if (email != null)
            mEmailEditText.setText(email);
        if (passwd != null)
            mPasswordEditText.setText(passwd);
        mCheckBox.setChecked(state);
    }

    @Override
    protected void onDestroy() {
        mPrensenter.onDetach();
        super.onDestroy();
    }
}
