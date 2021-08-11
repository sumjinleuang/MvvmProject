package com.sumjin.peppymvvm.section.me.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.sumjin.peppymvvm.BuildConfig;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.widget.ArrowItemView;
import com.sumjin.peppymvvm.section.base.BaseActivity;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class AboutPeppyActivity extends BaseInitActivity implements EaseTitleBar.OnBackPressListener, View.OnClickListener {
    @BindView(R.id.title_bar)
    public EaseTitleBar mTitleBar;

    @BindView(R.id.tv_version)
    public TextView mTvVersion;

    @BindView(R.id.item_product)
    public ArrowItemView mItemProduct;

    @BindView(R.id.item_author)
    public ArrowItemView mItemAuthor;


    public static void actionStart(Context context) {
        Intent starter = new Intent(context, AboutPeppyActivity.class);
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
    protected void initData() {
        mTvVersion.setText(getString(R.string.em_about_peppy_version, BuildConfig.VERSION_NAME));
    }

    @Override
    protected void initListener() {
        mTitleBar.setOnBackPressListener(this);
        mItemProduct.setOnClickListener(this);
        mItemAuthor.setOnClickListener(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_peppy;
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_product :
                //产品介绍
                jumpToPeppyIntroduction();
                break;
            case R.id.item_author :
                //作者介绍
                jumpToAuthorIntroduction();
                break;
        }
    }

    private void jumpToAuthorIntroduction() {
       // startActivity(new Intent(this,AuthorInActivity.class));
    }

    private void jumpToPeppyIntroduction() {
        startActivity(new Intent(this,PeppyInActivity.class));
    }
}
