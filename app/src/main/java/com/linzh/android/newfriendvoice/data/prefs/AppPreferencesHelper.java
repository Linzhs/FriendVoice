package com.linzh.android.newfriendvoice.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.linzh.android.newfriendvoice.di.ApplicationContext;
import com.linzh.android.newfriendvoice.di.PreferenceInfo;
import com.linzh.android.newfriendvoice.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by linzh on 2018/3/25.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_CURRENT_USER_PASSWORD = "PREF_KEY_CURRENT_USER_PASSWORD";
    private static final String PREF_KEY_REMEBER_PASSWORD_STATE = "PREF_KEY_REMEBER_PASSWORD_STATE";

    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context, @PreferenceInfo String prefFileName) {
        mSharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public Long getCurrentUserId() {
        Long userId = mSharedPreferences.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId == AppConstants.NULL_INDEX ? null : userId;
    }

    @Override
    public void setCurrentUserId(Long id) {
        Long userId = id == null ? AppConstants.NULL_INDEX : id;
        mSharedPreferences.edit().putLong(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mSharedPreferences.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String name) {
        mSharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_NAME, name).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mSharedPreferences.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mSharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserPassword() {
        return mSharedPreferences.getString(PREF_KEY_CURRENT_USER_PASSWORD, null);
    }

    @Override
    public void setCurrentUserPassword(String password) {
        mSharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_PASSWORD, password).apply();
    }

    @Override
    public boolean getRememberPasswdState() {
        return Boolean.valueOf(mSharedPreferences.getString(PREF_KEY_REMEBER_PASSWORD_STATE, String.valueOf(false)));
    }

    @Override
    public void setRememberPasswdState(boolean state) {
        String stateString = String.valueOf(state);
        mSharedPreferences.edit().putString(PREF_KEY_REMEBER_PASSWORD_STATE,  stateString).apply();
    }

    @Override
    public String getVoiceValue(String key) {
        return mSharedPreferences.getString(key, null);
    }

    @Override
    public void insertVoiceCode(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void updateVoiceCode(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void deleteVoiceCode(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
