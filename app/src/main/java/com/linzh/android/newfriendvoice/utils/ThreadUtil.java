package com.linzh.android.newfriendvoice.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by linzh on 2018/4/2.
 */

public final class ThreadUtil {

    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private ThreadUtil() {
        // Nothing to do
    }
    private static void doInBackground() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private static void post() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
