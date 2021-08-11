package com.sumjin.peppymvvm.section.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.hyphenate.easecallkit.EaseCallKit;
import com.hyphenate.easecallkit.base.EaseCallType;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.sumjin.peppymvvm.DemoHelper;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.dialog.DemoDialogFragment;
import com.sumjin.peppymvvm.section.dialog.SimpleDialogFragment;

import java.util.List;

public class ContactDetailActivity extends BaseInitActivity implements View.OnClickListener, EaseTitleBar.OnBackPressListener {

    private EaseTitleBar mEaseTitleBar;
    private EaseImageView mAvatarUser;
    private TextView mTvName;
    private TextView mTvNote;
    private TextView mBtnChat;
    private TextView mBtnVoice;
    private TextView mBtnVideo;
    private TextView mBtnAddContact;
    private TextView mBtnRemoveBlack;
    private Group mGroupFriend;

    private EaseUser mUser;
    private boolean mIsFriend;
    private boolean mIsBlack;

    public static void actionStart(Context context, EaseUser user) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, EaseUser user, boolean isFriend) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("isFriend", isFriend);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.demo_activity_friends_contact_detail;
    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        mEaseTitleBar = findViewById(R.id.title_bar_contact_detail);
        mAvatarUser = findViewById(R.id.avatar_user);
        mTvName = findViewById(R.id.tv_name);
        mTvNote = findViewById(R.id.tv_note);
        mBtnChat = findViewById(R.id.btn_chat);
        mBtnVoice = findViewById(R.id.btn_voice);
        mBtnVideo = findViewById(R.id.btn_video);
        mBtnAddContact = findViewById(R.id.btn_add_contact);
        mGroupFriend = findViewById(R.id.group_friend);
        mBtnRemoveBlack = findViewById(R.id.btn_remove_black);

        if(mIsFriend) {
            mGroupFriend.setVisibility(View.VISIBLE);
            mBtnAddContact.setVisibility(View.GONE);
         //   EaseUser user = DemoHelper.getInstance().getModel().getContactList().get(mUser.getUsername());
         //   if(user != null && user.getContact() == 1) {
         //       mIsBlack = true;
         //       //如果在黑名单中
         //       mGroupFriend.setVisibility(View.GONE);
         //       mBtnRemoveBlack.setVisibility(View.VISIBLE);
         //       invalidateOptionsMenu();
         //   }
        }else {
            mGroupFriend.setVisibility(View.GONE);
            mBtnAddContact.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEaseTitleBar.setOnBackPressListener(this);
        mTvNote.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);
        mBtnVoice.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnAddContact.setOnClickListener(this);
        mBtnRemoveBlack.setOnClickListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return mIsFriend && !mIsBlack;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_friends_contact_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detail_delete:
                showDeleteDialog(mUser);
                break;
            case R.id.action_add_black :
                //viewModel.addUserToBlackList(mUser.getUsername(), false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mUser = (EaseUser) getIntent().getSerializableExtra("user");
        mIsFriend = getIntent().getBooleanExtra("isFriend", true);
       // if(!mIsFriend) {
       //     List<String> users = null;
       //     if(DemoDbHelper.getInstance(mContext).getUserDao() != null) {
       //         users = DemoDbHelper.getInstance(mContext).getUserDao().loadAllUsers();
       //     }
       //     mIsFriend = users != null && users.contains(mUser.getUsername());
       // }
    }

    private void showDeleteDialog(EaseUser user) {
        new SimpleDialogFragment.Builder(mContext)
                .setTitle(R.string.ease_friends_delete_contact_hint)
                .setOnConfirmClickListener(new DemoDialogFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(View view) {
                        //viewModel.deleteContact(user.getUsername());
                    }
                })
                .showCancelButton(true)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_note :
                ToastUtil.showToast("跳转到备注设置");
                break;
            case R.id.btn_chat :
                ChatActivity.actionStart(mContext, mUser.getUsername(), EaseConstant.CHATTYPE_SINGLE);
                break;
            case R.id.btn_voice :
                EaseCallKit.getInstance().startSingleCall(EaseCallType.SINGLE_VOICE_CALL,mUser.getUsername(),null);
                break;
            case R.id.btn_video :
                EaseCallKit.getInstance().startSingleCall(EaseCallType.SINGLE_VIDEO_CALL,mUser.getUsername(),null);
                break;
            case R.id.btn_add_contact :
                //addContactViewModel.addContact(mUser.getUsername(), getResources().getString(R.string.em_add_contact_add_a_friend));
                break;
            case R.id.btn_remove_black://从黑名单中移除
                removeBlack();
                break;
        }
    }

    private void removeBlack() {
        new SimpleDialogFragment.Builder(mContext)
                .setTitle(R.string.em_friends_move_out_the_blacklist_hint)
                .setOnConfirmClickListener(new DemoDialogFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(View view) {
                        //blackViewModel.removeUserFromBlackList(mUser.getUsername());
                    }
                })
                .showCancelButton(true)
                .show();
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }
}
