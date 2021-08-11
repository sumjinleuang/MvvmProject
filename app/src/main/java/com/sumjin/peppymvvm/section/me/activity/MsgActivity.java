package com.sumjin.peppymvvm.section.me.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hyphenate.chat.EMPushManager;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.widget.ArrowItemView;
import com.sumjin.peppymvvm.common.widget.SwitchItemView;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class MsgActivity extends BaseInitActivity implements SwitchItemView.OnCheckedChangeListener, EaseTitleBar.OnBackPressListener, View.OnClickListener{
    @BindView(R.id.title_bar)
    public EaseTitleBar titleBar;
    @BindView(R.id.rl_switch_notification)
    public SwitchItemView rlSwitchNotification;
    @BindView(R.id.rl_switch_sound)
    public SwitchItemView rlSwitchSound;
    @BindView(R.id.rl_switch_vibrate)
    public SwitchItemView rlSwitchVibrate;
    @BindView(R.id.item_push_message_style)
    public ArrowItemView itemPushMessageStyle;

    private EMPushManager.DisplayStyle displayStyle;

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, MsgActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
    }

    @Override
    protected void initListener() {
        rlSwitchNotification.setOnCheckedChangeListener(this);
        rlSwitchSound.setOnCheckedChangeListener(this);
        rlSwitchVibrate.setOnCheckedChangeListener(this);
        itemPushMessageStyle.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_msg;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_push_message_style :
                MessagePushStyleActivity.actionStartForResult(mContext, displayStyle.ordinal(), 100);
                break;
        }
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onCheckedChanged(SwitchItemView buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rl_switch_notification ://接收新消息通知
                setSwitchVisible(isChecked);
               // model.setSettingMsgNotification(isChecked);
                break;
            case R.id.rl_switch_sound ://声音
               // model.setSettingMsgSound(isChecked);
                break;
            case R.id.rl_switch_vibrate ://震动
              //  model.setSettingMsgVibrate(isChecked);
                break;
        }
    }


    /**
     * 设置声音和震动的布局是否可见
     * @param isChecked
     */
    private void setSwitchVisible(boolean isChecked) {
        if(isChecked) {
            rlSwitchSound.setVisibility(View.VISIBLE);
            rlSwitchVibrate.setVisibility(View.VISIBLE);
        }else {
            rlSwitchSound.setVisibility(View.GONE);
            rlSwitchVibrate.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100) {
            //获取推送的相关设置
            //viewModel.getPushConfigs();
        }
    }
}
