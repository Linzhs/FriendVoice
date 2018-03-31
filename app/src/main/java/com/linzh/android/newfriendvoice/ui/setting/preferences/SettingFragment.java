package com.linzh.android.newfriendvoice.ui.setting.preferences;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.about.AboutActivity;
import com.linzh.android.newfriendvoice.ui.login.LoginActivity;
import com.linzh.android.newfriendvoice.ui.manual.ManualActivity;
import com.linzh.android.newfriendvoice.ui.setting.SettingActivity;

import javax.inject.Inject;

/**
 * Created by linzh on 2018/3/24.
 */

public class SettingFragment extends PreferenceFragment
        implements PreferencesMvpView, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @Inject
    PreferencesMvpPresenter<PreferencesMvpView> mPresenter;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SettingActivity)getActivity()).getActivityComponent().inject(this);

        setUp();
    }
    private void setUp() {
        // 加载preference xml
        addPreferencesFromResource(R.xml.preferences_setting);

        // 启动数据刷新
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // 设置点击事件
        Preference userHelpPref = findPreference(getString(R.string.user_help));
        userHelpPref.setOnPreferenceClickListener(this);
        Preference aboutAppPref = findPreference(getString(R.string.about_app));
        aboutAppPref.setOnPreferenceClickListener(this);
        Preference speaker = findPreference(getString(R.string.speaker));
        speaker.setOnPreferenceChangeListener(this);

        Preference cachePrefs = findPreference(getString(R.string.clean_cache));
        cachePrefs.setSummary("当前缓存 " + mPresenter.getApplicationCacheSize(getActivity()));
        cachePrefs.setOnPreferenceClickListener(this);

        Preference developerPrefs = findPreference(getString(R.string.developer_debug));
        developerPrefs.setOnPreferenceClickListener(this);

        EditTextPreference feedback = (EditTextPreference) findPreference(getString(R.string.feedback));
        feedback.setPositiveButtonText("提交");
        feedback.setNegativeButtonText("取消");
        feedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case "speaker":
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "user_help":
                openManualActivity();
                break;
            case "about_app":
                openAboutActivity();
                break;
            case "clean_cache":
                showCacheCleanTip();
                break;
            case "developer_debug":
                openLoginActivity();
                break;
            default:
        }
        return true;
    }

    @Override
    public void openManualActivity() {
        startActivity(ManualActivity.getStartIntent(getActivity()));
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(getActivity()));
    }

    @Override
    public void openAboutActivity() {
        startActivity(AboutActivity.getStartIntent(getActivity()));
    }

    @Override
    public void showCacheCleanTip() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("警告");
        dialog.setMessage("清除缓存数据存在风险，是否继续？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("清理缓存中...");
                progressDialog.show();

                mPresenter.cleanApplicationCache(getActivity());

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        progressDialog.dismiss();
                    }
                }, 1000);

                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
