package com.sumjin.peppymvvm;

import android.app.Application;
import android.content.Context;

import com.hyphenate.util.EMLog;
import com.sumjin.peppymvvm.common.interfaceOrImplement.UserActivityLifecycleCallbacks;
import com.sumjin.peppymvvm.common.util.PreferenceManager;

public class BaseApplication extends Application {

    private static Context mContext;
    private static BaseApplication instance;
    private UserActivityLifecycleCallbacks mLifecycleCallbacks = new UserActivityLifecycleCallbacks();
    
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getBaseContext();
        instance = this;
        registerActivityLifecycleCallbacks();
        // 初始化PreferenceManager
        PreferenceManager.init(this);

        if(DemoHelper.getInstance().getAutoLogin()) {
            EMLog.i("DemoApplication", "application initHx");
            DemoHelper.getInstance().init(this);
        }

    }

    public static Context getContext(){
        return mContext;
    }

    public static BaseApplication getInstance() {return instance;}

    private void registerActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    public UserActivityLifecycleCallbacks getLifecycleCallbacks() {
        return mLifecycleCallbacks;
    }


}
