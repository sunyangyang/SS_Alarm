package com.clock.sunyangyang.ss;

import android.support.multidex.MultiDexApplication;

/**
 * Created by sunyangyang on 17/9/19.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashCache crashCache = CrashCache.getInstance();
        crashCache.setCustomCrash(this);
    }
}
