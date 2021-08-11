package com.sumjin.peppymvvm;


import android.content.Context;
import android.content.SharedPreferences;

import com.hyphenate.easeui.manager.EasePreferenceManager;
import com.sumjin.peppymvvm.common.util.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * DemoModel主要用于SP存取及一些数据库的存取
 */
public class DemoModel {
    protected Context context = null;
    protected Map<Key,Object> valueCache = new HashMap<Key,Object>();

    public DemoModel(Context ctx){
        context = ctx;
        PreferenceManager.init(context);
    }

    /**
     * save current username
     * @param username
     */
    public void setCurrentUserName(String username){
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    public String getCurrentUsername(){
        return PreferenceManager.getInstance().getCurrentUsername();
    }

    /**
     * 保存当前用户密码
     * 此处保存密码是为了查看多端设备登录是，调用接口不再输入用户名及密码，实际开发中，不可在本地保存密码！
     * 注：实际开发中不可进行此操作！！！
     * @param pwd
     */
    public void setCurrentUserPwd(String pwd) {
        PreferenceManager.getInstance().setCurrentUserPwd(pwd);
    }

    public String getCurrentUserPwd(){
        return PreferenceManager.getInstance().getCurrentUserPwd();
    }

    /**
     * 设置昵称
     * @param nickname
     */
    public void setCurrentUserNick(String nickname) {
        PreferenceManager.getInstance().setCurrentUserNick(nickname);
    }

    public String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    /**
     * 设置头像
     * @param avatar
     */
    private void setCurrentUserAvatar(String avatar) {
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }

    public void setSettingMsgNotification(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgNotification(paramBoolean);
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgNotification();
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }

        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSound(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSound(paramBoolean);
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgSound();
            valueCache.put(Key.PlayToneOn, val);
        }

        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgVibrate(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgVibrate(paramBoolean);
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgVibrate();
            valueCache.put(Key.VibrateOn, val);
        }

        return (Boolean) (val != null?val:true);
    }

    public void setSettingMsgSpeaker(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSpeaker(paramBoolean);
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    public boolean getSettingMsgSpeaker() {
        Object val = valueCache.get(Key.SpakerOn);

        if(val == null){
            val = PreferenceManager.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }

        return (Boolean) (val != null?val:true);
    }

    public boolean isMsgRoaming() {
        return PreferenceManager.getInstance().isMsgRoaming();
    }

    public void setMsgRoaming(boolean roaming) {
        PreferenceManager.getInstance().setMsgRoaming(roaming);
    }

    /**
     * 保存未发送的文本消息内容
     * @param toChatUsername
     * @param content
     */
    public void saveUnSendMsg(String toChatUsername, String content) {
        EasePreferenceManager.getInstance().saveUnSendMsgInfo(toChatUsername, content);
    }

    public String getUnSendMsg(String toChatUsername) {
        return EasePreferenceManager.getInstance().getUnSendMsgInfo(toChatUsername);
    }

    public boolean isShowMsgTyping() {
        return PreferenceManager.getInstance().isShowMsgTyping();
    }

    public void showMsgTyping(boolean show) {
        PreferenceManager.getInstance().showMsgTyping(show);
    }

    /**
     * 检查是否是第一次安装登录
     * 默认值是true, 需要在用api拉取完会话列表后，就其置为false.
     * @return
     */
    public boolean isFirstInstall() {
        SharedPreferences preferences = BaseApplication.getInstance().getSharedPreferences("first_install", Context.MODE_PRIVATE);
        return preferences.getBoolean("is_first_install", true);
    }

    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
