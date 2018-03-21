package com.linzh.android.newfriendvoice.ui.manual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManualActivity extends BaseActivity implements ManualMvpView {

    @Inject
    ManualMvpPresenter<ManualMvpView> mPresenter;

    @BindView(R.id.manual_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.manual_toolbar_title)
    TextView mTextView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ManualActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        getActivityComponent().inject(this);

        setUnbinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }

        String info = mPresenter.getApplicationVersionInfo(this);
        if (!info.equals(R.string.find_application_version_info_error)) {
            updateTitle(info);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void updateTitle(String title) {
        mTextView.setText("友声智能手语 for Android " + title +" 使用说明");
    }
}
