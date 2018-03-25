package com.linzh.android.newfriendvoice.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.di.PerActivity;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.ui.setting.preferences.SettingFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements SettingMvpView {

    @Inject
    SettingMvpPrensetner<SettingMvpView> mPrensetner;

    @BindView(R.id.setting_toolbar)
    Toolbar mToolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getActivityComponent().inject(this);

        setUnbinder(ButterKnife.bind(this));
        mPrensetner.onAttach(this);

        setUp();
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
    protected void setUp() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("设置");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }

        initPreferencesFragment();
    }

    private void initPreferencesFragment() {
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(R.id.setting_pref, SettingFragment.newInstance())
                .commit();
    }
}
