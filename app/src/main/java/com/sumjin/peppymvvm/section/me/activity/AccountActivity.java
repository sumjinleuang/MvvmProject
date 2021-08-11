package com.sumjin.peppymvvm.section.me.activity;

import android.content.Context;
import android.content.Intent;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

public class AccountActivity extends BaseInitActivity {

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, AccountActivity.class);
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
    protected int getLayoutResId() {
        return R.layout.activity_account;
    }
}
