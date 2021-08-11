package com.sumjin.peppymvvm.section.me.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.widget.ArrowItemView;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class PrivateActivity extends BaseInitActivity implements View.OnClickListener, EaseTitleBar.OnBackPressListener {

    @BindView(R.id.title_bar)
    public EaseTitleBar mTitleBar;


    @BindView(R.id.item_black_manager)
    public ArrowItemView mItemBlackManager;

    @BindView(R.id.item_equipment_manager)
    public ArrowItemView mItemEquipmentManager;

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, PrivateActivity.class);
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
        mTitleBar.setOnBackPressListener(this);
        mItemBlackManager.setOnClickListener(this);
        mItemEquipmentManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_black_manager :
                //黑名单界面
                //ContactBlackListActivity.actionStart(mContext);
                break;
            case R.id.item_equipment_manager :

                break;
        }
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_private;
    }
}
