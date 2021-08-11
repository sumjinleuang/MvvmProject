package com.sumjin.peppymvvm.section.me.activity;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class SettleActivity extends BaseInitActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.item_account)
    public View mAccountSet;

    @BindView(R.id.item_msg_notify)
    public View mMsgSet;

    @BindView(R.id.item_privacy)
    public View mPrvSet;

    @BindView(R.id.item_help)
    public View mHelpSet;

    @BindView(R.id.item_product)
    public View mProductSet;

    @BindView(R.id.item_quit)
    public View mLoginSet;

    @Override
    protected void initPresenter() {
        mLoginSet.setOnClickListener(this);
        mProductSet.setOnClickListener(this);
        mHelpSet.setOnClickListener(this);
        mPrvSet.setOnClickListener(this);
        mMsgSet.setOnClickListener(this);
        mAccountSet.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_settle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_quit:
                //退出登录
                break;
            case R.id.item_product:
                //产品界面
                AboutPeppyActivity.actionStart(mContext);
                break;
            case R.id.item_help:
                //帮助界面
                HelpActivity.actionStart(mContext);
                break;
            case R.id.item_privacy:
                //隐私
                PrivateActivity.actionStart(mContext);
                break;
            case R.id.item_msg_notify:
                //消息通知
                MsgActivity.actionStart(mContext);
                break;
            case R.id.item_account:
                //账号与安全
                AccountActivity.actionStart(mContext);
                break;
        }
    }
}
