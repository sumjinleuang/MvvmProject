package com.sumjin.peppymvvm;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easecallkit.base.EaseCallKitListener;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.push.EMPushConfig;
import com.sumjin.peppymvvm.common.util.PreferenceManager;

import java.util.Map;

public class DemoHelper {
    private static final String TAG = DemoHelper.class.getSimpleName();

    public boolean isSDKInit;//SDK是否初始化
    private static DemoHelper mInstance;
    private DemoModel demoModel = null;  //数据库
    private Map<String, EaseUser> contactList;
    //private UserProfileManager userProManager; //头像

    private EaseCallKitListener callKitListener;
    private Context mianContext;
    private String tokenUrl = "http://a1-hsb.easemob.com/token/rtcToken";

    private DemoHelper() {}

    public static DemoHelper getInstance() {
        if(mInstance == null) {
            synchronized (DemoHelper.class) {
                if(mInstance == null) {
                    mInstance = new DemoHelper();
                }
            }
        }
        return mInstance;
    }

    public String getCurrentLoginUser() {
        return getModel().getCurrentUsername();
    }

    public String getCurrentUser() {
        return getEMClient().getCurrentUser();
    }

    /**
     * 获取本地标记，是否自动登录
     * @return
     */
    public boolean getAutoLogin() {
        return PreferenceManager.getInstance().getAutoLogin();
    }

    public void init(Context context) {
        //初始化IM SDK
        if(initSDK(context)) {
            // debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance().setDebugMode(true);
            // set Call options
           // setCallOptions(context);
           // //初始化推送
           // initPush(context);
           // //注册call Receiver
           // //initReceiver(context);
           // //初始化ease ui相关
           // initEaseUI(context);
           // //注册对话类型
           // registerConversationType();
//
           // //callKit初始化
           // InitCallKit(context);
        }

    }

    private boolean initSDK(Context context) {
        // 根据项目需求对SDK进行配置
        EMOptions options = initChatOptions(context);
        //配置自定义的rest server和im server
        //options.setRestServer("a1-hsb.easemob.com");
        //options.setIMServer("116.85.43.118");
        //options.setImPort(6717);
        // 初始化SDK
        isSDKInit = EaseIM.getInstance().init(context, options);
        mianContext = context;
        return isSDKInit();
    }

    /**
     * 根据自己的需要进行配置
     * @param context
     * @return
     */
    private EMOptions initChatOptions(Context context){
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // 设置是否自动接受加好友邀请,默认是true
        options.setAcceptInvitationAlways(false);
        // 设置是否需要接受方已读确认
        options.setRequireAck(true);
        // 设置是否需要接受方送达确认,默认false
        options.setRequireDeliveryAck(false);

        options.setUseRtcConfig(true);

        // 设置是否使用 fcm，有些华为设备本身带有 google 服务，
      // options.setUseFCM(demoModel.isUseFCM());

       // /**
       //  * NOTE:你需要设置自己申请的账号来使用三方推送功能，详见集成文档
       //  */
       // EMPushConfig.Builder builder = new EMPushConfig.Builder(context);
//
       // builder.enableVivoPush() // 需要在AndroidManifest.xml中配置appId和appKey
       //         .enableMeiZuPush("134952", "f00e7e8499a549e09731a60a4da399e3")
       //         .enableMiPush("2882303761517426801", "5381742660801")
       //         .enableOppoPush("0bb597c5e9234f3ab9f821adbeceecdb",
       //                 "cd93056d03e1418eaa6c3faf10fd7537")
       //         .enableHWPush() // 需要在AndroidManifest.xml中配置appId
       //         .enableFCM("921300338324");
       // options.setPushConfig(builder.build());
//


        String imServer = options.getImServer();
        String restServer = options.getRestServer();

       // // 设置是否允许聊天室owner离开并删除会话记录，意味着owner再不会受到任何消息
       // options.allowChatroomOwnerLeave(demoModel.isChatroomOwnerLeaveAllowed());
       // // 设置退出(主动和被动退出)群组时是否删除聊天消息
       // options.setDeleteMessagesAsExitGroup(demoModel.isDeleteMessagesAsExitGroup());
       // // 设置是否自动接受加群邀请
       // options.setAutoAcceptGroupInvitation(demoModel.isAutoAcceptGroupInvitation());
       // // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载
       // options.setAutoTransferMessageAttachments(demoModel.isSetTransferFileByUser());
       // // 是否自动下载缩略图，默认是true为自动下载
       // options.setAutoDownloadThumbnail(demoModel.isSetAutodownloadThumbnail());
        return options;
    }

    public boolean isSDKInit() {
        return isSDKInit;
    }

    /**
     * 获取IM SDK的入口类
     * @return
     */
    public EMClient getEMClient() {
        return EMClient.getInstance();
    }

    /**
     * 退出登录后，需要处理的业务逻辑
     */
    public void logoutSuccess() {
        Log.d(TAG, "logout: onSuccess");
        setAutoLogin(false);

    }

    /**
     * 设置本地标记，是否自动登录
     * @param autoLogin
     */
    public void setAutoLogin(boolean autoLogin) {
        PreferenceManager.getInstance().setAutoLogin(autoLogin);
    }

    public DemoModel getModel(){
        if(demoModel == null) {
            demoModel = new DemoModel(BaseApplication.getInstance());
        }
        return demoModel;
    }

    /**
     * 检查是否是第一次安装登录
     * 默认值是true, 需要在用api拉取完会话列表后，就其置为false.
     * @return
     */
    public boolean isFirstInstall() {
        return getModel().isFirstInstall();
    }



}
