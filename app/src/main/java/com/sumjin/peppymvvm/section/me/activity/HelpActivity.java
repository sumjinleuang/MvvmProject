package com.sumjin.peppymvvm.section.me.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class HelpActivity extends BaseInitActivity implements View.OnClickListener {

    @BindView(R.id.et_qq)
    public EditText mEtQq;
    @BindView(R.id.et_feedback)
    public EditText mEtFeedback;

    @BindView(R.id.btn_commit)
    public View mCommitBtn;


    public static void actionStart(Context context) {
        Intent starter = new Intent(context, HelpActivity.class);
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
        mCommitBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mEtQq.getText())) {
            ToastUtil.showToast("请输入QQ号码");
            return;
        }
        if (TextUtils.isEmpty(mEtFeedback.getText())) {
            ToastUtil.showToast("请描述一下问题或者好的建议哟");
            return;
        }
        //网络操作
        //隐藏键盘
        hideKeyboard();
    }
}
