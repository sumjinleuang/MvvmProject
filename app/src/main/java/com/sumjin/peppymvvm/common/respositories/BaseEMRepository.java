package com.sumjin.peppymvvm.common.respositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMChatRoomManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.easeui.manager.EaseThreadManager;
import com.sumjin.peppymvvm.BaseApplication;
import com.sumjin.peppymvvm.DemoHelper;

public class BaseEMRepository {

    /**
     * return a new liveData
     * @param item
     * @param <T>
     * @return
     */
    public <T> LiveData<T> createLiveData(T item) {
        return new MutableLiveData<>(item);
    }

    /**
     * login before
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 获取本地标记，是否自动登录
     * @return
     */
    public boolean isAutoLogin() {
        return DemoHelper.getInstance().getAutoLogin();
    }

    /**
     * 获取当前用户
     * @return
     */
    public String getCurrentUser() {
        return DemoHelper.getInstance().getCurrentUser();
    }

    /**
     * EMChatManager
     * @return
     */
    public EMChatManager getChatManager() {
        return DemoHelper.getInstance().getEMClient().chatManager();
    }



    /**
     * EMGroupManager
     * @return
     */
    public EMGroupManager getGroupManager() {
        return DemoHelper.getInstance().getEMClient().groupManager();
    }



    /**
     * 在主线程执行
     * @param runnable
     */
    public void runOnMainThread(Runnable runnable) {
        EaseThreadManager.getInstance().runOnMainThread(runnable);
    }

    /**
     * 在异步线程
     * @param runnable
     */
    public void runOnIOThread(Runnable runnable) {
        EaseThreadManager.getInstance().runOnIOThread(runnable);
    }

    public Context getContext() {
        return BaseApplication.getInstance().getApplicationContext();
    }

}
