package com.sumjin.peppymvvm.section.me.activity;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

public class AuthorInActivity extends BaseInitActivity {
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_author;
    }
}
