package com.linzh.android.newfriendvoice.ui;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.linzh.android.newfriendvoice.utils.FriendVoiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzh on 2018/3/19.
 */

public class ActivityCollector {

    private static List<Activity> sActivities = new ArrayList<>();

    private ActivityCollector() throws FriendVoiceException {
        throw new FriendVoiceException("Don't support this operator!");
    }

    public static void addActivity(Activity activity) {
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : sActivities) {
            activity.finish();
        }
    }
}
