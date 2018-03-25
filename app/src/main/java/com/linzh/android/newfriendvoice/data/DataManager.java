package com.linzh.android.newfriendvoice.data;

import com.linzh.android.newfriendvoice.data.db.DbHelper;
import com.linzh.android.newfriendvoice.data.prefs.PreferencesHelper;

import java.util.Map;

/**
 * Created by linzh on 2018/3/20.
 */

public interface DataManager extends PreferencesHelper, DbHelper {

    void updateUserInfo(Long userId, String userName, String email, String passwd);

    Map<String, String> getAllVocieCodes();
}
