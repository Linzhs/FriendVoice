package com.linzh.android.newfriendvoice.data.prefs;

/**
 * Created by linzh on 2018/3/25.
 */

public interface PreferencesHelper {

    Long getCurrentUserId();

    void setCurrentUserId(Long id);

    String getCurrentUserName();

    void setCurrentUserName(String name);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserPassword();

    void setCurrentUserPassword(String password);

    boolean getRememberPasswdState();

    void setRememberPasswdState(boolean state);

    String getVoiceValue(String key);

    void insertVoiceCode(String key, String value);

    void updateVoiceCode(String key, String value);
}
