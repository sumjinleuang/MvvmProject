package com.sumjin.peppymvvm.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.sumjin.peppymvvm.BaseApplication;

//Sp 工具 用来保存设置里的app选项
public class PreferenceManager {
    public static final String FILE_NAME = "share_peppy_data";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    private String SHARED_KEY_IS_LOGIN = "shared_key_is_login";
    private static String SHARED_KEY_AUTO_LOGIN = "shared_key_auto_login";

    private static String SHARED_KEY_CURRENTUSER_USERNAME = "SHARED_KEY_CURRENTUSER_USERNAME";
    private static String SHARED_KEY_CURRENTUSER_USER_PASSWORD = "SHARED_KEY_CURRENTUSER_USER_PASSWORD";
    private static String SHARED_KEY_CURRENTUSER_NICK = "SHARED_KEY_CURRENTUSER_NICK";
    private static String SHARED_KEY_CURRENTUSER_AVATAR = "SHARED_KEY_CURRENTUSER_AVATAR";

    private String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";
    private String SHARED_KEY_SETTING_SOUND = "shared_key_setting_sound";
    private String SHARED_KEY_SETTING_VIBRATE = "shared_key_setting_vibrate";
    private String SHARED_KEY_SETTING_SPEAKER = "shared_key_setting_speaker";

    private static String SHARED_KEY_MSG_ROAMING = "SHARED_KEY_MSG_ROAMING";
    private static String SHARED_KEY_SHOW_MSG_TYPING = "SHARED_KEY_SHOW_MSG_TYPING";

    @SuppressLint("CommitPrefEdits")
    private PreferenceManager(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context cxt){
        if(mPreferencemManager == null){
            mPreferencemManager = new PreferenceManager(cxt);
        }
    }

    /**
     * get instance of PreferenceManager
     *
     * @param
     * @return
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferencemManager;
    }


    public void setAccountIsLogin(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_IS_LOGIN, paramBoolean);
        editor.apply();
    }

    public boolean getAccountIsLogin() {
        return mSharedPreferences.getBoolean(SHARED_KEY_IS_LOGIN, false);
    }

    /**
     * 获取是否是自动登录
     * @return
     */
    public boolean getAutoLogin() {
        return mSharedPreferences.getBoolean(SHARED_KEY_AUTO_LOGIN, false);
    }

    /**
     * 设置是否自动登录,只有登录成功后，此值才能设置为true
     * @param autoLogin
     */
    public void setAutoLogin(boolean autoLogin) {
        editor.putBoolean(SHARED_KEY_AUTO_LOGIN, autoLogin);
        editor.commit();
    }

    public void setCurrentUserName(String username){
        editor.putString(SHARED_KEY_CURRENTUSER_USERNAME, username);
        editor.apply();
    }

    public String getCurrentUsername(){
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_USERNAME, null);
    }

    public void setCurrentUserPwd(String pwd) {
        editor.putString(SHARED_KEY_CURRENTUSER_USER_PASSWORD, pwd);
        editor.apply();
    }

    public String getCurrentUserPwd(){
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_USER_PASSWORD, null);
    }

    public void setCurrentUserNick(String nick) {
        editor.putString(SHARED_KEY_CURRENTUSER_NICK, nick);
        editor.apply();
    }

    public void setCurrentUserAvatar(String avatar) {
        editor.putString(SHARED_KEY_CURRENTUSER_AVATAR, avatar);
        editor.apply();
    }

    public String getCurrentUserNick() {
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_NICK, null);
    }

    public String getCurrentUserAvatar() {
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_AVATAR, null);
    }
    public void setSettingMsgNotification(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
        editor.apply();
    }

    public boolean getSettingMsgNotification() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION, true);
    }


    public void setSettingMsgSound(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_SOUND, paramBoolean);
        editor.apply();
    }

    public boolean getSettingMsgSound() {

        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SOUND, true);
    }

    public void setSettingMsgVibrate(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_VIBRATE, paramBoolean);
        editor.apply();
    }

    public boolean getSettingMsgVibrate() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_VIBRATE, true);
    }

    public void setSettingMsgSpeaker(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_SPEAKER, paramBoolean);
        editor.apply();
    }

    public boolean getSettingMsgSpeaker() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_SPEAKER, true);
    }

    public boolean isMsgRoaming() {
        return mSharedPreferences.getBoolean(SHARED_KEY_MSG_ROAMING, false);
    }

    public void setMsgRoaming(boolean isRoaming) {
        editor.putBoolean(SHARED_KEY_MSG_ROAMING, isRoaming);
        editor.apply();
    }


    public boolean isShowMsgTyping() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SHOW_MSG_TYPING, false);
    }

    public void showMsgTyping(boolean show) {
        editor.putBoolean(SHARED_KEY_SHOW_MSG_TYPING, show);
        editor.apply();
    }

}
