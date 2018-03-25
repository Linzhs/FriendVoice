package com.linzh.android.newfriendvoice.data;

import android.content.Context;

import com.linzh.android.newfriendvoice.data.db.DbHelper;
import com.linzh.android.newfriendvoice.data.db.model.User;
import com.linzh.android.newfriendvoice.data.prefs.PreferencesHelper;
import com.linzh.android.newfriendvoice.di.ApplicationContext;
import com.linzh.android.newfriendvoice.di.DatabaseInfo;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by linzh on 2018/3/25.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long id) {
        mPreferencesHelper.setCurrentUserId(id);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String name) {
        mPreferencesHelper.setCurrentUserName(name);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserPassword() {
        return mPreferencesHelper.getCurrentUserPassword();
    }

    @Override
    public void setCurrentUserPassword(String password) {
        mPreferencesHelper.setCurrentUserPassword(password);
    }

    @Override
    public boolean getRememberPasswdState() {
        return mPreferencesHelper.getRememberPasswdState();
    }

    @Override
    public void setRememberPasswdState(boolean state) {
        mPreferencesHelper.setRememberPasswdState(state);
    }

    @Override
    public String getVoiceValue(String key) {
        return mPreferencesHelper.getVoiceValue(key);
    }

    @Override
    public void insertVoiceCode(String key, String value) {
        mPreferencesHelper.insertVoiceCode(key, value);
    }

    @Override
    public void updateVoiceCode(String key, String value) {
        mPreferencesHelper.updateVoiceCode(key, value);
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<User> queryUser(Long id) {
        return mDbHelper.queryUser(id);
    }

    @Override
    public Observable<Void> updateUser(User user) {
        return mDbHelper.updateUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public void updateUserInfo(Long userId, String userName, String email, String passwd) {
        setCurrentUserId(userId);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserPassword(passwd);
    }

    @Override
    public Map<String, String> getAllVocieCodes() {
        return VoiceUtils.getVoiceCodes();
    }
}
